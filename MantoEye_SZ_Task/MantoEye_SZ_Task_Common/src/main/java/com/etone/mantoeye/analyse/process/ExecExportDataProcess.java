/**
 *  com.etone.mantoeye.analyse.process.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.process;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.myhkzhen.util.date.DateTimeUtils;
import org.myhkzhen.util.file.FileUtil;
import org.myhkzhen.util.ftp.FtpClientOperator;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;

import com.etone.mantoeye.analyse.domain.AnalyseParam;
import com.etone.mantoeye.analyse.service.common.IExecTaskManager;
import com.etone.mantoeye.analyse.service.common.IExportDataMananger;
import com.etone.mantoeye.analyse.service.common.IImportDataMananger;
import com.etone.mantoeye.analyse.service.impl.common.ExecTaskManagerImpl;
import com.etone.mantoeye.analyse.service.impl.common.ExportDataManangerImpl;
import com.etone.mantoeye.analyse.service.impl.common.ImportDataManangerImpl;
import com.etone.mantoeye.analyse.socket.LogClient;
import com.etone.mantoeye.analyse.user.IUserDefineAccess;
import com.etone.mantoeye.analyse.util.constant.Constant;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * 執行任務的子線程
 * 
 * @author Wu Zhenzhen
 * @version Apr 28, 2012 2:27:00 PM
 */
