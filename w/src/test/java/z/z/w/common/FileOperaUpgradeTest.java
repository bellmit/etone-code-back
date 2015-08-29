/**
 * z.z.w.common.FileOperaUpgradeTest.java
 */
package z.z.w.common;

import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Wu Zhenzhen
 * @version Dec 13, 2012 3:54:55 PM
 */
public class FileOperaUpgradeTest {
	private FileOperaUpgrade fileOper = null;

	/**
	 * <br>
	 * Created on: Dec 13, 2012 3:54:55 PM
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// fileOper = new FileOperator("etc/test",
		fileOper = new FileOperaUpgrade();
	}

	/**
	 * <br>
	 * Created on: Dec 13, 2012 3:54:55 PM
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		fileOper.close();
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.FileOperaUpgrade#write(java.lang.String)}.
	 */
	@Test
	public void testWrite() {
		try {

			fileOper.setFileName("etc/test");
			fileOper.openFileWriter();

			int i = 0;
			while (i++ < 3) {

				if (i % 413 == 0)
					fileOper.setFileName("etc/test-" + i);

				fileOper.write("wzz\t2012-12-15 18:04:23\t2432efdsf\t3\t\n");
			}

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
	 * Test method for {@link z.z.w.common.FileOperaUpgrade#readLine()}.
	 */
	@Test
	public void testReadLine() {
		try {
			fileOper.setFileName("etc/project.cfg");
			fileOper.openFileReader();
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
