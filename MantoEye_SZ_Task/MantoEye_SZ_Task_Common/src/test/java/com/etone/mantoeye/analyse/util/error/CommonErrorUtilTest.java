/**
 *  com.etone.mantoeye.analyse.util.error.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.util.error;

import org.junit.Test;

import com.ibatis.common.jdbc.exception.NestedSQLException;

/**
 * @author Wu Zhenzhen
 * @version May 21, 2012 5:11:28 PM
 */
public class CommonErrorUtilTest {

	/**
	 * Test method for
	 * {@link com.etone.mantoeye.analyse.util.error.CommonErrorUtil#isTableLockError(java.lang.Exception)}
	 * .
	 */
	@Test
	public final void testIsTableLockError() {

		boolean flag = CommonErrorUtil
				.isTableLockError(new NestedSQLException(
						new com.sybase.jdbc3.jdbc.SybSQLException(
								" ASA Error -1000011: Transaction 206054872 attempted to access 'tbTask' created by transaction 20605487",
								"2", 3, 4, 5, "6", "7", 8, null, 9, 10)));

		System.out.println(flag);
		flag = CommonErrorUtil
				.isTableLockError(new NestedSQLException(
						new com.sybase.jdbc3.jdbc.SybSQLException(
								"  ASA Error -210: User 'DBA' has the row in 'tbFactTableOperation' locked ",
								"2", 3, 4, 5, "6", "7", 8, null, 9, 10)));

		System.out.println(flag);

	}
}
