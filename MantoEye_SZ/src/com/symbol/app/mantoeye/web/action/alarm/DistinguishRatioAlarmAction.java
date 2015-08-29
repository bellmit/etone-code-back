package com.symbol.app.mantoeye.web.action.alarm;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.symbol.app.mantoeye.constants.ExportMsg;
import com.symbol.app.mantoeye.dto.alarm.AlarmBean;
import com.symbol.app.mantoeye.dto.alarm.AlarmRatioLimmit;
import com.symbol.app.mantoeye.service.alarm.DistinguishRatioAlarmManager;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.util.Utils;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;
import com.symbol.wp.tools.gtgrid.org.json.JSONArray;
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
public class DistinguishRatioAlarmAction extends BaseDispatchAction {

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

	private JFreeChart chart;

	public String pieQuery() {
		// String ddd = Struts2Utils.getRequest().getParameter("date");
		// String year = Common.getTimeNum(ddd, "year").toString();
		// String month = Common.getTimeNum(ddd, "month").toString();
		// String day = String.valueOf(Common.getTimeNum(ddd, "day") - 1);

		String year = Common.getTimeNum(Common.getToday(), "year").toString();
		String month = Common.getTimeNum(Common.getToday(), "month").toString();

		int d = Common.getTimeNum(Common.getToday(), "day");

		String day = "31";

		if (1 == d) {
			month = String.valueOf(Integer.parseInt(month) - 1);

			if ("2".equals(month)) {
				if (Utils.isLeapYear(Integer.parseInt(year))) {
					day = "29";
				} else {
					day = "28";
				}
			} else {
				switch (Integer.parseInt(month)) {
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

		ActionContext.getContext().put("yestoday",
				CommonUtils.getYestodayDate());

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

	private String cgiRatioAlarm;
	private String bscRatioAlarm;
	private String sgsnRatioAlarm;
	private String busiRatioAlarm;

	public void queryAlarmRatioLimmit() {
		GridServerHandler gridServerHandler = new GridServerHandler(
				Struts2Utils.getRequest(), Struts2Utils.getResponse());

		Map<String, String> condition = new HashMap<String, String>();
		condition.put("CGI", cgiRatioAlarm);
		condition.put("BSC", bscRatioAlarm);
		condition.put("SGSN", sgsnRatioAlarm);
		condition.put("业务", busiRatioAlarm);
		List<AlarmRatioLimmit> list = distinguishRatioAlarmManager
				.queryAlarmRatioLimmit(condition);
		gridServerHandler.setTotalRowNum(4);

		gridServerHandler.setData(list, AlarmRatioLimmit.class);
		Struts2Utils.renderJson(gridServerHandler.getLoadResponseText());

	}

	private String rangeOne;
	private String rangeTwo;

	private String areaFlag;

	public String dial() {

		DefaultValueDataset dataset = new DefaultValueDataset();
		MeterPlot meterPlot = new MeterPlot(dataset);

		// double minr = Common.StringTodouble(minRange);
		// if ("".equals(rangeOne.trim())) {
		// rangeOne = "0~33";
		// }
		if ("".equals(rangeTwo.trim())) {
			rangeTwo = "33~66";
		}

		// String[] roarr = rangeOne.split("~");
		String[] rtarr = rangeTwo.split("~");

		// double ro1 = Common.StringTodouble(roarr[0]);
		double rt1 = Common.StringTodouble(rtarr[0]);
		// double ro2 = Common.StringTodouble(roarr[1]);
		double rt2 = Common.StringTodouble(rtarr[1]);

		chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, meterPlot,
				true);

		LegendTitle legend = chart.getLegend();
		legend.setItemFont(new Font("宋体", Font.BOLD, 13));
		meterPlot.setBackgroundImageAlpha(0);

		meterPlot.setRange(new Range(0.0d, 100d));
		meterPlot.addInterval(new MeterInterval("正常", new Range(0.0D, rt1),
				Color.BLUE, null, new Color(0, 0, 255, 64)));
		meterPlot.addInterval(new MeterInterval("中等", new Range(rt1, rt2),
				Color.GREEN, null, new Color(0, 255, 0, 64)));
		meterPlot.addInterval(new MeterInterval("严重", new Range(rt2, 100),
				Color.RED, null, new Color(255, 0, 0, 128)));

		meterPlot.setUnits("%");
		meterPlot.setDialShape(DialShape.CHORD);
		meterPlot.setDialBackgroundPaint(Color.WHITE);
		meterPlot.setRange(new Range(0, 100));
		meterPlot.setDialOutlinePaint(Color.GRAY);
		meterPlot.setNeedlePaint(Color.BLACK);
		meterPlot.setTickLabelsVisible(true);
		meterPlot.setTickLabelPaint(Color.BLACK);
		meterPlot.setTickPaint(Color.GRAY);
		meterPlot.setTickLabelFormat(NumberFormat.getNumberInstance());
		// meterPlot.setTickSize(15);
		meterPlot.setMeterAngle(280);
		meterPlot.setValuePaint(Color.BLACK);
		dataset.setValue(Double.parseDouble(null == areaFlag
				|| "".equals(areaFlag.trim()) ? "0" : areaFlag.trim()));
		meterPlot.setValueFont(new Font(" 识别率(%) ", 1, 10));

		return SUCCESS;
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

	public void queryHistoryIndex() {
		List<AlarmBean> bscList = distinguishRatioAlarmManager
				.queryHistoryAlarm(startDate, endDate, "2");
		bscList = buildNameList(bscList, "BSC");
		List<AlarmBean> sgsnList = distinguishRatioAlarmManager
				.queryHistoryAlarm(startDate, endDate, "3");
		sgsnList = buildNameList(sgsnList, "SGSN");
		List<AlarmBean> cgiList = distinguishRatioAlarmManager
				.queryHistoryAlarm(startDate, endDate, "1");
		cgiList = buildNameList(cgiList, "CGI");
		List<AlarmBean> busiList = distinguishRatioAlarmManager
				.queryHistoryAlarm(startDate, endDate, "4");
		busiList = buildNameList(busiList, "BUSI");

		List<AlarmBean> list = new ArrayList<AlarmBean>();
		list.addAll(cgiList);
		list.addAll(bscList);
		list.addAll(sgsnList);
		list.addAll(busiList);

		JSONArray ja = JSONUtils.BeanList2JSONArray(list, AlarmBean.class);
		Struts2Utils.renderJson(ja.toString());

		// String lineXmlData = buildXmlData(list);
		//
		// Struts2Utils.renderText(lineXmlData);

	}

	private String buildXmlData(List<AlarmBean> list) {
		StringBuffer xmlData = new StringBuffer(500);
		if (null != list && !list.isEmpty()) {
			int i = 0;
			for (AlarmBean b : list) {
				xmlData.append(b.getFullDate());
				xmlData.append("^");
				xmlData.append(b.getAlarmRatio());
				xmlData.append("^");
				xmlData.append(b.getTypeName());
				if (i != list.size() - 1) {
					xmlData.append("@");
				}
				i++;
			}
		}

		return xmlData.toString();
	}

	private List<AlarmBean> buildNameList(List<AlarmBean> list, String typeName) {

		if (null == list || list.isEmpty()) {
			return list;
		}

		List<AlarmBean> nList = new ArrayList<AlarmBean>();
		int i = 0;
		for (AlarmBean b : list) {
			b.setTypeName(typeName);
			nList.add(i, b);
			i++;
		}

		return nList;
	}

	public void queryHistoryIndexBak20101102() {
		List<AlarmBean> list = distinguishRatioAlarmManager.queryHistoryAlarm(
				startDate, endDate, type);
		JSONArray ja = JSONUtils.BeanList2JSONArray(list, AlarmBean.class);
		Struts2Utils.renderJson(ja.toString());

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

	public String configAlarmRatioLimmit() {
		HttpServletRequest request = Struts2Utils.getRequest();
		try {
			distinguishRatioAlarmManager.configAlarmRatioLimmit(ar
					.getTypeName(), ar.getMinLimmit(), ar.getMaxLimmit());
			msg = "更新识别率[" + ar.getTypeName() + "]阀值成功！";
			request.setAttribute(VarConstants.SUCC_CODE,
					MsgConstants.SUCC_CODE_00102);
			commonManagerImpl.log(request, msg);
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00004);
			msg = "更新识别率阀值失败！";
			commonManagerImpl.log(request, msg);
			return ERROR;
		}
	}

	private AlarmRatioLimmit ar;
	private String typeName;
	private String msg;

	public String findAlarmRatioLimmit() {
		try {
			ar = distinguishRatioAlarmManager.findAlarmRatioLimmit(typeName);
			return "editPrePare";
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
			msg = "加载数据失败！";
			return ERROR;
		}

	}

	public JFreeChart getChart() {
		return chart;
	}

	public String getAreaFlag() {
		return areaFlag;
	}

	public void setAreaFlag(String areaFlag) {
		this.areaFlag = areaFlag;
	}

	public String getRangeOne() {
		return rangeOne;
	}

	public void setRangeOne(String rangeOne) {
		this.rangeOne = rangeOne;
	}

	public String getRangeTwo() {
		return rangeTwo;
	}

	public void setRangeTwo(String rangeTwo) {
		this.rangeTwo = rangeTwo;
	}

	public String getCgiRatioAlarm() {
		return cgiRatioAlarm;
	}

	public void setCgiRatioAlarm(String cgiRatioAlarm) {
		this.cgiRatioAlarm = cgiRatioAlarm;
	}

	public String getBscRatioAlarm() {
		return bscRatioAlarm;
	}

	public void setBscRatioAlarm(String bscRatioAlarm) {
		this.bscRatioAlarm = bscRatioAlarm;
	}

	public String getSgsnRatioAlarm() {
		return sgsnRatioAlarm;
	}

	public void setSgsnRatioAlarm(String sgsnRatioAlarm) {
		this.sgsnRatioAlarm = sgsnRatioAlarm;
	}

	public String getBusiRatioAlarm() {
		return busiRatioAlarm;
	}

	public void setBusiRatioAlarm(String busiRatioAlarm) {
		this.busiRatioAlarm = busiRatioAlarm;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getType() {
		return type;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public AlarmRatioLimmit getAr() {
		return ar;
	}

	public void setAr(AlarmRatioLimmit ar) {
		this.ar = ar;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
