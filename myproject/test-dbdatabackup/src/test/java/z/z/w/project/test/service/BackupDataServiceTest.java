/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.BackupDataServiceTest.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-7 下午05:02:18 
 *   LastChange: 2013-11-7 下午05:02:18 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import static org.junit.Assert.fail;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import z.z.w.project.test.SuperClass;
import z.z.w.project.test.vo.ExportData;
import z.z.w.project.test.vo.LoadData;
import z.z.w.project.util.common.DataTools;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.service.BackupDataServiceTest.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:etc/spring.xml" })
public class BackupDataServiceTest extends SuperClass {
	private BackupDataService backupDataService;

	/**
	 * Test method for
	 * {@link z.z.w.project.test.service.BackupDataService#getAllBackupTableNames()}
	 * .
	 */
	@Test
	public void testGetAllBackupTableNames() {
		try {
			List<String> list = backupDataService.getAllBackupTableNames();
			if (!DataTools.isEmpty(list))
				for (String str : list) {
					LogTools.getLogger(getClass()).debug("{}", str);
				}
			else
				LogTools.getLogger(getClass())
						.warn("Query table list is null.");
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.test.service.BackupDataService#getTableColumns(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetTableColumns() {
		try {
			List<String> list = backupDataService.getTableColumns("tbClass");
			// .getTableColumns("ftbStatWeekStreetBussTypeUsers_V2");
			if (!DataTools.isEmpty(list))
				for (String str : list) {
					LogTools.getLogger(getClass()).debug("{}", str);
				}
			else
				LogTools.getLogger(getClass()).warn(
						"Query table column list is null.");
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.test.service.BackupDataService#exportDataToFile(z.z.w.project.test.vo.ExportData)}
	 * .
	 */
	@Test
	public void testExportDataToFile() {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("set temporary option TEMP_EXTRACT_COLUMN_DELIMITER='|';\n");
			sb.append("set temporary option TEMP_EXTRACT_BINARY='OFF';\n");
			sb.append("set temporary option Temp_Extract_Null_As_Empty='ON';\n");
			sb.append("set temporary option TEMP_EXTRACT_NAME1='D:\\tbClass.csv';");
			// sb.append("set temporary option TEMP_EXTRACT_NAME1='/opt/iq/tbtaskinfo_zz.out';");
			ExportData ed = new ExportData();
			ed.setColumns("intClassId,intLevel,vcClass");
			// ed.setColumns("nmTerminalId,intYear,intWeek,nmUsers,dtStatTime,intRaitype,vcLocalIP");
			ed.setExportOption(sb.toString());
			ed.setTableName("tbClass");
			// ed.setTableName("ftbStatWeekTerminalTypeGprsSpaceUsers");
			backupDataService.exportDataToFile(ed);

		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.project.test.service.BackupDataService#loadDataFromFile(z.z.w.project.test.vo.LoadData)}
	 * .
	 */
	@Test
	public void testLoadDataFromFile() {
		try {
			LoadData ld = new LoadData();
			ld.setColumns("intClassId,intLevel,vcClass");
			ld.setFilePath("D:\\tbClass.out");
			ld.setTableName("tbClass_2");
			backupDataService.loadDataFromFile(ld);
		} catch (Exception e) {
			fail("MASSAGE : " + e.getMessage() + "\nCAUSE : " + e.getCause()
					+ "\nCLASS : " + e.getClass());
		}
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午03:52:06
	 * 
	 * @return the backupDataService
	 */
	public BackupDataService getBackupDataService() {
		return backupDataService;
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午03:52:06
	 * 
	 * @param backupDataService
	 *            the backupDataService to set
	 */
	@Resource
	public void setBackupDataService(BackupDataService backupDataService) {
		this.backupDataService = backupDataService;
	}

}
