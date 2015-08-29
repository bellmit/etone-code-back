/**
 *  com.etone.mantoeye.analyse.service.specific.impl.MantoEye_SZ_Task_TerminalChange 
 */
package com.etone.mantoeye.analyse.service.specific.impl;

import java.sql.SQLException;
import java.util.List;

import org.myhkzhen.dao.core.IExecDbOperateDao;
import org.myhkzhen.dao.core.impl.write.WriteExecDaoImpl;

import com.etone.mantoeye.analyse.domain.terminal.TerminalChange;
import com.etone.mantoeye.analyse.domain.terminal.TerminalChangeTask;
import com.etone.mantoeye.analyse.service.specific.ITerminalChangeTaskManager;

/**
 * @author Wu Zhenzhen
 * @version May 31, 2012 12:04:15 PM
 */
public class TerminalChangeTaskManagerImpl
		implements
			ITerminalChangeTaskManager {

	private IExecDbOperateDao execDbOperateDao;

	/**
	 * 
	 */
	public TerminalChangeTaskManagerImpl() {
		execDbOperateDao = new WriteExecDaoImpl();
	}
	/**
	 * 取得當前可執行的任務
	 */
	public List<TerminalChangeTask> getTerminalChangeTaskList()
			throws SQLException {
		return (List<TerminalChangeTask>) execDbOperateDao
				.queryForList("getTerminalChangeTaskList");
	}
	/**
	 * 更新操作
	 */
	public int update(String sqlId, Object param) throws SQLException {
		return execDbOperateDao.update(sqlId, param);
	}

	/**
	 * 根據終端升級統計ID ，找到相應的統計數據
	 */
	public TerminalChange getTerminalChange(Long nmTerminalChangeId)
			throws SQLException {
		return (TerminalChange) execDbOperateDao.queryForObject(
				"getTerminalChange", nmTerminalChangeId);
	}
}
