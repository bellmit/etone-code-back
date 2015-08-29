/**
 *  com.etone.mantoeye.analyse.service.specific.impl.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.service.specific.impl;

import java.sql.SQLException;
import java.util.List;

import org.myhkzhen.dao.core.IExecDbOperateDao;
import org.myhkzhen.dao.core.impl.write.WriteExecDaoImpl;

import com.etone.mantoeye.analyse.domain.extract.FtbDataGetterDecTask;
import com.etone.mantoeye.analyse.domain.extract.FtbDataGetterFilter;
import com.etone.mantoeye.analyse.domain.extract.FtbDataGetterTask;
import com.etone.mantoeye.analyse.domain.extract.FtbFilterColumnMapTask;
import com.etone.mantoeye.analyse.domain.extract.FtbOutColumn;
import com.etone.mantoeye.analyse.service.specific.IDataGetterTaskManager;

/**
 * @author Wu Zhenzhen
 * @version May 22, 2012 4:09:15 PM
 */
public class DataGetterTaskManagerImpl implements IDataGetterTaskManager {

	private IExecDbOperateDao execDbOperateDao = null;

	/**
	 * 
	 */
	public DataGetterTaskManagerImpl() {
		execDbOperateDao = new WriteExecDaoImpl();
	}

	/**
	 * 读出当前需要运行的任务
	 */
	public FtbDataGetterTask getFtbDataGetterTask() throws SQLException {
		return (FtbDataGetterTask) execDbOperateDao
				.queryForObject("getFtbDataGetterTask");
	}
	/**
	 * 更新任務狀態
	 */
	public void updateFtbDataGetterTask(Long nmDataGetterTaskId,
			int intTaskStatus) throws SQLException {
		FtbDataGetterTask data = new FtbDataGetterTask();
		data.setIntTaskStatus(intTaskStatus);
		data.setNmDataGetterTaskId(nmDataGetterTaskId);
		execDbOperateDao.update("updateFtbDataGetterTask", data);
	}
	/**
	 * 取得任務輸出列
	 */
	public List<FtbOutColumn> getFtbOutColumnList(Long nmDataGetterTaskId)
			throws SQLException {
		return (List<FtbOutColumn>) execDbOperateDao.queryForList(
				"getFtbOutColumnList", nmDataGetterTaskId);
	}
	/**
	 * 取得過濾列
	 */
	public List<FtbFilterColumnMapTask> getFtbFilterColumnMapTaskList(
			Long nmDataGetterTaskId) throws SQLException {
		return (List<FtbFilterColumnMapTask>) execDbOperateDao.queryForList(
				"getFtbFilterColumnMapTaskList", nmDataGetterTaskId);
	}

	public List<FtbFilterColumnMapTask> getFtbFilterColumnMapTaskValueList(
			FtbFilterColumnMapTask ftbFilterColumnMapTask) throws SQLException {
		return (List<FtbFilterColumnMapTask>) execDbOperateDao.queryForList(
				"getFtbFilterColumnMapTaskValueList", ftbFilterColumnMapTask);
	}

	/**
	 * 執行sqlId操作
	 */
	public int execute(String sqlId, Object param) throws SQLException {
		return execDbOperateDao.update(sqlId, param);
	}
	/**
	 * 保存文件信息
	 */
	public void saveFileInfo(FtbDataGetterDecTask fileInfo) throws SQLException {
		execDbOperateDao.insert("insertFtbDataGetterDecTask", fileInfo);
	}

	public List<FtbDataGetterFilter> getFtbDataGetterFilterList(
			Long nmDataGetterTaskId) throws SQLException {
		return (List<FtbDataGetterFilter>) execDbOperateDao.queryForList(
				"getFtbDataGetterFilterList", nmDataGetterTaskId);
	}
	public List<FtbDataGetterFilter> getFtbDataGetterFilterValueList(
			FtbDataGetterFilter ftbDataGetterFilter) throws SQLException {
		return (List<FtbDataGetterFilter>) execDbOperateDao.queryForList(
				"getFtbDataGetterFilterValueList", ftbDataGetterFilter);
	}
}
