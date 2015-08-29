/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.test.service.CheckFactTableServer.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-12-25 下午02:20:01 
 *   LastChange: 2013-12-25 下午02:20:01 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.test.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import z.z.w.project.test.dao.ICheckFactTableDao;

/**
 * z.z.w.project.test.service.CheckFactTableServer.java
 */
@Service
public class CheckFactTableServer {

	private ICheckFactTableDao iCheckFactTableDao;

	/**
	 * <br>
	 * Created on: 2013-12-25 下午02:23:12
	 * 
	 * @return the iCheckFactTableDao
	 */
	public ICheckFactTableDao getiCheckFactTableDao() {
		return iCheckFactTableDao;
	}

	/**
	 * <br>
	 * Created on: 2013-12-25 下午02:23:12
	 * 
	 * @param iCheckFactTableDao
	 *            the iCheckFactTableDao to set
	 */
	@Resource
	public void setiCheckFactTableDao(ICheckFactTableDao iCheckFactTableDao) {
		this.iCheckFactTableDao = iCheckFactTableDao;
	}

	/**
	 * <br>
	 * Created on: 2013-12-25 下午02:20:01
	 */
	public CheckFactTableServer() {
	}

	/**
	 * <br>
	 * Created on: 2013-12-25 下午02:44:51
	 * 
	 * @return
	 */
	public List<String> getNewTableNameList() {
		return iCheckFactTableDao.getNewTableNameList();
	}

	/**
	 * <br>
	 * Created on: 2013-12-25 下午02:51:38
	 * 
	 * @param tableName
	 * @return
	 */
	public long getCurrentTableDataCount(String tableName) {
		return iCheckFactTableDao.getCurrentTableDataCount(tableName);
	}

	/**
	 * <br>
	 * Created on: 2013-12-25 下午02:57:06
	 * 
	 * @param tableName
	 */
	public void updateCurrentFactTableDataStatus(String tableName) {
		iCheckFactTableDao.updateCurrentFactTableDataStatus(tableName);
	}

	/**
	 * <br>
	 * Created on: 2014-1-9 下午01:11:23
	 * 
	 * @return
	 */
	public List<String> getNewTaskTableNameList() {
		return iCheckFactTableDao.getNewTaskTableNameList();
	}

	/**
	 * <br>
	 * Created on: 2014-1-9 下午01:19:27
	 * 
	 * @param tableName
	 * @return
	 */
	public long getTableExist(String tableName) {
		return iCheckFactTableDao.getTableExist(tableName);
	}

	/**
	 * <br>
	 * Created on: 2014-1-9 下午01:21:48
	 * 
	 * @param tableName
	 */
	public void updateCurrentTaskTableDataStatus(String tableName) {
		iCheckFactTableDao.updateCurrentTaskTableDataStatus(tableName);
	}

	/**
	 * <br>
	 * Created on: 2014-1-13 下午04:03:17
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getIQConnectionList() {
		return iCheckFactTableDao.getIQConnectionList();
	}

	/**
	 * <br>
	 * Created on: 2014-1-13 下午04:25:39
	 * 
	 * @param connId
	 */
	public void dropConnection(Object connId) {
		iCheckFactTableDao.dropConnection(connId);
	}

}
