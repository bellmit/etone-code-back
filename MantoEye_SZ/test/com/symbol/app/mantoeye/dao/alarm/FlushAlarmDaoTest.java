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
import com.symbol.wp.modules.orm.Page;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class FlushAlarmDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public FlushAlarmDao getFlushAlarmDao() {
		return flushAlarmDao;
	}

	@Resource
	public void setFlushAlarmDao(FlushAlarmDao flushAlarmDao) {
		this.flushAlarmDao = flushAlarmDao;
	}

	private DataSource dataSource;

	private FlushAlarmDao flushAlarmDao;

	@Test
	public void testInitQuery() {
		int year = 2010;
		int month = 9;
		int day = 20;

		String tableName = "vAlarmBscFlush";
		String typeName = "BSC";

		tableName = "vAlarmCgiFlush";
		typeName = "CGI";
		// tableName = "vAlarmSgsnFlush";
		// typeName = "SGSN";

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("intYear", year);
		condition.put("intMonth", month);
		condition.put("intDay", day);
		condition.put("tableName", tableName);
		condition.put("typeName", typeName);

		AlarmBean bean = flushAlarmDao.initQuery(condition);

		Assert.assertThat(bean, Matchers.notNullValue());

		System.out.println(bean.getCountDecrease());
		System.out.println(bean.getCountIncrease());
		System.out.println(bean.getCountLow());
		System.out.println(bean.getTypeName());

	}

	@Test
	public void testGetTotalCount() {
		String startDate = "2010-09-01";
		String endDate = "2010-09-22";

		String tableName = "vAlarmCgiFlush";
		String columnName = "vcCgi";
		tableName = "vAlarmBscFlush";
		columnName = "vcName";
		tableName = "vAlarmSgsnFlush";
		columnName = "vcName";

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("startDate", startDate);
		condition.put("endDate", endDate);
		condition.put("tableName", tableName);
		condition.put("columnName", columnName);

		int total = flushAlarmDao.getTotalCount(condition);

		Assert.assertThat(total, Matchers.notNullValue());

	}

	@Test
	public void testQueryHistory() {
		String startDate = "2010-09-01";
		String endDate = "2010-09-22";

		String tableName = "vAlarmCgiFlush";
		String columnName = "vcCgi";
		tableName = "vAlarmBscFlush";
		columnName = "vcName";
		tableName = "vAlarmSgsnFlush";
		columnName = "vcName";

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("startDate", startDate);
		condition.put("endDate", endDate);
		condition.put("tableName", tableName);
		condition.put("columnName", columnName);

		Page<AlarmBean> page = new Page<AlarmBean>();
		List<AlarmBean> list = flushAlarmDao.queryHistory(page, condition);

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
		}

	}

	@Test
	public void testInitQuery2() {
		int year = 2010;
		int month = 9;
		int day = 20;

		String tableName = "vAlarmBscFlush";
		String typeName = "BSC";

		tableName = "vAlarmCgiFlush";
		typeName = "CGI";
		// tableName = "vAlarmSgsnFlush";
		// typeName = "SGSN";

		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("intYear", year);
		condition.put("intMonth", month);
		condition.put("intDay", day);
		condition.put("tableName", tableName);
		condition.put("typeName", typeName);

		AlarmBean bean = flushAlarmDao.initQuery2(condition);

		Assert.assertThat(bean, Matchers.notNullValue());

		System.out.println(bean.getCountDecrease());
		System.out.println(bean.getCountIncrease());
		System.out.println(bean.getCountLow());
		System.out.println(bean.getTypeName());

	}

}
