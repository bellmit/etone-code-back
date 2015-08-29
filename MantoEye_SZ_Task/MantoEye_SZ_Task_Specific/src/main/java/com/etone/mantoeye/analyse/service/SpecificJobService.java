/**
 *  com.etone.mantoeye.analyse.service.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.service;

import org.myhkzhen.quartz.BaseQuartzService;
import org.quartz.Scheduler;

import com.etone.mantoeye.analyse.service.job.specific.ExecSpecificTaskJob;

/**
 * 執行特定功能的主類，防止停止任務時，時，天，周，月任務收到影響
 * 
 * @author Wu Zhenzhen
 * @version May 15, 2012 4:55:18 PM
 */
public class SpecificJobService extends BaseQuartzService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.myhkzhen.quartz.BaseQuartzService#scheduleJob(org.quartz.Scheduler)
	 */
	@Override
	public void scheduleJob(Scheduler scheduler) {
		// 增加執行線程,是的下面的JOB工作在線程中啟動，防止每天重啟Task時造成某些任務線程被關閉
		// TODO -- 2012年5月15日16:56:08 已測試
		callSchedulerJob(scheduler, ExecSpecificTaskJob.class, 6);

		// TODO -- 2014-1-9 15:14:02添加事實表檢查程序用於通用拍照
		// callSchedulerJob(scheduler, ExecSpecificTaskJob.class, 6);
	}

}
