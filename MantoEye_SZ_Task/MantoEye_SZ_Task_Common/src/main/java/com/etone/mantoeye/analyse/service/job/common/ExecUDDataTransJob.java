/**
 *  com.etone.mantoeye.analyse.service.job.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.service.job.common;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.myhkzhen.util.date.DateTimeUtils;
import org.myhkzhen.util.file.FileUtil;
import org.myhkzhen.util.ftp.FtpClientOperator;
import org.myhkzhen.util.log.Logger;
import org.myhkzhen.util.log.LoggerFactory;
import org.myhkzhen.util.properties.PropertiesUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.etone.mantoeye.analyse.domain.ui.TbUDTimeBean;
import com.etone.mantoeye.analyse.service.common.IUDInterfaceManager;
import com.etone.mantoeye.analyse.service.impl.common.UDInterfaceManagerImpl;
import com.etone.mantoeye.analyse.util.constant.Constant;
import com.etone.mantoeye.analyse.util.error.CommonErrorUtil;

/**
 * 統一平臺接口
 * 
 * @author Wu Zhenzhen
 * @version May 21, 2012 11:16:49 AM
 */
public class ExecUDDataTransJob implements Job {
	/**
	 * 日誌
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ExecUDDataTransJob.class);
	/**
	 * FTP REMOTEDIR
	 */
	private static final String FTP_TOFILEPATH_UDINTERFACE = "/pm/";

	private static final String FTP_TOFILEPATH_UDINTERFACE_BUSS = "busi_sys";

	private static final String CONFIG_FILE = Constant.APP_HOME
			+ Constant.SEPARATOR_CHAR + Constant.CONFIG_PATH
			+ "uidatatrans.properties";

	/**
	 * FTP SERVER
	 */
	private static final String FTP_SERVER_UDINTERFACE = PropertiesUtil
			.getConfigProperties("ftpServerUD", CONFIG_FILE);
	/**
	 * FTP PORT
	 */
	private static final String FTP_PORT_UDINTERFACE_STRING = PropertiesUtil
			.getConfigProperties("ftpPortUD", CONFIG_FILE);
	/**
	 * FTP PORT
	 */
	private static final int FTP_PORT_UDINTERFACE = NumberUtils.toInt(
			FTP_PORT_UDINTERFACE_STRING, 21);

	/**
	 * FTP USER
	 */
	private static final String FTP_USER_UDINTERFACE = PropertiesUtil
			.getConfigProperties("ftpUserUD", CONFIG_FILE);

	/**
	 * FTP PASSWORD
	 */
	private static final String FTP_PASSWORD_UDINTERFACE = PropertiesUtil
			.getConfigProperties("ftpPasswordUD", CONFIG_FILE);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("[Quartz Job]---- Exec UI interface task.");

		IUDInterfaceManager manager = new UDInterfaceManagerImpl();

		TbUDTimeBean tbUDTime = null;

