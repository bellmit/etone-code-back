/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.macaddress.TestMacAddressTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-21 上午11:29:45 
 *   LastChange: 2013-8-21 上午11:29:45 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.macaddress;

import static org.junit.Assert.fail;

import org.junit.Test;

import z.z.w.project.SuperClass;

/**
 * z.z.w.project.test.macaddress.TestMacAddressTest.java
 */
public class TestMacAddressTest extends SuperClass {

	/**
	 * Test method for
	 * {@link z.z.w.project.test.macaddress.TestMacAddress#run()}.
	 */
	@Test
	public void testRun() {
		try {

			TestMacAddress server = new TestMacAddress();
			server.run();

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
