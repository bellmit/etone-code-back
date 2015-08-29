/**
 * com.etone.mantoeye.analyse.user.impl.GaoJiaoHuiData.java
 */
package com.etone.mantoeye.analyse.user.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.myhkzhen.util.date.DateTimeUtils;
import org.myhkzhen.util.file.FileUtil;
import org.myhkzhen.util.ftp.FtpClientOperator;
import org.myhkzhen.util.properties.PropertiesUtil;

import com.etone.mantoeye.analyse.domain.AnalyseParam;
import com.etone.mantoeye.analyse.service.common.IHiTechFairElexconDataMananger;
import com.etone.mantoeye.analyse.service.impl.common.HiTechFairElexconDataManangerImpl;
import com.etone.mantoeye.analyse.user.IUserDefineAccess;
import com.etone.mantoeye.analyse.util.constant.Constant;

/**
 * @author Wu Zhenzhen
 * @version Nov 15, 2012 10:08:55 AM
 */

public class HiTechFairElexconData implements IUserDefineAccess {

	private static final String HiTechFairElexcon = "HiTechFairElexcon";

	private static final String[] cgiArr = new String[]{"460-00-10129-3591",
			"460-00-10129-3641", "460-00-10129-3642", "460-00-10129-3643",
			"460-00-10129-3781", "460-00-10129-3791", "460-00-10129-3913",
			"460-00-10131-3711", "460-00-10131-3721", "460-00-10131-3741",
			"460-00-10131-3742", "460-00-10131-3743", "460-00-10131-3761",
			"460-00-10131-3762", "460-00-10131-3763", "460-00-10131-3791",
			"460-00-10131-3792", "460-00-10131-3801", "460-00-10131-3802",
			"460-00-10131-3822", "460-00-10131-3823", "460-00-10131-3931",
			"460-00-10131-3932", "460-00-10131-3972", "460-00-10131-3973",
			"460-00-10131-4031", "460-00-10131-4032", "460-00-10131-4033",
			"460-00-10131-4051", "460-00-10131-4052", "460-00-10131-4053",
			"460-00-10131-4081", "460-00-10131-4083", "460-00-10131-4101",
			"460-00-10131-4172", "460-00-10131-4201", "460-00-10131-4271",
			"460-00-10131-4272", "460-00-10131-4273", "460-00-10131-4391",
			"460-00-10131-4392", "460-00-10131-4393", "460-00-10133-4083",
			"460-00-10165-4890", "460-00-10166-4231", "460-00-10166-4243",
			"460-00-10166-4772", "460-00-9330-3881", "460-00-9330-3900",
			"460-00-9330-3991", "460-00-9330-3993", "460-00-9330-4000",
			"460-00-9330-4031", "460-00-9330-4032", "460-00-9330-4041",
			"460-00-9330-4042", "460-00-9330-4081", "460-00-9330-4082",
			"460-00-9330-4083", "460-00-9330-4091", "460-00-9330-4092",
			"460-00-9330-4093", "460-00-9330-4101", "460-00-9330-4102",
			"460-00-9330-4103", "460-00-9330-4232", "460-00-9330-4262",
			"460-00-9330-4401", "460-00-9330-4402", "460-00-9330-4403",
			"460-00-9330-4450", "460-00-9330-4460", "460-00-9330-4470",
			"460-00-9330-4481", "460-00-9330-4482", "460-00-9330-4491",
			"460-00-9330-4492", "460-00-9330-4501", "460-00-9330-4502",
			"460-00-9330-4503", "460-00-9330-4511", "460-00-9330-4512",
			"460-00-9330-4513", "460-00-9330-4521", "460-00-9330-4522",
			"460-00-9330-4523", "460-00-9330-4531", "460-00-9330-4532",
			"460-00-9330-4533", "460-00-9330-4540", "460-00-9330-4550",
			"460-00-9330-4561", "460-00-9330-4562", "460-00-9330-4563",
			"460-00-9330-4570", "460-00-9330-4580", "460-00-9330-4590",
			"460-00-9330-4600", "460-00-9330-4610", "460-00-9330-4621",
			"460-00-9330-4622", "460-00-9330-4631", "460-00-9330-4632",
			"460-00-9330-4640", "460-00-9330-4651", "460-00-9330-4752",
			"460-00-9330-4761", "460-00-9330-4772", "460-00-9330-4801",
			"460-00-9330-4803", "460-00-9330-4831", "460-00-9330-4832",
			"460-00-9330-4833", "460-00-9330-4850", "460-00-9330-4883",
			"460-00-9330-4920", "460-00-9330-5081", "460-00-42322-21161",
			"460-00-42322-21162", "460-00-42322-21164", "460-00-42322-21165",
			"460-00-42322-21181", "460-00-42322-21182", "460-00-42322-21184",
			"460-00-42322-21185", "460-00-42322-21201", "460-00-42322-21203",
			"460-00-42322-22661", "460-00-42322-22662", "460-00-42322-28001",
			"460-00-42322-28002", "460-00-42322-28003", "460-00-42322-28011",
			"460-00-42322-28012", "460-00-42322-28013", "460-00-42322-28021",
			"460-00-42322-28022", "460-00-42322-28023", "460-00-42322-28031",
			"460-00-42322-28033", "460-00-42322-28041", "460-00-42322-28043",
			"460-00-42322-28051", "460-00-42322-28052", "460-00-42322-28061",
			"460-00-42322-28062", "460-00-42322-28081", "460-00-42322-28082",
			"460-00-42322-28093", "460-00-42322-28101", "460-00-42322-28102"};

