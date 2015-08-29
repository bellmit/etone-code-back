package com.symbol.app.mantoeye.web.action.mms;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.mms.MmsTimeLevelManager;
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

public class MmsTimeLevelAction extends BaseDispatchAction {

	@Autowired
	private MmsTimeLevelManager mmsTimeLevelManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String dataType_search = "1";// GPRS
	private String timeLevel_search = "2";// 天
	private String startTime_search = CommonUtils.getPerThityDay();// 开始时间
	private String endTime_search = CommonUtils.getSYestoday();// 结束时间

	/**
	 * 查询数据
	 */
	public void query() throws ServletException, IOException {
		List<CommonFlush> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			if ("defaultsort".equals(order)) {
				page.setOrder("asc");
				page.setOrderBy("intYear");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils
						.getMmsSortField(si.getFieldName()));
			}
			// 默认排序方式
		} else {
			page.setOrderBy("intYear");
			page.setOrder("asc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		page = mmsTimeLevelManager.query(page, Integer
				.parseInt(dataType_search), Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = page.getTotalCount();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		list = page.getResult();
		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 获取图形数据
	 */
	public void getAjaxChartData() throws Exception {
		try {
			List<CommonFlush> list = mmsTimeLevelManager.listData(Integer
					.parseInt(dataType_search), Common
					.StringToInt(timeLevel_search), startTime_search,
					endTime_search);

			// if(list!=null&&list.size()>0){
			JSONArray ja = JSONUtils
					.BeanList2JSONArray(list, CommonFlush.class);
			// logger.info("json:"+ja.toString());
			Struts2Utils.renderJson(ja.toString());
			// }else{
			// Struts2Utils.renderJson("");
			// }
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 获取图形数据
	 */
	public void getAjaxChartXmlData() throws Exception {
		List<CommonFlush> list = mmsTimeLevelManager.listData(Integer
				.parseInt(dataType_search), Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search);

		String chartLabel = "";
		if (Common.StringToInt(timeLevel_search) == 4) {
			chartLabel = startTime_search.substring(0, startTime_search
					.lastIndexOf("-"))
					+ " 至 "
					+ endTime_search.substring(0, endTime_search
							.lastIndexOf("-"));
		} else {
			chartLabel = startTime_search + " 至 " + endTime_search;
		}

		chartLabel = chartLabel + " 彩信时段统计发送量分布图";
		String head = "<root><chart id=\"1\" name=\"\" fields=\"发送量|\">";
		String body = "";
		String xml = "";
		if (list != null && !list.isEmpty()) {
			for (CommonFlush c : list) {
				String shortLabel = "";
				switch (Common.StringToInt(timeLevel_search)) {
				case 1:
					shortLabel = c.getIntHour() + "";
					break;
				case 2:
					shortLabel = c.getIntDay() + "";
					break;
				case 3:
					shortLabel = c.getIntWeek() + "";
					break;
				case 4:
					shortLabel = c.getIntMonth() + "";
					break;
				}
				body = body + "<data label=\"" + c.getFullDate()
						+ "\" shortlabel = \"" + shortLabel + "\" total=\""
						+ c.getTotalSendFlush() + "\"/>";
			}
		}
		if (list != null && !list.isEmpty()) {
			xml = "<root><chart id=\"1\" name=\"" + chartLabel
					+ "\" fields=\"\">" + body;
		} else {
			xml = head;
		}

		xml = xml + "</chart></root>";
		Struts2Utils.renderText(xml);
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		List<CommonFlush> list = mmsTimeLevelManager.listData(Integer
				.parseInt(dataType_search), Common
				.StringToInt(timeLevel_search), startTime_search,
				endTime_search);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = ExportMsg.EXPORT_MMS_FLUSHHOUR
				+ Common.getTimeLevelCH(timeLevel_search) + "（"
				+ startTime_search + "~" + endTime_search + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public Page<CommonFlush> getPage() {
		return page;
	}

	public void setPage(Page<CommonFlush> page) {
		this.page = page;
	}

	public MmsTimeLevelManager getMmsTimeLevelManager() {
		return mmsTimeLevelManager;
	}

	public void setMmsTimeLevelManager(MmsTimeLevelManager mmsTimeLevelManager) {
		this.mmsTimeLevelManager = mmsTimeLevelManager;
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

	public String getStartTime_search() {
		return startTime_search;
	}

	public void setStartTime_search(String startTime_search) {
		this.startTime_search = startTime_search;
	}

	public String getEndTime_search() {
		return endTime_search;
	}

	public void setEndTime_search(String endTime_search) {
		this.endTime_search = endTime_search;
	}

}
