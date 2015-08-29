/**
 * z.z.w.ftp.FtpOperatorTest.java
 */
package z.z.w.ftp;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import z.z.w.env.Config;

/**
 * @author Wu Zhenzhen
 * @version Nov 22, 2012 3:46:11 PM
 */
public class FtpOperatorTest {

	private FtpOperator ftpOper = null;

	/**
	 * <br>
	 * Created on: Nov 22, 2012 3:01:17 PM
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Config conf = new Config("etc/project.cfg");
		conf.setRootKey("common.util");

		ftpOper = new FtpOperator(conf.getString("server-test"),
				conf.getInt("port-test"), conf.getString("user-test"),
				conf.getString("password-test"));

		ftpOper.createConnection();
	}

	/**
	 * <br>
	 * Created on: Nov 22, 2012 3:01:17 PM
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		ftpOper.closeConnection();
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#downLoad(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testDownLoadStringStringStringString() {
		try {
			ftpOper.downLoad("/home/mysql/", "test", "F:/bak", "test1");
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#downLoad(java.lang.String, java.lang.String, java.io.File)}
	 * .
	 */
	@Test
	public void testDownLoadStringStringFile() {
		try {
			ftpOper.downLoad("/home/mysql/", "test",
					(new File("F:/bak/9test99")));
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#downLoad(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testDownLoadStringStringString() {
		try {
			ftpOper.downLoad("/home/mysql/test", "F:/bak", "test99");
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#downLoad(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testDownLoadStringString() {
		try {
			ftpOper.downLoad("/home/mysql/test", (("F:/bak/kk9test99")));
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#downLoad(java.lang.String, java.io.File)}.
	 */
	@Test
	public void testDownLoadStringFile() {
		try {
			ftpOper.downLoad("/home/mysql/data",
					(new File("F:/bak/00kk9test99")));
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#upload(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testUploadStringStringStringString() {
		try {
			// ftpOper.setEncoding("GBK");
			ftpOper.upload("/home/mysql/", "00fd", "F:/bak",
					"tzsz_keyword_search_SZ.201210120001.dat");
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#upload(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testUploadStringStringString() {
		try {
			// ftpOper.setEncoding("UTF-8");
			ftpOper.upload("/tiest/", "20130325150000.ING_TEST",
					"e:/workspace_foshan_2013031018/prodtran/etc/gb/new/20130325150000.CSV");
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#upload(java.lang.String, java.lang.String, java.io.File)}
	 * .
	 */
	@Test
	public void testUploadStringStringFile() {
		try {
			// ftpOper.setEncoding("GBK");
			ftpOper.upload("/home/mysql/", "00fd", (new File(
					"F:/bak/tzsz_keyword_search_SZ.201210120001.dat")));
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}

		try {
			// ftpOper.setEncoding("GBK");
			ftpOper.upload("/home/mysql/09l0fd", (new File(
					"F:/bak/tzsz_keyword_search_SZ.201210120401.dat")));
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#createDirInServer(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testCreateDirInServer() {
		try {

			System.out.println(" CreateDirInServer ---> "
					+ ftpOper.createDirInServer("/home/mysql", "fdsdivll/ddd"));

			System.out.println(" CreateDirInServer ---> "
					+ ftpOper.createDirInServer("/home/mysql", "fdsdivll"));

			System.out
					.println(" CreateDirInServer ---> "
							+ ftpOper.createDirInServer("/home/mysql",
									"/fdsdivll/ddd"));

		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#deleteFile(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testDeleteFileStringString() {
		try {
			System.out.println(ftpOper.deleteFile("/home/mysql", "d"));
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#deleteFile(java.lang.String)}.
	 */
	@Test
	public void testDeleteFileString() {
		try {
			System.out.println(ftpOper.deleteFile("/home/mysql/ooo0fdd"));
			System.out.println(ftpOper.deleteFile("/home/mysql/dds"));
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#renameFile(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testRenameFileStringStringStringString() {
		try {
			System.out.println(ftpOper.renameFile("/home/mysql/", "Test",
					"/home/mysql/", "Testdd"));
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#renameFile(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testRenameFileStringStringString() {
		try {
			System.out.println(ftpOper.renameFile(
					"/home/wzz/new\20130325120000.FTP_GB_UPLOADING",
					".FTP_GB_UPLOADING", ".CVS"));
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#getFileList(java.lang.String)}.
	 */
	@Test
	public void testGetFileListString() {
		try {
			FTPFile[] arr = ftpOper.getFileList("/home/mysql");
			for (int i = 0; i < arr.length; i++) {
				System.out.println(arr[i].getName());
			}
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.ftp.FtpOperator#getFileList(java.lang.String, org.apache.commons.net.ftp.FTPFileFilter)}
	 * .
	 */
	@Test
	public void testGetFileListStringFTPFileFilter() {
		try {

			FTPFile[] arr = ftpOper.getFileList("/home/mysql",
					(new FTPFileFilter() {
						public boolean accept(FTPFile file) {
							return file.getName().endsWith("t");
						}
					}));

			for (int i = 0; i < arr.length; i++) {
				System.out.println(arr[i].getName() + "-----filter");
			}
		} catch (IOException e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
