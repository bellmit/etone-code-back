/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.dbop.FSSybaseIqOpertorTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-22 下午05:10:52 
 *   LastChange: 2013-10-22 下午05:10:52 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.dbop;

import static org.junit.Assert.fail;

import org.junit.Test;

import z.z.w.project.SuperClass;

/**
 * z.z.w.project.dbop.FSSybaseIqOpertorTest.java
 */
public class FSSybaseIqOpertorTest extends SuperClass {

	/**
	 * Test method for {@link z.z.w.project.dbop.FSSybaseIqOpertor#run()}.
	 */
	@Test
	public void testRun() {
		try {

			FSSybaseIqOpertor server = new FSSybaseIqOpertor();
			server.run();

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
