/**
 *  com.etone.mantoeye.analyse.util.constant.MantoEye_SZ_Task_Specific 
 */
package com.etone.mantoeye.analyse.util.constant;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.myhkzhen.util.properties.PropertiesUtil;

/**
 * @author Wu Zhenzhen
 * @version May 15, 2012 4:53:01 PM
 */
public class Constant {
	/**
	 * 項目主目錄,通過java -Dapp.home=/home/iq/MantoEye_SZ_Task獲取環境變量
	 */
	public static final String APP_HOME = StringUtils.trimToEmpty(System
			.getProperty("app.home"));

	/**
	 * 分隔符
	 */
	public static final String SEPARATOR_CHAR = String
			.valueOf(File.separatorChar);

	/**
	 * 配置文件默認文件夾etc
	 */
	public static final String CONFIG_PATH = StringUtils.join("etc",
			SEPARATOR_CHAR);
	/**
	 * 程序正確執行結果碼
	 */
	public static final String CORRECT_CODE = "CORRECT";

	/**
	 * 程序正確執行結果碼
	 */
	public static final String ERROR_CODE = "ERROR";

	/**
	 * 默認導出數據文件保存臨時位置
	 */
	public static final String DEFAULT_DATA_TEMP_PATH = PropertiesUtil
			.getConfigProperties("defaultDataTempPath");
	/**
	 * 文件類型 .dat
	 */
	public static final String FILE_FORMAT_DAT = ".dat";

	/**
	 * 默認FTP服務器地址
	 */
	public static final String DEFAULT_FTP_SERVER = PropertiesUtil
			.getConfigProperties("defaultFtpServer");

}
