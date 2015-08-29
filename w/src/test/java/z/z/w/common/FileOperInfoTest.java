/**
 * z.z.w.common.FileOperInfoTest.java
 */
package z.z.w.common;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Wu Zhenzhen
 * @version Dec 13, 2012 4:03:44 PM
 */
public class FileOperInfoTest {
	private FileOperInfo fileOperInfo = null;

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:03:44 PM
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.fileOperInfo = new FileOperInfo();

		FileOperaUpgrade fileOperaUpgrade = new FileOperaUpgrade();
		this.fileOperInfo.setFileOperaUpgrade(fileOperaUpgrade);
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 4:03:44 PM
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.fileOperInfo.close();
	}

	/**
	 * Test method for {@link z.z.w.common.FileOperInfo#getFileOperator()}.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetFileOperator() throws IOException {
		this.fileOperInfo.getFileOperaUpgrade().setFileName("etc/test");
		this.fileOperInfo.getFileOperaUpgrade().openFileWriter();
		int i = 0;
		while (i++ < 1000) {
			String content = i + "\n";
			this.fileOperInfo.getFileOperaUpgrade().write(content);
		}
		System.out.println("File size : "
				+ this.fileOperInfo.getFileOperaUpgrade().fileSize());
	}

}
