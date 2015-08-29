/**
 * com.symbol.app.mantoeye.dao.ud.LocalUserMsisdnCheckDaoTest.java
 */
package com.symbol.app.mantoeye.dao.ud;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Wu Zhenzhen
 * @version 2013-10-17 下午12:54:31
 */
@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class LocalUserMsisdnCheckDaoTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	private DataSource dataSource;
	private LocalUserMsisdnCheckDao localUserMsisdnCheckDao;

	/**
	 * <br>
	 * Created on: 2013-10-17 下午12:56:47
	 * 
	 * @return the localUserMsisdnCheckDao
	 */
	public LocalUserMsisdnCheckDao getLocalUserMsisdnCheckDao() {
		return localUserMsisdnCheckDao;
	}

	/**
	 * <br>
	 * Created on: 2013-10-17 下午12:56:47
	 * 
	 * @param localUserMsisdnCheckDao
	 *            the localUserMsisdnCheckDao to set
	 */
	@Resource
	public void setLocalUserMsisdnCheckDao(
			LocalUserMsisdnCheckDao localUserMsisdnCheckDao) {
		this.localUserMsisdnCheckDao = localUserMsisdnCheckDao;
	}

	/**
	 * <br>
	 * Created on: 2013-10-17 下午12:55:10
	 * 
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * <br>
	 * Created on: 2013-10-17 下午12:55:10
	 * 
	 * @param dataSource
	 *            the dataSource to set
	 */
	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Test method for
	 * {@link com.symbol.app.mantoeye.dao.ud.LocalUserMsisdnCheckDao#getLocalMsisdn()}
	 * .
	 */
	@Test
	public void testGetLocalMsisdn() {
		List<Integer> list = localUserMsisdnCheckDao.getLocalMsisdn();
		for (Integer i : list) {
			System.out.println(i);
		}
	}

}
