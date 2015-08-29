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

import com.symbol.app.mantoeye.dto.alarm.AlarmLimitBean;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class AlarmLimitConfigDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private DataSource dataSource;

	private AlarmLimitConfigDao alarmLimitConfigDao;

	@Test
	public void testQueryAlarmLimit() {
		String alarmTypeLow = "8";
		String alarmTypeDerease = "9";
		String alarmTypeInrease = "10";

		String type = "SGSN";

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("alarmTypeLow", alarmTypeLow);
		condition.put("alarmTypeDerease", alarmTypeDerease);
		condition.put("alarmTypeInrease", alarmTypeInrease);
		condition.put("type", type);

		List<AlarmLimitBean> list = alarmLimitConfigDao
				.queryAlarmLimit(condition);

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
	public void testUpdateAlarmLimit() {

		String alarmType = "1";
		String alarmLimit = "45"; // 阀值
		String timeLevel = "2"; // 时间粒度
		String operator = "<";

		Map<String, Object> condition = new HashMap<String, Object>();

		condition.put("alarmType", alarmType);
		condition.put("operator", operator);
		condition.put("alarmLimit", alarmLimit);
		condition.put("timeLevel", timeLevel);

		alarmLimitConfigDao.updateAlarmLimit(condition);

	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public AlarmLimitConfigDao getAlarmLimitConfigDao() {
		return alarmLimitConfigDao;
	}

	@Resource
	public void setAlarmLimitConfigDao(AlarmLimitConfigDao alarmLimitConfigDao) {
		this.alarmLimitConfigDao = alarmLimitConfigDao;
	}

}
