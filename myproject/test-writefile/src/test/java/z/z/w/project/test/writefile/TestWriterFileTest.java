/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.writefile.TestWriterFileTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-5 上午11:30:37 
 *   LastChange: 2013-9-5 上午11:30:37 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.writefile;

import static org.junit.Assert.fail;

import org.junit.Test;

import z.z.w.project.SuperClass;

/**
 * z.z.w.project.test.writefile.TestWriterFileTest.java
 */
public class TestWriterFileTest extends SuperClass {

	/**
	 * Test method for {@link z.z.w.project.test.writefile.TestWriterFile#run()}
	 * .
	 */
	@Test
	public void testRun() {
		try {

			TestWriterFile server = new TestWriterFile();
			server.run();

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
