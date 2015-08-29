/**
 *  com.etone.mantoeye.analyse.service.specific.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.service.specific;

import java.sql.SQLException;
import java.util.List;

import com.etone.mantoeye.analyse.domain.extract.FtbDataOutPutDecTask;
import com.etone.mantoeye.analyse.domain.extract.FtbDataOutPutTask;
import com.etone.mantoeye.analyse.domain.extract.FtbOutPutColumnMap;
import com.etone.mantoeye.analyse.domain.extract.FtbOutPutTableColumn;

/**
 * @author Wu Zhenzhen
 * @version May 28, 2012 2:54:17 PM
 */
public interface IDataOutPutTaskManager {
	/**
	 * 讀出是否有運行中的任務
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Integer getRunningTask() throws SQLException;

	/**
	 * 讀出當前需要運行的任務
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<FtbDataOutPutTask> getFtbDataOutPutTask() throws SQLException;

	/**
	 * 更新任務
	 * 
	 * @param nmDataOutPutTaskId
	 * @param intStatus
	 */
	public void updateFtbDataOutPutTask(Long nmDataOutPutTaskId, int intStatus)
			throws SQLException;

	/**
	 * 自定義輸出字段
	 * 
	 * @param nmDataOutPutTaskId
	 * @return
	 * @throws SQLException
	 */
	public List<FtbOutPutTableColumn> getFtbOutPutTableColumn(
			Long nmDataOutPutTaskId) throws SQLException;

	/**
	 * 自定義輸出的條件列
	 * 
	 * @param nmDataOutPutTaskId
	 * @return
	 * @throws SQLException
	 */
	public FtbOutPutColumnMap getFtbOutPutColumnMap(Long nmDataOutPutTaskId)
			throws SQLException;

	/**
	 * 自定義輸出的條件值
	 * 
	 * @param tableName
	 * @param columnName
	 * @param nmDataOutPutTaskId
	 * @return
	 * @throws SQLException
	 */
	public List<String> getFtbOutPutFilterValueList(String tableName,
			String columnName, Long nmDataOutPutTaskId) throws SQLException;

	/**
	 * 提取的存儲過程
	 * 
	 * @param sqlId
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public int execute(String sqlId, Object param) throws SQLException;

	/**
	 * 自定義輸出數據提取的文件存放信息
	 * 
	 * @param fileInfo
	 * @throws SQLException
	 */
	public void saveFileInfo(FtbDataOutPutDecTask fileInfo) throws SQLException;

	/**
	 * <br>
	 * Created on: 2014-1-9 下午03:49:57
	 * 
	 * @param nmDataOutPutTaskId
	 * @param startTime
	 */
	public void insertFactDataByTime(Long nmDataOutPutTaskId, String startTime)
			throws SQLException;

	/**
	 * <br>
	 * Created on: 2014-1-9 下午04:06:27
	 * 
	 * @return
	 */
	public Integer getRunningTask2() throws SQLException;

	/**
	 * <br>
	 * Created on: 2014-1-9 下午04:08:51
	 * 
	 * @return
	 */
	public List<FtbDataOutPutTask> getFtbDataOutPutTask2() throws SQLException;

	/**
	 * <br>
	 * Created on: 2014-1-9 下午04:17:17
	 * 
	 * @param nmDataOutPutTaskId
	 * @param status
	 */
	public void updateFtbFacTask(Long nmDataOutPutTaskId, int status)
			throws SQLException;

	/**
	 * <br>
	 * Created on: 2014-1-9 下午05:10:32
	 * 
	 * @return
	 */
	public List<FtbDataOutPutTask> getFtbDataOutPutStatusTask()
			throws SQLException;

	/**
	 * <br>
	 * Created on: 2014-1-10 上午11:32:47
	 * 
	 * @return
	 */
	public List<FtbDataOutPutTask> getCompTask() throws SQLException;
}
