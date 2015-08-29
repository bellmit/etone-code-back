/**
 *  com.etone.mantoeye.analyse.util.constant.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.util.constant;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.myhkzhen.util.properties.PropertiesUtil;

/**
 * 系統中應用到的常量
 * 
 * @author Wu Zhenzhen
 * @version Apr 28, 2012 2:21:07 PM
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
	public static final String CONFIG_PATH = "etc" + SEPARATOR_CHAR;

	/**
	 * 程序正確執行結果碼
	 */
	public static final String CORRECT_CODE = "CORRECT";

	/**
	 * 程序正確執行結果碼
	 */
	public static final String ERROR_CODE = "ERROR";

	/**
	 * 連接符號 : &
	 */
	public static final String CONNECT_SYMBOL = "&";

	/**
	 * SOCKET執行端口
	 */
	public static final String SOCKET_PORT = PropertiesUtil
			.getConfigProperties("logSocketPort");

	/**
	 * 默認導出數據文件保存臨時位置
	 */
	public static final String DEFAULT_DATA_TEMP_PATH = PropertiesUtil
			.getConfigProperties("defaultDataTempPath");
	/**
	 * 文件可以執行導入等操作的默認標示
	 */
	public static final String OK = CONNECT_SYMBOL + "OK";

	/**
	 * 文件傳輸類型:<1>FTP.<2>NFS
	 */
	public static final String TRANS_DATATYPE = PropertiesUtil
			.getConfigProperties("transDataType");

	/**
	 * 文件傳輸類型
	 */
	public static final String NFS = "NFS";

	/**
	 * 默認FTP服務器地址
	 */
	public static final String DEFAULT_FTP_SERVER = PropertiesUtil
			.getConfigProperties("defaultFtpServer");

	/**
	 * 默認FTP PORT
	 */
	public static final String DEFAULT_FTP_PORT_STRING = PropertiesUtil
			.getConfigProperties("defaultFtpPort");

	/**
	 * 默認FTP PORT
	 */
	public static final int DEFAULT_FTP_PORT = NumberUtils.toInt(
			DEFAULT_FTP_PORT_STRING, 21);
	/**
	 * 默認FTP USER
	 */
	public static final String DEFAULT_FTP_USER = PropertiesUtil
			.getConfigProperties("defaultFtpUser");
	/**
	 * 默認FTP PASSWORD
	 */
	public static final String DEFAULT_FTP_PASSWORD = PropertiesUtil
			.getConfigProperties("defaultFtpPassword");
	/**
	 * 文件類型 .txt
	 */
	public static final String FILE_FORMAT_TXT = ".txt";

	/**
	 * 已經進入隊列中的文件包含標誌
	 */
	public static final String EXEC_FILE_ING = ".ing";
}
