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
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.tools.gtgrid.server.GridServerHandler;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class FlushAlarmManagerTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private DataSource dataSource;
	private FlushAlarmManager flushAlarmManager;

	@Test
	public void testDoCancelWarn() {
		String alarmInfo = "測試測試測試測試測試測試測試";
		String typeName = "1";
		// String typeName = "SZ27B,SZ27A,SZ21B,SAMBSC2";
		String user = "admin";
		String type = "SGSN";
		flushAlarmManager.doCancelWarn(alarmInfo, typeName, user, type, "");
	}

	@Test
	public void testInitQuery() {
		int year = 2010;
		int month = 9;
		int day = 20;

		List<AlarmBean> list = flushAlarmManager.initQuery(year, month, day);

		Assert.assertThat(list, Matchers.notNullValue());

		for (AlarmBean bean : list) {
			System.out.println(bean.getCountDecrease());
			System.out.println(bean.getCountIncrease());
			System.out.println(bean.getCountLow());
			System.out.println(bean.getTypeName());
		}

	}

	@Test
	public void testGetTotalCount() {

	}

	@Test
	public void testQueryHistory() {
		String startDate = "2010-09-01";
		String endDate = "2010-09-22";

		String type = "BSC";
		type = "CGI";

		GridServerHandler gridServerHandler = new GridServerHandler();
		Page<AlarmBean> page = flushAlarmManager.queryHistoryAlarm(startDate,
				endDate, type, gridServerHandler, "", "");

		Assert.assertThat(page, Matchers.notNullValue());

		for (AlarmBean bean : page.getResult()) {
			System.out.println(bean.getTypeName());
			System.out.println(bean.getChangeRatio());
			System.out.println(bean.getDisposeTime());
			System.out.println(bean.getFullDate());
			System.out.println(bean.getIsDecrease());
			System.out.println(bean.getIsIncrease());
			System.out.println(bean.getIsLow());
			System.out.println(bean.getProcessor());
			System.out.println(bean.getChangeFlush());
			System.out.println(bean.getAlarmId());
		}

	}

	@Test
	public void testQueryHistoryList() {
		String startDate = "2010-09-01";
		String endDate = "2010-09-22";

		String type = "BSC";
		type = "CGI";

		List<AlarmBean> list = flushAlarmManager.queryHistoryAlarm(startDate,
				endDate, type, "", "");

		Assert.assertThat(list, Matchers.notNullValue());

		for (AlarmBean bean : list) {
			System.out.println(bean.getTypeName());
			System.out.println(bean.getChangeRatio());
			System.out.println(bean.getDisposeTime());
			System.out.println(bean.getFullDate());
			System.out.println(bean.getIsDecrease());
			System.out.println(bean.getIsIncrease());
			System.out.println(bean.getIsLow());
			System.out.println(bean.getProcessor());
			System.out.println(bean.getChangeFlush());
			System.out.println(bean.getAlarmId());
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public FlushAlarmManager getFlushAlarmManager() {
		return flushAlarmManager;
	}

	@Resource
	public void setFlushAlarmManager(FlushAlarmManager flushAlarmManager) {
		this.flushAlarmManager = flushAlarmManager;
	}

}
