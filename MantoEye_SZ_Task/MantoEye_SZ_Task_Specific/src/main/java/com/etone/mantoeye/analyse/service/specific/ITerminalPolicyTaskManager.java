/**
 *  com.etone.mantoeye.analyse.service.specific.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.service.specific;

import java.sql.SQLException;

/**
 * @author Wu Zhenzhen
 * @version May 31, 2012 10:05:44 AM
 */
public interface ITerminalPolicyTaskManager {
	/**
	 * 取得當前狀態初始化的終端業務任務
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Long getTerminalPolicy() throws SQLException;
	/**
	 * 更新狀態
	 * 
	 * @param nmTerminalPolicyId
	 * @param intStatus
	 * @throws SQLException
	 */
	public void updateFtbTerminalPolicy(Long nmTerminalPolicyId, int intStatus)
			throws SQLException;
	/**
	 * 生成本次的终端数据
	 * 
	 * @param sqlId
	 * @param param
	 * @throws SQLException
	 */
	public void update(String sqlId, Object param) throws SQLException;
}
