package com.symbol.app.mantoeye.web.action.groupBisness;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.CommonDimensionManager;
import com.symbol.app.mantoeye.service.gruopBisness.GroupBusinessUserBeLongManager;
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

public class GroupBusinessUserBeLongAction extends BaseDispatchAction {
	@Autowired
	private GroupBusinessUserBeLongManager groupBusinessUserBeLongManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	@Autowired
	private CommonDimensionManager commonDimensionManager;

	private String timeLevel = "2";// 时间周期 2:天
	private String searchDateStart = CommonUtils.getSYestoday();// 查询时间
	private int dataType = 1;
	private String apnName = "SZPSJL.GD";

	// 走势
	private String dataType_trend = "1";// GPRS
	private String timeLevel_trend = "1";// 小时
	private String startTime_trend = CommonUtils.getSYestoday() + " 00";// 开始时间
	private String endTime_trend = CommonUtils.getSYestoday() + " 23";// 结束时间
	private String apnName_trend = "SZPSJL.GD";
	private String belongId_trend = "1";
	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	/**
	 * 查询走势数据
	 */
	public void queryTrend() throws ServletException, IOException {
		List<CommonFlush> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		logger.info("dataType_trend:" + dataType_trend + " timeLevel_trend:"
				+ timeLevel_trend + " startTime_trend:" + startTime_trend
				+ " endTime_trend:" + endTime_trend + " apnName_trend:"
				+ apnName_trend + belongId_trend);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = groupBusinessUserBeLongManager.queryAllTrend(Common
					.StringToInt(dataType_trend), Common
					.StringToInt(timeLevel_trend), startTime_trend, endTime_trend,
					apnName_trend, belongId_trend).size();
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
		page = groupBusinessUserBeLongManager.queryTrend(page, Common
				.StringToInt(dataType_trend), Common
				.StringToInt(timeLevel_trend), startTime_trend, endTime_trend,
				apnName_trend, belongId_trend);
//		int totalRowNum = gridServerHandler.getTotalRowNum();
//		if (totalRowNum < 1) {
//			totalRowNum = page.getTotalCount();
//			gridServerHandler.setTotalRowNum(totalRowNum);
//		}
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
		belongId_trend = new String(belongId_trend.getBytes("ISO-8859-1"),
				"UTF-8");
		logger.info("dataType_trend:" + dataType_trend + " timeLevel_trend:"
				+ timeLevel_trend + " startTime_trend:" + startTime_trend
				+ " endTime_trend:" + endTime_trend + " belongName_trend:"
				+ belongId_trend);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<CommonFlush> list = null;
		list = groupBusinessUserBeLongManager.queryAllTrend(Common
				.StringToInt(dataType_trend), Common
				.StringToInt(timeLevel_trend), startTime_trend, endTime_trend,
				apnName_trend, belongId_trend);
		String exportmsg = ExportMsg.EXPORT_GROUPTREND_BELONGE
				+ Common.getTimeLevelCH(timeLevel_trend) + "（"
				+ startTime_trend + "~" + endTime_trend + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	/**
	 * 获取图形数据
	 */
	public void getAjaxTrendChartData() throws Exception {
		List<CommonFlush> list = null;
		list = groupBusinessUserBeLongManager.queryAllTrend(Common
				.StringToInt(dataType_trend), Common
				.StringToInt(timeLevel_trend), startTime_trend, endTime_trend,
				apnName_trend, belongId_trend);
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
		/*
		 * String dataTypeStr=request.getParameter("dataType");
		 * if(dataTypeStr!=null){ dataType=Common.StringToInt(dataTypeStr); }
		 */
		list = groupBusinessUserBeLongManager.queryUserAttachDistribute(Common
				.StringToInt(timeLevel), searchDateStart, dataType, apnName);
		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());

	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		/*
		 * String dataTypeStr = request.getParameter("dataType"); if
		 * (dataTypeStr != null) { dataType = Common.StringToInt(dataTypeStr); }
		 */

		List<CommonFlush> list = groupBusinessUserBeLongManager
				.queryUserAttachDistribute(Common.StringToInt(timeLevel),
						searchDateStart, dataType, apnName);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = ExportMsg.EXPORT_GROUPFLUSH_BELONGE
				+ Common.getTimeLevelCH(timeLevel) + "（" + searchDateStart
				+ "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public String getTimeLevel() {
		return timeLevel;
	}

	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

	public String getSearchDateStart() {
		return searchDateStart;
	}

	public void setSearchDateStart(String searchDateStart) {
		this.searchDateStart = searchDateStart;
	}

	public void getAllApnName() {
		String allApn = groupBusinessUserBeLongManager.getAllApnName();
		Struts2Utils.renderText(allApn);
	}

	public String getApnName() {
		return apnName;
	}

	public void setApnName(String apnName) {
		this.apnName = apnName;
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

	public String getBelongId_trend() {
		return belongId_trend;
	}

	public void setBelongId_trend(String belongIdTrend) {
		belongId_trend = belongIdTrend;
	}

}
