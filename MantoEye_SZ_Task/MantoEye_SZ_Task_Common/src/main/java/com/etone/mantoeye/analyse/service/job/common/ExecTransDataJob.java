/**
 *  com.etone.mantoeye.analyse.service.job.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.job.common;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.myhkzhen.util.date.DateTimeUtils;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;
import org.myhkzhen.util.properties.PropertiesUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.etone.mantoeye.analyse.domain.AnalyseParam;
import com.etone.mantoeye.analyse.service.common.IExecTaskManager;
import com.etone.mantoeye.analyse.service.impl.common.ExecTaskManagerImpl;
import com.etone.mantoeye.analyse.util.constant.Constant;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * 數據翻譯JOB
 * 
 * @author Wu Zhenzhen
 * @version May 8, 2012 5:42:03 PM
 */
public class ExecTransDataJob implements Job {
	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ExecTransDataJob.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("[Quartz Job]---- Exec trans data thread job.");

		final String transTableNameProperties = PropertiesUtil
				.getConfigProperties("transTableNames",
						(Constant.APP_HOME + Constant.SEPARATOR_CHAR
								+ Constant.CONFIG_PATH + "function.properties"));

		final String[] transTableNameArr = StringUtils.split(
				transTableNameProperties, ';');

		IExecTaskManager manager = new ExecTaskManagerImpl();

		for (final String transTableName : transTableNameArr) {
			// 啟動線程執行當前任務
			logger.debug("Trans Data Table : {}.", transTableName);
			// Thread transData = new Thread(new Runnable() {

			// public void run() {
			long begindate = System.currentTimeMillis();

			// 構造參數
			AnalyseParam param = getAnalyseParam(transTableName);

			logger.debug("Trans data param:{}.", param);
			// boolean flag = true;
			// int i = 0;
			// while (flag) {
			// if (i++ > 50) {
			// logger.warn("直接執行Load表數據任務超過[{}]次出錯,結束本次操作.", i);
			// // manager = null;
			// break;
			// }

			try {
				manager.execute(param.getSqlMapId(), param);
				// manager = null;
				// flag = false;
			} catch (SQLException e) {

				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Table lock,waiting...msg:[{}].", e.getCause()
							.getMessage());
				else
					logger.error(
							"执行[{}.{}]數據翻譯的SQL任務时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
							new Object[]{param.getSourceTableName(),
									param.getSqlMapId(), e.getMessage(),
									e.getCause(), e.getClass()});

				// flag = true;

				try {
					Thread.sleep(6000);
				} catch (InterruptedException e1) {
					// Ignore
				}

				continue;
				// } finally {
				// manager = null;
			}
			// }
			logger.info("Exec Trans data table [{}],use times:[{}] s.",
					new Object[]{transTableName,
							((System.currentTimeMillis() - begindate) / 1000)});
			// }
			// });
			// transData.start();
		}

		manager = null;
	}
	/**
	 * 構造參數 <br>
	 * Created on: May 9, 2012 10:50:32 AM
	 * 
	 * @param transTableName
	 * @return
	 */
	protected AnalyseParam getAnalyseParam(String transTableName) {
		String sqlMapId = "trans_" + transTableName;

		// 設置時間
		AnalyseParam param = new AnalyseParam();
		Date currentDate = DateTimeUtils.getCurrentDate();
		param.setIntYear(DateTimeUtils.getYear(currentDate));
		param.setIntMonth(DateTimeUtils.getMonth(currentDate));
		param.setIntDay(DateTimeUtils.getDay(currentDate));
		param.setIntWeek(DateTimeUtils.getWeek(currentDate));
		param.setIntHour(DateTimeUtils.getHour(currentDate));

		param.setSqlMapId(sqlMapId);

		return param;

	}

}
