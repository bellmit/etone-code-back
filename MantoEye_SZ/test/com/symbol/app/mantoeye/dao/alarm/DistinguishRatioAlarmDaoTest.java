package com.symbol.app.mantoeye.dao.alarm;

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

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class DistinguishRatioAlarmDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private DataSource dataSource;

	private DistinguishRatioAlarmDao distinguishRatioAlarmDao;

	@Test
	public void testFindAlarmRatioLimmit() {
		String type = "1";
		AlarmRatioLimmit ar = distinguishRatioAlarmDao
				.findAlarmRatioLimmit(type);
		Assert.assertThat(ar, Matchers.notNullValue());
		System.out.println(ar.getMaxLimmit());
		System.out.println(ar.getMinLimmit());
		System.out.println(ar.getTypeName());
	}

	@Test
	public void testConfigAlarmRatioLimmit() {
		String type = "1";
		String minLimmit = "0.01";
		String maxLimmit = "0.1";

		distinguishRatioAlarmDao.configAlarmRatioLimmit(type, minLimmit,
				maxLimmit);

	}

	@Test
	public void testQueryAlarmRatioLimmit() {
		List<AlarmRatioLimmit> list = distinguishRatioAlarmDao
				.queryAlarmRatioLimmit();

		Assert.assertThat(list, Matchers.notNullValue());

		for (AlarmRatioLimmit ar : list) {
			System.out.println(ar.getLimmit());
			System.out.println(ar.getMaxLimmit());
			System.out.println(ar.getMinLimmit());
			System.out.println(ar.getTypeName());
			System.out.println("-----------------------");
			System.out.println();
		}
	}

	@Test
	public void testPieQueryByType() {

		int year = 2010;
		int month = 9;
		int day = 19;

		int type = 1;

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("intType", type);
		condition.put("intYear", year);
		condition.put("intMonth", month);
		condition.put("intDay", day);

		AlarmBean bean = distinguishRatioAlarmDao.pieQueryByType(condition);

		// Object bean = new Object();

		Assert.assertThat(bean, Matchers.notNullValue());

		System.out.println(bean.getAlarmRatio());
		System.out.println(bean.getReAlarmRatio());
	}

	@Test
	public void testQueryHistoryAlarm() {
		String startDate = "2010-09-01";
		String endDate = "2010-09-19";
		int type = 1;

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("intType", type);
		condition.put("startDate", startDate);
		condition.put("endDate", endDate);

		Page<AlarmBean> page = new Page<AlarmBean>();

		List<AlarmBean> list = distinguishRatioAlarmDao.queryHistoryAlarm(page,
				condition);

		Assert.assertThat(list, Matchers.notNullValue());

		for (AlarmBean bean : list) {
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

	public DistinguishRatioAlarmDao getDistinguishRatioAlarmDao() {
		return distinguishRatioAlarmDao;
	}

	@Resource
	public void setDistinguishRatioAlarmDao(
			DistinguishRatioAlarmDao distinguishRatioAlarmDao) {
		this.distinguishRatioAlarmDao = distinguishRatioAlarmDao;
	}

}
