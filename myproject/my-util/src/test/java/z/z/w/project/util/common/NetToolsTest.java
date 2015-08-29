/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.common.NetToolsTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-12 上午11:27:48 
 *   LastChange: 2013-9-12 上午11:27:48 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.common;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

/**
 * z.z.w.project.util.common.NetToolsTest.java
 */
public class NetToolsTest {

	/**
	 * Test method for
	 * {@link z.z.w.project.util.common.NetTools#getMacAddressList()}.
	 */
	@Test
	public void testGetMacAddressList() {
		try {
			List<String> list = NetTools.getMacAddressList();
			for (String str : list) {
				System.out.println(str);
			}
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}
}
