/**
 *  com.etone.mantoeye.analyse.service.job.specific.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.service.job.specific;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;
import org.myhkzhen.util.properties.PropertiesUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.etone.mantoeye.analyse.process.handler.StreamHandler;
import com.etone.mantoeye.analyse.util.constant.Constant;

/**
 * 启动执行特定任务的线程
 * 
 * @author Wu Zhenzhen
 * @version May 15, 2012 4:57:01 PM
 */
public class ExecSpecificTaskJob implements Job {

	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ExecSpecificTaskJob.class);

	/**
	 * 任務查詢進程shell腳本
	 */
	private static final String SHELL_SCRIPT = StringUtils.join(
			Constant.APP_HOME, "/bin/process_specific_data.sh");

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("[Quartz Job]---- Exec specific task thread job.");

		final String config_file = StringUtils.join(new String[]{
				Constant.APP_HOME, Constant.SEPARATOR_CHAR,
				Constant.CONFIG_PATH, "function.properties"});

		final String specificJobNames = PropertiesUtil.getConfigProperties(
				"specificJobNames", config_file);

		final String[] jobNames = StringUtils.split(specificJobNames, ',');
		logger.debug("Specific job name:[{}].", jobNames);

		for (final String jobName : jobNames) {

			Thread createData = new Thread(new Runnable() {
				public void run() {
					// 啟動線程執行當前任務
					logger.debug("Exec Job {}.", jobName);
					execProcess(jobName);
				}

			});
			createData.start();
		}
	}

	/**
	 * 啟動線程,執行當前任務 <br>
	 * Created on: May 15, 2012 5:13:29 PM
	 * 
	 * @param jobName
	 */
	protected void execProcess(String jobName) {
		// 構建出執行當前task的java command指令
		String command = getProcessCommand(jobName);
		logger.debug("ExecSpecificTaskJob run task command:{}.",
				command.toString());

		Process process = null;
		try {
			process = Runtime.getRuntime().exec(command.toString());

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

			if (1 == process.exitValue()) {
				logger.error("Job : {} fail.", jobName);
			} else {
				logger.info("Job : {} finish.", jobName);
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
	 * <br>
	 * Created on: May 15, 2012 5:14:42 PM
	 * 
	 * @param jobName
	 * @return
	 */
	private String getProcessCommand(String jobName) {
		StringBuffer command = new StringBuffer(3000);
		command.append(SHELL_SCRIPT).append(" ").append(jobName);

		return StringUtils.trimToEmpty(command.toString());
	}

}
