/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.dao.ICheckFactTableDao.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-12-25 下午02:22:52 
 *   LastChange: 2013-12-25 下午02:22:52 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * z.z.w.project.test.dao.ICheckFactTableDao.java
 */
public interface ICheckFactTableDao {

	/**
	 * <br>
	 * Created on: 2013-12-25 下午02:45:09
	 * 
	 * @return
	 */
	List<String> getNewTableNameList();

	/**
	 * <br>
	 * Created on: 2013-12-25 下午02:54:05
	 * 
	 * @param tableName
	 * @return
	 */
	long getCurrentTableDataCount(@Param(value = "tableName") String tableName);

	/**
	 * <br>
	 * Created on: 2013-12-25 下午02:58:19
	 * 
	 * @param tableName
	 */
	void updateCurrentFactTableDataStatus(@Param(value = "tableName") String tableName);

	/**
	 * <br>
	 * Created on: 2014-1-9 下午01:11:42
	 * 
	 * @return
	 */
	List<String> getNewTaskTableNameList();

	/**
	 * <br>
	 * Created on: 2014-1-9 下午01:19:44
	 * 
	 * @param tableName
	 * @return
	 */
	long getTableExist(@Param(value = "tableName") String tableName);

	/**
	 * <br>
	 * Created on: 2014-1-9 下午01:22:01
	 * 
	 * @param tableName
	 */
	void updateCurrentTaskTableDataStatus(@Param(value = "tableName") String tableName);

	/**
	 * <br>
	 * Created on: 2014-1-13 下午04:03:35
	 * 
	 * @return
	 */
	List<Map<String, Object>> getIQConnectionList();

	/**
	 * <br>
	 * Created on: 2014-1-13 下午04:25:56
	 * 
	 * @param connId
	 */
	void dropConnection(@Param(value = "connId") Object connId);

}
