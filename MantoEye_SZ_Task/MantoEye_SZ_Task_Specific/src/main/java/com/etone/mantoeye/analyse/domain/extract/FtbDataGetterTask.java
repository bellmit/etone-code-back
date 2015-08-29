package com.etone.mantoeye.analyse.domain.extract;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据提取任务表DOMAIN
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月24日21:46:00
 */
public class FtbDataGetterTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6996495250025096081L;

	/**
	 * 任務ID
	 */
	private Long nmDataGetterTaskId;

	/**
	 * 任務開始時間
	 */
	private Date dtStartTime;

	/**
	 * 任務結束時間
	 */
	private Date dtEndTime;

	/**
	 * 任務狀態<br />
	 * 0.未开始<br />
	 * 1.进行中<br />
	 * 2.任务自然结束<br />
	 * 3.任务手工结束<br />
	 * 4.停止中<br />
	 * 5.任务异常停止<br />
	 */
	private int intTaskStatus;

	/**
	 * 任務類型:<br />
	 * 1.根据号码提取历史原始信令<br />
	 * 2.根据号码提取实时原始信令<br />
	 * 3.根据号码提取历史解析数据<br />
	 * 4.根据区域及业务提取用户号码<br />
	 * 5.已有业务规则拨测原始信令<br />
	 * 6.新增业务规则拨测原始信令<br />
	 * 7.已有业务规则拨测解码数据<br />
	 * 8.新增业务规则拨测解码数据<br />
	 */
	private int intTaskType;

	/**
	 * 任務名稱
	 */
	private String vcTaskName;

	/**
	 * @return the nmDataGetterTaskId
	 */
	public Long getNmDataGetterTaskId() {
		return nmDataGetterTaskId;
	}

	/**
	 * @param nmDataGetterTaskId
	 *            the nmDataGetterTaskId to set
	 */
	public void setNmDataGetterTaskId(Long nmDataGetterTaskId) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
	}

	/**
	 * @return the dtStartTime
	 */
	public Date getDtStartTime() {
		return dtStartTime;
	}

	/**
	 * @param dtStartTime
	 *            the dtStartTime to set
	 */
	public void setDtStartTime(Date dtStartTime) {
		this.dtStartTime = dtStartTime;
	}

	/**
	 * @return the dtEndTime
	 */
	public Date getDtEndTime() {
		return dtEndTime;
	}

	/**
	 * @param dtEndTime
	 *            the dtEndTime to set
	 */
	public void setDtEndTime(Date dtEndTime) {
		this.dtEndTime = dtEndTime;
	}

	/**
	 * @return the intTaskStatus
	 */
	public int getIntTaskStatus() {
		return intTaskStatus;
	}

	/**
	 * @param intTaskStatus
	 *            the intTaskStatus to set
	 */
	public void setIntTaskStatus(int intTaskStatus) {
		this.intTaskStatus = intTaskStatus;
	}

	/**
	 * @return the intTaskType
	 */
	public int getIntTaskType() {
		return intTaskType;
	}

	/**
	 * @param intTaskType
	 *            the intTaskType to set
	 */
	public void setIntTaskType(int intTaskType) {
		this.intTaskType = intTaskType;
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
	 * @param nmDataGetterTaskId
	 * @param dtStartTime
	 * @param dtEndTime
	 * @param intTaskStatus
	 * @param intTaskType
	 * @param vcTaskName
	 */
	public FtbDataGetterTask(Long nmDataGetterTaskId, Date dtStartTime,
			Date dtEndTime, int intTaskStatus, int intTaskType,
			String vcTaskName) {
		super();
		this.nmDataGetterTaskId = nmDataGetterTaskId;
		this.dtStartTime = dtStartTime;
		this.dtEndTime = dtEndTime;
		this.intTaskStatus = intTaskStatus;
		this.intTaskType = intTaskType;
		this.vcTaskName = vcTaskName;
	}

	/**
	 * 午餐構造方法
	 */
	public FtbDataGetterTask() {
	}

}
