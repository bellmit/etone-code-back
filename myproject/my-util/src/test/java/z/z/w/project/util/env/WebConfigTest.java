/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.env.WebConfigTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-8 下午10:51:08 
 *   LastChange: 2013-10-8 下午10:51:08 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.env;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * z.z.w.project.util.env.WebConfigTest.java
 */
public class WebConfigTest {

	/**
	 * Test method for {@link z.z.w.project.util.env.WebConfig#WebConfig()}.
	 */
	@Test
	public void testWebConfig() {
		try {
			String path = ClassLoader.getSystemResource("global.cfg").getPath();
			System.out.println(path);
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