public class ExecExportDataProcess {

	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ExecExportDataProcess.class);

	private static final String[] ERROR_SQL_ARR = {
			"load_ftbStatTerminalChange", "proc_TerminalUpdate_day",
			"query_ftbStatTerminalChange",
			"exec_delete_ftbAssistVetorRecogniseData",
			"exec_ftbAssistVetorRecognise",
			"exec_delete_ftbAssistVetorRecognise",
			"query_ftbAssistVetorRecognise"};

	/**
	 * 用戶自定義實現類包名
	 */
	public static final String USER_DEFINE_EXPORT = "com.etone.mantoeye.analyse.user.impl.";

	/**
	 * 執行方法 <br>
	 * Created on: May 2, 2012 2:17:52 PM
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// args
		// 4个参数,分别是:vcsqlld,vcTableName,dtStatTime,taskId
		// java -Xms256M -Xmx1024M -cp task.jar:
		// com.etone.mantoeyes.analyse.ExecProcess 70

		if (ArrayUtils.getLength(args) != 4) {
			logger.warn("The values of args : vcSqlId,vcTableName,dtStatTime,taskId .");
			System.exit(1);

		}

		// TODO -- 2012年4月28日17:25:35 執行數據導出操作,已測試
		ExecExportDataProcess exec = new ExecExportDataProcess();
		exec.execute(StringUtils.trimToEmpty(args[0]),
				StringUtils.trimToEmpty(args[1]),
				StringUtils.trimToEmpty(args[2]),
				StringUtils.trimToEmpty(args[3]));
	}

	/**
	 * 執行實際子線程任務 <br>
	 * Created on: May 2, 2012 2:20:52 PM
	 * 
	 * @param vcSqlId
	 * @param vcFactTableName
	 * @param dtStatTime
	 * @param currentTaskId
	 */
	private void execute(String vcSqlId, String vcFactTableName,
			String dtStatTime, String currentTaskId) {
		logger.debug("Execute params : [{}][{}][{}][{}].", new Object[]{
				vcSqlId, vcFactTableName, dtStatTime, currentTaskId});

		long begindate = System.currentTimeMillis();

		String[] sqlIds = StringUtils.split(vcSqlId, ';');
		if (ArrayUtils.isEmpty(sqlIds)) {
			logger.warn("SqlMapId is empty,programme will be exit.");
			System.exit(1);
		}

		// 初始化参数
		AnalyseParam param = getInitParam(vcFactTableName, dtStatTime,
				currentTaskId);

		logger.debug("Init param anaylse :[{}]", param);

		// 更新task狀態，為執行中
		LogClient.writeLog("state=" + currentTaskId + ";" + 2);
		// updateTaskStatus(currentTaskId, 2);
		logger.info("Start exec task : update current task id [{}] status 2.",
				currentTaskId);

		boolean flag = true;

		for (String sqlMapId : sqlIds) {

			sqlMapId = StringUtils.trimToEmpty(sqlMapId);

			logger.info("Current exec task --> [({}){}.{}].", new Object[]{
					currentTaskId, vcFactTableName, sqlMapId});

			param.setSqlMapId(sqlMapId);

			if (StringUtils.containsIgnoreCase(sqlMapId, "query_")) {
				logger.debug("Start query data from table to file.");
				exportData(param);

				File file = FileUtil
						.getFile((Constant.DEFAULT_DATA_TEMP_PATH + param
								.getFileName()));

				if (null == file) {
					logger.warn(
							"Export file [{}.{}{}] is empty,export data exception.",
							new Object[]{sqlMapId,
									Constant.DEFAULT_DATA_TEMP_PATH,
									param.getFileName()});

					if (!ArrayUtils.contains(ERROR_SQL_ARR, sqlMapId)) {
						LogClient.writeLog("state=" + currentTaskId + ";" + 0);
						deleteLoadedFile(param.getFileName());
						flag = false;
					}

					break;
				}

				long len = FileUtils.sizeOf(file);
				if (len == 0l) {
					logger.warn(
							"Export file [{}.{}{}] is empty,export data exception.",
							new Object[]{sqlMapId,
									Constant.DEFAULT_DATA_TEMP_PATH,
									param.getFileName()});
					if (!ArrayUtils.contains(ERROR_SQL_ARR, sqlMapId)) {
						LogClient.writeLog("state=" + currentTaskId + ";" + 0);
						deleteLoadedFile(param.getFileName());
						flag = false;
					}
					break;
				} else {
					logger.info(
							"Query [{}.({}{})] size is [{}].",
							new Object[]{sqlMapId,
									Constant.DEFAULT_DATA_TEMP_PATH,
									param.getFileName(), len});
				}

			} else if (StringUtils.containsIgnoreCase(sqlMapId, "load_")) {
				logger.debug("Start load data from file to table.");

				importData(param);

				// 刪除load后的文件
				deleteLoadedFile(param.getFileName());

			} else if (StringUtils.containsIgnoreCase(sqlMapId, "ftp")) {
				logger.debug("Start ftp file.");

				logger.debug("File trans type:{}", Constant.TRANS_DATATYPE);
				if (!StringUtils.equalsIgnoreCase(Constant.NFS,
						Constant.TRANS_DATATYPE)) {
					uploadFTPFile(param.getFileName(), currentTaskId);
					// } else {
					// logger.info("NFS system type,do not ftp operation.");
				}

			} else if (StringUtils.containsIgnoreCase(sqlMapId, "user_")) {
				logger.debug("Start user specific process.");
				newInstanceExec(param);
			} else {
				logger.debug("Start exec other sql.");
				execSqlMapId(param);

				try {
					Thread.sleep(6000);
				} catch (InterruptedException e1) {
					// Ignore
				}
			}

		}
		// SOCKET記錄任務操作日誌信息
		if (flag) {
			LogClient.writeLog("log=" + currentTaskId);
			logger.info("End exec complete current task [{}] update logs.",
					currentTaskId);

			logger.info("Exec [({}){}.{}],use times:[{}]s.",
					new Object[]{currentTaskId, vcFactTableName, sqlIds,
							((System.currentTimeMillis() - begindate) / 1000)});
		} else {
			logger.warn(
					"Exec task : [{}:[{}]-{}] failed.",
					new Object[]{
							currentTaskId,
							DateFormatUtils.format(param.getDtLoadedTime(),
									"yyyy-MM-dd HH:mm:ss"), sqlIds});
		}
		// writeTaskCompleteLog(currentTaskId);

	}

	/**
	 * 初始化參數 <br>
	 * Created on: May 11, 2012 11:42:50 AM
	 * 
	 * @param vcFactTableName
	 * @param dtStatTime
	 * @param currentTaskId
	 * @return
	 */
	private AnalyseParam getInitParam(String vcFactTableName,
			String dtStatTime, String currentTaskId) {

		String fileName = currentTaskId + Constant.CONNECT_SYMBOL
				+ vcFactTableName;
		Date date = DateTimeUtils.getParseStrToDate(dtStatTime,
				"yyyy-MM-ddHH:mm:ss");

		AnalyseParam param = new AnalyseParam();
		param.setSourceTableName(vcFactTableName);
		param.setFileName(fileName);
		param.setDtLoadedTime(date);
		param.setTaskId(NumberUtils.toLong(currentTaskId));
		param.setIntYear(DateTimeUtils.getYear(date));
		param.setIntMonth(DateTimeUtils.getMonth(date));
		param.setIntDay(DateTimeUtils.getDay(date));
		param.setIntHour(DateTimeUtils.getHour(date));
		param.setIntWeek(DateTimeUtils.getWeek(date));

		return param;
	}

	/**
	 * 導入數據文件到表中 <br>
	 * Created on: May 11, 2012 11:31:39 AM
	 * 
	 * @param param
	 */
	private void importData(AnalyseParam param) {

		IImportDataMananger importDataMananger = null;

		boolean flag = true;
		int i = 0;
		while (flag) {
			if (i++ > 50) {
				logger.warn("直接執行Load表數據任務超過[{}]次出錯,結束本次操作.", i);
				LogClient.writeLog("state=" + param.getTaskId() + ";" + 0);
				// updateTaskStatus(param.getTaskId().toString(), 0);
				logger.info(
						"Exec error and Rollback current task id [{}] status 0.",
						param.getTaskId());
				deleteLoadedFile(param.getFileName());

				importDataMananger = null;
				System.exit(1);
			}

			try {
				importDataMananger = new ImportDataManangerImpl();
				importDataMananger.execImport(param);
				flag = false;
				importDataMananger = null;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"第[{}]次直接執行[{}.{}]Load表數據任務时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
							new Object[]{i, param.getSourceTableName(),
									param.getSqlMapId(), e.getMessage(),
									e.getCause(), e.getClass()});

				flag = true;

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// Ignore
				}
			} finally {
				importDataMananger = null;
			}
		}
	}

	/**
	 * 刪除文件 <br>
	 * Created on: May 11, 2012 11:45:32 AM
	 * 
	 * @param fileName
	 */
	private void deleteLoadedFile(String fileName) {
		String file = Constant.DEFAULT_DATA_TEMP_PATH + fileName;
		logger.debug("After Load ,Delete file [{}] start.", file);
		if (StringUtils.equalsIgnoreCase(Constant.NFS, Constant.TRANS_DATATYPE)) {
			logger.debug("File start delete in nfs trans system.");
			deleteNFSFile(file);
		} else {
			logger.debug("File start delete in ftp trans system.");
			deleteFTPFile(file);
		}
	}

	/**
	 * ftp傳送 <br>
	 * Created on: May 9, 2012 10:02:11 AM
	 * 
	 * @param file
	 */
	private void deleteFTPFile(String fileName) {
		FtpClientOperator ftp = new FtpClientOperator(
				Constant.DEFAULT_FTP_SERVER, Constant.DEFAULT_FTP_PORT,
				Constant.DEFAULT_FTP_USER, Constant.DEFAULT_FTP_PASSWORD);

		if (!ftp.connectFtpServer()) {
			logger.error("执行準備Load數據后刪除FTP服務器上文件时,报错:FTP服務器[{}]無法被用戶[{}]連接.",
					new Object[]{Constant.DEFAULT_FTP_SERVER,
							Constant.DEFAULT_FTP_USER});
		}

		ftp.setBinaryTransferType();
		ftp.setTransferCharsetUTF8();

		if (!ftp.deleteFile(Constant.DEFAULT_DATA_TEMP_PATH, fileName))
			logger.warn("Delete ftp file [{}] error.",
					Constant.DEFAULT_DATA_TEMP_PATH, fileName);

		ftp.closeConnect();
	}

	/**
	 * nfs傳送 <br>
	 * Created on: May 9, 2012 10:01:47 AM
	 * 
	 * @param file
	 */
	private void deleteNFSFile(String file) {
		if (FileUtil.deleteFile(file))
			logger.info("File [{}] delete.", file);
	}

	/**
	 * 直接執行一些sql操作 <br>
	 * Created on: May 4, 2012 2:46:41 PM
	 * 
	 * @param param
	 */
	private void execSqlMapId(AnalyseParam param) {
		IExecTaskManager exec = null;
		boolean flag = true;
		int i = 0;
		while (flag) {
			if (i++ > 20) {
				logger.warn("直接執行其他的SQL任務超過[{}]次,出錯,結束本次操作.", i);
				LogClient.writeLog("state=" + param.getTaskId() + ";" + 0);
				// updateTaskStatus(param.getTaskId().toString(), 0);
				logger.info(
						"Exec error and Rollback current task id [{}] status 0.",
						param.getTaskId());

				exec = null;
				System.exit(1);
			}

			try {
				exec = new ExecTaskManagerImpl();
				exec.execute(param.getSqlMapId(), param);
				flag = false;
				exec = null;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]---Table lock,waiting...msg:[{}].",
							i, e.getCause().getMessage());
				else
					logger.error(
							"第[{}]次执行[{}.{}]其他的SQL任務时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
							new Object[]{i, param.getSourceTableName(),
									param.getSqlMapId(), e.getMessage(),
									e.getCause(), e.getClass()});

				flag = true;

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					// Ignore
				}
			} finally {
				exec = null;
			}
		}
	}

	/**
	 * 用戶自定義的一些task <br>
	 * Created on: May 4, 2012 2:37:39 PM
	 * 
	 * @param param
	 */
	private void newInstanceExec(AnalyseParam param) {

		String className = USER_DEFINE_EXPORT
				+ StringUtils.substring(param.getSqlMapId(), 5);
		logger.debug("ClassName:{}.", className);

		try {
			Class<?> c = Class.forName(className);
			IUserDefineAccess userDefineAccess = (IUserDefineAccess) c
					.newInstance();
			userDefineAccess.execute(param);
		} catch (ClassNotFoundException e) {
			logger.error(
					"执行自定義功能任務时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
					new Object[]{e.getMessage(), e.getCause(), e.getClass()});
			LogClient.writeLog("state=" + param.getTaskId() + ";" + 0);
			// updateTaskStatus(param.getTaskId().toString(), 0);
			logger.info(
					"Exec error and Rollback current task id [{}] status 0.",
					param.getTaskId());
			System.exit(1);
		} catch (InstantiationException e) {
			logger.error(
					"执行自定義功能任務时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
					new Object[]{e.getMessage(), e.getCause(), e.getClass()});
			LogClient.writeLog("state=" + param.getTaskId() + ";" + 0);
			// updateTaskStatus(param.getTaskId().toString(), 0);
			logger.info(
					"Exec error and Rollback current task id [{}] status 0.",
					param.getTaskId());
			System.exit(1);
		} catch (IllegalAccessException e) {
			logger.error(
					"执行自定義功能任務时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
					new Object[]{e.getMessage(), e.getCause(), e.getClass()});
			LogClient.writeLog("state=" + param.getTaskId() + ";" + 0);
			// updateTaskStatus(param.getTaskId().toString(), 0);
			logger.info(
					"Exec error and Rollback current task id [{}] status 0.",
					param.getTaskId());
			System.exit(1);
		} catch (SQLException e) {
			logger.error(
					"执行自定義功能任務时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
					new Object[]{e.getMessage(), e.getCause(), e.getClass()});
			LogClient.writeLog("state=" + param.getTaskId() + ";" + 0);
			// updateTaskStatus(param.getTaskId().toString(), 0);
			logger.info(
					"Exec error and Rollback current task id [{}] status 0.",
					param.getTaskId());
			System.exit(1);
		}

	}

	/**
	 * 上傳文件到FTP服務器 <br>
	 * Created on: May 4, 2012 2:24:01 PM
	 * 
	 * @param fileName
	 * @param currentTaskId
	 */
	private void uploadFTPFile(String fileName, String currentTaskId) {
		FtpClientOperator ftp = new FtpClientOperator(
				Constant.DEFAULT_FTP_SERVER, Constant.DEFAULT_FTP_PORT,
				Constant.DEFAULT_FTP_USER, Constant.DEFAULT_FTP_PASSWORD);

		if (!ftp.connectFtpServer()) {
			logger.error("执行FTP上傳文件到 服務器端时,报错:FTP服務器[{}]無法被用戶[{}]連接.",
					new Object[]{Constant.DEFAULT_FTP_SERVER,
							Constant.DEFAULT_FTP_USER});

			LogClient.writeLog("state=" + currentTaskId + ";" + 0);
			// updateTaskStatus(currentTaskId, 0);
			logger.info(
					"Exec error and Rollback current task id [{}] status 0.",
					currentTaskId);
			System.exit(1);
		}

		ftp.setBinaryTransferType();
		ftp.setTransferCharsetUTF8();

		if (!ftp.update(Constant.DEFAULT_DATA_TEMP_PATH,
				Constant.DEFAULT_DATA_TEMP_PATH, fileName)) {
			logger.error("执行FTP上傳文件到 服務器端时,报错,文件[{}{}]無法上傳至服務器.",
					Constant.DEFAULT_DATA_TEMP_PATH, fileName);

			LogClient.writeLog("state=" + currentTaskId + ";" + 0);
			// updateTaskStatus(currentTaskId, 0);
			logger.info(
					"Exec error and Rollback current task id [{}] status 0.",
					currentTaskId);
			ftp.closeConnect();
			System.exit(1);
		}

		ftp.closeConnect();

	}

	/**
	 * 導出數據到文件 <br>
	 * Created on: May 4, 2012 11:56:31 AM
	 * 
	 * @param param
	 */
	private void exportData(AnalyseParam param) {
		logger.debug("Export data param --->[{}].", param);
		IExportDataMananger exportDataMananger = new ExportDataManangerImpl();
		try {
			exportDataMananger.execExport(param);
			exportDataMananger = null;
		} catch (SQLException e) {
			logger.error(
					"执行[{}.{}]操作从数据库中生成文件时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
					new Object[]{param.getSourceTableName(),
							param.getSqlMapId(), e.getMessage(), e.getCause(),
							e.getClass()});
			LogClient.writeLog("state=" + param.getTaskId() + ";" + 0);
			logger.info(
					"Exec error and Rollback current task id [{}] status 0.",
					param.getTaskId());
			exportDataMananger = null;
			System.exit(1);
		} finally {
			exportDataMananger = null;
		}

	}
}
