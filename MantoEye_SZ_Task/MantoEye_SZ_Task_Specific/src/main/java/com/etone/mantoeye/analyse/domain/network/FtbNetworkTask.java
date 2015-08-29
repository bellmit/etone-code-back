package com.etone.mantoeye.analyse.domain.network;

import java.io.Serializable;

/**
 * ftbNetworkTask業務健康度DTO
 * 
 * @author Wu Zhenzhen
 * @version 2.0 2011年10月12日11:18:08
 * 
 */
public class FtbNetworkTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3022769891933956959L;

	/**
	 * 任務ID
	 */
	private Long nmNetworkTaskId;

	/**
	 * 任務模塊<br />
	 * 1、数据提取模块<br />
	 * 2、业务拨测模块<br />
	 */
	private Integer intTaskDelong;

	/**
	 * 任務名稱
	 */
	private String vcTaskName;

	/**
	 * 任務類型<br />
	 * 1.GSM健康度<br />
	 * 2.TD健康度<br />
	 */
	private Integer intTaskType;

	/**
	 * 任務定制時間
	 */
	private String dtOrderTime;

	/**
	 * 任務定制人
	 */
	private String vcTaskOrder;

	/**
	 * 任務開始時間
	 */
	private String dtStartTime;

	/**
	 * 任務結束時間
	 */
	private String dtEndTime;

	/**
	 * 任務激活狀態<br />
	 * 0.非激活<br />
	 * 1.激活<br />
	 */
	private Integer bitTaskActiveFlag;

	/**
	 * 任務狀態<br />
	 * 0.未开始<br />
	 * 1.进行中<br />
	 * 2.任务自然结束<br />
	 * 3.任务手工结束<br />
	 * 4.停止中<br />
	 * 5.任务异常停止<br />
	 */
	private Integer intTaskStatus;

	/**
	 * 業務ID
	 */
	private Long nmBussinessId;

	/**
	 * 活跃用户天数门限
	 */
	private Integer intActiveDay;

	/**
	 * IP字段名
	 */
	private String vcLocalIP;

	/**
	 * @return the nmNetworkTaskId
	 */
	public Long getNmNetworkTaskId() {
		return nmNetworkTaskId;
	}

	/**
	 * @param nmNetworkTaskId
	 *            the nmNetworkTaskId to set
	 */
	public void setNmNetworkTaskId(Long nmNetworkTaskId) {
		this.nmNetworkTaskId = nmNetworkTaskId;
	}

	/**
	 * @return the intTaskDelong
	 */
	public Integer getIntTaskDelong() {
		return intTaskDelong;
	}

	/**
	 * @param intTaskDelong
	 *            the intTaskDelong to set
	 */
	public void setIntTaskDelong(Integer intTaskDelong) {
		this.intTaskDelong = intTaskDelong;
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
	 * @return the intTaskType
	 */
	public Integer getIntTaskType() {
		return intTaskType;
	}

	/**
	 * @param intTaskType
	 *            the intTaskType to set
	 */
	public void setIntTaskType(Integer intTaskType) {
		this.intTaskType = intTaskType;
	}

	/**
	 * @return the dtOrderTime
	 */
	public String getDtOrderTime() {
		return dtOrderTime;
	}

	/**
	 * @param dtOrderTime
	 *            the dtOrderTime to set
	 */
	public void setDtOrderTime(String dtOrderTime) {
		this.dtOrderTime = dtOrderTime;
	}

	/**
	 * @return the vcTaskOrder
	 */
	public String getVcTaskOrder() {
		return vcTaskOrder;
	}

	/**
	 * @param vcTaskOrder
	 *            the vcTaskOrder to set
	 */
	public void setVcTaskOrder(String vcTaskOrder) {
		this.vcTaskOrder = vcTaskOrder;
	}

	/**
	 * @return the dtStartTime
	 */
	public String getDtStartTime() {
		return dtStartTime;
	}

	/**
	 * @param dtStartTime
	 *            the dtStartTime to set
	 */
	public void setDtStartTime(String dtStartTime) {
		this.dtStartTime = dtStartTime;
	}

	/**
	 * @return the dtEndTime
	 */
	public String getDtEndTime() {
		return dtEndTime;
	}

	/**
	 * @param dtEndTime
	 *            the dtEndTime to set
	 */
	public void setDtEndTime(String dtEndTime) {
		this.dtEndTime = dtEndTime;
	}

	/**
	 * @return the bitTaskActiveFlag
	 */
	public Integer getBitTaskActiveFlag() {
		return bitTaskActiveFlag;
	}

	/**
	 * @param bitTaskActiveFlag
	 *            the bitTaskActiveFlag to set
	 */
	public void setBitTaskActiveFlag(Integer bitTaskActiveFlag) {
		this.bitTaskActiveFlag = bitTaskActiveFlag;
	}

	/**
	 * @return the intTaskStatus
	 */
	public Integer getIntTaskStatus() {
		return intTaskStatus;
	}

	/**
	 * @param intTaskStatus
	 *            the intTaskStatus to set
	 */
	public void setIntTaskStatus(Integer intTaskStatus) {
		this.intTaskStatus = intTaskStatus;
	}

	/**
	 * @return the nmBussinessId
	 */
	public Long getNmBussinessId() {
		return nmBussinessId;
	}

	/**
	 * @param nmBussinessId
	 *            the nmBussinessId to set
	 */
	public void setNmBussinessId(Long nmBussinessId) {
		this.nmBussinessId = nmBussinessId;
	}

	/**
	 * @return the intActiveDay
	 */
	public Integer getIntActiveDay() {
		return intActiveDay;
	}

	/**
	 * @param intActiveDay
	 *            the intActiveDay to set
	 */
	public void setIntActiveDay(Integer intActiveDay) {
		this.intActiveDay = intActiveDay;
	}

	/**
	 * @param nmNetworkTaskId
	 * @param intTaskDelong
	 * @param vcTaskName
	 * @param intTaskType
	 * @param dtOrderTime
	 * @param vcTaskOrder
	 * @param dtStartTime
	 * @param dtEndTime
	 * @param bitTaskActiveFlag
	 * @param intTaskStatus
	 * @param nmBussinessId
	 * @param intActiveDay
	 */
	public FtbNetworkTask(Long nmNetworkTaskId, Integer intTaskDelong,
			String vcTaskName, Integer intTaskType, String dtOrderTime,
			String vcTaskOrder, String dtStartTime, String dtEndTime,
			Integer bitTaskActiveFlag, Integer intTaskStatus,
			Long nmBussinessId, Integer intActiveDay) {
		this.nmNetworkTaskId = nmNetworkTaskId;
		this.intTaskDelong = intTaskDelong;
		this.vcTaskName = vcTaskName;
		this.intTaskType = intTaskType;
		this.dtOrderTime = dtOrderTime;
		this.vcTaskOrder = vcTaskOrder;
		this.dtStartTime = dtStartTime;
		this.dtEndTime = dtEndTime;
		this.bitTaskActiveFlag = bitTaskActiveFlag;
		this.intTaskStatus = intTaskStatus;
		this.nmBussinessId = nmBussinessId;
		this.intActiveDay = intActiveDay;
	}

	/**
	 * 無慘構造方法
	 */
	public FtbNetworkTask() {
	}

	/**
	 * @return the vcLocalIP
	 */
	public String getVcLocalIP() {
		return vcLocalIP;
	}

	/**
	 * @param vcLocalIP
	 *            the vcLocalIP to set
	 */
	public void setVcLocalIP(String vcLocalIP) {
		this.vcLocalIP = vcLocalIP;
	}

}
