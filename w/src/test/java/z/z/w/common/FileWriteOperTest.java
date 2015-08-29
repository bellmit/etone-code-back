/**
 * z.z.w.common.FileWriteOperTest.java
 */
package z.z.w.common;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Wu Zhenzhen
 * @version Apr 19, 2013 3:09:20 PM
 */
public class FileWriteOperTest {

	private FileWriteOper fileWriteOper = null;

	/**
	 * <br>
	 * Created on: Apr 19, 2013 3:09:20 PM
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		fileWriteOper = new FileWriteOper("etc/test");
	}

	/**
	 * <br>
	 * Created on: Apr 19, 2013 3:09:20 PM
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		fileWriteOper.closeFileWriter();
	}

	/**
	 * Test method for {@link z.z.w.common.FileWriteOper#getFile()}.
	 */
	@Test
	public void testGetFile() {
		File file = fileWriteOper.getFile();
		if (null != file)
			System.out.println(file.getAbsolutePath());
		else
			System.out.println("file is null.");

	}

	/**
	 * Test method for {@link z.z.w.common.FileWriteOper#getPath()}.
	 */
	@Test
	public void testGetPath() {
		System.out.println(fileWriteOper.getPath());
	}

	/**
	 * Test method for {@link z.z.w.common.FileWriteOper#getFileName()}.
	 */
	@Test
	public void testGetFileName() {
		System.out.println(fileWriteOper.getFileName());
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.FileWriteOper#writeString(java.lang.String)}.
	 */
	@Test
	public void testWriteString() {
		try {
			fileWriteOper.openFileWriter("GBK");
			fileWriteOper.writeString("家福克斯了幾聲");
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
