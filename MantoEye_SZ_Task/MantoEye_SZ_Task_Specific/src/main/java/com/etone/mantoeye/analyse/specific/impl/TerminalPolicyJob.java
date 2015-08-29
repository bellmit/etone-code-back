/**
 *  com.etone.mantoeye.analyse.specific.impl.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.specific.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.etone.mantoeye.analyse.service.specific.ITerminalPolicyTaskManager;
import com.etone.mantoeye.analyse.service.specific.impl.TerminalPolicyTaskManagerImpl;
import com.etone.mantoeye.analyse.specific.ISpecificTaskJob;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * 終端撥測任務
 * 
 * @author Wu Zhenzhen
 * @version May 22, 2012 3:59:51 PM
 */
public class TerminalPolicyJob implements ISpecificTaskJob {

	private ITerminalPolicyTaskManager manager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.specific.ISpecificTaskJob#execute()
	 */
	public void execute() {
		logger.info("[SpecificTaskJob] -- Exec Terminal Policy Job.");

		Long nmTerminalPolicyId = getTerminalPolicyId();

		if (null == nmTerminalPolicyId || 0 == nmTerminalPolicyId) {
			logger.info("No new terminal policy task, exit current task.");
			System.exit(0);
		}

		// 2.进行中
		// updateTaskStatus(nmTerminalPolicyId, 2);

		runTerminalPolicyTask(nmTerminalPolicyId);

	}

	private void runTerminalPolicyTask(long nmTerminalPolicyId) {
		Long errorNmTerminalPolicyId = null;
		// 標誌
		boolean flag = true;
		int i = 0;
		while (flag) {

			// 返回錯誤ID集合
			errorNmTerminalPolicyId = doTaskAndReturnErrorTaskId(nmTerminalPolicyId);

			// 如果沒有錯誤id產生,跳出循環.
			if (null == errorNmTerminalPolicyId) {
				// break;
				flag = false;
				continue;
			}

			// 異常次數超過6次
			if (++i > 6) {
				logger.warn(
						"Terminal Id {} getter data {} times,stop getter data task.",
						errorNmTerminalPolicyId, i);

				// 改為初始狀態
				updateTaskStatus(errorNmTerminalPolicyId, 1);

				System.exit(1);
			}

		}
	}

	private Long doTaskAndReturnErrorTaskId(long nmTerminalPolicyId) {

		// 2.进行中
		updateTaskStatus(nmTerminalPolicyId, 2);
		logger.info("Mark Terminal Policy Task Id:{} running.",
				nmTerminalPolicyId);

		// 执行存储过程,生成本次的终端数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nmTerminalPolicyId", nmTerminalPolicyId);

		logger.info(
				"Exec proc_create_data_tempBuss use nmTerminalPolicyId:{}.",
				nmTerminalPolicyId);

		try {
			manager = new TerminalPolicyTaskManagerImpl();
			manager.update("proc_create_data_tempBuss", map);

			// 存儲過程中更新完成狀態
			// updateTaskStatus(nmTerminalPolicyId, 3);// 3.已完成

			logger.info("Terminal Data get success, Terminal id :{}.",
					nmTerminalPolicyId);

		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行終端撥測任務--執行存儲過程时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
			return nmTerminalPolicyId;
		} finally {
			map = null;
			manager = null;
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}

		return null;

	}

	private void updateTaskStatus(long nmTerminalPolicyId, int status) {
		boolean flag = true;
		int i = 0;
		while (flag) {
			if (i++ > 50) {
				logger.warn("Data Terminal Policy task update task status error.");
				manager = null;
				System.exit(0);
			}

			try {
				manager = new TerminalPolicyTaskManagerImpl();
				manager.updateFtbTerminalPolicy(nmTerminalPolicyId, status);
				flag = false;

			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.warn(
							"Update task status error, move current taskid [{}].",
							nmTerminalPolicyId);

				flag = true;
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e2) {
				}
			} finally {
				manager = null;
			}
		}
	}

	private Long getTerminalPolicyId() {

		Long nmTerminalPolicyId = 1l;

		manager = new TerminalPolicyTaskManagerImpl();

		try {
			nmTerminalPolicyId = manager.getTerminalPolicy();
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行終端撥測任務--查詢任務為未執行狀態时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
			nmTerminalPolicyId = 0l;
		} finally {
			manager = null;
		}

		logger.debug("nmTerminalPolicyId :[{}].", nmTerminalPolicyId);

		return nmTerminalPolicyId;
	}

}
