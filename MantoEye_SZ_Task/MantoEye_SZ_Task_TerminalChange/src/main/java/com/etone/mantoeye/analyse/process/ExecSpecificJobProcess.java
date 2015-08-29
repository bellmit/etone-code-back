/**
 *  com.etone.mantoeye.analyse.process.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.process;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

import com.etone.mantoeye.analyse.specific.ISpecificTaskJob;

/**
 * 執行任務的子線程
 * 
 * @author Wu Zhenzhen
 * @version May 15, 2012 5:21:41 PM
 */
public class ExecSpecificJobProcess {
	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ExecSpecificJobProcess.class);

	/**
	 * 用戶自定義實現類包名
	 */
	public static final String USER_DEFINE_SPECIFIC = "com.etone.mantoeye.analyse.specific.impl.";

	/**
	 * <br>
	 * Created on: May 15, 2012 5:21:41 PM
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (ArrayUtils.getLength(args) != 1) {
			logger.warn("The values of args : jobName .");
			System.exit(1);

		}

		// TODO -- 2012年5月16日10:35:59 執行實際子線程任務,已測試
		ExecSpecificJobProcess exec = new ExecSpecificJobProcess();
		exec.execute(StringUtils.trimToEmpty(args[0]));
	}

	/**
	 * 執行實際子線程任務 <br>
	 * Created on: May 16, 2012 10:36:21 AM
	 * 
	 * @param jobName
	 */
	private void execute(String jobName) {

		String className = USER_DEFINE_SPECIFIC + jobName;
		logger.debug("Job name : [{}].", className);

		try {
			Class<?> c = Class.forName(className);
			ISpecificTaskJob specigicJob = (ISpecificTaskJob) c.newInstance();
			specigicJob.execute();
		} catch (ClassNotFoundException e) {
			logger.error(
					"执行自定義功能任務时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
					new Object[]{e.getMessage(), e.getCause(), e.getClass()});
			System.exit(1);
		} catch (InstantiationException e) {
			logger.error(
					"执行自定義功能任務时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
					new Object[]{e.getMessage(), e.getCause(), e.getClass()});
			System.exit(1);
		} catch (IllegalAccessException e) {
			logger.error(
					"执行自定義功能任務时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
					new Object[]{e.getMessage(), e.getCause(), e.getClass()});
			System.exit(1);
		}

	}

}
