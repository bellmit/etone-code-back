package com.symbol.app.mantoeye.web.action.groupBisness;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.constants.SessionConstants;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.gruopBisness.GroupBisnessAreaDistriburteManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.DataFormatUtils;
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

public class GroupBisAreaDistributeAction extends BaseDispatchAction {

	@Autowired
	private GroupBisnessAreaDistriburteManager groupBisnessAreaDistriburteManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private Page<CommonFlush> page = new Page<CommonFlush>(
			VarConstants.PAGE_LONG_ROW_SIZE);

	private String timeLevel_search = "2";// 时间周期 2:天
	private String time_search = CommonUtils.getSYestoday();// 查询时间
	private String apnName = "SZPSJL.GD";// 业务
	private int dataType = 1;
	private String spaceLevel_search = "1";

	// 走势
	private String dataType_trend = "1";// GPRS
	private String timeLevel_trend = "1";// 小时
	private String startTime_trend = CommonUtils.getSYestoday() + " 00";// 开始时间
	private String endTime_trend = CommonUtils.getSYestoday() + " 23";// 结束时间
	private String areaType_trend = "1";// BSC
	private String apnName_trend = "SZPSJL.GD";
	private String areaName_trend = "SZ27B";

	/**
	 * 查询走势数据
	 */
	public void queryTrend() throws ServletException, IOException {

		List<CommonFlush> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		logger.info("dataType_trend:" + dataType_trend + " timeLevel_trend:"
				+ timeLevel_trend + " startTime_trend:" + startTime_trend
				+ " endTime_trend:" + endTime_trend + " areaType_trend:"
				+ areaType_trend + " apnName_trend:" + apnName_trend
				+ areaName_trend);
		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = groupBisnessAreaDistriburteManager.queryAllTrend(Common
					.StringToInt(dataType_trend), Common
					.StringToInt(areaType_trend), Common
					.StringToInt(timeLevel_trend), startTime_trend, endTime_trend,
					apnName_trend, areaName_trend).size();
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
		page = groupBisnessAreaDistriburteManager.queryTrend(page, Common
				.StringToInt(dataType_trend), Common
				.StringToInt(areaType_trend), Common
				.StringToInt(timeLevel_trend), startTime_trend, endTime_trend,
				apnName_trend, areaName_trend);
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
		areaName_trend = new String(areaName_trend.getBytes("ISO-8859-1"),
				"UTF-8");
		logger.info("dataType_trend:" + dataType_trend + " timeLevel_trend:"
				+ timeLevel_trend + " startTime_trend:" + startTime_trend
				+ " endTime_trend:" + endTime_trend + " areaType_trend:"
				+ areaType_trend + " apnName_trend:" + apnName_trend
				+ areaName_trend);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<CommonFlush> list = null;
		list = groupBisnessAreaDistriburteManager.queryAllTrend(Common
				.StringToInt(dataType_trend), Common
				.StringToInt(areaType_trend), Common
				.StringToInt(timeLevel_trend), startTime_trend, endTime_trend,
				apnName_trend, areaName_trend);
		String exportmsg = ExportMsg.EXPORT_GROUPTREND_SPACE
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
		list = groupBisnessAreaDistriburteManager.queryAllTrend(Common
				.StringToInt(dataType_trend), Common
				.StringToInt(areaType_trend), Common
				.StringToInt(timeLevel_trend), startTime_trend, endTime_trend,
				apnName_trend, areaName_trend);
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

		// SortInfo si = gridServerHandler.getSingleSortInfo();
		// if (si != null) {
		// String order = si.getSortOrder();
		// /**
		// * grid控件排序有3种情况：asc desc 和 第一次加载数据
		// * 事实上可以更改源码去掉defaultsort,但改后第一次加载数据不存在,故通过后台管理
		// */
		// if ("defaultsort".equals(order)) {
		// page.setOrder("desc");
		// page.setOrderBy("nmFlush");
		// } else {
		// page.setOrder(order);
		// page.setOrderBy(CommonUtils.getSortField(si.getFieldName()));
		// }
		// // 默认排序方式
		// } else {
		// page.setOrderBy("nmFlush");
		// page.setOrder("desc");
		// }
		// page.setPageSize(gridServerHandler.getPageSize());
		// page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		// // int isTD = 0;// TD标识
		// page = groupBisnessAreaDistriburteManager.query(page, dataType,
		// Common
		// .StringToInt(spaceLevel_search), Common
		// .StringToInt(timeLevel_search), time_search, apnName);
		// int totalRowNum = gridServerHandler.getTotalRowNum();
		// if (totalRowNum < 1) {
		// totalRowNum = page.getTotalCount();
		// gridServerHandler.setTotalRowNum(totalRowNum);
		// }
		// list = page.getResult();

		page.setOrderBy("nmFlush");
		page.setOrder("desc");
		list = groupBisnessAreaDistriburteManager.listData(page, dataType,
				Common.StringToInt(spaceLevel_search), Common
						.StringToInt(timeLevel_search), time_search, apnName);

		String sxml = buildChartStr(list);
		this.getServletRequest().getSession().setAttribute(
				SessionConstants.SESSION_DATA_GROUP_SPACE, sxml);

		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void getAjaxChartXmlData() throws Exception {
		String xml = this.getServletRequest().getSession().getAttribute(
				SessionConstants.SESSION_DATA_GROUP_SPACE)
				+ "";
		Struts2Utils.renderText(xml);
	}

	/**
	 * 获取图形数据
	 */
	public String buildChartStr(List<CommonFlush> list) {
		// GridServerHandler gridServerHandler = new GridServerHandler(
		// Struts2Utils.getRequest(), Struts2Utils.getResponse());
		// SortInfo si = gridServerHandler.getSingleSortInfo();
		// if (si != null) {
		// String order = si.getSortOrder();
		// /**
		// * grid控件排序有3种情况：asc desc 和 第一次加载数据
		// * 事实上可以更改源码去掉defaultsort,但改后第一次加载数据不存在,故通过后台管理
		// */
		// if ("defaultsort".equals(order)) {
		// page.setOrder("desc");
		// page.setOrderBy("nmFlush");
		// } else {
		// page.setOrder(order);
		// page.setOrderBy(CommonUtils.getSortField(si.getFieldName()));
		// }
		// // 默认排序方式
		// } else {
		// page.setOrderBy("nmFlush");
		// page.setOrder("desc");
		// }
		// page.setPageSize(gridServerHandler.getPageSize());
		// page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		// // int isTD = 0;// TD标识
		//
		// List<CommonFlush> list = groupBisnessAreaDistriburteManager.listData(
		// page, dataType, Common.StringToInt(spaceLevel_search), Common
		// .StringToInt(timeLevel_search), time_search, apnName);

		int chaertType = Common.StringToInt(spaceLevel_search);
		String name = "";
		switch (chaertType) {
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BSC:
			name = "BSC";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN:
			name = "SGSN";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_STREET:
			name = "街道办";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA:
			name = "营销片区";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BRANCH:
			name = "分公司";
			break;
		}
		String time = time_search;
		if (Common.StringToInt(timeLevel_search) == 3) {
			time = CommonUtils.getDayInnerWeek(time_search);
		}
		String chartTitle = "\"" + time + " " + name + "{dataname}分布\"";
		// 去掉名称为其他的数据
		list = DataFormatUtils.exceptCommonFlush(list, "其他");
		// 根据数量级获取流量的单位，防止数量太大
		String flushUnit = DataFormatUtils.getFlushUnit(list);
		String imsisUnit = DataFormatUtils.getImsisUnit(list);

		logger.info("flushUnit:" + flushUnit + "--imsisUnit:" + imsisUnit);

		String xml = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" + "<Data>"
				+ "<Stat id=\"\" unit=\"" + flushUnit + "|" + imsisUnit
				+ "\" tipunit=\"\" title=" + chartTitle + " trip=\"流量用户数统计{br}"
				+ name + "：{name}{br}总流量：{total}" + flushUnit + "{br}上行流量：{up}"
				+ flushUnit + "{br}下行流量：{down}" + flushUnit + "{br}用户数：{imsis}"
				+ imsisUnit + "\">";
		if (list != null && !list.isEmpty()) {
			for (CommonFlush c : list) {
				xml = xml + "<Statdata name=\"" + c.getBusinessName()
						+ "\" total=\""
						+ DataFormatUtils.getFlushByUnit(c, flushUnit, "total")
						+ "\" up=\""
						+ DataFormatUtils.getFlushByUnit(c, flushUnit, "up")
						+ "\" down=\""
						+ DataFormatUtils.getFlushByUnit(c, flushUnit, "down")
						+ "\" imsis =\""
						+ DataFormatUtils.getIVByUnit(c, imsisUnit, "imsis")
						+ "\"/>";
			}
		}
		xml = xml + "</Stat>" + "</Data>";
		return xml;

	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		/*
		 * String dataTypeStr = request.getParameter("dataType"); if
		 * (dataTypeStr != null) { dataType = Common.StringToInt(dataTypeStr); }
		 */

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		SortInfo si = gridServerHandler.getSingleSortInfo();
		if (si != null) {
			String order = si.getSortOrder();
			/**
			 * grid控件排序有3种情况：asc desc 和 第一次加载数据
			 * 事实上可以更改源码去掉defaultsort,但改后第一次加载数据不存在,故通过后台管理
			 */
			if ("defaultsort".equals(order)) {
				page.setOrder("desc");
				page.setOrderBy("nmFlush");
			} else {
				page.setOrder(order);
				page.setOrderBy(MantoEyeUtils.getSortField(si.getFieldName()));
			}
			// 默认排序方式
		} else {
			page.setOrderBy("nmFlush");
			page.setOrder("desc");
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
		// int isTD = 0;// TD标识

		List<CommonFlush> list = groupBisnessAreaDistriburteManager.listData(
				page, dataType, Common.StringToInt(spaceLevel_search), Common
						.StringToInt(timeLevel_search), time_search, apnName);
		String exportmsg = ExportMsg.EXPORT_GROUPFLUSH_SPACE
				+ Common.getTimeLevelCH(timeLevel_search) + "（" + time_search
				+ "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
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

	public String getApnName() {
		return apnName;
	}

	public void setApnName(String apnName) {
		this.apnName = apnName;
	}

	public String getSpaceLevel_search() {
		return spaceLevel_search;
	}

	public void setSpaceLevel_search(String spaceLevel_search) {
		this.spaceLevel_search = spaceLevel_search;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
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

	public String getAreaType_trend() {
		return areaType_trend;
	}

	public void setAreaType_trend(String areaTypeTrend) {
		areaType_trend = areaTypeTrend;
	}

	public String getApnName_trend() {
		return apnName_trend;
	}

	public void setApnName_trend(String apnNameTrend) {
		apnName_trend = apnNameTrend;
	}

	public String getAreaName_trend() {
		return areaName_trend;
	}

	public void setAreaName_trend(String areaNameTrend) {
		areaName_trend = areaNameTrend;
	}
}
