/**
 *  com.etone.mantoeye.analyse.domain.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.domain;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author Wu Zhenzhen
 * @version May 4, 2012 10:25:49 AM
 */
public class AnalyseParam {

	/**
	 * 源數據庫表名(用於select時的表名)
	 */
	private String sourceTableName;

	/**
	 * 目標數據庫表名(用於load時的表名)
	 */
	private String targetTableName;

	/**
	 * 數據加載時間
	 */
	private Date dtLoadedTime;

	/**
	 * 數據Load文件名
	 */
	private String fileName;

	/**
	 * 參數
	 */
	private String option;

	/**
	 * Ibtatis中SQL的ID
	 */
	private String sqlMapId;

	/**
	 * hyjun add 20100906 读分析空间本地IP地址
	 */
	private static String vcLocalIP = null;

	/**
	 * 2012年2月14日15:50:31 添加任務ID
	 */
	private Long taskId;

	/**
	 * 2012年2月15日15:27:03 添加id列名字段
	 */
	private String idColumnName;

	/**
	 * 2012年3月20日15:27:58 添加時間字段
	 */
	private Integer intYear;

	/**
	 * 2012年3月20日15:27:58 添加時間字段
	 */
	private Integer intMonth;

	/**
	 * 2012年3月20日15:27:58 添加時間字段
	 */
	private Integer intDay;

	/**
	 * 2012年3月20日15:27:58 添加時間字段
	 */
	private Integer intHour;

	/**
	 * 2012年3月20日15:27:58 添加時間字段
	 */
	private Integer intWeek;

	static {
		try {
			vcLocalIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			vcLocalIP = "127.0.0.1";
			// vcLocalIP = PropertiesUtil.getConfigProperties("localIP");
		}
	}

	/**
	 * @return the sourceTableName
	 */
	public String getSourceTableName() {
		return sourceTableName;
	}

	/**
	 * @param sourceTableName
	 *            the sourceTableName to set
	 */
	public void setSourceTableName(String sourceTableName) {
		this.sourceTableName = sourceTableName;
	}

	/**
	 * @return the targetTableName
	 */
	public String getTargetTableName() {
		return targetTableName;
	}

	/**
	 * @param targetTableName
	 *            the targetTableName to set
	 */
	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	/**
	 * @return the dtLoadedTime
	 */
	public Date getDtLoadedTime() {
		return dtLoadedTime;
	}

	/**
	 * @param dtLoadedTime
	 *            the dtLoadedTime to set
	 */
	public void setDtLoadedTime(Date dtLoadedTime) {
		this.dtLoadedTime = dtLoadedTime;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the option
	 */
	public String getOption() {
		return option;
	}

	/**
	 * @param option
	 *            the option to set
	 */
	public void setOption(String option) {
		this.option = option;
	}

	/**
	 * @return the sqlMapId
	 */
	public String getSqlMapId() {
		return sqlMapId;
	}

	/**
	 * @param sqlMapId
	 *            the sqlMapId to set
	 */
	public void setSqlMapId(String sqlMapId) {
		this.sqlMapId = sqlMapId;
	}

	/**
	 * @return the taskId
	 */
	public Long getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the idColumnName
	 */
	public String getIdColumnName() {
		return idColumnName;
	}

	/**
	 * @param idColumnName
	 *            the idColumnName to set
	 */
	public void setIdColumnName(String idColumnName) {
		this.idColumnName = idColumnName;
	}

	/**
	 * @return the intYear
	 */
	public Integer getIntYear() {
		return intYear;
	}

	/**
	 * @param intYear
	 *            the intYear to set
	 */
	public void setIntYear(Integer intYear) {
		this.intYear = intYear;
	}

	/**
	 * @return the intMonth
	 */
	public Integer getIntMonth() {
		return intMonth;
	}

	/**
	 * @param intMonth
	 *            the intMonth to set
	 */
	public void setIntMonth(Integer intMonth) {
		this.intMonth = intMonth;
	}

	/**
	 * @return the intDay
	 */
	public Integer getIntDay() {
		return intDay;
	}

	/**
	 * @param intDay
	 *            the intDay to set
	 */
	public void setIntDay(Integer intDay) {
		this.intDay = intDay;
	}

	/**
	 * @return the intHour
	 */
	public Integer getIntHour() {
		return intHour;
	}

	/**
	 * @param intHour
	 *            the intHour to set
	 */
	public void setIntHour(Integer intHour) {
		this.intHour = intHour;
	}

	/**
	 * @return the intWeek
	 */
	public Integer getIntWeek() {
		return intWeek;
	}

	/**
	 * @param intWeek
	 *            the intWeek to set
	 */
	public void setIntWeek(Integer intWeek) {
		this.intWeek = intWeek;
	}

	/**
	 * @return the vcLocalIP
	 */
	public static String getVcLocalIP() {
		return vcLocalIP;
	}

	/**
	 * @param sourceTableName
	 * @param targetTableName
	 * @param dtLoadedTime
	 * @param fileName
	 * @param option
	 * @param sqlMapId
	 * @param taskId
	 * @param idColumnName
	 * @param intYear
	 * @param intMonth
	 * @param intDay
	 * @param intHour
	 * @param intWeek
	 */
	public AnalyseParam(String sourceTableName, String targetTableName,
			Date dtLoadedTime, String fileName, String option, String sqlMapId,
			Long taskId, String idColumnName, Integer intYear,
			Integer intMonth, Integer intDay, Integer intHour, Integer intWeek) {
		this.sourceTableName = sourceTableName;
		this.targetTableName = targetTableName;
		this.dtLoadedTime = dtLoadedTime;
		this.fileName = fileName;
		this.option = option;
		this.sqlMapId = sqlMapId;
		this.taskId = taskId;
		this.idColumnName = idColumnName;
		this.intYear = intYear;
		this.intMonth = intMonth;
		this.intDay = intDay;
		this.intHour = intHour;
		this.intWeek = intWeek;
	}

	/**
	 * 
	 */
	public AnalyseParam() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AnalyseParam [sourceTableName=" + sourceTableName
				+ ", targetTableName=" + targetTableName + ", dtLoadedTime="
				+ dtLoadedTime + ", fileName=" + fileName + ", option="
				+ option + ", sqlMapId=" + sqlMapId + ", taskId=" + taskId
				+ ", idColumnName=" + idColumnName + ", intYear=" + intYear
				+ ", intMonth=" + intMonth + ", intDay=" + intDay
				+ ", intHour=" + intHour + ", intWeek=" + intWeek + "]";
	}

}
