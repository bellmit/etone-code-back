package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.common.CommonDimension;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.CommonDimensionManager;
import com.symbol.app.mantoeye.service.businessAnalyse.ApnDistributeTrendManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseAction;
import com.symbol.wp.tools.gtgrid.org.json.JSONArray;
import com.symbol.wp.tools.gtgrid.org.json.JSONObject;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

@Controller("apnDistributeTrendAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class ApnDistributeTrendAction extends BaseAction {

	private static final long serialVersionUID = 4697129587672551966L;
	private ApnDistributeTrendManager apnDistributeTrendManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	@Autowired
	private CommonDimensionManager commonDimensionManager;

	public ApnDistributeTrendManager getApnDistributeTrendManager() {
		return apnDistributeTrendManager;
	}

	@Resource
	public void setApnDistributeTrendManager(
			ApnDistributeTrendManager apnDistributeTrendManager) {
		this.apnDistributeTrendManager = apnDistributeTrendManager;
	}

	private String timeLevel = "1";
	private String apnDomain = "CMWAP";
	private String startTime_trend = CommonUtils.getSYestoday() + " 00";// 开始时间
	private String endTime_trend = CommonUtils.getSYestoday() + " 23";// 结束时间
	private String raitype_trend = "1";

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

		List<CommonFlush> list = apnDistributeTrendManager.queryByPage(
				gridServerHandler, timeLevel, apnDomain, startTime_trend,
				endTime_trend, raitype_trend).getResult();

		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void export() throws IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		apnDomain = new String(apnDomain.getBytes("ISO-8859-1"), "UTF-8");
		logger.info(apnDomain);
		List<CommonFlush> list = apnDistributeTrendManager.queryAll(timeLevel,
				apnDomain, startTime_trend, endTime_trend, raitype_trend);
		String exportmsg = ExportMsg.EXPORT_TREND_APN
				+ Common.getTimeLevelCH(timeLevel) + "（" + startTime_trend
				+ "~" + endTime_trend + "|" + apnDomain + "）";
		commonManagerImpl.log(this.getServletRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public void getAjaxChartXmlData() throws Exception {
		logger.info("getAjaxChartXmlData------------------");
		List<CommonFlush> list = apnDistributeTrendManager.queryAll(timeLevel,
				apnDomain, startTime_trend, endTime_trend, raitype_trend);

		JSONArray ja = JSONUtils.BeanList2JSONArray(list, CommonFlush.class);
		Struts2Utils.renderJson(ja.toString());

	}

	public void getAllApnType() {
		List<CommonDimension> list = commonDimensionManager.findAllApnType();
		JSONObject json = new JSONObject();
		json.put("datalist", JSONUtils.BeanList2JSONArray(list,
				CommonDimension.class));
		Struts2Utils.renderJson(json.toString());
	}

	public String getTimeLevel() {
		return timeLevel;
	}

	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

	public String getApnDomain() {
		return apnDomain;
	}

	public void setApnDomain(String apnDomain) {
		this.apnDomain = apnDomain;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getRaitype_trend() {
		return raitype_trend;
	}

	public void setRaitype_trend(String raitypeTrend) {
		raitype_trend = raitypeTrend;
	}

}
