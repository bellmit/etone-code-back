package com.symbol.app.mantoeye.web.action.dataAnalyse;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.service.businessAnalyse.SpaceDistributeTrendManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseAction;
import com.symbol.wp.tools.gtgrid.org.json.JSONArray;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

@Controller("spaceDistributeTrendAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class SpaceDistributeTrendAction extends BaseAction {

	private static final long serialVersionUID = 264970860388207518L;

	private SpaceDistributeTrendManager spaceDistributeTrendManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	private String timeLevel = "1";
	private String areaType = "1";
	private String areaValue = "SZ27B";
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

		List<CommonFlush> list = spaceDistributeTrendManager.queryByPage(
				gridServerHandler, timeLevel, areaType, areaValue,
				startTime_trend, endTime_trend).getResult();

		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void export() throws IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		areaValue = new String(areaValue.getBytes("ISO-8859-1"), "UTF-8");
		logger.info(areaValue);
		List<CommonFlush> list = spaceDistributeTrendManager.queryAll(
				timeLevel, areaType, areaValue, startTime_trend, endTime_trend);
		String exportmsg = ExportMsg.EXPORT_TREND_SPACE
				+ Common.getTimeLevelCH(timeLevel) + "（" + startTime_trend
				+ "~" + endTime_trend + "）" + areaValue;
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public void getAjaxChartXmlData() throws Exception {
		logger.info("getAjaxChartXmlData------------------");
		List<CommonFlush> list = spaceDistributeTrendManager.queryAll(
				timeLevel, areaType, areaValue, startTime_trend, endTime_trend);

		JSONArray ja = JSONUtils.BeanList2JSONArray(list, CommonFlush.class);
		Struts2Utils.renderJson(ja.toString());

	}

	public SpaceDistributeTrendManager getSpaceDistributeTrendManager() {
		return spaceDistributeTrendManager;
	}

	@Resource
	public void setSpaceDistributeTrendManager(
			SpaceDistributeTrendManager spaceDistributeTrendManager) {
		this.spaceDistributeTrendManager = spaceDistributeTrendManager;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTimeLevel() {
		return timeLevel;
	}

	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getAreaValue() {
		return areaValue;
	}

	public void setAreaValue(String areaValue) {
		this.areaValue = areaValue;
	}
}
