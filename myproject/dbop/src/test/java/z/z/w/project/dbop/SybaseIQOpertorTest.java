/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.dbop.SybaseIQOpertorTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-10 上午11:12:29 
 *   LastChange: 2013-9-10 上午11:12:29 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.dbop;

import static org.junit.Assert.*;

import org.junit.Test;

import z.z.w.project.SuperClass;

/**
 * z.z.w.project.dbop.SybaseIQOpertorTest.java
 */
public class SybaseIQOpertorTest extends SuperClass {

	/**
	 * Test method for {@link z.z.w.project.dbop.SybaseIQOpertor#run()}.
	 */
	@Test
	public void testRun() {
		try {

			SybaseIQOpertor server = new SybaseIQOpertor();
			server.run();

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
