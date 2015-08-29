package com.etone.mantoeye.analyse.domain.terminal;

import java.io.Serializable;

/**
 * 終端升級任務定制DOMAIN
 * 
 * @author Wu Zhenzhen
 * @since job 1.0
 * @version 2.0 2011年10月28日10:32:41
 * 
 */
public class TerminalChangeTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2235811913717846025L;
	/**
	 * 终端升级任务表ID
	 */
	private Long nmTerminalChangeIdTaskId;
	/**
	 * 终端升级表ID
	 */
	private Long nmTerminalChangeId;
	/**
	 * 任务名
	 */
	private String vcTaskName;
	/**
	 * 任务订制时间
	 */
	private String dtOrderTime;
	/**
	 * 任务定制人
	 */
	private String vcTaskOrder;
	/**
	 * 任务开始时间
	 */
	private String dtTaskStartTime;
	/**
	 * 任务结束时间
	 */
	private String dtTaskEndTime;
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
	 * 分佈式分機IP
	 */
	private String vcLocalIP;

	/**
	 * @return the nmTerminalChangeIdTaskId
	 */
	public Long getNmTerminalChangeIdTaskId() {
		return nmTerminalChangeIdTaskId;
	}

	/**
	 * @param nmTerminalChangeIdTaskId
	 *            the nmTerminalChangeIdTaskId to set
	 */
	public void setNmTerminalChangeIdTaskId(Long nmTerminalChangeIdTaskId) {
		this.nmTerminalChangeIdTaskId = nmTerminalChangeIdTaskId;
	}

	/**
	 * @return the nmTerminalChangeId
	 */
	public Long getNmTerminalChangeId() {
		return nmTerminalChangeId;
	}

	/**
	 * @param nmTerminalChangeId
	 *            the nmTerminalChangeId to set
	 */
	public void setNmTerminalChangeId(Long nmTerminalChangeId) {
		this.nmTerminalChangeId = nmTerminalChangeId;
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
	 * @return the dtTaskStartTime
	 */
	public String getDtTaskStartTime() {
		return dtTaskStartTime;
	}

	/**
	 * @param dtTaskStartTime
	 *            the dtTaskStartTime to set
	 */
	public void setDtTaskStartTime(String dtTaskStartTime) {
		this.dtTaskStartTime = dtTaskStartTime;
	}

	/**
	 * @return the dtTaskEndTime
	 */
	public String getDtTaskEndTime() {
		return dtTaskEndTime;
	}

	/**
	 * @param dtTaskEndTime
	 *            the dtTaskEndTime to set
	 */
	public void setDtTaskEndTime(String dtTaskEndTime) {
		this.dtTaskEndTime = dtTaskEndTime;
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
	 * @param nmTerminalChangeIdTaskId
	 * @param nmTerminalChangeId
	 * @param vcTaskName
	 * @param dtOrderTime
	 * @param vcTaskOrder
	 * @param dtTaskStartTime
	 * @param dtTaskEndTime
	 * @param bitTaskActiveFlag
	 * @param intTaskStatus
	 */
	public TerminalChangeTask(Long nmTerminalChangeIdTaskId,
			Long nmTerminalChangeId, String vcTaskName, String dtOrderTime,
			String vcTaskOrder, String dtTaskStartTime, String dtTaskEndTime,
			Integer bitTaskActiveFlag, Integer intTaskStatus) {
		this.nmTerminalChangeIdTaskId = nmTerminalChangeIdTaskId;
		this.nmTerminalChangeId = nmTerminalChangeId;
		this.vcTaskName = vcTaskName;
		this.dtOrderTime = dtOrderTime;
		this.vcTaskOrder = vcTaskOrder;
		this.dtTaskStartTime = dtTaskStartTime;
		this.dtTaskEndTime = dtTaskEndTime;
		this.bitTaskActiveFlag = bitTaskActiveFlag;
		this.intTaskStatus = intTaskStatus;
	}

	/**
	 * 無慘構造方法
	 */
	public TerminalChangeTask() {
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
