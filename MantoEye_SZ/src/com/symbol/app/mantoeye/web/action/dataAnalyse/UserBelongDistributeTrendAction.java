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
import com.symbol.app.mantoeye.service.businessAnalyse.UserBelongDistributeTrendManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseAction;
import com.symbol.wp.tools.gtgrid.org.json.JSONArray;
import com.symbol.wp.tools.gtgrid.org.json.JSONObject;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

@Controller("userBelongDistributeTrendAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class UserBelongDistributeTrendAction extends BaseAction {

	private static final long serialVersionUID = 1555303300895968484L;

	private UserBelongDistributeTrendManager userBelongDistributeTrendManager;
	@Resource(name = "commonManagerImpl")
	public ICommonManager commonManagerImpl;

	@Autowired
	private CommonDimensionManager commonDimensionManager;

	private String timeLevel = "1";
	private String userBelongId = "1";
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

		List<CommonFlush> list = userBelongDistributeTrendManager.queryByPage(
				gridServerHandler, timeLevel, userBelongId, startTime_trend,
				endTime_trend).getResult();

		gridServerHandler.setData(list, CommonFlush.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void export() throws IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		List<CommonFlush> list = userBelongDistributeTrendManager.queryAll(
				timeLevel, userBelongId, startTime_trend, endTime_trend);
		String exportmsg = ExportMsg.EXPORT_TREND_BELONG
				+ Common.getTimeLevelCH(timeLevel) + "(" + startTime_trend
				+ "~" + endTime_trend + ")";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);
		gridServerHandler.exportXLS(list, CommonFlush.class);
	}

	public void getAjaxChartXmlData() throws Exception {
		logger.info("getAjaxChartXmlData------------------");
		List<CommonFlush> list = userBelongDistributeTrendManager.queryAll(
				timeLevel, userBelongId, startTime_trend, endTime_trend);

		JSONArray ja = JSONUtils.BeanList2JSONArray(list, CommonFlush.class);
		Struts2Utils.renderJson(ja.toString());

	}

	public void getAllUserBelongIdAndDesc() {
		String idAndDesc = userBelongDistributeTrendManager
				.getAllUserBelongIdAndDesc();

		Struts2Utils.renderText(idAndDesc);
	}

	public void findAllUserBelong() {
		List<CommonDimension> list = commonDimensionManager.findAllUserBelong();
		JSONObject json = new JSONObject();
		json.put("datalist", JSONUtils.BeanList2JSONArray(list,
				CommonDimension.class));
		Struts2Utils.renderJson(json.toString());
	}

	public UserBelongDistributeTrendManager getUserBelongDistributeTrendManager() {
		return userBelongDistributeTrendManager;
	}

	@Resource
	public void setUserBelongDistributeTrendManager(
			UserBelongDistributeTrendManager userBelongDistributeTrendManager) {
		this.userBelongDistributeTrendManager = userBelongDistributeTrendManager;
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

	public String getUserBelongId() {
		return userBelongId;
	}

	public void setUserBelongId(String userBelongId) {
		this.userBelongId = userBelongId;
	}

}
