/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.file.FileToolsTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-12 下午03:26:14 
 *   LastChange: 2013-9-12 下午03:26:14 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.file;

import org.junit.Test;

/**
 * z.z.w.project.util.file.FileToolsTest.java
 */
public class FileToolsTest {

	/**
	 * Test method for
	 * {@link z.z.w.project.util.file.FileTools#freeSpaceGB(java.lang.String)}.
	 */
	@Test
	public void testFreeSpaceGBString() {
		System.out.println(FileTools.freeSpaceKB("D:\\03_localsvn"));
		System.out.println(FileTools.freeSpaceGB("D:\\03_localsvn"));
		System.out.println(FileTools.freeSpaceMB("E:\\01-download"));
	}

}
