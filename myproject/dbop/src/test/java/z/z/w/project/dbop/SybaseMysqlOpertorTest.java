/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.dbop.SybaseMysqlOpertorTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-22 上午10:48:55 
 *   LastChange: 2013-10-22 上午10:48:55 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.dbop;

import static org.junit.Assert.fail;

import org.junit.Test;

import z.z.w.project.SuperClass;

/**
 * z.z.w.project.dbop.SybaseMysqlOpertorTest.java
 */
public class SybaseMysqlOpertorTest extends SuperClass {

	/**
	 * Test method for {@link z.z.w.project.dbop.FSMysqlOpertor#getCache()}.
	 */
	@Test
	public void testGetCache() {
		try {

			FSMysqlOpertor server = new FSMysqlOpertor();
			server.run();

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
