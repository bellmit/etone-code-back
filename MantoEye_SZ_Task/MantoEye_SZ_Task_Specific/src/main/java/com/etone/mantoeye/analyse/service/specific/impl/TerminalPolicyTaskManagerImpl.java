/**
 *  com.etone.mantoeye.analyse.service.specific.impl.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.service.specific.impl;

import java.sql.SQLException;

import org.myhkzhen.dao.core.IExecDbOperateDao;
import org.myhkzhen.dao.core.impl.write.WriteExecDaoImpl;

import com.etone.mantoeye.analyse.domain.terminal.TerminalPolicy;
import com.etone.mantoeye.analyse.service.specific.ITerminalPolicyTaskManager;

/**
 * @author Wu Zhenzhen
 * @version May 31, 2012 10:05:58 AM
 */
public class TerminalPolicyTaskManagerImpl
		implements
			ITerminalPolicyTaskManager {

	private IExecDbOperateDao execDbOperateDao;

	/**
	 * 
	 */
	public TerminalPolicyTaskManagerImpl() {
		execDbOperateDao = new WriteExecDaoImpl();
	}
	public Long getTerminalPolicy() throws SQLException {
		// -- 2011年12月9日11:40:59 改為每次只查詢一個任務
		return (Long) execDbOperateDao.queryForObject("getTerminalPolicy");
	}
	/**
	 * 更新狀態
	 */
	public void updateFtbTerminalPolicy(Long nmTerminalPolicyId, int intStatus)
			throws SQLException {
		TerminalPolicy terminalPolicy = new TerminalPolicy();
		terminalPolicy.setIntStatus(intStatus);
		terminalPolicy.setNmTerminalPolicyId(nmTerminalPolicyId);
		update("updateFtbTerminalPolicy", terminalPolicy);

	}
	/**
	 * 生成本次的终端数据
	 */
	public void update(String sqlId, Object param) throws SQLException {
		execDbOperateDao.update(sqlId, param);
	}

}
