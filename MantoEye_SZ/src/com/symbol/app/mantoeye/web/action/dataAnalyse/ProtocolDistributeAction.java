package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.common.CommonDimension;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.CommonDimensionManager;
import com.symbol.app.mantoeye.service.businessAnalyse.ProtocolDistributeManager;
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
import com.symbol.wp.tools.gtgrid.org.json.JSONObject;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

public class ProtocolDistributeAction extends BaseDispatchAction {
	@Autowired
	private ProtocolDistributeManager protocolDistributeManager;

	@Autowired
	private CommonDimensionManager commonDimensionManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String protocolType = "1";// 默认应用协议
	private String dataType_search = "1";// 默认GPRS
	private String timeLevel_search = "2";// 默认天
	private String time_search = CommonUtils.getSYestoday();// 默认昨天
	private String total_time_level;// 全网连接过来参数
	private String total_time_search;

	// 走势
	private String dataType_trend = "1";// GPRS
	private String timeLevel_trend = "1";// 小时
	private String startTime_trend = CommonUtils.getSYestoday() + " 00";// 开始时间
	private String endTime_trend = CommonUtils.getSYestoday() + " 23";// 结束时间
	private String tcpName_trend = "Wap2.0";

	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	/**
	 * 查询走势数据
	 */
	public void queryTrend() throws ServletException, IOException {

		try {
			List<CommonFlush> list = null;
			GridServerHandler gridServerHandler = new GridServerHandler(
					Struts2Utils.getRequest(), Struts2Utils.getResponse());
			logger.info("dataType_trend:" + dataType_trend
					+ " timeLevel_trend:" + timeLevel_trend
					+ " startTime_trend:" + startTime_trend + " endTime_trend:"
					+ endTime_trend + " apnName_trend:" + tcpName_trend);

			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = protocolDistributeManager.queryAllTrend(
						Integer.parseInt(protocolType),
						Common.StringToInt(dataType_trend),
						Common.StringToInt(timeLevel_trend), startTime_trend,
						endTime_trend, tcpName_trend).size();
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

			page = protocolDistributeManager.queryTrend(Integer
					.parseInt(protocolType), page, Common
					.StringToInt(dataType_trend), Common
					.StringToInt(timeLevel_trend), startTime_trend,
					endTime_trend, tcpName_trend);
			list = page.getResult();
			gridServerHandler.setData(list, CommonFlush.class);
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 数据导出
	 */
	public void exportTrend() throws ServletException, IOException {
		/*
		 * String dataTypeStr = request.getParameter("dataType"); if
		 * (dataTypeStr != null) { dataType = Common.StringToInt(dataTypeStr); }
		 */
		tcpName_trend = new String(tcpName_trend.getBytes("ISO-8859-1"),
				"UTF-8");
		logger.info("dataType_trend:" + dataType_trend + " timeLevel_trend:"
				+ timeLevel_trend + " startTime_trend:" + startTime_trend
				+ " endTime_trend:" + endTime_trend + " apnName_trend:"
				+ tcpName_trend);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<CommonFlush> list = null;
		list = protocolDistributeManager.queryAllTrend(Integer
				.parseInt(protocolType), Common.StringToInt(dataType_trend),
				Common.StringToInt(timeLevel_trend), startTime_trend,
				endTime_trend, tcpName_trend);
		String exportmsg = ExportMsg.EXPORT_TREND_TCP
				+ Common.getTimeLevelCH(timeLevel_search) + "（" + time_search
				+ "）";
		if (protocolType.equals("1")) {
			exportmsg = ExportMsg.EXPORT_TREND_WAP
					+ Common.getTimeLevelCH(timeLevel_search) + "（"
					+ time_search + "）";
		}
		commonManagerImpl.log(this.getServletRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	/**
	 * 获取图形数据
	 */
	public void getAjaxTrendChartData() throws Exception {
		List<CommonFlush> list = null;
		list = protocolDistributeManager.queryAllTrend(Integer
				.parseInt(protocolType), Common.StringToInt(dataType_trend),
				Common.StringToInt(timeLevel_trend), startTime_trend,
				endTime_trend, tcpName_trend);
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
		// 判断是否从全网跳转到协议页面
		if (!"null".equals(total_time_level)) {
			timeLevel_search = total_time_level;
			time_search = total_time_search;
		}
		List<CommonFlush> list = protocolDistributeManager.listData(Integer
				.parseInt(protocolType), Common.StringToInt(dataType_search),
				Common.StringToInt(timeLevel_search), time_search);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		if (list == null) {
			list = new ArrayList<CommonFlush>();
		}
		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		List<CommonFlush> list = null;
		list = protocolDistributeManager.listData(Integer
				.parseInt(protocolType), Common.StringToInt(dataType_search),
				Common.StringToInt(timeLevel_search), time_search);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = ExportMsg.EXPORT_FLUSH_TCP
				+ Common.getTimeLevelCH(timeLevel_search) + "（" + time_search
				+ "）";
		if (protocolType.equals("1")) {
			exportmsg = ExportMsg.EXPORT_FLUSH_WAP
					+ Common.getTimeLevelCH(timeLevel_search) + "（"
					+ time_search + "）";
		}
		commonManagerImpl.log(this.getServletRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public void getAllWap() {
		List<CommonDimension> list = commonDimensionManager.findAllWap();
		JSONObject json = new JSONObject();
		json.put("datalist", JSONUtils.BeanList2JSONArray(list,
				CommonDimension.class));
		Struts2Utils.renderJson(json.toString());
	}

	public void getAllTcp() {
		List<CommonDimension> list = commonDimensionManager.findAllTcp();
		JSONObject json = new JSONObject();
		json.put("datalist", JSONUtils.BeanList2JSONArray(list,
				CommonDimension.class));
		Struts2Utils.renderJson(json.toString());
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

	public String getTcpName_trend() {
		return tcpName_trend;
	}

	public void setTcpName_trend(String tcpNameTrend) {
		tcpName_trend = tcpNameTrend;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
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

	public String getTotal_time_level() {
		return total_time_level;
	}

	public void setTotal_time_level(String total_time_level) {
		this.total_time_level = total_time_level;
	}

	public String getTotal_time_search() {
		return total_time_search;
	}

	public void setTotal_time_search(String total_time_search) {
		this.total_time_search = total_time_search;
	}

	public Page<CommonFlush> getPage() {
		return page;
	}

	public void setPage(Page<CommonFlush> page) {
		this.page = page;
	}

}
