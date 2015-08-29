/**
 *  com.etone.mantoeye.analyse.service.specific.impl.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.service.specific.impl;

import java.sql.SQLException;
import java.util.List;

import org.myhkzhen.dao.core.IExecDbOperateDao;
import org.myhkzhen.dao.core.impl.write.WriteExecDaoImpl;

import com.etone.mantoeye.analyse.domain.extract.DataOutPutParam;
import com.etone.mantoeye.analyse.domain.extract.FtbDataOutPutDecTask;
import com.etone.mantoeye.analyse.domain.extract.FtbDataOutPutTask;
import com.etone.mantoeye.analyse.domain.extract.FtbOutPutColumnMap;
import com.etone.mantoeye.analyse.domain.extract.FtbOutPutTableColumn;
import com.etone.mantoeye.analyse.service.specific.IDataOutPutTaskManager;

/**
 * @author Wu Zhenzhen
 * @version May 28, 2012 2:54:27 PM
 */
public class DataOutPutTaskManagerImpl implements IDataOutPutTaskManager {

	private IExecDbOperateDao execDbOperateDao;

	/**
	 * 
	 */
	public DataOutPutTaskManagerImpl() {
		execDbOperateDao = new WriteExecDaoImpl();
	}

	/**
	 * 讀出是否有運行中的任務
	 */
	public Integer getRunningTask() throws SQLException {
		return (Integer) execDbOperateDao
				.queryForObject("getRunningOutPutTask");
	}

	/**
	 * 讀出當前需要運行的任務
	 */
	public List<FtbDataOutPutTask> getFtbDataOutPutTask() throws SQLException {
		// -- 2011年12月9日11:43:15 改為每次只查詢一個任務
		return (List<FtbDataOutPutTask>) execDbOperateDao
				.queryForList("getFtbDataOutPutTask");
	}

	/**
	 * 更新任務
	 */
	public void updateFtbDataOutPutTask(Long nmDataOutPutTaskId, int intStatus)
			throws SQLException {
		FtbDataOutPutTask data = new FtbDataOutPutTask();
		data.setIntTaskStatus(intStatus);
		data.setNmDataOutPutTaskId(nmDataOutPutTaskId);
		execDbOperateDao.update("updateFtbDataOutPutTask", data);
	}

	/**
	 * 自定義輸出字段
	 */
	public List<FtbOutPutTableColumn> getFtbOutPutTableColumn(
			Long nmDataOutPutTaskId) throws SQLException {
		return (List<FtbOutPutTableColumn>) execDbOperateDao.queryForList(
				"getFtbOutPutTableColumn", nmDataOutPutTaskId);
	}

	/**
	 * 自定義輸出的條件列
	 */
	public FtbOutPutColumnMap getFtbOutPutColumnMap(Long nmDataOutPutTaskId)
			throws SQLException {
		return (FtbOutPutColumnMap) execDbOperateDao.queryForObject(
				"getFtbOutPutColumnMap", nmDataOutPutTaskId);
	}

	/**
	 * 自定義輸出的條件值
	 */
	public List<String> getFtbOutPutFilterValueList(String tableName,
			String columnName, Long nmDataOutPutTaskId) throws SQLException {
		DataOutPutParam param = new DataOutPutParam();
		param.setColumnName(columnName);
		param.setNmDataOutPutTaskId(nmDataOutPutTaskId);
		param.setTableName(tableName);
		return (List<String>) execDbOperateDao.queryForList(
				"getFtbOutPutFilterValue", param);
	}

	/**
	 * 提取的存儲過程
	 */
	public int execute(String sqlId, Object param) throws SQLException {
		return execDbOperateDao.update(sqlId, param);
	}

	/**
	 * 自定義輸出數據提取的文件存放信息
	 */
	public void saveFileInfo(FtbDataOutPutDecTask fileInfo) throws SQLException {
		execDbOperateDao.insert("insertFtbDataOutPutDecTask", fileInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.specific.IDataOutPutTaskManager#
	 * insertFactDataByTime(java.lang.Long, java.lang.String)
	 */
	public void insertFactDataByTime(Long nmDataOutPutTaskId, String startTime)
			throws SQLException {
		DataOutPutParam param = new DataOutPutParam();
		param.setNmDataOutPutTaskId(nmDataOutPutTaskId);
		param.setTableName(startTime);
		execDbOperateDao.insert("insertFactDataByTime", param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.specific.IDataOutPutTaskManager#
	 * getRunningTask2()
	 */
	public Integer getRunningTask2() throws SQLException {
		return (Integer) execDbOperateDao
				.queryForObject("getRunningOutPutTask2");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.specific.IDataOutPutTaskManager#
	 * getFtbDataOutPutTask2()
	 */
	public List<FtbDataOutPutTask> getFtbDataOutPutTask2() throws SQLException {
		return (List<FtbDataOutPutTask>) execDbOperateDao
				.queryForList("getFtbDataOutPutTaskList2");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.specific.IDataOutPutTaskManager#
	 * updateFtbFacTask(java.lang.Long, int)
	 */
	public void updateFtbFacTask(Long nmDataOutPutTaskId, int intStatus)
			throws SQLException {
		FtbDataOutPutTask data = new FtbDataOutPutTask();
		data.setIntTaskStatus(intStatus);
		data.setNmDataOutPutTaskPreId(nmDataOutPutTaskId);
		execDbOperateDao.update("updateFtbFacTask", data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.specific.IDataOutPutTaskManager#
	 * getFtbDataOutPutStatusTask()
	 */
	public List<FtbDataOutPutTask> getFtbDataOutPutStatusTask()
			throws SQLException {
		return (List<FtbDataOutPutTask>) execDbOperateDao
				.queryForList("getFtbDataOutPutStatusTask");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.service.specific.IDataOutPutTaskManager#
	 * getCompTask()
	 */
	public List<FtbDataOutPutTask> getCompTask() throws SQLException {
		return (List<FtbDataOutPutTask>) execDbOperateDao
				.queryForList("getCompTask");
	}

}
