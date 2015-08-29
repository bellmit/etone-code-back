/**
 *  com.etone.mantoeye.analyse.domain.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 事實表對象
 * 
 * @author Wu Zhenzhen
 * @version Apr 27, 2012 4:30:31 PM
 */
public class FactTableOperation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8993471419366908009L;

	/**
	 * 事实有操作日志序
	 */
	private Long intFtbOid;

	/**
	 * 事实表名
	 */
	private String vcTableName;

	/**
	 * 已完成时间
	 */
	private Date dtLoadedTime;

	/**
	 * 开始加载时间
	 */
	private Date dtBLoadTime;

	/**
	 * 完成加载时间
	 */
	private Date dtELoadTime;

	/**
	 * 开始建立索引时间
	 */
	private Date dtBIndexTime;

	/**
	 * 完成建立索引时间
	 */
	private Date dtEIndexTime;

	/**
	 * 索引是否完成 0表未完成1表已完成
	 */
	private String chIndexComplete;

	/**
	 * 任务是否生成
	 */
	private String chTaskCreated;

	/**
	 * 任务数量
	 */
	private Long intTaskNum;

	/**
	 * 任务生成时间
	 */
	private Date dtTaskCreateTime;

	/**
	 * 任务是否完成
	 */
	private String chTaskComplete;

	/**
	 * 任务完成数量
	 */
	private Long intTaskCompleteNum;

	/**
	 * 任务完成时间
	 */
	private Date dtTaskCompleteTime;

	/**
	 * @return the intFtbOid
	 */
	public Long getIntFtbOid() {
		return intFtbOid;
	}

	/**
	 * @param intFtbOid
	 *            the intFtbOid to set
	 */
	public void setIntFtbOid(Long intFtbOid) {
		this.intFtbOid = intFtbOid;
	}

	/**
	 * @return the vcTableName
	 */
	public String getVcTableName() {
		return vcTableName;
	}

	/**
	 * @param vcTableName
	 *            the vcTableName to set
	 */
	public void setVcTableName(String vcTableName) {
		this.vcTableName = vcTableName;
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
	 * @return the dtBLoadTime
	 */
	public Date getDtBLoadTime() {
		return dtBLoadTime;
	}

	/**
	 * @param dtBLoadTime
	 *            the dtBLoadTime to set
	 */
	public void setDtBLoadTime(Date dtBLoadTime) {
		this.dtBLoadTime = dtBLoadTime;
	}

	/**
	 * @return the dtELoadTime
	 */
	public Date getDtELoadTime() {
		return dtELoadTime;
	}

	/**
	 * @param dtELoadTime
	 *            the dtELoadTime to set
	 */
	public void setDtELoadTime(Date dtELoadTime) {
		this.dtELoadTime = dtELoadTime;
	}

	/**
	 * @return the dtBIndexTime
	 */
	public Date getDtBIndexTime() {
		return dtBIndexTime;
	}

	/**
	 * @param dtBIndexTime
	 *            the dtBIndexTime to set
	 */
	public void setDtBIndexTime(Date dtBIndexTime) {
		this.dtBIndexTime = dtBIndexTime;
	}

	/**
	 * @return the dtEIndexTime
	 */
	public Date getDtEIndexTime() {
		return dtEIndexTime;
	}

	/**
	 * @param dtEIndexTime
	 *            the dtEIndexTime to set
	 */
	public void setDtEIndexTime(Date dtEIndexTime) {
		this.dtEIndexTime = dtEIndexTime;
	}

	/**
	 * @return the chIndexComplete
	 */
	public String getChIndexComplete() {
		return chIndexComplete;
	}

	/**
	 * @param chIndexComplete
	 *            the chIndexComplete to set
	 */
	public void setChIndexComplete(String chIndexComplete) {
		this.chIndexComplete = chIndexComplete;
	}

	/**
	 * @return the chTaskCreated
	 */
	public String getChTaskCreated() {
		return chTaskCreated;
	}

	/**
	 * @param chTaskCreated
	 *            the chTaskCreated to set
	 */
	public void setChTaskCreated(String chTaskCreated) {
		this.chTaskCreated = chTaskCreated;
	}

	/**
	 * @return the intTaskNum
	 */
	public Long getIntTaskNum() {
		return intTaskNum;
	}

	/**
	 * @param intTaskNum
	 *            the intTaskNum to set
	 */
	public void setIntTaskNum(Long intTaskNum) {
		this.intTaskNum = intTaskNum;
	}

	/**
	 * @return the dtTaskCreateTime
	 */
	public Date getDtTaskCreateTime() {
		return dtTaskCreateTime;
	}

	/**
	 * @param dtTaskCreateTime
	 *            the dtTaskCreateTime to set
	 */
	public void setDtTaskCreateTime(Date dtTaskCreateTime) {
		this.dtTaskCreateTime = dtTaskCreateTime;
	}

	/**
	 * @return the chTaskComplete
	 */
	public String getChTaskComplete() {
		return chTaskComplete;
	}

	/**
	 * @param chTaskComplete
	 *            the chTaskComplete to set
	 */
	public void setChTaskComplete(String chTaskComplete) {
		this.chTaskComplete = chTaskComplete;
	}

	/**
	 * @return the intTaskCompleteNum
	 */
	public Long getIntTaskCompleteNum() {
		return intTaskCompleteNum;
	}

	/**
	 * @param intTaskCompleteNum
	 *            the intTaskCompleteNum to set
	 */
	public void setIntTaskCompleteNum(Long intTaskCompleteNum) {
		this.intTaskCompleteNum = intTaskCompleteNum;
	}

	/**
	 * @return the dtTaskCompleteTime
	 */
	public Date getDtTaskCompleteTime() {
		return dtTaskCompleteTime;
	}

	/**
	 * @param dtTaskCompleteTime
	 *            the dtTaskCompleteTime to set
	 */
	public void setDtTaskCompleteTime(Date dtTaskCompleteTime) {
		this.dtTaskCompleteTime = dtTaskCompleteTime;
	}

	/**
	 * 
	 */
	public FactTableOperation() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FactTableOperation [intFtbOid=" + intFtbOid + ", vcTableName="
				+ vcTableName + ", dtLoadedTime=" + dtLoadedTime
				+ ", dtBLoadTime=" + dtBLoadTime + ", dtELoadTime="
				+ dtELoadTime + ", dtBIndexTime=" + dtBIndexTime
				+ ", dtEIndexTime=" + dtEIndexTime + ", chIndexComplete="
				+ chIndexComplete + ", chTaskCreated=" + chTaskCreated
				+ ", intTaskNum=" + intTaskNum + ", dtTaskCreateTime="
				+ dtTaskCreateTime + ", chTaskComplete=" + chTaskComplete
				+ ", intTaskCompleteNum=" + intTaskCompleteNum
				+ ", dtTaskCompleteTime=" + dtTaskCompleteTime + "]";
	}
}
