package com.symbol.app.mantoeye.dao.mms;

import static org.junit.Assert.fail;

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
public class SpMmsTitleDAOTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	private DataSource dataSource;
	private SpMmsTitleDAO spMmsTitleDAO;

	@Test
	public void testBuildSqlBig() {

		String sql = spMmsTitleDAO.buildSqlBig(false, 1, 2, "2010-08-26",
				"2010-08-26", null, "");
		System.out.println(sql);

	}

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public SpMmsTitleDAO getSpMmsTitleDAO() {
		return spMmsTitleDAO;
	}

	@Resource
	public void setSpMmsTitleDAO(SpMmsTitleDAO spMmsTitleDAO) {
		this.spMmsTitleDAO = spMmsTitleDAO;
	}

}
