/**
 *  com.etone.mantoeye.analyse.local.action.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.local.action;

import org.apache.commons.lang3.StringUtils;
import org.myhkzhen.quartz.SchedulerSingleton;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.etone.mantoeye.analyse.service.SpecificJobService;
import com.etone.mantoeye.analyse.util.FileLocker;
import com.etone.mantoeye.analyse.util.constant.Constant;

/**
 * 執行特定功能的主類，防止停止任務時，時，天，周，月任務收到影響
 * 
 * @author Wu Zhenzhen
 * @version May 15, 2012 4:49:34 PM
 */
public class SpecificJobAction {

	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SpecificJobAction.class);

	/**
	 * 導出文件進程鎖文件
	 */
	private static final String SPECIFIC_LOCK_FILE = StringUtils.join(
			Constant.APP_HOME, Constant.SEPARATOR_CHAR, Constant.CONFIG_PATH,
			"terminalMain.lock");

	/**
	 * <br>
	 * Created on: May 15, 2012 4:49:34 PM
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// File file = getFile();
		FileLocker lock = new FileLocker(SPECIFIC_LOCK_FILE);

		if (lock.tryLock()) {
			SpecificJobService specificJobService = new SpecificJobService();

			try {
				Scheduler scheduler = SchedulerSingleton.getInstance();
				specificJobService.scheduleJob(scheduler);
				scheduler.start();
			} catch (SchedulerException e) {
				logger.error(
						"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
						new Object[]{
								"特定功能服務SpecificDataAction任務調度實例化scheduler时,报错:",
								e.getMessage(), e.getCause(), e.getClass()});
				specificJobService = null;
			}
		}

	}

}
