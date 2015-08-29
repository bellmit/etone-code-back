/**
 *  com.etone.mantoeye.analyse.service.job.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.job.common;

import java.sql.SQLException;
import java.util.List;

import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.etone.mantoeye.analyse.domain.CurrentTask;
import com.etone.mantoeye.analyse.service.common.IExecTaskManager;
import com.etone.mantoeye.analyse.service.impl.common.ExecTaskManagerImpl;
import com.etone.mantoeye.analyse.service.queue.CurrentTaskQueue;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * @author Wu Zhenzhen
 * @version May 16, 2012 1:31:27 PM
 */
public class ExecReadTaskToQueueJob implements Job {
	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ExecReadTaskToQueueJob.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("[Quartz Job]---- Exec read task to queue job.");

		if (CurrentTaskQueue.getQueue().isEmpty()) {

			// 創建小時任務列表
			createHourTaskList();

			// 將task放置到隊列中
			readTaskToQueue();

			logger.info(
					"CurrentTaskQueue task count:[{}], running task count:[{}].",
					CurrentTaskQueue.getQueue().size(),
					CurrentTaskQueue.getCurrentQueueTaskCount());
		}
		// else {
		//
		// }
	}

	/**
	 * 
	 * <br>
	 * Created on: May 25, 2012 11:54:11 AM
	 * 
	 */
	private void createHourTaskList() {
		IExecTaskManager manager = null;
		boolean flag = true;
		int i = 0;
		while (flag) {
			if (i++ > 50) {
				logger.warn("Create hour task list error,Ingore exec create hour task list operation.");
				manager = null;
				break;
			}

			if (null == manager) {
				manager = new ExecTaskManagerImpl();
			}

			try {
				manager.createHourTaskList();
				flag = false;
				manager = null;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"第[{}]次createTaskList批量生成小時的新任務列表时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
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
	 * 將tbTask表中未執行任務加入到隊列中,2011年12月2日11:13:02 枷鎖標識synchronized <br>
	 * Created on: Apr 27, 2012 4:59:03 PM
	 * 
	 */
	private synchronized void readTaskToQueue() {
		if (!CurrentTaskQueue.getQueue().isEmpty())
			return;

		IExecTaskManager manager = new ExecTaskManagerImpl();
		List<CurrentTask> list = null;

		try {
			list = manager.getCurrentTaskList();
		} catch (SQLException e) {
			logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"取得當前tbTask裱中未執行的任務List时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
			manager = null;
			list = null;
			return;
		}

		logger.info("TBTASK has [{}] tasks wait to do.", list.size());

		if (null != list && !list.isEmpty()) {
			// -- 2011年11月28日11:46:25 增加限制隊列中的任務數 100 個，如果有任務堆積,則容易造成表鎖,或其他bug
			for (CurrentTask currentTask : list) {
				if (CurrentTaskQueue.getQueue().size() > 99) {
					logger.info("Queue of tasks have 100,stop to queue.");
					manager = null;
					break;
				}

				CurrentTaskQueue.add(currentTask);

				int i = 0;
				boolean flag = true;
				while (flag) {
					if (i++ > 50) {
						// 50次后仍然有異常,移出剛剛進入隊列的task
						CurrentTaskQueue.remove();
						logger.warn(
								"Task [{}.{}] read to queue error,Ingore this task read to queue operation.",
								currentTask.getVcFactTableName(),
								currentTask.getVcTaskName());
						// manager = null;
						break;
					}

					try {
						manager.updateCurrentTaskState(
								currentTask.getIntTaskId(), 1);

						flag = false;

						logger.info(
								"Task [{}({}).{}] to Queue.",
								new Object[]{currentTask.getVcFactTableName(),
										currentTask.getIntTaskId(),
										currentTask.getVcTaskName()});
					} catch (SQLException e) {

						if (CommonErrorUtil.isTableLockError(e))
							logger.warn(
									"Times[{}]-Table lock,waiting...msg:[{}].",
									i, e.getCause().getMessage());
						else
							logger.error(
									"第[{}]次將當前任務[{}]放到隊列中,更新任務狀態時时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
									new Object[]{i, currentTask.getIntTaskId(),
											e.getMessage(), e.getCause(),
											e.getClass()});

						flag = true;

						try {
							Thread.sleep(6000);
						} catch (InterruptedException e1) {
							// Ingore;
						}
					}
				}
			}
		}

		manager = null;
	}
}
