/**
 * com.symbol.app.mantoeye.service.ud.LocalUserMsisdnCheckManagerTest.java
 */
package com.symbol.app.mantoeye.service.ud;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.entity.UserBelongMsisdn;

/**
 * @author Wu Zhenzhen
 * @version 2013-10-17 下午01:17:14
 */
@ContextConfiguration(locations = { "classpath:/application-test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class LocalUserMsisdnCheckManagerTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	private DataSource dataSource;

	private LocalUserMsisdnCheckManager localUserMsisdnCheckManager;

	/**
	 * Test method for
	 * {@link com.symbol.app.mantoeye.service.ud.LocalUserMsisdnCheckManager#checkLocalMsisdnAll(java.util.List)}
	 * .
	 */
	@Test
	public void testCheckLocalMsisdnAll() {
		List<UserBelongMsisdn> ml = new ArrayList<UserBelongMsisdn>();
		UserBelongMsisdn ub = new UserBelongMsisdn();
		ub.setNmMsisdn("13622856209");
		ml.add(ub);
		ub = new UserBelongMsisdn();
		ub.setNmMsisdn("13510013865");
		ml.add(ub);
		ub = new UserBelongMsisdn();
		ub.setNmMsisdn("18899871503");
		ml.add(ub);
		ub = new UserBelongMsisdn();
		ub.setNmMsisdn("13643021785");
		ml.add(ub);

		System.out.println(ml.size());
		List<UserBelongMsisdn> mll = localUserMsisdnCheckManager
				.checkLocalMsisdnAll(ml);
		for (UserBelongMsisdn um : mll) {
			// System.out.println(um.getNmMsisdn());
		}
		System.out.println(mll.size());
	}

	/**
	 * <br>
	 * Created on: 2013-10-17 下午01:17:58
	 * 
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * <br>
	 * Created on: 2013-10-17 下午01:17:58
	 * 
	 * @param dataSource
	 *            the dataSource to set
	 */
	@Resource
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * <br>
	 * Created on: 2013-10-17 下午01:17:58
	 * 
	 * @return the localUserMsisdnCheckManager
	 */
	public LocalUserMsisdnCheckManager getLocalUserMsisdnCheckManager() {
		return localUserMsisdnCheckManager;
	}

	/**
	 * <br>
	 * Created on: 2013-10-17 下午01:17:58
	 * 
	 * @param localUserMsisdnCheckManager
	 *            the localUserMsisdnCheckManager to set
	 */
	@Resource
	public void setLocalUserMsisdnCheckManager(
			LocalUserMsisdnCheckManager localUserMsisdnCheckManager) {
		this.localUserMsisdnCheckManager = localUserMsisdnCheckManager;
	}

}
