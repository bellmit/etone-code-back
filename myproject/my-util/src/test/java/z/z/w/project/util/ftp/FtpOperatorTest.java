/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.ftp.FtpOperatorTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-9-12 下午04:02:21 
 *   LastChange: 2013-9-12 下午04:02:21 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.ftp;

import static org.junit.Assert.fail;

import org.apache.commons.net.ftp.FTPFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.env.Config;

/**
 * z.z.w.project.util.ftp.FtpOperatorTest.java
 */
public class FtpOperatorTest {
	private FtpOperator ftpOper = null;

	/**
	 * <br>
	 * Created on: 2013-9-12 下午04:02:21
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Config conf = new Config("etc/ftp.cfg");
		conf.setRootKey("common.util");

		ftpOper = new FtpOperator(conf.getString("server-test"),
				conf.getInt("port-test"), conf.getString("user-test"),
				conf.getString("password-test"));

		ftpOper.createConnection();
	}

	/**
	 * <br>
	 * Created on: 2013-9-12 下午04:02:21
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		ftpOper.closeConnection();
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.util.ftp.FtpOperator#createDirInServer(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	@Ignore
	public void testCreateDirInServer() {
		try {

			System.out.println(" CreateDirInServer fdsdivll---> "
					+ ftpOper.createDirInServer("/home/etone/wzz", "fdsdivll"));

			System.out.println(" CreateDirInServer fdsdivll/ddd---> "
					+ ftpOper.createDirInServer("/home/etone/wzz",
							"fdsdivll/ddd"));

			System.out.println(" CreateDirInServer /fivll---> "
					+ ftpOper.createDirInServer("/home/etone/wzz", "fivll"));
			System.out
					.println(" CreateDirInServer /fivll/ddd---> "
							+ ftpOper.createDirInServer(
									"/home/etone/wzz/fivll", "ddd"));
			System.out.println(" CreateDirInServer /divll/ddd/test---> "
					+ ftpOper.createDirInServer("/home/etone/wzz/",
							"/divll/ddd/test"));
			System.out.println(" CreateDirInServer /divll/ddd/test---> "
					+ ftpOper.createDirInServer("/home/etone/wzz",
							"/divll/ddd/test"));

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.util.ftp.FtpOperator#getFileList(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetFileListStringString() {
		try {
			FTPFile[] ftps = ftpOper.getFileList(
					"/home/etone/wdata/ftpdownload", "3G*csv");
			if (!DataTools.isEmpty(ftps))
				for (FTPFile ftpFile : ftps) {
					System.out.println(ftpFile.getName());
				}
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

}
