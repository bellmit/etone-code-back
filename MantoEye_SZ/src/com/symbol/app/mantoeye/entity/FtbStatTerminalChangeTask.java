package com.symbol.app.mantoeye.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * FtbDataGetterTask entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbStatTerminalChangeTask", schema = "DBA", catalog = "iqwrite")
/**
 * 数据提取任务表。 【重要约束】对于实时提取任务，正在进行的任务和还为开始的任务，涉及的用户号码累计不能超过20个！
 * 任务未开始可以更改任务策略，其它状态则不行！
 * 
 */
public class FtbStatTerminalChangeTask implements java.io.Serializable {

	private Long nmTerminalChangeIdTaskId;
	private Long nmTerminalChangeId;
	private String vcTaskName;
	private Date dtOrderTime;
	private String vcTaskOrder;
	private Date dtTaskStartTime;
	private Date dtTaskEndTime;
	private Boolean bitTaskActiveFlag;	
	private Integer intTaskStatus;

	
	public FtbStatTerminalChangeTask() {
	}

	/** full constructor */
	public FtbStatTerminalChangeTask(Long nmTerminalChangeId, String vcTaskName,
			Date dtOrderTime, String vcTaskOrder, Date dtTaskStartTime,
			Date dtTaskEndTime, Boolean bitTaskActiveFlag, Integer intTaskStatus) {
		this.nmTerminalChangeId = nmTerminalChangeId;
		this.vcTaskName = vcTaskName;
		this.dtOrderTime = dtOrderTime;
		this.vcTaskOrder = vcTaskOrder;
		this.dtTaskStartTime = dtTaskStartTime;
		this.dtTaskEndTime = dtTaskEndTime;
		this.bitTaskActiveFlag = bitTaskActiveFlag;
		this.intTaskStatus = intTaskStatus;
	}
	@Id
	@GeneratedValue
	@Column(name = "nmTerminalChangeIdTaskId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmTerminalChangeIdTaskId() {
		return nmTerminalChangeIdTaskId;
	}

	public void setNmTerminalChangeIdTaskId(Long nmTerminalChangeIdTaskId) {
		this.nmTerminalChangeIdTaskId = nmTerminalChangeIdTaskId;
	}
	@Column(name = "nmTerminalChangeId")
	public Long getNmTerminalChangeId() {
		return nmTerminalChangeId;
	}

	public void setNmTerminalChangeId(Long nmTerminalChangeId) {
		this.nmTerminalChangeId = nmTerminalChangeId;
	}
	@Column(name = "vcTaskName")
	public String getVcTaskName() {
		return vcTaskName;
	}

	public void setVcTaskName(String vcTaskName) {
		this.vcTaskName = vcTaskName;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtOrderTime", length = 23)
	public Date getDtOrderTime() {
		return dtOrderTime;
	}

	public void setDtOrderTime(Date dtOrderTime) {
		this.dtOrderTime = dtOrderTime;
	}
	@Column(name = "vcTaskOrder")
	public String getVcTaskOrder() {
		return vcTaskOrder;
	}

	public void setVcTaskOrder(String vcTaskOrder) {
		this.vcTaskOrder = vcTaskOrder;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtTaskStartTime", length = 23)
	public Date getDtTaskStartTime() {
		return dtTaskStartTime;
	}

	public void setDtTaskStartTime(Date dtTaskStartTime) {
		this.dtTaskStartTime = dtTaskStartTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtTaskEndTime", length = 23)
	public Date getDtTaskEndTime() {
		return dtTaskEndTime;
	}

	public void setDtTaskEndTime(Date dtTaskEndTime) {
		this.dtTaskEndTime = dtTaskEndTime;
	}
	@Column(name = "bitTaskActiveFlag")
	public Boolean getBitTaskActiveFlag() {
		return bitTaskActiveFlag;
	}

	public void setBitTaskActiveFlag(Boolean bitTaskActiveFlag) {
		this.bitTaskActiveFlag = bitTaskActiveFlag;
	}
	@Column(name = "intTaskStatus")
	public Integer getIntTaskStatus() {
		return intTaskStatus;
	}

	public void setIntTaskStatus(Integer intTaskStatus) {
		this.intTaskStatus = intTaskStatus;
	}

	
	
}