	private static final String CONFIG_FILE = Constant.APP_HOME
			+ Constant.SEPARATOR_CHAR + Constant.CONFIG_PATH
			+ "uidatatrans.properties";
	/**
	 * FTP REMOTEDIR
	 */
	private static final String FTP_TOFILEPATH_UDINTERFACE = "/pm/";
	private static final String FTP_TOFILEPATH_UDINTERFACE_BUSS = "busi_sys";
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
	 * @see
	 * com.etone.mantoeye.analyse.user.IUserDefineAccess#execute(com.etone.mantoeye
	 * .analyse.domain.AnalyseParam)
	 */
	public void execute(AnalyseParam analyseParam) throws SQLException {

		logger.info("Start exec china hi-tech fair elexcon data ...");

		long startTime = System.currentTimeMillis();
		long useTime = startTime;

		if (null == analyseParam) {
			logger.warn("AnalyseParam is null, return.");
			throw new SQLException("AnalyseParam is null, return");
		}

		// export data
		String fileName = HiTechFairElexcon
				+ "_"
				+ DateTimeUtils.getParseDateToStr(
						analyseParam.getDtLoadedTime(), "yyyyMMddHH");

		logger.debug("china hi-tech fair elexcon data file name : [{}].",
				fileName);

		analyseParam.setFileName(fileName);
		// exportDataToFile(analyseParam);

		for (String cgi : cgiArr) {

			try {
				long lstartTime = System.currentTimeMillis();
				analyseParam.setIdColumnName(cgi);

				logger.debug("AnalyseParam info :[{}].",
						analyseParam.toString());

				List<String> list = exportDataToFile(analyseParam);

				if (null != list && !list.isEmpty()) {
					logger.info(
							"Source table :[{}], cgi :[{}], list size :[{}].",
							new Object[]{analyseParam.getSourceTableName(),
									cgi, list.size()});
					FileUtils.writeLines((new File(
							Constant.DEFAULT_DATA_TEMP_PATH + fileName)), list,
							true);
					list.clear();
					list = null;
				}

				useTime = System.currentTimeMillis() - lstartTime;
				logger.info(
						"Export cgi [{}] china hi-tech fair elexcon data to file [{}{}] complate, use time :[{}]s.",
						new Object[]{cgi, Constant.DEFAULT_DATA_TEMP_PATH,
								fileName, (useTime * 1.0 / 1000)});

			} catch (IOException e) {
				logger.warn("Query cgi [{}] error, continue.", cgi);
				continue;
			} catch (RuntimeException e) {
				logger.warn("Query cgi [{}] error, continue.", cgi);
				continue;
			}
		}

		try {
			Thread.sleep(1000 * 2);
		} catch (InterruptedException e) {
		}

		useTime = System.currentTimeMillis() - startTime;
		logger.info(
				"Export china hi-tech fair elexcon data to file complate, use time :[{}]s.",
				(useTime * 1.0 / 1000));

		// rename file
		File file = new File(Constant.DEFAULT_DATA_TEMP_PATH + fileName);
		if (null == file || !file.exists()) {
			logger.warn(
					"Export china hi-tech fair elexcon data file [{}{}] is null, return.",
					Constant.DEFAULT_DATA_TEMP_PATH, fileName);
			throw new SQLException(
					"Export china hi-tech fair elexcon data file is null, return.");
		}

		String newFileName = fileName + Constant.FILE_FORMAT_TXT;
		if (!FileUtil.rename(Constant.DEFAULT_DATA_TEMP_PATH + fileName,
				Constant.DEFAULT_DATA_TEMP_PATH + newFileName)) {
			logger.warn("File [{}{}] rename to [{}{}] error.", new Object[]{
					Constant.DEFAULT_DATA_TEMP_PATH, fileName,
					Constant.DEFAULT_DATA_TEMP_PATH, newFileName});
			newFileName = fileName;
		}

		analyseParam.setFileName(newFileName);

		// FTP
		ftpDataFileToServer(analyseParam);

		// delete file
		if (FileUtil
				.deleteFile((Constant.DEFAULT_DATA_TEMP_PATH + newFileName)))
			logger.info(
					"Delete china hi-tech fair elexcon data file [{}{}] complete.",
					Constant.DEFAULT_DATA_TEMP_PATH, newFileName);

		useTime = System.currentTimeMillis() - startTime;
		logger.info(
				"Exec china hi-tech fair elexcon data complate, use time :[{}]s.",
				(useTime * 1.0 / 1000));
	}

	/**
	 * <br>
	 * Created on: Nov 15, 2012 10:47:36 AM
	 * 
	 * Created by: Wu Zhenzhen
	 * 
	 * @param analyseParam
	 */
	private void ftpDataFileToServer(AnalyseParam param) {

		String date = DateTimeUtils.getParseDateToStr(param.getDtLoadedTime(),
				"yyyyMMdd");

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

		/*
		 * /pm/20121115
		 */
		String remotePath = FTP_TOFILEPATH_UDINTERFACE;
		ftp.mkdir(remotePath, date);

		/*
		 * /pm/20121115/busi_sys
		 */
		remotePath = remotePath + date + Constant.SEPARATOR_CHAR;
		ftp.mkdir(remotePath, FTP_TOFILEPATH_UDINTERFACE_BUSS);

		/*
		 * /pm/20121115/busi_sys/bz
		 */
		remotePath = remotePath + FTP_TOFILEPATH_UDINTERFACE_BUSS
				+ Constant.SEPARATOR_CHAR;
		ftp.mkdir(remotePath, "bz");

		/*
		 * /pm/20121115/busi_sys/bz/0000
		 */
		remotePath = remotePath + "bz" + Constant.SEPARATOR_CHAR;
		ftp.mkdir(remotePath, DateTimeUtils.getParseDateToStr(
				param.getDtLoadedTime(), "HHmm"));

		// finally remote path : /pm/20121115/busi_sys/bz/0000/
		remotePath = remotePath
				+ DateTimeUtils.getParseDateToStr(param.getDtLoadedTime(),
						"HHmm") + Constant.SEPARATOR_CHAR;

		logger.info("china hi-tech fair elexcon data remote : [{}].",
				remotePath);

		if (!ftp.update(Constant.DEFAULT_DATA_TEMP_PATH, remotePath,
				param.getFileName())) {
			logger.error(
					"执行FTP上傳文件到 服務器端时,报错,文件[{}{}]無法上傳至服務器[{}].",
					new Object[]{Constant.DEFAULT_DATA_TEMP_PATH,
							param.getFileName(), remotePath});
		} else {
			logger.info("File [{}{}] FTP to [{}].", new Object[]{
					Constant.DEFAULT_DATA_TEMP_PATH, param.getFileName(),
					remotePath});
		}

		ftp.closeConnect();

	}

	/**
	 * <br>
	 * Created on: Nov 15, 2012 10:23:59 AM
	 * 
	 * Created by: Wu Zhenzhen
	 * 
	 * @param analyseParam
	 */
	private List<String> exportDataToFile(AnalyseParam param) {

		IHiTechFairElexconDataMananger hiTechFairElexconDataMananger = null;
		List<String> list = null;
		try {

			hiTechFairElexconDataMananger = new HiTechFairElexconDataManangerImpl();
			list = hiTechFairElexconDataMananger
					.getHiTechFairElexconDataList(param);
			hiTechFairElexconDataMananger = null;

			return list;
		} catch (SQLException e) {

			logger.error(
					"执行[{}.{}]操作从数据库中生成文件时,报错\n MASSAGE : {} \n CAUSE : {} \n CLASS : {}\n",
					new Object[]{param.getSourceTableName(),
							param.getSqlMapId(), e.getMessage(), e.getCause(),
							e.getClass()});
			hiTechFairElexconDataMananger = null;
			list = null;
		} finally {
			hiTechFairElexconDataMananger = null;
		}
		return list;
	}
}