		try {
			// 查看是否存在正在執行 的任務
			tbUDTime = manager.getRunningTask();
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行統一平臺接口任務时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});
			manager = null;
			return;
		}

		if (null != tbUDTime && null != tbUDTime.getDtStatTime()) {
			logger.info("UI data is running.");
			manager = null;
			return;
		}

		try {
			// 要生成同一平臺數據的時間
			tbUDTime = manager.getTbUDTime();

			if (null == tbUDTime) {
				logger.info("UI Data not found.");
				manager = null;
				return;
			}
		} catch (SQLException e) {
			logger.error(
					"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行統一平臺接口任務时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});

			manager = null;
			return;

		}

		Long count = 0l;
		try {
			// 查看時間數據是否生成
			count = manager.getFtbOverViewData(tbUDTime);
		} catch (SQLException e) {
			logger.error("{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
					new Object[]{"執行統一平臺接口任務查看是否有傳入時間的數據时,报错:", e.getMessage(),
							e.getCause(), e.getClass()});

			writteErrorStatus(tbUDTime, manager, "查看是否有傳入時間的數據", 0);
			manager = null;
			return;
		}

		if (count != 0) {
			try {
				tbUDTime.setIntStatus(1);
				manager.update("udpate_status", tbUDTime);
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Table lock,waiting...msg:[{}].", e.getCause()
							.getMessage());
				else
					logger.error(
							"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{"執行統一平臺接口任務更新執行狀態时,报错:",
									e.getMessage(), e.getCause(), e.getClass()});

				manager = null;
				return;
			}

			logger.info("UI interface data in [{}] exsist. ",
					DateTimeUtils.getParseDateToStr(tbUDTime.getDtStatTime()));

			// 按intType的类型判断是1.APP的数据还是2.BB的数据
			if (1 == tbUDTime.getIntType()) {
				// APP
				// 1.循环读数据(1.BB,2.彩信,3.腾讯,4.飞信,5.139邮箱)
				// 20110504 赖 要求多加以下二种业务: IM_QQ, 腾讯微博

				int error_time = 0;
				for (int i = 2; i <= 5; i++) {
					try {
						getData2Ftp(manager, tbUDTime.getDtStatTime(), i);
					} catch (SQLException e) {
						logger.error(
								"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
								new Object[]{"統一平台数据生成APP文件时,报错:",
										e.getMessage(), e.getCause(),
										e.getClass()});

						++error_time;

					}
				}

				logger.info("Finish: mms , qq , fetion , 139 data.");

				logger.debug("error_time ==> {}.", error_time);
				if (error_time != 0) {
					logger.info("Failed: mms , qq , fetion , 139 data.");
					writteErrorStatus(tbUDTime, manager, "APP", 0);
					manager = null;
					return;
				}

				error_time = 0;
				// 20110707大运会新增需求
				// for (int i = 10; i <= 10; i++) {
				try {
					getData2Ftp(manager, tbUDTime.getDtStatTime(), 10);
					// getData2Ftp(manager, tbUDTime.getDtStatTime(), i);
				} catch (SQLException e) {
					logger.error(
							"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{"統一平台数据生成大运会新增需求文件时,报错:",
									e.getMessage(), e.getCause(), e.getClass()});
					++error_time;
				}
				// }

				logger.info("Finish: U niversity data.");
				logger.debug("error_time ==> {}.", error_time);

				if (error_time != 0) {
					logger.info("Failed: U niversity data.");
					writteErrorStatus(tbUDTime, manager, "大运会新增需求", 0);
					manager = null;
					return;
				}

				// 存储过程生成数据,类型为11,12,13 即业务排名，重要客户，集团客户
				try {
					getData2BigSport(manager, tbUDTime.getDtStatTime());
				} catch (SQLException e) {
					logger.error(
							"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{
									"統一平台数据調用存储过程生成数据[业务排名,重要客户,集团客户]文件时,报错:",
									e.getMessage(), e.getCause(), e.getClass()});
					writteErrorStatus(tbUDTime, manager, "[业务排名,重要客户,集团客户]", 0);
					manager = null;
					logger.info("Failed: buss top, import client, group client data.");
					return;
				}

			} else if (2 == tbUDTime.getIntType()) {
				// 1.BB
				try {
					getData2Ftp(manager, tbUDTime.getDtStatTime(), 1);
				} catch (SQLException e) {
					logger.error(
							"{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{"統一平台数据生成BB文件时,报错:", e.getMessage(),
									e.getCause(), e.getClass()});
					writteErrorStatus(tbUDTime, manager, "BB", 0);
					logger.info("Failed: BB data.");
					manager = null;
					return;
				}

				logger.info("Finish: BB data.");
			}

			// 跟新日誌
			updateLog(manager, tbUDTime);

		}
		manager = null;
	}
	/**
	 * 
	 * <br>
	 * Created on: May 21, 2012 2:10:24 PM
	 * 
	 * @param manager
	 * @param tbUDTime
	 */
	private void updateLog(IUDInterfaceManager manager, TbUDTimeBean tbUDTime) {
		// 4.更新传输日志(标明已传了什么)
		logger.info("Update UI data Log : [{}].",
				DateTimeUtils.getParseDateToStr(tbUDTime.getDtStatTime()));

		int i = 0;
		boolean flag = true;
		while (flag) {
			if (i++ > 50) {
				manager = null;
				break;
			}
			try {
				manager.updateStatTime(tbUDTime);
				flag = false;
				manager = null;
			} catch (SQLException e) {
				if (CommonErrorUtil.isTableLockError(e))
					logger.warn("Times[{}]-Table lock,waiting...msg:[{}].", i,
							e.getCause().getMessage());
				else
					logger.error(
							"第[{}]次:{}\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
							new Object[]{i, "統一平台数据更新狀態时,报错:", e.getMessage(),
									e.getCause(), e.getClass()});
				flag = true;
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
				}
			}
		}
		manager = null;
	}

	/**
	 * 储过程生成数据,类型为11,12,13 即业务排名，重要客户，集团客户 <br>
	 * Created on: May 21, 2012 11:43:20 AM
	 * 
	 * @param manager
	 * @param dtStatTime
	 */
	private void getData2BigSport(IUDInterfaceManager manager, Date dtStatTime)
			throws SQLException {
		logger.debug("Buss top data time:{}",
				DateTimeUtils.getParseDateToStr(dtStatTime));

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statdate", DateTimeUtils.getParseDateToStr(dtStatTime));
		logger.info("Exec proc_buss_top use param:[{}]", map);
		manager.update("proc_buss_top", map);
		map = null;

		// 生成文件
		for (int i = 11; i <= 13; i++) {

			logger.info("Query {} Data to File.", ((i == 11)
					? "Buss Top"
					: ((i == 12) ? "Import Client" : "Group Client")));
			TbUDTimeBean param = new TbUDTimeBean(dtStatTime, i, 0);
			List<String> list = manager.getFtbOverViewSport(param);
			if (null != list && !list.isEmpty()) {
				// 2.写文件
				logger.debug("1--[getData2BigSport]----Config file --> [{}].",
						CONFIG_FILE);

				logger.debug("1.1---type-->[{}].", i);

				String fileName = "fileName" + i;

				logger.debug("2--[getData2BigSport]----File name --> [{}].",
						fileName);

				fileName = PropertiesUtil.getConfigProperties(fileName,
						CONFIG_FILE);

				logger.debug("3--[getData2BigSport]----File name --> [{}].",
						fileName);

				fileName = fileName + Constant.FILE_FORMAT_TXT;

				logger.info("UI Data File : [{}{}].",
						Constant.DEFAULT_DATA_TEMP_PATH, fileName);

				if (!FileUtil.createFileByList(list,
						(Constant.DEFAULT_DATA_TEMP_PATH + fileName)))
					logger.info("Create file from list error.");
				else {
					// 3.通过FTP传输
					ftpUploadFile(i, fileName, param.getDtStatTime());

					// 4.删除文件

					if (FileUtil
							.deleteFile((Constant.DEFAULT_DATA_TEMP_PATH + fileName)))
						logger.info("Delete ui file [{}{}] complete.",
								Constant.DEFAULT_DATA_TEMP_PATH, fileName);
				}

			}
		}
	}
	/**
	 * 
	 * <br>
	 * Created on: May 21, 2012 11:59:42 AM
	 * 
	 * @param intType
	 * @param fileName
	 * @param dtStatTime
	 */
	private void ftpUploadFile(int intType, String fileName, Date dtStatTime) {

		String date = DateTimeUtils.getParseDateToStr(dtStatTime, "yyyyMMdd");

		logger.debug(
				"Ftp upload file param :[type:{} - fileName:{} - time:{}].",
				new Object[]{intType, fileName, date});

		FtpClientOperator ftp = new FtpClientOperator(FTP_SERVER_UDINTERFACE,
				FTP_PORT_UDINTERFACE, FTP_USER_UDINTERFACE,
				FTP_PASSWORD_UDINTERFACE);

		if (!ftp.connectFtpServer()) {
			logger.error("执行FTP上傳文件到 服務器端时,报错:FTP服務器[{}]無法被用戶[{}]連接.",
					new Object[]{Constant.DEFAULT_FTP_SERVER,
							Constant.DEFAULT_FTP_USER});
		}

		ftp.setBinaryTransferType();
		ftp.setTransferCharsetUTF8();

		String remotePath = FTP_TOFILEPATH_UDINTERFACE;
		logger.debug("Remote path :[{}].", remotePath);
		ftp.mkdir(remotePath, date);

		remotePath = FTP_TOFILEPATH_UDINTERFACE + date
				+ Constant.SEPARATOR_CHAR;

		logger.debug("Remote path :[{}].", remotePath);
		ftp.mkdir(remotePath, FTP_TOFILEPATH_UDINTERFACE_BUSS);
		// (1.BB,2.彩信,3.腾讯,4.飞信,5.139邮箱)
		String dirName = "";
		switch (intType) {
			case 1 :
				dirName = "bb";
				break;
			case 2 :
				dirName = "mms";
				break;
			case 3 :
			case 6 :
			case 7 :
			case 8 :
			case 9 :
				dirName = "qq";
				break;
			case 4 :
				dirName = "fetion";
				break;
			case 5 :
				dirName = "139";
				break;
			case 10 :
			case 11 :
			case 12 :
			case 13 :
				dirName = "bz";
				break;
			default :
				break;
		}

		remotePath = FTP_TOFILEPATH_UDINTERFACE + date
				+ Constant.SEPARATOR_CHAR + FTP_TOFILEPATH_UDINTERFACE_BUSS
				+ Constant.SEPARATOR_CHAR;

		logger.debug("Remote path :[{}].", remotePath);
		ftp.mkdir(remotePath, dirName);

		remotePath = FTP_TOFILEPATH_UDINTERFACE + date
				+ Constant.SEPARATOR_CHAR + FTP_TOFILEPATH_UDINTERFACE_BUSS
				+ Constant.SEPARATOR_CHAR + dirName + Constant.SEPARATOR_CHAR;

		logger.debug("Remote path :[{}].", remotePath);
		ftp.mkdir(remotePath,
				DateTimeUtils.getParseDateToStr(dtStatTime, "HHmm"));

		remotePath = FTP_TOFILEPATH_UDINTERFACE + date
				+ Constant.SEPARATOR_CHAR + FTP_TOFILEPATH_UDINTERFACE_BUSS
				+ Constant.SEPARATOR_CHAR + dirName + Constant.SEPARATOR_CHAR
				+ DateTimeUtils.getParseDateToStr(dtStatTime, "HHmm")
				+ Constant.SEPARATOR_CHAR;

		logger.debug("Ftp remote path : [{}].", remotePath);

		if (!ftp.update(Constant.DEFAULT_DATA_TEMP_PATH, remotePath, fileName)) {
			logger.error("执行FTP上傳文件到 服務器端时,报错,文件[{}{}]無法上傳至服務器[{}].",
					new Object[]{Constant.DEFAULT_DATA_TEMP_PATH, fileName,
							remotePath});
		} else {
			logger.info("File [{}{}] FTP to [{}].", new Object[]{
					Constant.DEFAULT_DATA_TEMP_PATH, fileName, remotePath});
		}

		ftp.closeConnect();
	}
	/**
	 * 數據上傳FTP <br>
	 * Created on: May 21, 2012 11:42:54 AM
	 * 
	 * @param manager
	 * @param dtStatTime
	 * @param intType
	 */
	private void getData2Ftp(IUDInterfaceManager manager, Date dtStatTime,
			int intType) throws SQLException {
		logger.debug("getData2Ftp param-->dtStatTime:{},intType:{}.",
				DateTimeUtils.getParseDateToStr(dtStatTime), intType);

		TbUDTimeBean param = new TbUDTimeBean(dtStatTime, intType, 0);

		List<String> list = null;
		if (intType != 3 && intType != 10) {
			list = manager.getFtbOverView(param);
		} else if (intType == 3) {
			list = manager.getFtbOverView2All(param);
		} else if (intType == 10) {
			try {
				list = manager.getFtbOverView2Wireless(param);
			} catch (Exception e) {
				logger.error(
						"執行同一平臺傳送數據getFtbOverView2Wireless任務时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
						new Object[]{e.getMessage(), e.getCause(), e.getClass()});
				manager.update("inittbUDTimeStatus", null);
				logger.warn("Rollback current hour data.");
				manager = null;
				return;
			}
		}
		if (null != list && !list.isEmpty()) {
			// 2.写文件

			logger.debug("1-[getData2Ftp]---->Config file --> [{}].",
					CONFIG_FILE);

			logger.debug("1.1---type-->[{}].", intType);

			String fileName = "fileName" + intType;

			logger.debug("2-[getData2Ftp]---->File name --> [{}].", fileName);

			fileName = PropertiesUtil
					.getConfigProperties(fileName, CONFIG_FILE);

			logger.debug("3-[getData2Ftp]----File name --> [{}].", fileName);

			fileName = (fileName + Constant.FILE_FORMAT_TXT);

			logger.debug("4-[getData2Ftp]----UI Data File :{}{}.",
					Constant.DEFAULT_DATA_TEMP_PATH, fileName);

			if (!FileUtil.createFileByList(list,
					(Constant.DEFAULT_DATA_TEMP_PATH + fileName)))
				logger.warn("Create file from list error.");
			else {
				// 3.通过FTP传输
				ftpUploadFile(intType, fileName, param.getDtStatTime());

				// 4.删除文件
				if (FileUtil.deleteFile(Constant.DEFAULT_DATA_TEMP_PATH
						+ fileName))
					logger.info("Delete ui file [{}{}] complete.",
							Constant.DEFAULT_DATA_TEMP_PATH, fileName);
			}

		}
	}
	/**
	 * 
	 * <br>
	 * Created on: May 21, 2012 11:36:50 AM
	 * 
	 * @param tbUDTime
	 * @param manager
	 * @param typeName
	 * @param status
	 */
	private void writteErrorStatus(TbUDTimeBean tbUDTime,
			IUDInterfaceManager manager, String typeName, int status) {
		try {
			tbUDTime.setIntStatus(status);
			manager.update("udpate_status", tbUDTime);
			manager = null;
		} catch (SQLException e) {
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e2) {
			}

			try {
				tbUDTime.setIntStatus(status);
				manager.update("udpate_status", tbUDTime);
			} catch (SQLException e1) {
				logger.error(
						"{}{}任務时,报错:\n MASSAGE : {} \n CAUSE :{} \n CLASS : {}\n",
						new Object[]{"統一平台", typeName, e.getMessage(),
								e.getCause(), e.getClass()});
			}
		} finally {
			manager = null;
		}
	}
}
