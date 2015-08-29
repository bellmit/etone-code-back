/**
 *  com.etone.mantoeye.analyse.service.job.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.job.common;

import java.sql.SQLException;

import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.etone.mantoeye.analyse.service.common.IExecTaskManager;
import com.etone.mantoeye.analyse.service.impl.common.ExecTaskManagerImpl;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * 周任務產生
 * 
 * @author Wu Zhenzhen
 * @version May 2, 2012 11:54:28 AM
 */
public class CreateWeekTaskJob implements Job {
	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(CreateWeekTaskJob.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("[Quartz Job]---- Exec create Week task job.");

		IExecTaskManager manager = new ExecTaskManagerImpl();

		boolean flag = true;
		int i = 0;
		while (flag) {
			if (i++ > 50) {
				logger.warn("Create week task list error,Ingore exec create week task list operation.");
				manager = null;
				break;
			}

			try {
				manager.createWeekTaskList();
				flag = false;
				manager = null;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"第[{}]次:{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{i, "createTaskList批量生成周的新任務列表时,报错:",
									e.getMessage(), e.getCause(), e.getClass()});

				flag = true;

				try {
					Thread.sleep(6000);
				} catch (InterruptedException e1) {
					// Ingore
				}
			}

		}

		manager = null;
	}

}
