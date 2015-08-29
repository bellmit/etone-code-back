/**
 *  com.etone.mantoeye.analyse.specific.impl.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.specific.impl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.myhkzhen.util.date.DateTimeUtils;

import com.etone.mantoeye.analyse.domain.extract.FtbDataOutPutTask;
import com.etone.mantoeye.analyse.service.specific.IDataOutPutTaskManager;
import com.etone.mantoeye.analyse.service.specific.impl.DataOutPutTaskManagerImpl;
import com.etone.mantoeye.analyse.specific.ISpecificTaskJob;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * 自定義輸出任務
 * 
 * @author Wu Zhenzhen
 * @version May 22, 2012 4:00:29 PM
 */
public class PreFactDataOutPutJob implements ISpecificTaskJob {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.etone.mantoeye.analyse.specific.ISpecificTaskJob#execute()
	 */
	public void execute() {
		logger.info("[SpecificTaskJob] -- Exec Data Out Put Job.");

		IDataOutPutTaskManager manager = new DataOutPutTaskManagerImpl();
		checkAllData(manager);

		int count = getRunningTask(manager);

		if (count > 0) {
			logger.info("Data out put task is running.");
			manager = null;
			System.exit(0);
		}

		List<FtbDataOutPutTask> list = getFtbDataOutPutTaskList(manager);

		if (null != list && !list.isEmpty()) {

			Iterator<FtbDataOutPutTask> it = list.iterator();
			while (it.hasNext()) {
				FtbDataOutPutTask ftbDataOutPutTask = it.next();
				String startTime = ftbDataOutPutTask.getDtStartTime();
				String endTime = ftbDataOutPutTask.getDtEndTime();
				if (1 == ftbDataOutPutTask.getBitTaskActiveFlag()) {
					Long nmDataOutPutTaskId = ftbDataOutPutTask
							.getNmDataOutPutTaskId();

					// 0,任務開始，更新任務狀態為1，執行中,如果任務多,可能被重複進入list中，這裡前表面改任務狀態
					logger.debug("Mark Data Output Task Id:{} running.",
							nmDataOutPutTaskId);

					updateTaskStatus(ftbDataOutPutTask, manager, 1);

					logger.info("Out Put Task TaskName:{}.",
							ftbDataOutPutTask.getVcTaskName());

					// 1.數據提取
					if (1 == ftbDataOutPutTask.getIntTaskDelong()) {
						if (3 == ftbDataOutPutTask.getIntTaskType()) {
							// 3.歷史數據提取
						} else {
							// 其他任務--通用拍照功能
							try {
								Date sTime = DateTimeUtils.getParseStrToDate(
										startTime, DateTimeUtils.DEFAUL_PARSE
												+ ".S");
								Date eTime = DateTimeUtils.getParseStrToDate(
										endTime, DateTimeUtils.DEFAUL_PARSE
												+ ".S");

								Calendar ct = Calendar.getInstance();
								ct.setTime(sTime);
								Calendar cd = Calendar.getInstance();
								cd.setTime(eTime);

								while (ct.before(cd) || ct.equals(cd)) {

									startTime = DateTimeUtils
											.getParseDateToStr(ct.getTime(),
													"yyyy-MM-dd HH:00:00");
									logger.info("Start time : [{}].", startTime);

									ct.add(java.util.Calendar.HOUR, 1);

									insertFactData(ftbDataOutPutTask,
											startTime, manager);
								}

							} catch (Exception e) {
							}
						}
					} else {
						// 業務撥測任務
					}
				}
			}
		}

		manager = null;
	}

