/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.space.moniter.TestSpaceMoniterTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-26 下午02:46:41 
 *   LastChange: 2013-8-26 下午02:46:41 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.space.moniter;

import static org.junit.Assert.fail;

import org.junit.Test;

import z.z.w.project.SuperClass;

/**
 * z.z.w.project.test.space.moniter.TestSpaceMoniterTest.java
 */
public class TestSpaceMoniterTest extends SuperClass {

	/**
	 * Test method for
	 * {@link z.z.w.project.test.space.moniter.TestSpaceMoniter#run()}.
	 */
	@Test
	public void testRun() {
		try {

			TestSpaceMoniter server = new TestSpaceMoniter();
			server.run();

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
