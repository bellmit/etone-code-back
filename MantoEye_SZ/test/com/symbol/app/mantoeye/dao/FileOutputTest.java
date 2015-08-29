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
public class FileOutputTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Test
	public void testDropIQConnections() {

		String sql = "query_queryCMNETDataFromFactTable";
		StringBuffer option = new StringBuffer();
		if (sql.equalsIgnoreCase("query_queryCMNETDataFromFactTable")) {
			option.append("set temporary option TEMP_EXTRACT_NAME1='/opt/ss.txt';");
		} else {
			option.append("set temporary option TEMP_EXTRACT_NAME1='/opt/tt.txt';");
		}
		option.append("set temporary option TEMP_EXTRACT_COLUMN_DELIMITER=';';");
		option.append("set temporary option TEMP_EXTRACT_BINARY='OFF';");
		option.append("set temporary option TEMP_EXTRACT_SWAP='OFF';");
		
		dropIQConnectionDao.update(sql);
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
