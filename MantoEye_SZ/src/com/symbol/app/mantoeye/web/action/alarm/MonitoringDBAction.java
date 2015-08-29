package com.symbol.app.mantoeye.web.action.alarm;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.jfree.chart.ChartFrame;
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
import com.symbol.app.mantoeye.bean.MonitoringBean;
import com.symbol.app.mantoeye.service.alarm.MonitoringDBManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.struts2.base.BaseDispatchAction;

/**
 * 数据库告警监控
 * 
 * @author Jane
 * 
 */
@Controller
@Scope("prototype")
public class MonitoringDBAction extends BaseDispatchAction {
	private JFreeChart chart;
	private String type;

	private String threshold;
	private String maxValue;
	private String curValue;

	public String dial() {

		DefaultValueDataset dataset = new DefaultValueDataset();
		MeterPlot meterPlot = new MeterPlot(dataset);

		chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, meterPlot,
				true);

		double thresholdValue = 0.0D;
		if ("ov".equals(type)) {
			threshold = threshold.split("G")[0];
			thresholdValue = Double.parseDouble(threshold);
			maxValue = String.valueOf(thresholdValue * 2);
		} else if ("conn".equals(type)) {
			threshold = threshold.split("%")[0];
			thresholdValue = Double.parseDouble(maxValue)
					* (Double.parseDouble(threshold) / 100);
		} else if ("space".equals(type)) {
			threshold = threshold.split("%")[0];
			thresholdValue = Double.parseDouble(maxValue)
					* (Double.parseDouble(threshold) / 100);
		}

		LegendTitle legend = chart.getLegend();
		legend.setItemFont(new Font("黑体", Font.PLAIN, 13));
		meterPlot.setBackgroundImageAlpha(0);

		meterPlot.setRange(new Range(0.0d, 100d));
		meterPlot.addInterval(new MeterInterval("正常", new Range(0.0D,
				thresholdValue), Color.BLUE, null, new Color(0, 0, 255, 180)));
		meterPlot.addInterval(new MeterInterval("异常",
				new Range(thresholdValue, Double.parseDouble((null == maxValue
						|| "".equals(maxValue) ? "0" : maxValue))), Color.RED,
				null, new Color(255, 0, 0, 180)));

		meterPlot.setUnits("G");
		meterPlot.setDialShape(DialShape.CHORD);
		meterPlot.setDialBackgroundPaint(Color.WHITE);
		meterPlot.setRange(new Range(0, 200));
		meterPlot.setDialOutlinePaint(Color.BLACK);
		meterPlot.setTickLabelsVisible(true);
		meterPlot.setTickPaint(Color.BLACK);
		meterPlot.setTickLabelFormat(NumberFormat.getNumberInstance());
		meterPlot.setMeterAngle(300);
		meterPlot.setValuePaint(Color.BLACK);
		meterPlot.setValueFont(new Font("黑体", 1, 30));

		dataset.setValue(Double.parseDouble((null == curValue
				|| "".equals(curValue) ? "0" : curValue)));

		return SUCCESS;
	}

	private MonitoringDBManager monitoringDBManager;

	public String init() {
		String currentDate = Common.getShortYMDH(new Date());
		MonitoringBean bean = monitoringDBManager.initQuery(currentDate);

		if (null == bean) {
			ActionContext.getContext().put("nodata", "NODATA");
		} else {
			ActionContext.getContext().put("monitoringBean", bean);
		}

		return "init";

	}

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MonitoringDBManager getMonitoringDBManager() {
		return monitoringDBManager;
	}

	@Resource
	public void setMonitoringDBManager(MonitoringDBManager monitoringDBManager) {
		this.monitoringDBManager = monitoringDBManager;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getCurValue() {
		return curValue;
	}

	public void setCurValue(String curValue) {
		this.curValue = curValue;
	}
}
