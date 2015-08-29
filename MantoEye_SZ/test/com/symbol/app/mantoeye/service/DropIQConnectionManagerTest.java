package com.symbol.app.mantoeye.service;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.entity.DropIQConnectionDto;

@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class DropIQConnectionManagerTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	private DataSource dataSource;
	private DropIQConnectionManager dropIQConnectionManager;
 
	@Test
	public void testIqConnectionList() {
		List<DropIQConnectionDto> list = dropIQConnectionManager
				.iqConnectionList();
    
		Assert.assertNotNull(list);

	 	for (DropIQConnectionDto dto : list) {
			System.out.print(dto.getConnectionId() + "\t");
			System.out.print(dto.getLastReqTime() + "\t");
			System.out.print(dto.getLastIQCmdTime() + "\t");
			System.out.print(dto.getCreateTime() + "\t");
			System.out.print(dto.getConnectionIp() + "\t");
			System.out.println();
		}
	}

	@Test
	public void testDropConnection() {
		dropIQConnectionManager.dropConnection();

		List<DropIQConnectionDto> list = dropIQConnectionManager
				.iqConnectionList();

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

	public DataSource getDataSource() {
		return dataSource;
	}

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DropIQConnectionManager getDropIQConnectionManager() {
		return dropIQConnectionManager;
	}

	@Resource
	public void setDropIQConnectionManager(
			DropIQConnectionManager dropIQConnectionManager) {
		this.dropIQConnectionManager = dropIQConnectionManager;
	}
}
