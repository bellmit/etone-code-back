/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.BackupDataService.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-7 下午12:27:52 
 *   LastChange: 2013-11-7 下午12:27:52 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import z.z.w.project.test.dao.IBackupDataDao;
import z.z.w.project.test.vo.ExportData;
import z.z.w.project.test.vo.LoadData;
import z.z.w.project.util.common.LogTools;

/**
 * z.z.w.project.test.service.BackupDataService.java
 */
@Service
public class BackupDataService {

	private IBackupDataDao iBackupDataDao;

	/**
	 * <br>
	 * Created on: 2013-11-7 下午12:29:16
	 * 
	 * @return the iBackupDataDao
	 */
	public IBackupDataDao getiBackupDataDao() {
		return iBackupDataDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午12:29:16
	 * 
	 * @param iBackupDataDao
	 *            the iBackupDataDao to set
	 */
	@Resource
	public void setiBackupDataDao(IBackupDataDao iBackupDataDao) {
		this.iBackupDataDao = iBackupDataDao;
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午12:33:53
	 * 
	 * @return
	 */
	public List<String> getAllBackupTableNames() {
		LogTools.getLogger(getClass()).debug(" --- getAllBackupTableNames ---");
		return iBackupDataDao.getAllBackupTableNames();
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午03:48:04
	 * 
	 * @param tableName
	 * @return
	 */
	public List<String> getTableColumns(String tableName) {
		return iBackupDataDao.getTableColumns(tableName);
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午04:56:38
	 * 
	 * @param ed
	 */
	public void exportDataToFile(ExportData ed) {
		iBackupDataDao.exportDataToFile(ed);
	}

	/**
	 * <br>
	 * Created on: 2013-11-7 下午05:00:19
	 * 
	 * @param ld
	 */
	public void loadDataFromFile(LoadData ld) {
		iBackupDataDao.loadDataFromFile(ld);
	}
}
