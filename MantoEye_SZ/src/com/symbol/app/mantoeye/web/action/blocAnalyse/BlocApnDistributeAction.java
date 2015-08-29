package com.symbol.app.mantoeye.web.action.blocAnalyse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.constants.SessionConstants;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.dto.flush.CommonFlushComparator;
import com.symbol.app.mantoeye.service.businessAnalyse.BlocApnDistributeManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.org.json.JSONArray;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

public class BlocApnDistributeAction extends BaseDispatchAction {
	@Autowired
	private BlocApnDistributeManager blocApnDistributeManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String dataType_search = "1";
	private String timeLevel_search = "2";

	private String time_search = CommonUtils.getSYestoday();
	private String apnType = "3";

	// 走势
	private String dataType_trend = "1";// GPRS
	private String timeLevel_trend = "1";// 小时
	private String startTime_trend = CommonUtils.getSYestoday() + " 00";// 开始时间
	private String endTime_trend = CommonUtils.getSYestoday() + " 23";// 结束时间
	private String apnName_trend = "SZPSJL.GD";

	/**
	 * 查询走势数据
	 */
	public void queryTrend() throws ServletException, IOException {

		List<CommonFlush> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		logger.info("dataType_trend:" + dataType_trend + " timeLevel_trend:"
				+ timeLevel_trend + " startTime_trend:" + startTime_trend
				+ " endTime_trend:" + endTime_trend + " apnName_trend:");

		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = blocApnDistributeManager.queryAllTrend(
					Common.StringToInt(dataType_trend),
					Common.StringToInt(timeLevel_trend), startTime_trend,
					endTime_trend, apnName_trend).size();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			page.setOrder(order);
			page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));

		} else {
			page.setOrderBy("fullDate");
			page.setOrder("asc");
		}
		page = blocApnDistributeManager.queryTrend(page, Common
				.StringToInt(dataType_trend), Common
				.StringToInt(timeLevel_trend), startTime_trend, endTime_trend,
				apnName_trend);
		list = page.getResult();
		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 数据导出
	 */
	public void exportTrend() throws ServletException, IOException {
		/*
		 * String dataTypeStr = request.getParameter("dataType"); if
		 * (dataTypeStr != null) { dataType = Common.StringToInt(dataTypeStr); }
		 */
		apnName_trend = new String(apnName_trend.getBytes("ISO-8859-1"),
				"UTF-8");
		logger.info("dataType_trend:" + dataType_trend + " timeLevel_trend:"
				+ timeLevel_trend + " startTime_trend:" + startTime_trend
				+ " endTime_trend:" + endTime_trend + " apnName_trend:");
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<CommonFlush> list = null;
		list = blocApnDistributeManager.queryAllTrend(Common
				.StringToInt(dataType_trend), Common
				.StringToInt(timeLevel_trend), startTime_trend, endTime_trend,
				apnName_trend);
		String exportmsg = ExportMsg.EXPORT_GROUPTREND_DISC
				+ Common.getTimeLevelCH(timeLevel_search) + "（"
				+ startTime_trend + "~" + endTime_trend + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	/**
	 * 获取图形数据
	 */
	public void getAjaxTrendChartData() throws Exception {
		List<CommonFlush> list = null;
		list = blocApnDistributeManager.queryAllTrend(Common
				.StringToInt(dataType_trend), Common
				.StringToInt(timeLevel_trend), startTime_trend, endTime_trend,
				apnName_trend);
		if (list != null && list.size() > 0) {
		} else {
			list = new ArrayList<CommonFlush>();
		}
		JSONArray ja = JSONUtils.BeanList2JSONArray(list, CommonFlush.class);
		Struts2Utils.renderJson(ja.toString());
	}

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		List<CommonFlush> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		list = blocApnDistributeManager.listData(Integer
				.parseInt(dataType_search), Common.StringToInt(apnType),
				Integer.parseInt(timeLevel_search), time_search);
		if (list == null) {
			list = new ArrayList<CommonFlush>();
		}
		String sxml = buildChartStr(list);
		this.getServletRequest().getSession().setAttribute(
				SessionConstants.SESSION_DATA_GROUP_FLUSH, sxml);
		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void getAjaxChartXmlData() throws Exception {
		String xml = this.getServletRequest().getSession().getAttribute(
				SessionConstants.SESSION_DATA_GROUP_FLUSH)
				+ "";
		Struts2Utils.renderText(xml);
	}

	/**
	 * 获取图形数据
	 */
	public String buildChartStr(List<CommonFlush> list) {
		// 查询数据
		// List<CommonFlush> list = blocApnDistributeManager.listData(Integer
		// .parseInt(dataType_search), Common.StringToInt(apnType), Integer
		// .parseInt(timeLevel_search), time_search);
		// 将数据放入map 集合
		Map<String, CommonFlush> map = new HashMap<String, CommonFlush>();
		if (list != null && !list.isEmpty()) {
			for (CommonFlush c : list) {
				map.put(c.getBusinessName(), c);
			}
		}
		// 按map集合规定的排序进行排序
		List<CommonFlush> totalSortList = new ArrayList<CommonFlush>(map
				.values());
		CommonFlushComparator com1 = new CommonFlushComparator(1, 2);// 流量降序
		Collections.sort(totalSortList, com1);
		List<CommonFlush> imsisSortList = new ArrayList(map.values());
		CommonFlushComparator com2 = new CommonFlushComparator(2, 2);// 用户数降序
		Collections.sort(imsisSortList, com2);
		List<CommonFlush> countSortList = new ArrayList(map.values());
		CommonFlushComparator com3 = new CommonFlushComparator(3, 2);// 激活次数降序
		Collections.sort(countSortList, com3);

		// 遍历得到总流量 总用户数 总激活次数
		Double totalFlush = 0D;
		long totalImis = 0l;
		long totalCount = 0l;
		if (!totalSortList.isEmpty()) {
			for (int i = 0; i < totalSortList.size(); i++) {
				CommonFlush c = totalSortList.get(i);
				totalFlush = totalFlush + c.getTotalFlushMB();
				totalImis = totalImis + c.getIntImsis();
				totalCount = totalCount + c.getActiveCount();
			}
		}

		// 组装图形数据
		String xml = "<root>";
		String totalXml = "";
		String imsisXml = "";
		String countXml = "";
		if (!totalSortList.isEmpty()) {
			int size = totalSortList.size() > 10 ? 10 : totalSortList.size();// 获取前10条记录，剩下的作为其他
			for (int i = 0; i < size; i++) {
				CommonFlush totalC = totalSortList.get(i);// 流量前10
				CommonFlush imsisC = imsisSortList.get(i);// 用户数前10
				CommonFlush countC = countSortList.get(i);// 激活次数前10
				totalXml = totalXml + "<data label=\""
						+ totalC.getBusinessName() + "\" value=\""
						+ totalC.getTotalFlushMB() + "\"/>";

				imsisXml = imsisXml + "<data label=\""
						+ imsisC.getBusinessName() + "\" value=\""
						+ imsisC.getIntImsis() + "\"/>";

				countXml = countXml + "<data label=\""
						+ countC.getBusinessName() + "\" value=\""
						+ countC.getActiveCount() + "\"/>";

				totalFlush = totalFlush - totalC.getTotalFlushMB();
				totalImis = totalImis - imsisC.getIntImsis();
				totalCount = totalCount - countC.getActiveCount();
			}
			if (size == 10) {// 其它
				String otherName = "非前10总计";
				totalXml = totalXml + "<data label=\"" + otherName
						+ "\" value=\"" + totalFlush + "\"/>";
				imsisXml = imsisXml + "<data label=\"" + otherName
						+ "\" value=\"" + totalImis + "\"/>";
				countXml = countXml + "<data label=\"" + otherName
						+ "\" value=\"" + totalCount + "\"/>";
			}
		}
		if (Common.StringToInt(timeLevel_search) == 3) {
			time_search = CommonUtils.getDayInnerWeek(time_search);
		}
		totalXml = "<chart id=\"1\" name=\"" + time_search + "集团APN流量分布图\">"
				+ totalXml + "</chart>";
		imsisXml = "<chart id=\"2\" name=\"" + time_search + "集团APN用户数分布图\">"
				+ imsisXml + "</chart>";
		countXml = "<chart id=\"3\" name=\"" + time_search + "集团APN激活次数分布图\">"
				+ countXml + "</chart>";
		xml = xml + totalXml + imsisXml + countXml;
		xml = xml + "</root>";
		return xml;
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		List<CommonFlush> list = blocApnDistributeManager.listData(Integer
				.parseInt(dataType_search), Common.StringToInt(apnType),
				Integer.parseInt(timeLevel_search), time_search);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = ExportMsg.EXPORT_GROUPFLUSH_DISC
				+ Common.getTimeLevelCH(timeLevel_search) + "（" + time_search
				+ "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public Page<CommonFlush> getPage() {
		return page;
	}

	public void setPage(Page<CommonFlush> page) {
		this.page = page;
	}

	public String getDataType_search() {
		return dataType_search;
	}

	public void setDataType_search(String dataType_search) {
		this.dataType_search = dataType_search;
	}

	public String getTimeLevel_search() {
		return timeLevel_search;
	}

	public void setTimeLevel_search(String timeLevel_search) {
		this.timeLevel_search = timeLevel_search;
	}

	public String getTime_search() {
		return time_search;
	}

	public void setTime_search(String time_search) {
		this.time_search = time_search;
	}

	public String getApnType() {
		return apnType;
	}

	public void setApnType(String apnType) {
		this.apnType = apnType;
	}

	public String getDataType_trend() {
		return dataType_trend;
	}

	public void setDataType_trend(String dataTypeTrend) {
		dataType_trend = dataTypeTrend;
	}

	public String getTimeLevel_trend() {
		return timeLevel_trend;
	}

	public void setTimeLevel_trend(String timeLevelTrend) {
		timeLevel_trend = timeLevelTrend;
	}

	public String getStartTime_trend() {
		return startTime_trend;
	}

	public void setStartTime_trend(String startTimeTrend) {
		startTime_trend = startTimeTrend;
	}

	public String getEndTime_trend() {
		return endTime_trend;
	}

	public void setEndTime_trend(String endTimeTrend) {
		endTime_trend = endTimeTrend;
	}

	public String getApnName_trend() {
		return apnName_trend;
	}

	public void setApnName_trend(String apnNameTrend) {
		apnName_trend = apnNameTrend;
	}

}
