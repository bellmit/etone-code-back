package com.symbol.app.mantoeye.service.alarm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dto.alarm.AlarmBean;
import com.symbol.app.mantoeye.dto.alarm.AlarmRatioLimmit;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class DistinguishRatioAlarmManagerTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private DataSource dataSource;

	private DistinguishRatioAlarmManager distinguishRatioAlarmManager;

	@Test
	public void testFindAlarmRatioLimmit() {
		String typeName = "CGI";
		AlarmRatioLimmit ar = distinguishRatioAlarmManager
				.findAlarmRatioLimmit(typeName);
		Assert.assertThat(ar, Matchers.notNullValue());
		System.out.println(ar.getMaxLimmit());
		System.out.println(ar.getMinLimmit());
		System.out.println(ar.getTypeName());
	}

	@Test
	public void testConfigAlarmRatioLimmit() {
		String typeName = "CGI";
		String minLimmit = "1.567";
		String maxLimmit = "10.32";

		distinguishRatioAlarmManager.configAlarmRatioLimmit(typeName,
				minLimmit, maxLimmit);
	}

	@Test
	public void testQueryAlarmRatioLimmit() {
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("CGI", "30");
		condition.put("BSC", "40");
		condition.put("SGSN", "10");
		condition.put("业务", "3");
		List<AlarmRatioLimmit> list = distinguishRatioAlarmManager
				.queryAlarmRatioLimmit(condition);

		Assert.assertThat(list, Matchers.notNullValue());

		for (AlarmRatioLimmit ar : list) {
			System.out.println(ar.getLimmit());
			System.out.println(ar.getMaxLimmit());
			System.out.println(ar.getMinLimmit());
			System.out.println(ar.getTypeName());
			System.out.println(ar.getReAlarmRatio());
			System.out.println(ar.getLimmitLevel());
			System.out.println("-----------------------");
		}
	}

	@Test
	public void testPieQueryByType() {

		String year = "2010";
		String month = "9";
		String day = "19";

		String type = "2";

		AlarmBean bean = distinguishRatioAlarmManager.pieQueryByTpye(year,
				month, day, type);

		Assert.assertThat(bean, Matchers.notNullValue());

		System.out.println(bean.getAlarmRatio());
		System.out.println(bean.getReAlarmRatio());

	}

	@Test
	public void testQueryHistoryAlarm() {
		String startDate = "2010-09-01";
		String endDate = "2010-09-19";
		String type = "1";
		GridServerHandler gridServerHandler = new GridServerHandler();
		Page<AlarmBean> page = distinguishRatioAlarmManager.queryHistoryAlarm(
				startDate, endDate, type, gridServerHandler);

		Assert.assertThat(page, Matchers.notNullValue());

		for (AlarmBean bean : page.getResult()) {
			System.out.println(bean.getAlarmRatio());
			System.out.println(bean.getReAlarmRatio());
			System.out.println(bean.getFullDate());
		}

	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DistinguishRatioAlarmManager getDistinguishRatioAlarmManager() {
		return distinguishRatioAlarmManager;
	}

	@Resource
	public void setDistinguishRatioAlarmManager(
			DistinguishRatioAlarmManager distinguishRatioAlarmManager) {
		this.distinguishRatioAlarmManager = distinguishRatioAlarmManager;
	}

}
