/**
 *  com.etone.mantoeye.analyse.service.specific.MantoEye_SZ_Task_TerminalChange 
 */
package com.etone.mantoeye.analyse.service.specific;

import java.sql.SQLException;
import java.util.List;

import com.etone.mantoeye.analyse.domain.terminal.TerminalChange;
import com.etone.mantoeye.analyse.domain.terminal.TerminalChangeTask;

/**
 * @author Wu Zhenzhen
 * @version May 31, 2012 12:04:02 PM
 */
public interface ITerminalChangeTaskManager {
	/**
	 * 取得當前可執行的任務
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<TerminalChangeTask> getTerminalChangeTaskList()
			throws SQLException;
	/**
	 * 更新操作
	 * 
	 * @param sqlId
	 *            SQL ID
	 * @param param
	 *            參數
	 * @return
	 * @throws SQLException
	 */
	public int update(String sqlId, Object param) throws SQLException;
	/**
	 * 根據終端升級統計ID ，找到相應的統計數據
	 * 
	 * @param nmTerminalChangeId
	 *            終端升級統計表ID
	 * @return
	 * @throws SQLException
	 */
	public TerminalChange getTerminalChange(Long nmTerminalChangeId)
			throws SQLException;
}
