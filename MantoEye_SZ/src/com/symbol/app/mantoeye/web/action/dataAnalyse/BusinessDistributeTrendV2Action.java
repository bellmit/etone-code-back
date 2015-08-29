package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.businessAnalyse.BusinessDistributeTrendManager;
import com.symbol.app.mantoeye.service.businessAnalyse.BusinessDistributeTrendV2Manager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseAction;
import com.symbol.wp.tools.gtgrid.org.json.JSONArray;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

public class BusinessDistributeTrendV2Action extends BaseAction {

	private static final long serialVersionUID = 2018544402159733587L;
	@Autowired
	private BusinessDistributeTrendV2Manager businessDistributeTrendV2Manager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String timeLevel = "1";
	private String businessName = "腾讯视频";
	private String startTime_trend = CommonUtils.getSYestoday() + " 00";// 开始时间
	private String endTime_trend = CommonUtils.getSYestoday() + " 23";// 结束时间

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

	public void query() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		List<CommonFlush> list = businessDistributeTrendV2Manager.queryByPage(
				gridServerHandler, timeLevel, businessName, startTime_trend,
				endTime_trend).getResult();

		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void export() throws IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		businessName = new String(businessName.getBytes("ISO-8859-1"), "UTF-8");
		logger.info(businessName);
		List<CommonFlush> list = businessDistributeTrendV2Manager.queryAll(
				timeLevel, businessName, startTime_trend, endTime_trend);
		String exportmsg = ExportMsg.EXPORT_TREND_BUSINESS
				+ Common.getTimeLevelCH(timeLevel) + "（" + startTime_trend
				+ "~" + endTime_trend + "|" + businessName + "）";
		commonManagerImpl.log(this.getServletRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public void getAjaxChartXmlData() throws Exception {
		logger.info("getAjaxChartXmlData------------------");
		List<CommonFlush> list = businessDistributeTrendV2Manager.queryAll(
				timeLevel, businessName, startTime_trend, endTime_trend);

		JSONArray ja = JSONUtils.BeanList2JSONArray(list, CommonFlush.class);
		Struts2Utils.renderJson(ja.toString());

	}

	public String getTimeLevel() {
		return timeLevel;
	}

	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

}