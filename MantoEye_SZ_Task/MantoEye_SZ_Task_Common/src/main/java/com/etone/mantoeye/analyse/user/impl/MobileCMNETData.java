/**
 *  com.etone.mantoeye.analyse.user.impl.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.user.impl;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.myhkzhen.util.date.DateTimeUtils;
import org.myhkzhen.util.file.FileUtil;
import org.myhkzhen.util.ftp.FtpClientOperator;
import org.myhkzhen.util.properties.PropertiesUtil;

import com.etone.mantoeye.analyse.domain.AnalyseParam;
import com.etone.mantoeye.analyse.domain.CmnetAnalyseParam;
import com.etone.mantoeye.analyse.service.common.IExportDataMananger;
import com.etone.mantoeye.analyse.service.impl.common.ExportDataManangerImpl;
import com.etone.mantoeye.analyse.user.IUserDefineAccess;
import com.etone.mantoeye.analyse.util.constant.Constant;

/**
 * CMNET數據傳送功能
 * 
 * @author Wu Zhenzhen
 * @version May 4, 2012 3:07:00 PM
 */
public class MobileCMNETData implements IUserDefineAccess {

	/**
	 * 自定義CMNET數據傳送FTP服務器地址
	 */
	private static final String DEFINE_FTP_SERVER_CMNET = PropertiesUtil
			.getConfigProperties("ftpServerCmnet");

	/**
	 * 自定義CMNET數據傳送FTP PORT
	 */
	private static final String DEFINE_FTP_PORT_CMNET_STRING = PropertiesUtil
			.getConfigProperties("ftpPortCmnet");

	/**
	 * 自定義CMNET數據傳送FTP PORT
	 */
	private static final int DEFINE_FTP_PORT_CMNET = NumberUtils.toInt(
			DEFINE_FTP_PORT_CMNET_STRING, 21);
	/**
	 * 自定義CMNET數據傳送FTP USER
	 */
	private static final String DEFINE_FTP_USER_CMNET = PropertiesUtil
			.getConfigProperties("ftpUserCmnet");
	/**
	 * 自定義CMNET數據傳送FTP PASSWORD
	 */
	private static final String DEFINE_FTP_PASSWORD_CMNET = PropertiesUtil
			.getConfigProperties("ftpPasswordCmnet");

	/**
	 * 自定義CMNET數據傳送FTP路徑
	 */
	private static final String DEFINE_DATA_FTP_CMNET = PropertiesUtil
			.getConfigProperties("cmnetDataRemotePath");
	// private String fileName = "";

