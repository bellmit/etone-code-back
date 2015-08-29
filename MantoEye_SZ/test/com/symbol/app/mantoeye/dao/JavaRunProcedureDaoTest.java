package com.symbol.app.mantoeye.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.entity.DropIQConnectionDto;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class JavaRunProcedureDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Test
	public void testDropIQConnections() {

		Long taskId = dropIQConnectionDao.getTaskId();

		Assert.assertNotNull(taskId);

		System.out.println(taskId);

	}

	public DropIQConnectionDao getDropIQConnectionDao() {
		return dropIQConnectionDao;
	}

	@Resource
	public void setDropIQConnectionDao(DropIQConnectionDao dropIQConnectionDao) {
		this.dropIQConnectionDao = dropIQConnectionDao;
	}

	private DataSource dataSource;
	private DropIQConnectionDao dropIQConnectionDao;

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
