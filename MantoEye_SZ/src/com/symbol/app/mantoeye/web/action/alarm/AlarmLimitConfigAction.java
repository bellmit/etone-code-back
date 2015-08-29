package com.symbol.app.mantoeye.web.action.alarm;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.symbol.app.mantoeye.dto.alarm.AlarmLimitBean;
import com.symbol.app.mantoeye.service.alarm.AlarmLimitConfigManager;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

@Controller
@Scope("prototype")
public class AlarmLimitConfigAction extends BaseAction {

	private static final long serialVersionUID = 61000798555993520L;

	private AlarmLimitConfigManager alarmLimitConfigManager;

	public void initQuery() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		List<AlarmLimitBean> list = alarmLimitConfigManager.initQuery(type);

		gridServerHandler.setData(list, AlarmLimitBean.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public String initQuery2() {
		List<AlarmLimitBean> list = alarmLimitConfigManager.initQuery(type);

		for (AlarmLimitBean bean : list) {
			if ("4".equals(bean.getAlarmType())
					|| "1".equals(bean.getAlarmType())
					|| "8".equals(bean.getAlarmType())
					|| "11".equals(bean.getAlarmType())
					|| "14".equals(bean.getAlarmType())) {
				ActionContext.getContext().getSession()
						.put("warmBean", bean.getAlarmLimit());
			} else if ("5".equals(bean.getAlarmType())
					|| "2".equals(bean.getAlarmType())
					|| "9".equals(bean.getAlarmType())
					|| "12".equals(bean.getAlarmType())
					|| "15".equals(bean.getAlarmType())) {
				ActionContext.getContext().getSession()
						.put("addBean", bean.getAlarmLimit());
			} else if ("6".equals(bean.getAlarmType())
					|| "3".equals(bean.getAlarmType())
					|| "10".equals(bean.getAlarmType())
					|| "13".equals(bean.getAlarmType())
					|| "16".equals(bean.getAlarmType())) {
				ActionContext.getContext().getSession()
						.put("mvBean", bean.getAlarmLimit());
			}
		}
		ActionContext.getContext().getSession().put("type", type);
		return "initQ";
	}

	private String type;
	private String alarmType;
	private String alarmLimit;
	private String timeLevel;
	private String operator;

	public void updateAlarm() {
		alarmLimitConfigManager.updateAlarm(type, alarmType, alarmLimit,
				timeLevel, operator);
		Struts2Utils.renderText("阀值更新完毕！");
	}

	public void updateAlarm2() {
		alarmLimitConfigManager.updateAlarm2(type, alarmType, alarmLimit,
				timeLevel, operator);
		Struts2Utils.renderText("阀值更新完毕！");
	}

	public AlarmLimitConfigManager getAlarmLimitConfigManager() {
		return alarmLimitConfigManager;
	}

	@Resource
	public void setAlarmLimitConfigManager(
			AlarmLimitConfigManager alarmLimitConfigManager) {
		this.alarmLimitConfigManager = alarmLimitConfigManager;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public void setAlarmLimit(String alarmLimit) {
		this.alarmLimit = alarmLimit;
	}

	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