	// private boolean flag = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.etone.mantoeye.analyse.user.IUserDefineAccess#execute(com.etone.mantoeye
	 * .analyse.domain.AnalyseParam)
	 */
	public void execute(AnalyseParam analyseParam) throws SQLException {
		logger.info(
				"[User Process] --- Cmnet data trans thread start and table name [{}].",
				analyseParam.getSourceTableName());

		long begindate = System.currentTimeMillis();

		// 分60個文件上傳
		List<CmnetAnalyseParam> list = getParamList(analyseParam);

		if (null == list || list.isEmpty()) {
			logger.warn("Cmnet data is not exists,process will be over.");
			System.exit(0);
		}

		// 導出文件
		exportData(list);

		// 改名字
		// fileReName(list);

		// 上傳文件
		// ftpTransFile(list);

		// 刪除文件
		// deleteLocalFile(list);

		logger.info("CMNET data trans ,use time : {} s\\t",
				((System.currentTimeMillis() - begindate) / 1000));

	}

	/**
	 * 為文件改名字，以備FTP 工具上傳使用 <br>
	 * Created on: Aug 8, 2012 10:54:36 AM
	 * 
	 * @param list
	 */
	// private void fileReName(List<CmnetAnalyseParam> list) {
	//
	// for (CmnetAnalyseParam param : list) {
	// String newFileName = "bill_" + param.getFileName();
	//
	// if (FileUtil.rename(
	// Constant.DEFAULT_DATA_TEMP_PATH + param.getFileName(),
	// Constant.DEFAULT_DATA_TEMP_PATH + newFileName))
	// logger.info("File [{}{}] to RENAME [{}{}]", new Object[]{
	// Constant.DEFAULT_DATA_TEMP_PATH, param.getFileName(),
	// Constant.DEFAULT_DATA_TEMP_PATH, newFileName});
	// else
	// logger.warn(
	// "File [{}{}] to RENAME [{}{}] ERROR.",
	// new Object[]{Constant.DEFAULT_DATA_TEMP_PATH,
	// param.getFileName(),
	// Constant.DEFAULT_DATA_TEMP_PATH, newFileName});
	// }
	//
	// }
	/**
	 * 刪除本地文件 <br>
	 * Created on: May 7, 2012 11:26:15 AM
	 * 
	 * @param list
	 */
	// private void deleteLocalFile(List<CmnetAnalyseParam> list) {
	// logger.debug("Start delete cmnet date file...");
	// long begindate = System.currentTimeMillis();
	// for (CmnetAnalyseParam param : list) {
	// String fileName = StringUtils.join(Constant.DEFAULT_DATA_TEMP_PATH,
	// param.getFileName());
	// if (FileUtil.deleteFile(fileName))
	// logger.info("File [{}] is deleted.", fileName);
	// }
	// logger.info("Delete cmnet local data file ,use time : {} s\\t",
	// ((System.currentTimeMillis() - begindate) / 1000));
	// }
	/**
	 * 上傳文件 <br>
	 * Created on: May 7, 2012 11:19:40 AM
	 * 
	 * @param list
	 */
	// private void ftpTransFile(List<CmnetAnalyseParam> list) {
	//
	// while (true) {
	// if (flag)
	// break;
	// else {
	// logger.warn("CMNET DATA FILE UNEXIST.PLEASE WAITING...");
	// try {
	// Thread.sleep(300);
	// } catch (InterruptedException e) {
	// }
	// continue;
	// }
	// }
	//
	// logger.debug("Start ftp cmnet data file to ftpServer.");
	// long begindate = System.currentTimeMillis();
	// int threadNum = 4;
	// ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);
	//
	// File localPath = new File(Constant.DEFAULT_DATA_TEMP_PATH);
	// File[] files = localPath.listFiles();
	//
	// String startWith = "bill_";
	// if (null != files && files.length > 0) {
	// for (int i = 0; i < files.length; i++) {
	// if (files[i].isFile() && (!files[i].isHidden())) {
	// fileName = files[i].getName();
	// logger.debug("Local file :{}.", fileName);
	//
	// if (fileName.toLowerCase().startsWith(
	// startWith.toLowerCase())
	// || fileName.startsWith(startWith)) {
	//
	// logger.debug("Prepared upload file :{}.", fileName);
	//
	// Runnable createData = new ExecFtpThread(fileName);
	//
	// threadPool.execute(createData);
	//
	// }
	// }
	// }
	// }
	//
	// // for (final CmnetAnalyseParam param : list) {
	// //
	// // // 利用多線程ftp文件
	// // // Thread createData = new Thread(new Runnable() {
	// //
	// // // public void run() {
	// // String fileName = param.getFileName();
	// // if (!ftp.update(Constant.DEFAULT_DATA_TEMP_PATH,
	// // DEFINE_DATA_FTP_CMNET, fileName)) {
	// // logger.error("执行FTP上傳文件到 服務器端时,报错,文件[{}{}]無法上傳至服務器.",
	// // Constant.DEFAULT_DATA_TEMP_PATH, fileName);
	// // } else {
	// // logger.info("File [{}{}] FTP to [{}].", new Object[]{
	// // Constant.DEFAULT_DATA_TEMP_PATH, fileName,
	// // DEFINE_DATA_FTP_CMNET});
	// //
	// // fileName = Constant.DEFAULT_DATA_TEMP_PATH
	// // + param.getFileName();
	// //
	// // }
	// //
	// // if (FileUtil.deleteFile(fileName))
	// // logger.info("File [{}] is deleted.", fileName);
	// //
	// // // // 刪除文件
	// // // if (FileUtil.deleteFile(StringUtils.join(
	// // // Constant.DEFAULT_DATA_TEMP_PATH, fileName)))
	// // // logger.info("File [{}{}] is deleted.",
	// // // Constant.DEFAULT_DATA_TEMP_PATH, fileName);
	// // // }
	// //
	// // // });
	// // // createData.start();
	// // }
	//
	// logger.info("Ftp cmnet data ,total use time : {} s\\t",
	// ((System.currentTimeMillis() - begindate) / 1000));
	//
	// threadPool.shutdown();
	// threadPool = null;
	// }

	class ExecFtpThread implements Runnable {

		private FtpClientOperator ftp = null;
		private String fileName = "";

		/**
		 * <br>
		 * Created on: Jan 6, 2013 11:26:12 AM
		 * 
		 * Created by: Wu Zhenzhen
		 * 
		 * @param ftp
		 * @param fileName
		 */
		public ExecFtpThread(String fileName) {
			this.fileName = fileName;
			init();
		}

		/**
		 * <br>
		 * Created on: Jan 6, 2013 11:27:49 AM
		 * 
		 * Created by: Wu Zhenzhen
		 * 
		 */
		private void init() {
			this.ftp = new FtpClientOperator(DEFINE_FTP_SERVER_CMNET,
					DEFINE_FTP_PORT_CMNET, DEFINE_FTP_USER_CMNET,
					DEFINE_FTP_PASSWORD_CMNET);

			if (!ftp.connectFtpServer()) {
				logger.error("执行FTP上傳文件到 服務器端时,报错:FTP服務器[{}]無法被用戶[{}]連接.",
						new Object[]{Constant.DEFAULT_FTP_SERVER,
								Constant.DEFAULT_FTP_USER});
				// System.exit(1);
				return;
			}

			ftp.setBinaryTransferType();
			ftp.setTransferCharsetUTF8();

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			long begindate = System.currentTimeMillis();

			try {
				if (!ftp.update(Constant.DEFAULT_DATA_TEMP_PATH,
						DEFINE_DATA_FTP_CMNET, fileName)) {
					logger.error("执行FTP上傳文件到 服務器端时,报错,文件[{}{}]無法上傳至服務器.",
							Constant.DEFAULT_DATA_TEMP_PATH, fileName);

				} else {
					logger.info("File [{}{}] FTP to [{}].", new Object[]{
							Constant.DEFAULT_DATA_TEMP_PATH, fileName,
							DEFINE_DATA_FTP_CMNET});

				}

				fileName = Constant.DEFAULT_DATA_TEMP_PATH + fileName;

				if (FileUtil.deleteFile(fileName))
					logger.info("File [{}] is deleted.", fileName);

			} finally {
				ftp.closeConnect();
			}

			logger.info("Ftp cmnet data ,use time : {} s\\t",
					((System.currentTimeMillis() - begindate) / 1000));

		}

	}

	/**
	 * 導出文件 <br>
	 * Created on: May 7, 2012 11:17:08 AM
	 * 
	 * @param list
	 */
	private void exportData(List<CmnetAnalyseParam> list) {
		logger.debug("Start export cmnet data to file.");
		long begindate = System.currentTimeMillis();

		int threadNum = 4;
		ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);

		for (CmnetAnalyseParam param : list) {

			Runnable createData = new ExecExportThread(param);

			threadPool.execute(createData);

		}

		logger.info("Export cmnet data ,total use time : {} s\\t",
				((System.currentTimeMillis() - begindate) / 1000));

		threadPool.shutdown();
		threadPool = null;

	}

	class ExecExportThread implements Runnable {

		private CmnetAnalyseParam param = null;
		private FtpClientOperator ftp = null;
		/**
		 * <br>
		 * Created on: Jan 6, 2013 1:58:32 PM
		 * 
		 * Created by: Wu Zhenzhen
		 * 
		 * @param param
		 */
		public ExecExportThread(CmnetAnalyseParam param) {
			this.param = param;
			logger.info("Start export cmnet data :[{}].", param.toString());
		}

		/**
		 * <br>
		 * Created on: Jan 6, 2013 11:27:49 AM
		 * 
		 * Created by: Wu Zhenzhen
		 * 
		 */
		private void init() {
			this.ftp = new FtpClientOperator(DEFINE_FTP_SERVER_CMNET,
					DEFINE_FTP_PORT_CMNET, DEFINE_FTP_USER_CMNET,
					DEFINE_FTP_PASSWORD_CMNET);

			if (!ftp.connectFtpServer()) {
				logger.error("执行FTP上傳文件到 服務器端时,报错:FTP服務器[{}]無法被用戶[{}]連接.",
						new Object[]{Constant.DEFAULT_FTP_SERVER,
								Constant.DEFAULT_FTP_USER});
				// System.exit(1);
				return;
			}

			ftp.setBinaryTransferType();
			ftp.setTransferCharsetUTF8();

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			
			init();

			String newFileName = "bill_" + param.getFileName();

			exportFile(newFileName);
			File file = new File(Constant.DEFAULT_DATA_TEMP_PATH + newFileName);

			if (null != file && file.exists())
				ftpFile(newFileName);
			else
				logger.warn("Local cmnet file :[{}] not exist.",
						file.getAbsoluteFile());
		}

		/**
		 * <br>
		 * Created on: Jan 6, 2013 3:39:13 PM
		 * 
		 * Created by: Wu Zhenzhen
		 * 
		 */
		private void exportFile(String newFileName) {
			long begindate = System.currentTimeMillis();

			IExportDataMananger exportDataMananger = new ExportDataManangerImpl();

			try {
				exportDataMananger.execExport(param);
				exportDataMananger = null;

				if (FileUtil.rename(
						Constant.DEFAULT_DATA_TEMP_PATH + param.getFileName(),
						Constant.DEFAULT_DATA_TEMP_PATH + newFileName))
					logger.info(
							"File [{}{}] to RENAME [{}{}]",
							new Object[]{Constant.DEFAULT_DATA_TEMP_PATH,
									param.getFileName(),
									Constant.DEFAULT_DATA_TEMP_PATH,
									newFileName});
				else
					logger.warn(
							"File [{}{}] to RENAME [{}{}] ERROR.",
							new Object[]{Constant.DEFAULT_DATA_TEMP_PATH,
									param.getFileName(),
									Constant.DEFAULT_DATA_TEMP_PATH,
									newFileName});

			} catch (SQLException e) {
				logger.error(
						"执行[{}.{}]从数据库中生成CMNET文件时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
						new Object[]{param.getSourceTableName(),
								param.getSqlMapId(), e.getMessage(),
								e.getCause(), e.getClass()});
			} finally {
				exportDataMananger = null;
			}

			logger.info("Export cmnet data ,use time : {} s\\t",
					((System.currentTimeMillis() - begindate) / 1000));
		}

		/**
		 * <br>
		 * Created on: Jan 6, 2013 3:38:13 PM
		 * 
		 * Created by: Wu Zhenzhen
		 * 
		 * @param newFileName
		 */
		private void ftpFile(String fileName) {
			long begindate = System.currentTimeMillis();

			try {
				if (!ftp.update(Constant.DEFAULT_DATA_TEMP_PATH,
						DEFINE_DATA_FTP_CMNET, fileName)) {
					logger.error("执行FTP上傳文件到 服務器端时,报错,文件[{}{}]無法上傳至服務器.",
							Constant.DEFAULT_DATA_TEMP_PATH, fileName);

				} else {
					logger.info("File [{}{}] FTP to [{}].", new Object[]{
							Constant.DEFAULT_DATA_TEMP_PATH, fileName,
							DEFINE_DATA_FTP_CMNET});

				}

				fileName = Constant.DEFAULT_DATA_TEMP_PATH + fileName;

				if (FileUtil.deleteFile(fileName))
					logger.info("File [{}] is deleted.", fileName);

			} finally {
				ftp.closeConnect();
			}

			logger.info("Ftp cmnet data ,use time : {} s\\t",
					((System.currentTimeMillis() - begindate) / 1000));
		}

	}

	/**
	 * 構建60個文件信息 <br>
	 * Created on: May 7, 2012 10:46:18 AM
	 * 
	 * @param analyseParam
	 * @return
	 */
	private List<CmnetAnalyseParam> getParamList(AnalyseParam analyseParam) {

		List<CmnetAnalyseParam> paramList = new Vector<CmnetAnalyseParam>();
		CmnetAnalyseParam param = null;

		for (int i = 0; i < 60; i++) {
			param = getParam(analyseParam);

			Date startTime = DateUtils.setMinutes(
					analyseParam.getDtLoadedTime(), i);
			Date endTime = null;

			if (i == 59) {
				endTime = DateUtils.addHours(analyseParam.getDtLoadedTime(), 1);
			} else {
				endTime = DateUtils.setMinutes(analyseParam.getDtLoadedTime(),
						(i + 1));
			}

			logger.debug("Cmnet data time --> [{}]~[{}].",
					DateTimeUtils.getParseDateToStr(startTime),
					DateTimeUtils.getParseDateToStr(endTime));

			param.setStartTime(startTime);
			param.setEndTime(endTime);

			String fileName = DateTimeUtils.getParseDateToStr(startTime,
					"yyyyMMddHHmm") + "_sz" + Constant.FILE_FORMAT_TXT;

			logger.debug("Cmnet data file name : [{}].", fileName);

			param.setFileName(fileName);

			paramList.add(param);
		}

		return paramList;
	}
	/**
	 * 構建新參數 <br>
	 * Created on: May 7, 2012 10:54:15 AM
	 * 
	 * @param analyseParam
	 * @return
	 */
	private CmnetAnalyseParam getParam(AnalyseParam analyseParam) {

		CmnetAnalyseParam param = new CmnetAnalyseParam();
		param.setSourceTableName(analyseParam.getSourceTableName());
		param.setTargetTableName(analyseParam.getTargetTableName());
		param.setDtLoadedTime(analyseParam.getDtLoadedTime());
		param.setOption(analyseParam.getOption());
		param.setSqlMapId(analyseParam.getSqlMapId());

		return param;
	}
}