	/**
	 * <br>
	 * Created on: 2014-1-9 下午05:10:07
	 * 
	 * @param manager
	 */
	private void checkAllData(IDataOutPutTaskManager manager) {
		List<FtbDataOutPutTask> list = null;

		try {
			list = manager.getCompTask();

			if (null != list && !list.isEmpty()) {

				Iterator<FtbDataOutPutTask> it = list.iterator();
				while (it.hasNext()) {

					try {
						FtbDataOutPutTask ftbDataOutPutTask = it.next();
						String endTime = ftbDataOutPutTask.getDtEndTime();

						Date eTime = DateTimeUtils.getParseStrToDate(endTime,
								DateTimeUtils.DEFAUL_PARSE + ".S");

						Calendar ct = Calendar.getInstance();
						ct.setTime(new Date());
						Calendar cd = Calendar.getInstance();
						cd.setTime(eTime);

						long time1 = ct.getTimeInMillis();
						long time2 = cd.getTimeInMillis();
						long diff = time1 - time2;
						long diffHours = diff / (60 * 60 * 1000);

						if (diffHours > 3) {
							updateTaskStatus(ftbDataOutPutTask, manager, 2);
							logger.info(
									"Task : [{}], endtime :[{}] commplete.",
									ftbDataOutPutTask.getVcTaskName(),
									DateTimeUtils.getParseDateToStr(
											cd.getTime(), "yyyy-MM-dd HH:00:00"));
						}
					} catch (Exception e) {
						logger.error(
								"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
								new Object[] {
										"執行自定義輸出任務--checkAllData的任務时,报错:",
										e.getMessage(), e.getCause(),
										e.getClass() });
					}

				}
			}

		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢狀態為未執行的任務时,报错:",
							e.getMessage(), e.getCause(), e.getClass() });
			manager = null;
			list = null;
		}

	}

	/**
	 * <br>
	 * Created on: 2014-1-9 下午03:48:10
	 * 
	 * @param ftbDataOutPutTask
	 * @param startTime
	 * @param manager
	 */
	private void insertFactData(FtbDataOutPutTask ftbDataOutPutTask,
			String startTime, IDataOutPutTaskManager manager) {
		try {
			manager.insertFactDataByTime(
					ftbDataOutPutTask.getNmDataOutPutTaskId(), startTime);
		} catch (SQLException e) {
			if (CommonErrorUtil.isTableLockError(e))
				logger.warn("Times-Table lock,waiting...msg:[{}].", e
						.getCause().getMessage());
			else
				logger.error(
						"執行{}任務--{}.{}insertFactData為執行中时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
						new Object[] { "自定義輸出",
								ftbDataOutPutTask.getNmDataOutPutTaskId(),
								ftbDataOutPutTask.getVcTaskName(),
								e.getMessage(), e.getCause(), e.getClass() });

		}
	}

	private void updateTaskStatus(FtbDataOutPutTask ftbDataOutPutTask,
			IDataOutPutTaskManager manager, int status) {

		boolean flag = true;
		int i = 0;
		while (flag) {
			if (i++ > 50) {
				logger.warn("Data out put task update task status error.");
				manager = null;
				System.exit(0);
			}

			try {
				manager.updateFtbDataOutPutTask(
						ftbDataOutPutTask.getNmDataOutPutTaskId(), status);
				flag = false;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"執行{}任務--{}.{}任務更新任務狀態為執行中时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[] { "自定義輸出",
									ftbDataOutPutTask.getNmDataOutPutTaskId(),
									ftbDataOutPutTask.getVcTaskName(),
									e.getMessage(), e.getCause(), e.getClass() });

				flag = true;
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e2) {
				}
			}
		}
	}

	/**
	 * 
	 * <br>
	 * Created on: 2014-1-9 下午03:18:11
	 * 
	 * @param manager
	 * @return
	 */
	private List<FtbDataOutPutTask> getFtbDataOutPutTaskList(
			IDataOutPutTaskManager manager) {
		List<FtbDataOutPutTask> list = null;

		try {
			list = manager.getFtbDataOutPutTask();
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢狀態為未執行的任務时,报错:",
							e.getMessage(), e.getCause(), e.getClass() });
			manager = null;
			list = null;
		}

		return list;
	}

	/**
	 * 
	 * <br>
	 * Created on: May 28, 2012 2:56:51 PM
	 * 
	 * @param manager
	 * @return
	 */
	private Integer getRunningTask(IDataOutPutTaskManager manager) {
		Integer count = 1;
		try {
			count = manager.getRunningTask();
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[] { "執行自定義輸出任務--查詢是否存在執行中任務时,报错:",
							e.getMessage(), e.getCause(), e.getClass() });
			manager = null;
			count = 1;
		}

		return count;
	}

}
