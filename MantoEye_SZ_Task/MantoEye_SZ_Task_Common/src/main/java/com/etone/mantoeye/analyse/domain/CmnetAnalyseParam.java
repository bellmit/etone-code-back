/**
 *  com.etone.mantoeye.analyse.domain.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.domain;

import java.util.Date;

/**
 * cmnet 屬性
 * 
 * @author Wu Zhenzhen
 * @version May 7, 2012 10:51:10 AM
 */
public class CmnetAnalyseParam extends AnalyseParam {
	/**
	 * 2011年9月19日14:29:32 添加CMNET數據導出功能所需開始時間
	 */
	private Date startTime;

	/**
	 * 2011年9月19日14:29:32 添加CMNET數據導出功能所需結束時間
	 */
	private Date endTime;

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 
	 */
	public CmnetAnalyseParam() {
		super();
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
	public CmnetAnalyseParam(String sourceTableName, String targetTableName,
			Date dtLoadedTime, String fileName, String option, String sqlMapId,
			Long taskId, String idColumnName, Integer intYear,
			Integer intMonth, Integer intDay, Integer intHour, Integer intWeek) {
		super(sourceTableName, targetTableName, dtLoadedTime, fileName, option,
				sqlMapId, taskId, idColumnName, intYear, intMonth, intDay,
				intHour, intWeek);
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
	 * @param startTime
	 * @param endTime
	 */
	public CmnetAnalyseParam(String sourceTableName, String targetTableName,
			Date dtLoadedTime, String fileName, String option, String sqlMapId,
			Long taskId, String idColumnName, Integer intYear,
			Integer intMonth, Integer intDay, Integer intHour, Integer intWeek,
			Date startTime, Date endTime) {
		super(sourceTableName, targetTableName, dtLoadedTime, fileName, option,
				sqlMapId, taskId, idColumnName, intYear, intMonth, intDay,
				intHour, intWeek);
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CmnetAnalyseParam [startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}
}
