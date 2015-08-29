package com.symbol.app.mantoeye.dao.alarm;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.bean.MonitoringBean;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class MonitoringDBDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Test
	public void testInitQuery() {

		String currentTime = "2010-11-08";

		MonitoringBean bean = monitoringDBDao.initQuery(currentTime);

		System.out.println(bean.getCurrentTime());
		System.out.println(bean.getUnit());
		System.out.println(bean.getConnMax());
		System.out.println(bean.getCurConn());
		System.out.println(bean.getMainSpaceRate());
		System.out.println(bean.getMainSpaceSize());
		System.out.println(bean.getOtherVersion());
		System.out.println(bean.getUsedMainSpaceSize());

		Assert.assertThat(bean, Matchers.notNullValue());

	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public MonitoringDBDao getMonitoringDBDao() {
		return monitoringDBDao;
	}

	@Resource
	public void setMonitoringDBDao(MonitoringDBDao monitoringDBDao) {
		this.monitoringDBDao = monitoringDBDao;
	}

	private DataSource dataSource;
	private MonitoringDBDao monitoringDBDao;

}
