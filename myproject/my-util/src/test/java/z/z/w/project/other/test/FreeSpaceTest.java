/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.other.test.FreeSpaceTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-11 下午03:12:34 
 *   LastChange: 2013-9-11 下午03:12:34 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.other.test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileSystemUtils;
import org.junit.Test;

import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.DateTools;

/**
 * z.z.w.project.other.test.FreeSpaceTest.java
 */
public class FreeSpaceTest {
	private String path = "D:/09_study/media/TAKARAZUKA";

	@SuppressWarnings("deprecation")
	@Test
	public void testFreeSpaceByFileFun() throws IOException {

		long startTime = DateTools.getCurrentDateToLong();
		File file = new File(path);
		long useableSpaceSize = file.getUsableSpace();
		System.out.println(DataTools.formatDataSize(useableSpaceSize, 'G'));
		System.out.println("use time : "
				+ (DateTools.getCurrentDateToLong() - startTime));

		startTime = DateTools.getCurrentDateToLong();
		System.out.println(FileSystemUtils.freeSpaceKb(path) + "KB");
		System.out.println("use time : "
				+ (DateTools.getCurrentDateToLong() - startTime));
	}

}
