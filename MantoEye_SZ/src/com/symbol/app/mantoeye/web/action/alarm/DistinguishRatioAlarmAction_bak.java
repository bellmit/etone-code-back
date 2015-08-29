package com.symbol.app.mantoeye.web.action.alarm;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.alarm.AlarmBean;
import com.symbol.app.mantoeye.service.alarm.DistinguishRatioAlarmManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.org.json.JSONObject;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;
import com.symbol.wp.tools.gtgrid.util.JSONUtils;

/**
 * 识别率告警ACTION
 * 
 * @author Jane
 * 
 */
@Controller
@Scope("prototype")
public class DistinguishRatioAlarmAction_bak extends BaseDispatchAction {

	public ICommonManager getCommonManagerImpl() {
		return commonManagerImpl;
	}

	@Resource(name = "commonManagerImpl")
	public void setCommonManagerImpl(ICommonManager commonManagerImpl) {
		this.commonManagerImpl = commonManagerImpl;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setType(String type) {
		this.type = type;
	}

	private static final long serialVersionUID = 1L;

	public DistinguishRatioAlarmManager getDistinguishRatioAlarmManager() {
		return distinguishRatioAlarmManager;
	}

	@Resource
	public void setDistinguishRatioAlarmManager(
			DistinguishRatioAlarmManager distinguishRatioAlarmManager) {
		this.distinguishRatioAlarmManager = distinguishRatioAlarmManager;
	}

	private DistinguishRatioAlarmManager distinguishRatioAlarmManager;

	private String startDate;
	private String endDate;
	private String type;

	public String pieQuery() {
		// String ddd = Struts2Utils.getRequest().getParameter("date");
		// String year = Common.getTimeNum(ddd, "year").toString();
		// String month = Common.getTimeNum(ddd, "month").toString();
		// String day = String.valueOf(Common.getTimeNum(ddd, "day") - 1);

		String year = Common.getTimeNum(Common.getToday(), "year").toString();
		String month = Common.getTimeNum(Common.getToday(), "month").toString();
		String day = String
				.valueOf(Common.getTimeNum(Common.getToday(), "day") - 1);

		ActionContext.getContext().put("yestoday", CommonUtils.getYestodayDate());

		ActionContext.getContext().put(
				"cgiBean",
				distinguishRatioAlarmManager.pieQueryByTpye(year, month, day,
						"1"));
		ActionContext.getContext().put(
				"bscBean",
				distinguishRatioAlarmManager.pieQueryByTpye(year, month, day,
						"2"));
		ActionContext.getContext().put(
				"sgsnBean",
				distinguishRatioAlarmManager.pieQueryByTpye(year, month, day,
						"3"));
		ActionContext.getContext().put(
				"busiBean",
				distinguishRatioAlarmManager.pieQueryByTpye(year, month, day,
						"4"));

		return "initPie";
	}

	public void queryHistory() {

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		Page<AlarmBean> page = distinguishRatioAlarmManager.queryHistoryAlarm(
				startDate, endDate, type, gridServerHandler);

		gridServerHandler.setTotalRowNum(page.getTotalCount());

		gridServerHandler.setData(page.getResult(), AlarmBean.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public ICommonManager commonManagerImpl;

	public void export() throws IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		List<AlarmBean> list = distinguishRatioAlarmManager.queryHistoryAlarm(
				startDate, endDate, type);
		String exportmsg = ExportMsg.EXPORT_DISTRINGUISH_ALARM + "（"
				+ startDate + "~" + endDate + "）";
		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		gridServerHandler.exportXLS(list, AlarmBean.class);
	}

}
