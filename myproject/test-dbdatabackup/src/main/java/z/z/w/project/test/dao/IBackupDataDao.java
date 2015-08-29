/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.dao.IBackupDataDao.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-11-7 下午12:27:10 
 *   LastChange: 2013-11-7 下午12:27:10 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.dao;

import java.util.List;

import z.z.w.project.test.vo.ExportData;
import z.z.w.project.test.vo.LoadData;

/**
 * z.z.w.project.test.dao.IBackupDataDao.java
 */
public interface IBackupDataDao {

	/**
	 * <br>
	 * Created on: 2013-11-7 下午12:35:03
	 * 
	 * @return
	 */
	List<String> getAllBackupTableNames();

	/**
	 * <br>
	 * Created on: 2013-11-7 下午03:01:32
	 * 
	 * @param exportData
	 */
	void exportDataToFile(ExportData exportData);

	/**
	 * <br>
	 * Created on: 2013-11-7 下午03:48:26
	 * 
	 * @param tableName
	 * @return
	 */
	List<String> getTableColumns(String tableName);

	/**
	 * <br>
	 * Created on: 2013-11-7 下午05:00:48
	 * 
	 * @param ld
	 */
	void loadDataFromFile(LoadData ld);

}
