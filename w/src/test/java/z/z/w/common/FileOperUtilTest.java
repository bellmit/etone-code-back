/**
 * z.z.w.common.FileOperUtilTest.java
 */
package z.z.w.common;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import z.z.w.log.LogUtil;

/**
 * @author Wu Zhenzhen
 * @version 2013-7-21 上午11:34:38
 */
public class FileOperUtilTest {

	/**
	 * Test method for
	 * {@link z.z.w.common.FileOperUtil#writeString(java.lang.String)}.
	 */
	@Test
	public void testWriteString() {
		FileOperUtil fu = FileOperUtil
				.getInstance("D:\\03_workspace\\util\\w\\etc\\writeString.txt");
		long n = 100000;
		try {
			fu.createFile();
			fu.openFileWriter("UTF-8");
			long startTime = DateTools.getCurrentDateToLong();
			String str = "";
			for (int i = 0; i < n; i++) {
				String text = "14,460023119016843,,42470,24602,17,client.map.baidu.com,80,,223229,14.5000,";
				// 一行一行寫
				str = i + "  - " + text + "\n";
				fu.writeString(str);

			}
			fu.closeFileWriter();
			LogUtil.getLogger(this.getClass()).debug("Use time : [{}]",
					(DateTools.getCurrentDateToLong() - startTime));
		} catch (IOException e) {
			Assert.fail("MASSAGE : " + e.getMessage() + "\nCAUSE : "
					+ e.getCause() + "\nCLASS : " + e.getClass());
		}
	}

}
