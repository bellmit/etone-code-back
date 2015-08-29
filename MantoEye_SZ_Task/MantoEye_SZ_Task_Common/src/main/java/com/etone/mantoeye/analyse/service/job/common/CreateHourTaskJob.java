/**
 *  com.etone.mantoeye.service.job.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.job.common;

import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.etone.mantoeye.analyse.service.common.IExecTaskManager;
import com.etone.mantoeye.analyse.service.impl.common.ExecTaskManagerImpl;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * 創建小時tbTask任務數據
 * 
 * @author Wu Zhenzhen
 * @version Apr 25, 2012 2:05:32 PM
 */
public class CreateHourTaskJob implements Job {

	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(CreateHourTaskJob.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("[Quartz Job]---- Exec create hour task job.");

		// 建立小時臨時用戶表
		boolean flag = false;

		String count = getRunningTask();

		if (StringUtils.isBlank(count)) {
			logger.warn("Fact table has running task and not create new task.");
			return;
		}

		if (StringUtils.equalsIgnoreCase(count, "signal")) {
			logger.info("SIGNAL,FTBASSISTRULEDATA,FTBMAINRULEFLUSHDATA,FTBASSISTVETORRECOGNISEDATA fact create hour data.");
			createHourSignalFactData();
			return;
		} else {
			if (NumberUtils.toInt(count) != 0) {
				logger.warn("Fact table has running task and not create new task.");
				return;
			}
			// 建立小時臨時用戶表
			flag = createHourUserTempTable();

			if (flag) {
				// 數據回滾，用於出錯的情況
				rollbackHourFactTableLog();
			}

			// 創建小時任務列表
			// createHourTaskList();

			// // 將task放置到隊列中
			// readTaskToQueue();
		}

	}

	/**
	 * 
	 * <br>
	 * Created on: May 25, 2012 1:47:14 PM
	 * 
	 */
	private void createHourSignalFactData() {
		IExecTaskManager manager = null;
		int i = 0;
		boolean flag = true;
		while (flag) {
			if (i++ > 50) {
				logger.warn("SIGNAL,FTBASSISTRULEDATA,FTBMAINRULEFLUSHDATA,FTBASSISTVETORRECOGNISEDATA hour task list error.");
				manager = null;
				break;
			}

			if (null == manager) {
				manager = new ExecTaskManagerImpl();
			}

			try {
				manager.createHourSignalFactData();
				flag = false;
				manager = null;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"第[{}]次createTaskList批量生成SIGNAL,FTBASSISTRULEDATA,FTBMAINRULEFLUSHDATA,FTBASSISTVETORRECOGNISEDATA數據时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{i, e.getMessage(), e.getCause(),
									e.getClass()});

				flag = true;

				try {
					Thread.sleep(6000);
				} catch (InterruptedException e1) {
					// Ingore
				}
			} finally {
				manager = null;
			}
		}
	}
	/**
	 * 是否存在狀態為4的事實表數據 <br>
	 * Created on: May 16, 2012 10:18:00 PM
	 * 
	 * @return
	 */
	private String getRunningTask() {
		IExecTaskManager manager = new ExecTaskManagerImpl();;
		String count = "1";

		try {
			count = manager.getRunningTask();
		} catch (SQLException e) {
			if (CommonErrorUtil.isTableLockError(e))
				logger.warn("Table lock,waiting...msg:[{}].", e.getCause()
						.getMessage());
			else
				logger.error(
						"getRunningTask查看是否存在狀態為4的新任務列表时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
						new Object[]{e.getMessage(), e.getCause(), e.getClass()});
		} finally {
			manager = null;
		}

		return StringUtils.trimToEmpty(count);
	}

	/**
	 * 
	 * <br>
	 * Created on: May 16, 2012 7:12:20 PM
	 * 
	 */
	// private void createHourTaskList() {
	// IExecTaskManager manager = null;
	// boolean flag = true;
	// int i = 0;
	// while (flag) {
	// if (i++ > 50) {
	// logger.warn("Create hour task list error,Ingore exec create hour task list operation.");
	// manager = null;
	// break;
	// }
	//
	// if (null == manager) {
	// manager = new ExecTaskManagerImpl();
	// }
	//
	// try {
	// manager.createHourTaskList();
	// flag = false;
	// manager = null;
	// } catch (SQLException e) {
	// if (CommonErrorUtil.isTableLockError(e))
	// logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
	// e.getCause().getMessage());
	// else
	// logger.error(
	// "第[{}]次createTaskList批量生成小時的新任務列表时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
	// new Object[]{i, e.getMessage(), e.getCause(),
	// e.getClass()});
	//
	// flag = true;
	//
	// try {
	// Thread.sleep(6000);
	// } catch (InterruptedException e1) {
	// // Ingore
	// }
	// } finally {
	// manager = null;
	// }
	// }
	// }

	/**
	 * 
	 * <br>
	 * Created on: May 16, 2012 7:10:27 PM
	 * 
	 */
	private void rollbackHourFactTableLog() {
		IExecTaskManager manager = null;
		int i = 0;
		boolean flag = true;
		while (flag) {
			if (i++ > 50) {
				logger.warn("Rollback hour task list error.");
				manager = null;
				break;
			}

			if (null == manager) {
				manager = new ExecTaskManagerImpl();
			}

			try {
				manager.updateHourFactTableStatus();
				flag = false;
				manager = null;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"第[{}]次createTaskList批量Rollback事實表日誌中狀態為3和4的數據时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{i, e.getMessage(), e.getCause(),
									e.getClass()});

				flag = true;

				try {
					Thread.sleep(6000);
				} catch (InterruptedException e1) {
					// Ingore
				}
			} finally {
				manager = null;
			}
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: May 16, 2012 7:10:24 PM
	 * 
	 * @return
	 */
	private boolean createHourUserTempTable() {
		// if (CurrentTaskQueue.getQueue().isEmpty()) {
		IExecTaskManager manager = new ExecTaskManagerImpl();
		boolean flag = false;
		try {
			manager.createHourTempUsers();
			flag = false;
			manager = null;
		} catch (SQLException e) {
			if (CommonErrorUtil.isTableLockError(e))
				logger.warn("Table lock,waiting...msg:[{}].", e.getCause()
						.getMessage());
			else
				logger.error(
						"建立臨時用戶數表createTaskList时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
						new Object[]{e.getMessage(), e.getCause(), e.getClass()});

			flag = true;

			try {
				Thread.sleep(6000);
			} catch (InterruptedException e1) {
				// Ingore
			}
		} finally {
			manager = null;
		}

		return flag;

	}

}
