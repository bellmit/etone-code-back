package com.symbol.app.mantoeye.web.action.alarm;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class AlarmLimitConfigActionTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private DataSource dataSource;

	private AlarmLimitConfigAction alarmLimitConfigAction;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public AlarmLimitConfigAction getAlarmLimitConfigAction() {
		return alarmLimitConfigAction;
	}

	@Resource
	public void setAlarmLimitConfigAction(
			AlarmLimitConfigAction alarmLimitConfigAction) {
		this.alarmLimitConfigAction = alarmLimitConfigAction;
	}

}
