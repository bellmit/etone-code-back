/**
 *  com.etone.mantoeye.local.action.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.local.action;

import org.myhkzhen.quartz.SchedulerSingleton;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.etone.mantoeye.analyse.service.ExportDataService;
import com.etone.mantoeye.analyse.util.FileLocker;
import com.etone.mantoeye.analyse.util.constant.Constant;

/**
 * 導出數據進程啟動類
 * 
 * @author Wu Zhenzhen
 * @version Apr 25, 2012 10:01:18 AM
 */
public class ExportDataAction {

	/**
	 * 導出文件進程鎖文件
	 */
	private static final String EXPORT_LOCK_FILE = (Constant.APP_HOME
			+ Constant.SEPARATOR_CHAR + Constant.CONFIG_PATH + "exportMain.lock");

	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ExportDataAction.class);

	/**
	 * <br>
	 * Created on: Apr 25, 2012 10:01:18 AM
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// File file = getFile();
		FileLocker lock = new FileLocker(EXPORT_LOCK_FILE);

		if (lock.tryLock()) {

			// set server manager moniter
			(new Moniter()).start();

			ExportDataService exportDataService = new ExportDataService();

			// 初始化任務列表
			// -- 2012年4月25日12:27:52 添加初始化任務列表，已測試
			// -- 2012年5月2日10:48:44 測試完成
			logger.info("Table tbtask init list ....");
			exportDataService.initTaskList();
			logger.debug("Task inited list ....");

			// TODO -- 2012年5月18日15:33:58 添加初始化臨時用戶表操作，已測試
			logger.info("Table tbFactTableOperation init ....");
			exportDataService.initFactTable();

			// TODO -- 2012年5月21日15:55:20 添加初始化同一平臺數據傳送操作，已測試
			logger.info("Table tbUDTime init ....");
			exportDataService.initTbUDTimeTable();

			try {
				Scheduler scheduler = SchedulerSingleton.getInstance();
				exportDataService.scheduleJob(scheduler);
				scheduler.start();
			} catch (SchedulerException e) {
				logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
						new Object[]{
								"导出数据服務ExportDataService任務調度實例化scheduler时,报错:",
								e.getMessage(), e.getCause(), e.getClass()});

				exportDataService = null;
			}
		}

	}

}
