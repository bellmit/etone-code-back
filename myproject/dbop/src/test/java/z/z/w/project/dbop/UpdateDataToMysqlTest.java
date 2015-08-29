/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.dbop.UpdateDataToMysqlTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-8-20 下午11:02:16 
 *   LastChange: 2013-8-20 下午11:02:16 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.dbop;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import z.z.w.project.SuperClass;

/**
 * z.z.w.project.dbop.UpdateDataToMysqlTest.java
 */
public class UpdateDataToMysqlTest extends SuperClass {

	@Before
	public void before() {
		// Thread thread = new Thread(new Runnable() {
		//
		// public void run() {
		//
		// // try {
		// // ReadFileReceiver server = new ReadFileReceiver();
		// // server.run();
		// // } catch (Exception e) {
		// // fail("MASSAGE : " + e.getMessage() + "\nCAUSE : "
		// // + e.getCause() + "\nCLASS : " + e.getClass());
		// // }
		// }
		// });
		// thread.start();

	}

	/**
	 * Test method for
	 * {@link z.z.w.project.dbop.UpdateDataToMysql#UpdateDataToMysql()}.
	 */
	@Test
	public void testUpdateDataToMysql() {
		try {

			UpdateDataToMysql server = new UpdateDataToMysql();
			server.run();

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
