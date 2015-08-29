/**
 *  com.etone.mantoeye.analyse.domain.common.MantoEye_SZ_Task_Common 
 */
package com.etone.mantoeye.analyse.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 當前任務對象
 * 
 * @author Wu Zhenzhen
 * @version Apr 27, 2012 4:16:17 PM
 */
public class CurrentTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5592240865999092468L;

	/**
	 * 
	 */
	public CurrentTask() {
	}

	/**
	 * 当前任务表序号
	 */
	private Long intTaskId;

	/**
	 * 任务序号
	 */
	private Long intTaskInfoId;

	/**
	 * 任务名称
	 */
	private String vcTaskName;

	/**
	 * 统计时间 小时:YYYY-MM-DD HH:00:00 天:YYYY-MM-DD 00:00:00
	 */
	private Date dtStatTime;

	/**
	 * 粒度 0.小时 1.天 2.周 3.月
	 */
	private String chGranularity;

	/**
	 * 任务优先级 0級最高,優先執行,1,2,3...級別依次提升
	 */
	private int intTaskPriority;

	/**
	 * 当前状态 0.初始狀態1.等待 2.执行 3.完成(实现中考虑???)
	 */
	private int intTaskState;

	/**
	 * 完成时间
	 */
	private Date dtCompleteTime;

	/**
	 * 事实表名
	 */
	private String vcFactTableName;

	/**
	 * 事实表操作日志表的ID
	 */
	private Long intFtbOid;

	/**
	 * sql流程
	 */
	private String vcSqlId;

	/**
	 * 查询语句ID
	 */
	private String vcSelectId;

	/**
	 * LOADDATA的ID
	 */
	private String vcLoadId;

	/**
	 * @return the intTaskId
	 */
	public Long getIntTaskId() {
		return intTaskId;
	}

	/**
	 * @param intTaskId
	 *            the intTaskId to set
	 */
	public void setIntTaskId(Long intTaskId) {
		this.intTaskId = intTaskId;
	}

	/**
	 * @return the intTaskInfoId
	 */
	public Long getIntTaskInfoId() {
		return intTaskInfoId;
	}

	/**
	 * @param intTaskInfoId
	 *            the intTaskInfoId to set
	 */
	public void setIntTaskInfoId(Long intTaskInfoId) {
		this.intTaskInfoId = intTaskInfoId;
	}

	/**
	 * @return the vcTaskName
	 */
	public String getVcTaskName() {
		return vcTaskName;
	}

	/**
	 * @param vcTaskName
	 *            the vcTaskName to set
	 */
	public void setVcTaskName(String vcTaskName) {
		this.vcTaskName = vcTaskName;
	}

	/**
	 * @return the dtStatTime
	 */
	public Date getDtStatTime() {
		return dtStatTime;
	}

	/**
	 * @param dtStatTime
	 *            the dtStatTime to set
	 */
	public void setDtStatTime(Date dtStatTime) {
		this.dtStatTime = dtStatTime;
	}

	/**
	 * @return the chGranularity
	 */
	public String getChGranularity() {
		return chGranularity;
	}

	/**
	 * @param chGranularity
	 *            the chGranularity to set
	 */
	public void setChGranularity(String chGranularity) {
		this.chGranularity = chGranularity;
	}

	/**
	 * @return the intTaskPriority
	 */
	public int getIntTaskPriority() {
		return intTaskPriority;
	}

	/**
	 * @param intTaskPriority
	 *            the intTaskPriority to set
	 */
	public void setIntTaskPriority(int intTaskPriority) {
		this.intTaskPriority = intTaskPriority;
	}

	/**
	 * @return the intTaskState
	 */
	public int getIntTaskState() {
		return intTaskState;
	}

	/**
	 * @param intTaskState
	 *            the intTaskState to set
	 */
	public void setIntTaskState(int intTaskState) {
		this.intTaskState = intTaskState;
	}

	/**
	 * @return the dtCompleteTime
	 */
	public Date getDtCompleteTime() {
		return dtCompleteTime;
	}

	/**
	 * @param dtCompleteTime
	 *            the dtCompleteTime to set
	 */
	public void setDtCompleteTime(Date dtCompleteTime) {
		this.dtCompleteTime = dtCompleteTime;
	}

	/**
	 * @return the vcFactTableName
	 */
	public String getVcFactTableName() {
		return vcFactTableName;
	}

	/**
	 * @param vcFactTableName
	 *            the vcFactTableName to set
	 */
	public void setVcFactTableName(String vcFactTableName) {
		this.vcFactTableName = vcFactTableName;
	}

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
	 * @return the vcSqlId
	 */
	public String getVcSqlId() {
		return vcSqlId;
	}

	/**
	 * @param vcSqlId
	 *            the vcSqlId to set
	 */
	public void setVcSqlId(String vcSqlId) {
		this.vcSqlId = vcSqlId;
	}

	/**
	 * @return the vcSelectId
	 */
	public String getVcSelectId() {
		return vcSelectId;
	}

	/**
	 * @param vcSelectId
	 *            the vcSelectId to set
	 */
	public void setVcSelectId(String vcSelectId) {
		this.vcSelectId = vcSelectId;
	}

	/**
	 * @return the vcLoadId
	 */
	public String getVcLoadId() {
		return vcLoadId;
	}

	/**
	 * @param vcLoadId
	 *            the vcLoadId to set
	 */
	public void setVcLoadId(String vcLoadId) {
		this.vcLoadId = vcLoadId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CurrentTask [intTaskId=" + intTaskId + ", intTaskInfoId="
				+ intTaskInfoId + ", vcTaskName=" + vcTaskName
				+ ", dtStatTime=" + dtStatTime + ", chGranularity="
				+ chGranularity + ", intTaskPriority=" + intTaskPriority
				+ ", intTaskState=" + intTaskState + ", dtCompleteTime="
				+ dtCompleteTime + ", vcFactTableName=" + vcFactTableName
				+ ", intFtbOid=" + intFtbOid + ", vcSqlId=" + vcSqlId
				+ ", vcSelectId=" + vcSelectId + ", vcLoadId=" + vcLoadId + "]";
	}

}
