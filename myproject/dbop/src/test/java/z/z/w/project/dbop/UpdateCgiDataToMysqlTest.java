/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.dbop.UpdateCgiDataToMysqlTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-21 上午01:35:31 
 *   LastChange: 2013-8-21 上午01:35:31 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.dbop;

import static org.junit.Assert.fail;

import org.junit.Test;

import z.z.w.project.SuperClass;

/**
 * z.z.w.project.dbop.UpdateCgiDataToMysqlTest.java
 */
public class UpdateCgiDataToMysqlTest extends SuperClass {

	/**
	 * Test method for {@link z.z.w.project.dbop.UpdateCgiDataToMysql#run()}.
	 */
	@Test
	public void testRun() {
		try {

			UpdateCgiDataToMysql server = new UpdateCgiDataToMysql();
			server.run();

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
