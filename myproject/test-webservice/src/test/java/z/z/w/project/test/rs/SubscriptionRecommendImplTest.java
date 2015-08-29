/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.rs.SubscriptionRecommendImplTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-3 下午06:31:03 
 *   LastChange: 2013-10-3 下午06:31:03 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.rs;

import static org.junit.Assert.fail;

import org.junit.Test;

import z.z.w.project.test.SuperClass;

/**
 * z.z.w.project.test.rs.SubscriptionRecommendImplTest.java
 */
public class SubscriptionRecommendImplTest extends SuperClass {

	/**
	 * Test method for
	 * {@link z.z.w.project.test.rs.SubscriptionRecommendImpl#getUserMsisdnInfos(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetUserMsisdnInfos() {
		try {
			SubscriptionRecommendImpl impl = new SubscriptionRecommendImpl();
			System.out.println(impl.getUserMsisdnInfos("13829323333"));
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
