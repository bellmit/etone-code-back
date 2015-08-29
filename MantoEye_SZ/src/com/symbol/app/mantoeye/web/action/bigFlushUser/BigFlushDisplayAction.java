package com.symbol.app.mantoeye.web.action.bigFlushUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.BigFlushUser;
import com.symbol.app.mantoeye.service.bigflushuser.BigFlushUserManager;
import com.symbol.app.mantoeye.service.bigflushuser.BlackUserManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.modules.struts2.base.IAction;
import com.symbol.wp.tools.gtgrid.model.SortInfo;
import com.symbol.wp.tools.gtgrid.org.json.JSONArray;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

public class BigFlushDisplayAction extends BaseDispatchAction implements
		IAction {
	HttpServletRequest request = ServletActionContext.getRequest();
	private Page<BigFlushUser> page = new Page<BigFlushUser>(
			VarConstants.PAGE_LONG_ROW_SIZE);
	@Autowired
	private BigFlushUserManager bigFlushUserManager;
	@Autowired
	private BlackUserManager blackUserManager;
	@Autowired
	@Qualifier("commonManagerImpl")
	private ICommonManager commonManagerImpl;

	private String searchDateStart = CommonUtils.getSYestoday();// 查询时间;
	private String searchDateEnd = CommonUtils.getSYestoday();// 查询时间;
	private String isBlackUser = "1";
	private String s_msisdn = "";

	private String dataType_trend = "1";// GPRS
	private String timeLevel_trend = "1";// 小时
	private String startTime_trend = CommonUtils.getSYestoday();// 开始时间
	private String endTime_trend = CommonUtils.getSYestoday();// 结束时间
	private String msisdn_trend = "0";

	/**
	 * 查询走势数据
	 */
	public void queryTrend() throws ServletException, IOException {

		List<BigFlushUser> list = null;
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		logger.info("dataType_trend:" + dataType_trend + " timeLevel_trend:"
				+ timeLevel_trend + " startTime_trend:" + startTime_trend
				+ " endTime_trend:" + endTime_trend + " apnName_trend:"
				+ msisdn_trend);

		int totalRowNum = gridServerHandler.getTotalRowNum();
		if (totalRowNum < 1) {
			totalRowNum = bigFlushUserManager.queryAllTrend(startTime_trend,
					endTime_trend, msisdn_trend).size();
			gridServerHandler.setTotalRowNum(totalRowNum);
		}
		page.setPageSize(gridServerHandler.getPageSize());
		page.setPageNo(gridServerHandler.getPageInfo().getPageNum());

		page = bigFlushUserManager.queryTrend(page, startTime_trend,
				endTime_trend, msisdn_trend);
		list = page.getResult();
		gridServerHandler.setData(list, BigFlushUser.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 数据导出
	 */
	public void exportTrend() throws ServletException, IOException {

		logger.info("dataType_trend:" + dataType_trend + " timeLevel_trend:"
				+ timeLevel_trend + " startTime_trend:" + startTime_trend
				+ " endTime_trend:" + endTime_trend + " apnName_trend:");
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<BigFlushUser> list = null;
		list = bigFlushUserManager.queryAllTrend(startTime_trend,
				endTime_trend, msisdn_trend);
		gridServerHandler.exportXLS(list, BigFlushUser.class);
	}

	/**
	 * 获取图形数据
	 */
	public void getAjaxTrendChartData() throws Exception {
		List<BigFlushUser> list = null;
		list = bigFlushUserManager.queryAllTrend(startTime_trend,
				endTime_trend, msisdn_trend);
		if (list != null && list.size() > 0) {
		} else {
			list = new ArrayList<BigFlushUser>();
		}
		JSONArray ja = JSONUtils.BeanList2JSONArray(list, BigFlushUser.class);
		Struts2Utils.renderJson(ja.toString());
	}

	public String delete() throws Exception {
		return null;
	}

	public String edit() throws Exception {
		return null;
	}

	public String list() throws Exception {
		return INDEX;
	}

	public String save() throws Exception {
		return null;
	}

	public String update() throws Exception {
		return null;
	}

	public void query() {// 查询大流量用户
		try {
			GridServerHandler gridServerHandler = new GridServerHandler(
					Struts2Utils.getRequest(), Struts2Utils.getResponse());
			SortInfo si = gridServerHandler.getSingleSortInfo();
			logger.info("s_msisdn:" + s_msisdn);
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
					page.setOrderBy(MantoEyeUtils
							.getSortFieldForBigFlushUser(si.getFieldName()));
				}
				// 默认排序方式
			} else {
				page.setOrderBy("nmFlush");
				page.setOrder("desc");
			}
			this.page.setPageSize(gridServerHandler.getPageSize());
			this.page.setPageNo(gridServerHandler.getPageInfo().getPageNum());
			page = bigFlushUserManager.query(searchDateStart, searchDateEnd,
					page, Common.StringToInt(isBlackUser), s_msisdn);
			int totalRowNum = gridServerHandler.getTotalRowNum();
			if (totalRowNum < 1) {
				totalRowNum = this.page.getTotalCount();
				gridServerHandler.setTotalRowNum(totalRowNum);
			}
			List<BigFlushUser> list = page.getResult();
			logger.info("SIZE:" + list.size());
			gridServerHandler.setData(list, BigFlushUser.class);
			Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 数据导出
	 */
	public void export() throws ServletException, IOException {
		/*
		 * String dataTypeStr = request.getParameter("dataType"); if
		 * (dataTypeStr != null) { dataType = Common.StringToInt(dataTypeStr); }
		 */

		List<BigFlushUser> list = bigFlushUserManager.queryAll(searchDateStart,
				searchDateEnd, Common.StringToInt(isBlackUser), s_msisdn);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		String exportmsg = ExportMsg.EXPORT_BIGFLUSH_USER + "("
				+ searchDateStart + "~" + searchDateEnd + ")";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, BigFlushUser.class);
	}

	/**
	 * 数据导出
	 */
	public void exportCsv() throws ServletException, IOException {

		List<BigFlushUser> list = bigFlushUserManager.queryAll(searchDateStart,
				searchDateEnd, Common.StringToInt(isBlackUser), s_msisdn);
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		gridServerHandler.exportCSV(list, BigFlushUser.class);
	}

	public void distinationDisplay() {// 查询分布
		String msisdn = request.getParameter("msisdn");
		String date = request.getParameter("date");
		String name = request.getParameter("name");
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<BigFlushUser> list = bigFlushUserManager
				.queryDistinationByBigUser(date, Common.StringToLong(msisdn),
						name);
		gridServerHandler.setData(list, BigFlushUser.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	/**
	 * 大流量用户分布数据导出
	 */
	public void exportDistination() throws ServletException, IOException {
		/*
		 * String dataTypeStr = request.getParameter("dataType"); if
		 * (dataTypeStr != null) { dataType = Common.StringToInt(dataTypeStr); }
		 */
		String msisdn = request.getParameter("msisdn");
		String date = request.getParameter("date");
		String name = request.getParameter("name");
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<BigFlushUser> list = bigFlushUserManager
				.queryDistinationByBigUser(date, Common.StringToLong(msisdn),
						name);
		gridServerHandler.exportXLS(list, BigFlushUser.class);
	}

	/**
	 * 把大流量用户添加成黑名单用户
	 */
	public void saveFromBigFlushUser() {
		String keys = request.getParameter("keys");
		String descrite = request.getParameter("descrite");
		String msg = "";
		try {
			List<BigFlushUser> list = bigFlushUserManager
					.queryEntiryByKeys(keys);// 根据ID查询 该ID对应的号码
			msg = "添加黑名单成功!";
			blackUserManager.insertBlackUser(list, descrite);// 把该号码插入黑名单用户表中
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
		} catch (Exception e) {
			logger.error(e.getMessage());
			msg = "添加黑名单失败!";
			commonManagerImpl.log(request, msg);
			Struts2Utils.renderText(msg);
		}

	}

	public void queryMsisdn() throws Exception {
		try {
			HttpServletRequest request = Struts2Utils.getRequest();
			String like_msisdn_search = request
					.getParameter("like_msisdn_search");
			String message = "";
			List<String> list = bigFlushUserManager.findBigFlushMsisdn(
					searchDateStart, searchDateEnd, like_msisdn_search);

			if (list != null && !list.isEmpty()) {
				if (list.size() > 100) {
					message = "ERROR:TOO MUCH MSISDN";
				} else {
					for (String t : list) {
						message = message + t + "&&&&&";
					}
				}
			}
			if ((!"".equals(message)) && (message.indexOf("ERROR:") == -1)) {
				message = message.substring(0, message.lastIndexOf("&&&&&"));
			}
			Struts2Utils.renderText(message);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public String getSearchDateStart() {
		return searchDateStart;
	}

	public void setSearchDateStart(String searchDateStart) {
		this.searchDateStart = searchDateStart;
	}

	public String getIsBlackUser() {
		return isBlackUser;
	}

	public void setIsBlackUser(String isBlackUser) {
		this.isBlackUser = isBlackUser;
	}

	public String getSearchDateEnd() {
		return searchDateEnd;
	}

	public void setSearchDateEnd(String searchDateEnd) {
		this.searchDateEnd = searchDateEnd;
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

	public String getMsisdn_trend() {
		return msisdn_trend;
	}

	public void setMsisdn_trend(String msisdnTrend) {
		msisdn_trend = msisdnTrend;
	}

	public String getS_msisdn() {
		return s_msisdn;
	}

	public void setS_msisdn(String sMsisdn) {
		s_msisdn = sMsisdn;
	}
}
