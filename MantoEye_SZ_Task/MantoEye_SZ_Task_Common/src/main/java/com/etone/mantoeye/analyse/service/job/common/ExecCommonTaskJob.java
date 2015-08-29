/**
 *  com.etone.mantoeye.analyse.service.job.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.job.common;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.myhkzhen.util.date.DateTimeUtils;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;
import org.myhkzhen.util.properties.PropertiesUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.etone.mantoeye.analyse.domain.CurrentTask;
import com.etone.mantoeye.analyse.process.handler.StreamHandler;
import com.etone.mantoeye.analyse.service.queue.CurrentTaskQueue;
import com.etone.mantoeye.analyse.util.constant.Constant;

/**
 * 启动执行任务的线程
 * 
 * @author Wu Zhenzhen
 * @version Apr 28, 2012 11:41:46 AM
 */
public class ExecCommonTaskJob implements Job {

	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ExecCommonTaskJob.class);

	/**
	 * 執行多進程啟動IQ任務數據
	 */
	private static final String EXEC_COMMON_TASK_NUM_STR = PropertiesUtil
			.getConfigProperties("execCommonTaskNum");

	/**
	 * 執行多進程啟動IQ任務數據
	 */
	private static final Integer EXEC_COMMON_TASK_NUM = NumberUtils.toInt(
			EXEC_COMMON_TASK_NUM_STR, 4);

	/**
	 * 任務查詢進程shell腳本
	 */
	private static final String SHELL_SCRIPT = (Constant.APP_HOME + "/bin/process_export_data.sh");

	/**
	 * 啟動執行任務的線程主類
	 */
	// public static final String EXEC_EXPORT_TASK_JOB =
	// "com.etone.mantoeye.analyse.process.ExecExportDataProcess";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("[Quartz Job]---- Exec export task thread job.");

		logger.debug(
				"CurrentTaskQueue task count:[{}], running task count:[{}].",
				CurrentTaskQueue.getQueue().size(),
				CurrentTaskQueue.getCurrentQueueTaskCount());

		while (!CurrentTaskQueue.getQueue().isEmpty()) {

			// 最大运行数为10个,其实IQ只用5个,這裡運行4個,從0開始,
			// 一個cpu支持5個，多個cpu可以開多個iq進程
			if (CurrentTaskQueue.getCurrentQueueTaskCount() < EXEC_COMMON_TASK_NUM) {
				// 隊列中移出一個任務,并執行此任務
				CurrentTask currentTask = CurrentTaskQueue.remove();

				logger.debug("Current task:[{}].", currentTask);

				// 隊列中正在執行的任務個數(+1)
				CurrentTaskQueue.plusQueueTaskCount();

				if (null != currentTask) {
					// 啟動線程執行當前任務
					execProcess(currentTask);
				} else {
					logger.warn("Current task is null --> [{}].", currentTask);
				}

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// Igrone;
				}
			} else {
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) {
					// Igrone;
				}
			}
		}

		// if (CurrentTaskQueue.getQueue().size() == 0
		// || CurrentTaskQueue.getQueue().isEmpty()) {
		// logger.info("ErrorTaskVector-->[{}].", ErrorTaskVector
		// .getInstance().size());
		// logger.info("SuccessTaskVector-->[{}].", SuccessTaskVector
		// .getInstance().size());
		// // updateStateTaskList();
		// }
	}

	/**
	 * 
	 * <br>
	 * Created on: May 17, 2012 4:09:25 AM
	 * 
	 */
	// private synchronized void updateStateTaskList() {
	// if (null != ErrorTaskVector.getInstance()
	// && !ErrorTaskVector.getInstance().isEmpty()
	// && null != ErrorTaskVector.getInstance().get(0)) {
	//
	// logger.info("Start update error task status.");
	// String taskIds = StringUtils.join(ErrorTaskVector.getInstance()
	// .get(0));
	// for (int i = 1; i < ErrorTaskVector.getInstance().size(); i++) {
	// taskIds = StringUtils.join(taskIds, ",", ErrorTaskVector
	// .getInstance().get(i));
	// }
	//
	// ErrorTaskVector.getInstance().clear();
	//
	// logger.info("Tasks [{}] errors.", taskIds);
	// updateFlag(taskIds);
	// }
	//
	// try {
	// Thread.sleep(3000);
	// } catch (InterruptedException e1) {
	// }
	//
	// if (null != SuccessTaskVector.getInstance()
	// && !SuccessTaskVector.getInstance().isEmpty()
	// && null != SuccessTaskVector.getInstance().get(0)) {
	// logger.info("Start complete success task status.");
	// String taskIds = StringUtils.join(SuccessTaskVector.getInstance()
	// .get(0));
	// for (int i = 1; i < SuccessTaskVector.getInstance().size(); i++) {
	// taskIds = StringUtils.join(taskIds, ",", SuccessTaskVector
	// .getInstance().get(i));
	// }
	// SuccessTaskVector.getInstance().clear();
	//
	// writeLog(taskIds);
	// logger.info("Tasks [{}] complete.", taskIds);
	// }
	// }
	// /**
	// *
	// * <br>
	// * Created on: May 17, 2012 4:16:17 AM
	// *
	// * @param taskId
	// */
	// private synchronized void writeLog(String taskId) {
	// IExecTaskManager manager = null;
	// boolean flag = true;
	// int i = 0;
	// while (flag) {
	// if (i++ > 100) {
	// logger.warn("Socket log complete currentTask process error , break complete process.");
	// break;
	// }
	//
	// if (null == manager) {
	// manager = new ExecTaskManagerImpl();
	// }
	//
	// try {
	// List<CurrentTask> currentTaskList = manager
	// .completeCurrentTaskList(taskId);
	//
	// logger.info("Task wait delete has [{}].",
	// currentTaskList.size());
	//
	// for (CurrentTask currentTask : currentTaskList) {
	//
	// // if (null != currentTask) {
	// logger.debug("[writeLog -- start] ---- COMPLETE {} TASK.",
	// currentTask.getIntTaskId());
	//
	// // manager.completeCurrentTask(currentTask);
	// logger.info("Write log to tbTaskLog.");
	// manager.writeCurrentTaskLog(currentTask);
	//
	// // delete current task
	// logger.info("Delete current task from tbTask.");
	// manager.deleteCurrentTask(currentTask);
	//
	// if (null != currentTask.getIntFtbOid()) {
	// logger.info("Update fact table status.");
	// manager.updateTaskCompleted(currentTask);
	// }
	//
	// logger.info("Delete complete task [{}.{}] .",
	// currentTask.getIntTaskId(),
	// currentTask.getVcTaskName());
	// // }
	// }
	// flag = false;
	// } catch (SQLException e) {
	// if (CommonErrorUtil.isTableLockError(e))
	// logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
	// e.getCause().getMessage());
	// else
	// logger.error(
	// "第[{}]次:运行完成的任务,删除任务ID [{}]记录,写SOCKET日志表时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
	// new Object[]{i, taskId, e.getMessage(),
	// e.getCause(), e.getClass()});
	//
	// flag = true;
	// try {
	// Thread.sleep(6000);
	// } catch (InterruptedException e1) {
	// }
	// }
	// }
	//
	// manager = null;
	// }
	// /**
	// *
	// * <br>
	// * Created on: May 17, 2012 4:13:21 AM
	// *
	// * @param taskId
	// */
	// private synchronized void updateFlag(String intTaskId) {
	// IExecTaskManager manager = null;
	// boolean flag = true;
	// // 怕LOCK TALBE,如果LOCK TABLE时,就等待循环执行
	// int i = 0;
	// while (flag) {
	// if (i++ > 50) {
	// logger.warn("Socket log update currentTask process error , break update process.");
	// break;
	// }
	//
	// if (null == manager) {
	// manager = new ExecTaskManagerImpl();
	// }
	//
	// try {
	//
	// manager.updateCurrentTaskStateList(intTaskId);
	// logger.debug("[updateFlag -- end] ---- UPDATE [{}.0] TASK.",
	// intTaskId);
	//
	// flag = false;
	//
	// } catch (SQLException e) {
	// if (CommonErrorUtil.isTableLockError(e))
	// logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
	// e.getCause().getMessage());
	// else
	// logger.error(
	// "第[{}]次:任務出錯,回滾任务ID[{}]狀態為0操作时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
	// new Object[]{i, intTaskId, e.getMessage(),
	// e.getCause(), e.getClass()});
	//
	// flag = true;
	// try {
	// Thread.sleep(6000);
	// } catch (InterruptedException e1) {
	// }
	// }
	// }
	// manager = null;
	// }

	/**
	 * 啟動線程,執行當前任務 <br>
	 * Created on: Apr 28, 2012 2:18:10 PM
	 * 
	 * @param currentTask
	 */
	private void execProcess(CurrentTask currentTask) {
		// 構建出執行當前task的java command指令
		String command = getProcessCommand(currentTask);
		logger.debug("ExecTaskJob run task command:{}.", command.toString());

		Process process = null;
		try {
			process = Runtime.getRuntime().exec(command.toString());

			logger.debug(
					"CurrentTaskQueue task count:{},running task count:{}",
					CurrentTaskQueue.getQueue().size(),
					CurrentTaskQueue.getCurrentQueueTaskCount());

			StreamHandler inputPumper = new StreamHandler(
					process.getInputStream(), Constant.CORRECT_CODE);
			StreamHandler errorPumper = new StreamHandler(
					process.getErrorStream(), Constant.ERROR_CODE);

			// starts pumping away the generated output/error
			inputPumper.start();
			errorPumper.start();

			// Wait for everything to finish
			process.waitFor();
			inputPumper.join();
			errorPumper.join();

			logger.debug("EXIT VALUE == > {}.", process.exitValue());

			// Long taskId = currentTask.getIntTaskId();

			if (1 == process.exitValue()) {
				logger.error(
						"Task:[{}.({}){}] is failed.",
						new Object[]{currentTask.getVcFactTableName(),
								currentTask.getIntTaskId(),
								currentTask.getVcSqlId()});
				// ErrorTaskVector.add(taskId);
				// LogClient.writeLog("state=" + taskId + ";" + 0);
				// LogClient.writeLog(StringUtils.join(new Object[]{"state=",
				// taskId, ";0"}));
				// logger.warn("Error task list size [{}].", ErrorTaskVector
				// .getInstance().size());

			} else {
				logger.info(
						"Task Finish:[{}.({}){}].",
						new Object[]{currentTask.getVcFactTableName(),
								currentTask.getIntTaskId(),
								currentTask.getVcSqlId()});
				// SuccessTaskVector.add(taskId);
				// LogClient.writeLog(StringUtils
				// .join(new Object[]{"log=", taskId}));
				// logger.info("Success task list size [{}].", SuccessTaskVector
				// .getInstance().size());
			}

			process.getInputStream().close();
			process.getOutputStream().close();
			process.getErrorStream().close();
			process.destroy();
			process = null;
		} catch (IOException e) {
			logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"啟動一個線程來執行任務execProcess时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
		} catch (InterruptedException e) {
			logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"啟動一個線程來執行任務execProcess时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
		} finally {
			CurrentTaskQueue.reduce();

			logger.info(
					"FINALLY-->CurrentTaskQueue task count:{},running task count:{}",
					CurrentTaskQueue.getQueue().size(),
					CurrentTaskQueue.getCurrentQueueTaskCount());

			if (null != process) {
				try {
					if (null != process.getInputStream()) {
						process.getInputStream().close();
					}
				} catch (IOException e) {
				}
				try {
					if (null != process.getOutputStream()) {
						process.getOutputStream().close();
					}
				} catch (IOException e) {
				}
				try {
					if (null != process.getErrorStream()) {
						process.getErrorStream().close();
					}
				} catch (IOException e) {
				}

				process.destroy();
				process = null;
			}
		}

	}

	/**
	 * 構建出執行當前task的java command指令 <br>
	 * Created on: Apr 28, 2012 2:29:38 PM
	 * 
	 * @param currentTask
	 * @return
	 */
	private String getProcessCommand(CurrentTask currentTask) {

		StringBuffer command = new StringBuffer(3000);
		command.append(SHELL_SCRIPT).append(" ");
		command.append(StringUtils.trimToEmpty(currentTask.getVcSqlId()));
		command.append(" ");
		command.append(StringUtils.trimToEmpty(currentTask.getVcFactTableName()));
		command.append(" ");
		command.append(DateTimeUtils.getParseDateToStr(
				currentTask.getDtStatTime(), "yyyy-MM-ddHH:mm:ss"));
		command.append(" ").append(currentTask.getIntTaskId());

		return StringUtils.trimToEmpty(command.toString());

	}
}
