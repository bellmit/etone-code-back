package com.symbol.app.mantoeye.web.action.alarm;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.alarm.AlarmBean;
import com.symbol.app.mantoeye.service.alarm.FlushAlarmManager;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.util.Utils;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseAction;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

@Controller
@Scope("prototype")
public class FlushAlarmAction extends BaseAction {

	private static final long serialVersionUID = -3940179217809006837L;

	private FlushAlarmManager flushAlarmManager;

	public void initQuery() {

		// String ddd = Struts2Utils.getRequest().getParameter("ddd");
		//
		// int year = Common.getTimeNum(ddd, "year");
		// int month = Common.getTimeNum(ddd, "month");
		// int day = Common.getTimeNum(ddd, "day");

		int year = Common.getTimeNum(Common.getToday(), "year");
		int month = Common.getTimeNum(Common.getToday(), "month");
		int d = Common.getTimeNum(Common.getToday(), "day");

		String day = "31";

		if (1 == d) {
			month = month - 1;

			if (2 == month) {
				if (Utils.isLeapYear(year)) {
					day = "29";
				} else {
					day = "28";
				}
			} else {
				switch (month) {
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					day = "31";
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					day = "30";
					break;
				}
			}

		} else {
			day = String.valueOf(d - 1);
		}

		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		List<AlarmBean> list = flushAlarmManager.initQuery(year, month, Integer
				.parseInt(day));

		gridServerHandler.setData(list, AlarmBean.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());

	}

	private String startDate;
	private String endDate;
	private String type;

	public void queryHistory() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		Page<AlarmBean> page = flushAlarmManager.queryHistoryAlarm(startDate,
				endDate, type, gridServerHandler, warmOK, flushOK);

		gridServerHandler.setTotalRowNum(page.getTotalCount());

		gridServerHandler.setData(page.getResult(), AlarmBean.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	private String warmOK;
	private String flushOK;

	public void queryHistoryDay() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		Page<AlarmBean> page = flushAlarmManager.queryHistoryAlarm(startDate,
				type, gridServerHandler, warmOK);

		gridServerHandler.setTotalRowNum(page.getTotalCount());

		gridServerHandler.setData(page.getResult(), AlarmBean.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void queryHistoryDayAdd() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		Page<AlarmBean> page = flushAlarmManager.queryHistoryAlarmAdd(
				startDate, type, gridServerHandler);

		gridServerHandler.setTotalRowNum(page.getTotalCount());

		gridServerHandler.setData(page.getResult(), AlarmBean.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void queryHistoryDayMv() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		Page<AlarmBean> page = flushAlarmManager.queryHistoryAlarmMv(startDate,
				type, gridServerHandler);

		gridServerHandler.setTotalRowNum(page.getTotalCount());

		gridServerHandler.setData(page.getResult(), AlarmBean.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public void queryHistoryDayWarm() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());
		Page<AlarmBean> page = flushAlarmManager.queryHistoryAlarmWarm(
				startDate, type, gridServerHandler);

		gridServerHandler.setTotalRowNum(page.getTotalCount());

		gridServerHandler.setData(page.getResult(), AlarmBean.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());
	}

	public ICommonManager commonManagerImpl;

	public void export() throws IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		List<AlarmBean> list = flushAlarmManager.queryHistoryAlarm(startDate,
				endDate, type, warmOK, flushOK);
		String exportmsg = ExportMsg.EXPORT_FLUSH_ALARM + "（" + startDate + "~"
				+ endDate + "）";

		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		gridServerHandler.exportXLS(list, AlarmBean.class);
	}

	public void exportDay() throws IOException {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		List<AlarmBean> list = flushAlarmManager.queryHistoryAlarmDay(
				startDate, type);
		String exportmsg = ExportMsg.EXPORT_FLUSH_ALARM + "（" + startDate + "~"
				+ endDate + "）";

		commonManagerImpl.log(Struts2Utils.getRequest(), exportmsg);

		gridServerHandler.exportXLS(list, AlarmBean.class);
	}

	private String alarmInfo;
	private String typeName;
	private String typeId;
	private String warmDate;

	public String cancelWarn() {

		try {
			LoginListener l = getLoginListener();
			String user = l.getSessionContainer().getLoginName();
			flushAlarmManager.doCancelWarn(alarmInfo, typeId, user, type,
					warmDate);
			Struts2Utils.getRequest().setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_10000);
		} catch (Exception e) {
			logger.error(e.getMessage());
			Struts2Utils.getRequest().setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_10000);
			return ERROR;
		}

		return SUCCESS;
	}

	public FlushAlarmManager getFlushAlarmManager() {
		return flushAlarmManager;
	}

	@Resource
	public void setFlushAlarmManager(FlushAlarmManager flushAlarmManager) {
		this.flushAlarmManager = flushAlarmManager;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public ICommonManager getCommonManagerImpl() {
		return commonManagerImpl;
	}

	@Resource
	public void setCommonManagerImpl(ICommonManager commonManagerImpl) {
		this.commonManagerImpl = commonManagerImpl;
	}

	public String getAlarmInfo() {
		return alarmInfo;
	}

	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public void setWarmOK(String warmOK) {
		this.warmOK = warmOK;
	}

	public void setFlushOK(String flushOK) {
		this.flushOK = flushOK;
	}

	public void setWarmDate(String warmDate) {
		this.warmDate = warmDate;
	}

}
