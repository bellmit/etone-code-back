/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.dbop.InsertDataToMysqlTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-21 下午04:11:10 
 *   LastChange: 2013-10-21 下午04:11:10 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.dbop;

import static org.junit.Assert.*;

import org.junit.Test;

import z.z.w.project.SuperClass;

/**
 * z.z.w.project.dbop.InsertDataToMysqlTest.java
 */
public class InsertDataToMysqlTest extends SuperClass {

	/**
	 * Test method for {@link z.z.w.project.dbop.InsertDataToMysql#run()}.
	 */
	@Test
	public void testRun() {
		try {

			InsertDataToMysql server = new InsertDataToMysql();
			server.run();

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
