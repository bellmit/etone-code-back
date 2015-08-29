package com.symbol.app.mantoeye.web.action.alarm;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class MonitoringDBActionTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private DataSource dataSource;
	private MonitoringDBAction monitoringDBAction;

	@Test
	public void testInit() {
		String init = monitoringDBAction.init();
		System.out.println(init);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public MonitoringDBAction getMonitoringDBAction() {
		return monitoringDBAction;
	}

	@Resource
	public void setMonitoringDBAction(MonitoringDBAction monitoringDBAction) {
		this.monitoringDBAction = monitoringDBAction;
	}

}
