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
public class FlushAlarmActionTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public FlushAlarmAction getFlushAlarmAction() {
		return flushAlarmAction;
	}

	@Resource
	public void setFlushAlarmAction(FlushAlarmAction flushAlarmAction) {
		this.flushAlarmAction = flushAlarmAction;
	}

	private DataSource dataSource;

	private FlushAlarmAction flushAlarmAction;

}
