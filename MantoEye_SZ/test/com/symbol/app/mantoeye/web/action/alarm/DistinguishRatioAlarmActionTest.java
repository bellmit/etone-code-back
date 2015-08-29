package com.symbol.app.mantoeye.web.action.alarm;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dto.alarm.AlarmRatioLimmit;
import com.symbol.wp.core.util.Common;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class DistinguishRatioAlarmActionTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	public DataSource getDataSource() {
		return dataSource;
	}

	@Test
	public void testEdit() {
		String res = distinguishRatioAlarmAction.configAlarmRatioLimmit();
		Assert.assertThat(res, Matchers.is("success"));
	}

	@Test
	public void testFindById() {
		distinguishRatioAlarmAction.findAlarmRatioLimmit();
	}

	@Test
	public void testQueryAlarmRatioLimmit() {
		distinguishRatioAlarmAction.queryAlarmRatioLimmit();
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DistinguishRatioAlarmAction getDistinguishRatioAlarmAction() {
		return distinguishRatioAlarmAction;
	}

	@Resource
	public void setDistinguishRatioAlarmAction(
			DistinguishRatioAlarmAction distinguishRatioAlarmAction) {
		this.distinguishRatioAlarmAction = distinguishRatioAlarmAction;
	}

	private DataSource dataSource;

	private DistinguishRatioAlarmAction distinguishRatioAlarmAction;

	@Test
	public void testDial() {
		// distinguishRatioAlarmAction.setMinRange("33");
		String rs = distinguishRatioAlarmAction.dial();
	}

	public static void main(String[] args) {
		DefaultValueDataset dataset = new DefaultValueDataset();
		MeterPlot meterPlot = new MeterPlot(dataset);
		meterPlot.setUnits("%");
		double minr = Common.StringTodouble("33");
		double maxr = Common.StringTodouble("100");

		JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT,
				meterPlot, true);

		LegendTitle legend = chart.getLegend();
		legend.setItemFont(new Font("宋体", Font.BOLD, 13));
		meterPlot.setBackgroundImageAlpha(0);

		meterPlot.setRange(new Range(0.0d, maxr));
		meterPlot.addInterval(new MeterInterval("正常", new Range(0.0D, minr),
				Color.BLUE, null, new Color(0, 0, 255, 64)));
		meterPlot.addInterval(new MeterInterval("中等", new Range(minr,
				(2 * minr)), Color.GREEN, null, new Color(0, 255, 0, 64)));
		meterPlot.addInterval(new MeterInterval("严重", new Range((2 * minr),
				maxr), Color.RED, null, new Color(255, 0, 0, 128)));

		meterPlot.setDialShape(DialShape.CHORD);
		meterPlot.setDialBackgroundPaint(Color.WHITE);
		meterPlot.setRange(new Range(0, 100));
		meterPlot.setDialOutlinePaint(Color.GRAY);
		meterPlot.setNeedlePaint(Color.BLACK);
		meterPlot.setTickLabelsVisible(true);
		meterPlot.setTickLabelPaint(Color.BLACK);
		meterPlot.setTickPaint(Color.GRAY);
		meterPlot.setTickLabelFormat(NumberFormat.getNumberInstance());
		meterPlot.setTickSize(10);
		meterPlot.setMeterAngle(280);
		meterPlot.setValuePaint(Color.BLACK);
		dataset.setValue(1.05);
		meterPlot.setValueFont(new Font("宋体", 1, 14));

		ChartFrame frame = new ChartFrame("識別率 ", chart, true);
		frame.pack();
		frame.setVisible(true);

	}
}
