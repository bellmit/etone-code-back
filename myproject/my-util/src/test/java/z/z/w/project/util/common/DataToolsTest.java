/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.common.DataToolsTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-22 下午02:56:27 
 *   LastChange: 2013-11-22 下午02:56:27 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.common;

import org.junit.Test;

/**
 * z.z.w.project.util.common.DataToolsTest.java
 */
public class DataToolsTest {

	/**
	 * Test method for {@link z.z.w.project.util.common.DataTools#isEmpty(char)}.
	 */
	@Test
	public void testIsEmptyChar() {
		char c = ' ';
		c = '\0';
		System.out.println(DataTools.isEmpty(c));
	}

}
