package com.symbol.app.mantoeye.dao.common;

import static org.junit.Assert.fail;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations =
{ "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class CommonQueryDAOTest extends
		AbstractTransactionalJUnit4SpringContextTests
{
	private DataSource dataSource;

	public DataSource getDataSource()
	{
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	@Test
	public void testGetSession()
	{
		
		Assert.assertThat(actual, matcher)
		fail("Not yet implemented");
	}

}
