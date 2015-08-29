/**
 * z.z.w.common.FileOperatorTest.java
 */
package z.z.w.common;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Wu Zhenzhen
 * @version Nov 22, 2012 5:45:30 PM
 */
public class FileOperatorTest {

	private FileOperator fileOper = null;

	/**
	 * <br>
	 * Created on: Nov 22, 2012 5:45:30 PM
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// fileOper = new FileOperator("etc/test",
		// FileOperator.FILE_OPER_TYPE_WRITE);
		fileOper = new FileOperator(FileOperator.FILE_OPER_TYPE_WRITE);
		// fileOper.setFileName("etc/test");
		// fileOper = new FileOperator("etc/jdbc.cfg2",
		// FileOperator.FILE_OPER_TYPE_WRITE);
		// fileOper = new FileOperator("etc/jdbc.cfg",
		// FileOperator.FILE_OPER_TYPE_READ);
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 5:45:30 PM
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		fileOper.close();
	}

	/**
	 * Test method for {@link z.z.w.common.FileOperator#createFile()}.
	 */
	@Test
	public void testCreateFile() {
		try {
			fileOper.createFile();
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for {@link z.z.w.common.FileOperator#write(java.lang.String)}
	 * .
	 */
	@Test
	public void testWrite() {
		try {
			fileOper.setFileName("etc/test");
			int i = 0;
			while (i++ < 1000) {

				if (i % 43 == 0)
					fileOper.reSetFile("etc/test-" + i);

				fileOper.write("line -- > " + i);
			}
			System.out.println(fileOper.getWriteSize() + "  -- write size.");
			System.out.println(fileOper.fileSize()
					+ "---"
					+ DateTools.getParseDateToStr(DateTools
							.getDateByLongTime(fileOper.fileLastModified())));
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for {@link z.z.w.common.FileOperator#readLine()}.
	 */
	@Test
	public void testReadLine() {
		try {
			String text = "";
			while ((text = fileOper.readLine()) != null) {
				System.out.println(text);
			}

		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
