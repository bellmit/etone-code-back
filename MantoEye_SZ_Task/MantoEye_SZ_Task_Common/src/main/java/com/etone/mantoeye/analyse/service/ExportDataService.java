/**
 *  com.etone.mantoeye.service.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service;

import java.sql.SQLException;

import org.myhkzhen.quartz.BaseQuartzService;
import org.quartz.Scheduler;

import com.etone.mantoeye.analyse.service.common.IExecTaskManager;
import com.etone.mantoeye.analyse.service.impl.common.ExecTaskManagerImpl;
import com.etone.mantoeye.analyse.service.job.common.CreateDayTaskJob;
import com.etone.mantoeye.analyse.service.job.common.CreateHourTaskJob;
import com.etone.mantoeye.analyse.service.job.common.CreateMonthTaskJob;
import com.etone.mantoeye.analyse.service.job.common.CreateReDoDateTaskJob;
import com.etone.mantoeye.analyse.service.job.common.CreateWeekTaskJob;
import com.etone.mantoeye.analyse.service.job.common.ExecCommonTaskJob;
import com.etone.mantoeye.analyse.service.job.common.ExecReadTaskToQueueJob;
import com.etone.mantoeye.analyse.service.job.common.ExecTransDataJob;
import com.etone.mantoeye.analyse.service.job.common.ExecUDDataTransJob;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * 查詢數據庫表數據到文件服務
 * 
 * @author Wu Zhenzhen
 * @version Apr 25, 2012 11:15:01 AM
 */
public class ExportDataService extends BaseQuartzService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.myhkzhen.quartz.BaseQuartzService#scheduleJob(org.quartz.Scheduler)
	 */
	@Override
	public void scheduleJob(Scheduler scheduler) {

		// 小時任務列表生成
		// todo -- 2012年4月27日16:56:52 小時列表生成，已測試
		// 事實表
		callSchedulerJob(scheduler, CreateHourTaskJob.class, 6);

		// TODO -- 2012年5月16日13:30:34 添加單獨進入queue隊列的job
		callSchedulerJob(scheduler, ExecReadTaskToQueueJob.class, 2);

		// todo -- 2012年4月28日11:41:00 執行數據導出操作,已測試
		callSchedulerJob(scheduler, ExecCommonTaskJob.class, 2);

		// TODO -- 2012年5月2日11:51:59 天任務列表生成，已測試
		callSchedulerJob(scheduler, CreateDayTaskJob.class, "0 0 4 * * ?");

		// TODO -- 2012年5月2日11:52:59 周任務列表生成，已測試
		callSchedulerJob(scheduler, CreateWeekTaskJob.class, "0 20 4 ? * MON");

		// TODO -- 2012年5月2日11:53:59 月任務列表生成，已測試
		callSchedulerJob(scheduler, CreateMonthTaskJob.class, "0 30 4 1 * ?");

		// TODO -- 2012年5月2日11:54:59 重做天、周、月任務任務列表生成，已測試
		callSchedulerJob(scheduler, CreateReDoDateTaskJob.class,
				"0 0 0/3 * * ?");

		// TODO -- 2012年5月8日17:40:51 翻譯name，已測試
		callSchedulerJob(scheduler, ExecTransDataJob.class, "0 55 * * * ?");

		// TODO --2012年5月21日11:15:52 同一平臺數據傳送
		callSchedulerJob(scheduler, ExecUDDataTransJob.class, 6);

	}

	/**
	 * 初始化任務列表,將tbtask表中的任務狀態非2(執行中)的任務更新狀態為0:未執行 <br>
	 * Created on: Apr 25, 2012 11:16:24 AM
	 * 
	 */
	public void initTaskList() {

		logger.debug("Init task list ....initTaskList().");

		IExecTaskManager manager = new ExecTaskManagerImpl();

		logger.debug("Manager--->[{}].", manager);

		boolean flag = true;
		int i = 0;
		while (flag) {
			logger.debug("i = {}.", i);
			if (i++ > 50) {
				logger.warn("Init task list error,Ingore exec init task list operation.");
				manager = null;
				break;
			}

			logger.debug("i++ = {}.", i);

			try {
				logger.debug("Start init task list....");
				int updated = manager.initTaskList();
				logger.info("Init task total [{}].", updated);
				logger.debug("Complete init task list....");
				flag = false;
				manager = null;
			} catch (SQLException e) {

				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"第[{}]次:初始化tbTask表中已存在但未執行的任務时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{i, e.getMessage(), e.getCause(),
									e.getClass()});

				flag = true;

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// ignore
				}
			}
		}
		manager = null;
		logger.debug("Complete init task list ....initTaskList().");

	}

	/**
	 * 初始化臨時用戶表建立操作 <br>
	 * Created on: May 18, 2012 3:35:25 PM
	 * 
	 */
	public void initFactTable() {
		logger.debug("Init tbFactTableOperation  ....initFactTable().");

		IExecTaskManager manager = new ExecTaskManagerImpl();
		//
		// int count = 0;
		// try {
		// count = manager.initFactTable();
		// } catch (SQLException e) {
		// if (CommonErrorUtil.isTableLockError(e))
		// logger.warn("Table lock,waiting...msg:[{}].", e.getCause()
		// .getMessage());
		// else
		// logger.error(
		// "初始化tbFactTableOperation表中任務状态为4的任務时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
		// new Object[]{e.getMessage(), e.getCause(), e.getClass()});
		// count = 0;
		// }
		//
		// if (count == 0) {
		// manager = null;
		// return;
		// }

		boolean flag = true;
		int i = 0;
		while (flag) {
			logger.debug("i = {}.", i);
			if (i++ > 50) {
				logger.warn("Init tbFactTableOperation error,Ingore exec init tbFactTableOperation list operation.");
				manager = null;
				break;
			}

			logger.debug("i++ = {}.", i);

			try {
				logger.debug("Start init tbFactTableOperation ....");
				// Map<String, String> map = new HashMap<String, String>();
				// map.put("setChtaskcreated", "0");
				// map.put("whereChtaskcreated", "4");
				// manager.updateHourFactTableStatus(map);
				int updated = manager.initHourFactTableStatus();
				logger.info("Init tbFactTableOperation total [{}].", updated);
				flag = false;
				manager = null;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"第[{}]次:初始化tbFactTableOperation表中任務状态为4的任務时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{i, e.getMessage(), e.getCause(),
									e.getClass()});

				flag = true;

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// ignore
				}
			}
		}
		manager = null;
		logger.debug("Complete init tbFactTableOperation ....initFactTable().");

	}

	public void initTbUDTimeTable() {
		logger.debug("Init tbUDTime  ....initFactTable().");

		IExecTaskManager manager = new ExecTaskManagerImpl();

		boolean flag = true;
		int i = 0;
		while (flag) {
			logger.debug("i = {}.", i);
			if (i++ > 200) {
				logger.warn("Init tbUDTime error,Ingore exec init tbUDTime list operation.");
				manager = null;
				break;
			}

			logger.debug("i++ = {}.", i);

			try {
				logger.debug("Start init tbUDTime ....");
				int updated = manager.inittbUDTimeStatus();
				logger.info("Init tbFactTableOperation total [{}].", updated);
				flag = false;
				manager = null;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"第[{}]次:初始化tbUDTime表中任務状态为1的任務时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{i, e.getMessage(), e.getCause(),
									e.getClass()});

				flag = true;

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// ignore
				}
			}
		}

		manager = null;
		logger.debug("Complete init tbUDTime ....initTbUDTimeTable().");
	}
}
