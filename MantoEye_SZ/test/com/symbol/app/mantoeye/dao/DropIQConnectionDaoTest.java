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
public class DropIQConnectionDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Test
	public void testDropIQConnections() {

		List<DropIQConnectionDto> list = dropIQConnectionDao.iqConnectionList();

		Assert.assertNotNull(list);

		for (DropIQConnectionDto dto : list) {
			System.out.print(dto.getConnectionId() + "\t");
			System.out.print(dto.getConnectionIp() + "\t");
			System.out.print(dto.getCreateTime() + "\t");
			System.out.print(dto.getLastIQCmdTime() + "\t");
			System.out.print(dto.getLastReqTime() + "\t");
			System.out.println();
		}
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
