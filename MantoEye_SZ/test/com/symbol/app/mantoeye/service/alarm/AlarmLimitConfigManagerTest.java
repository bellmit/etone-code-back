package com.symbol.app.mantoeye.service.alarm;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dto.alarm.AlarmLimitBean;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class AlarmLimitConfigManagerTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private DataSource dataSource;

	private AlarmLimitConfigManager alarmLimitConfigManager;

	@Test
	public void testInitQuery() {
		String type = "SGSN";

		List<AlarmLimitBean> list = alarmLimitConfigManager.initQuery(type);

		Assert.assertThat(list, Matchers.notNullValue());

		for (AlarmLimitBean bean : list) {
			System.out.println(bean.getAlarmName());
			System.out.println(bean.getAlarmType());
			System.out.println(bean.getOperator());
			System.out.println(bean.getAlarmLimit());
			System.out.println(bean.getTimeLevel());
			System.out.println("------------------");
		}
	}

	@Test
	public void testUpdateAlarm() {
		String type = "BSC";
		String alarmType = "1";
		String alarmLimit = "45"; // 阀值
		String timeLevel = "2"; // 时间粒度
		String operator = "<";

		alarmLimitConfigManager.updateAlarm(type, alarmType, alarmLimit,
				timeLevel, operator);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public AlarmLimitConfigManager getAlarmLimitConfigManager() {
		return alarmLimitConfigManager;
	}

	@Resource
	public void setAlarmLimitConfigManager(
			AlarmLimitConfigManager alarmLimitConfigManager) {
		this.alarmLimitConfigManager = alarmLimitConfigManager;
	}

}
