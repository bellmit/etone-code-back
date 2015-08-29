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
@Table(name = "ftbDataGetterTask", schema = "DBA", catalog = "iqwrite")
/**
 * 数据提取任务表。 【重要约束】对于实时提取任务，正在进行的任务和还为开始的任务，涉及的用户号码累计不能超过20个！
 * 任务未开始可以更改任务策略，其它状态则不行！
 * 
 */
public class FtbDataGetterTask implements java.io.Serializable {

	/**
	 * 主键ID
	 */
	private Long nmDataGetterTaskId;
	/**
	 * 所属模块标识 1、数据提取模块2、业务拨测模块
	 */
	private Integer intTaskDelong;
	/**
	 * 任务名称
	 */
	private String vcTaskName;
	/**
	 * 1.根据号码提取历史原始信令 2.根据号码提取实时原始信令 3.根据号码提取历史解析数据 4.根据区域及业务提取用户号码 5.已有业务规则拨测(信令数据)
	 * 6.新增业务规则拨测(信令数据)7.已有业务规则拨测(解码数据)8.新增业务规则拨测(解码数据)
	 */
	private Integer intTaskType;
	/**
	 * 定制时间
	 */
	private Date dtOrderTime;
	/**
	 * 定制人
	 */
	private String nmTaskOrder;
	/**
	 * 开始时间
	 */
	private Date dtStartTime;
	/**
	 * 结束时间
	 */
	private Date dtEndTime;
	/**
	 * 激活标识 0.非激活 1.激活
	 */
	private Boolean bitTaskActiveFlag;
	/**
	 * 任务状态 0.未开始 1.进行中 2.任务自然结束 3.任务手工结束 4.停止中
	 */
	private Integer intTaskStatus;


	/** default constructor */
	public FtbDataGetterTask() {
	}

	/** full constructor */
	public FtbDataGetterTask(Integer intTaskDelong, String vcTaskName,
			Integer intTaskType, Date dtOrderTime, String nmTaskOrder,
			Date dtStartTime, Date dtEndTime, Boolean bitTaskActiveFlag,
			Integer intTaskStatus) {
		this.intTaskDelong = intTaskDelong;
		this.vcTaskName = vcTaskName;
		this.intTaskType = intTaskType;
		this.dtOrderTime = dtOrderTime;
		this.nmTaskOrder = nmTaskOrder;
		this.dtStartTime = dtStartTime;
		this.dtEndTime = dtEndTime;
		this.bitTaskActiveFlag = bitTaskActiveFlag;
		this.intTaskStatus = intTaskStatus;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmDataGetterTaskId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmDataGetterTaskId() {
		return this.nmDataGetterTaskId;
	}

	public void setNmDataGetterTaskId(Long nmDataGetterTaskId) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
	}

	@Column(name = "intTaskDelong")
	public Integer getIntTaskDelong() {
		return this.intTaskDelong;
	}

	public void setIntTaskDelong(Integer intTaskDelong) {
		this.intTaskDelong = intTaskDelong;
	}

	@Column(name = "vcTaskName", length = 30)
	public String getVcTaskName() {
		return this.vcTaskName;
	}

	public void setVcTaskName(String vcTaskName) {
		this.vcTaskName = vcTaskName;
	}

	@Column(name = "intTaskType")
	public Integer getIntTaskType() {
		return this.intTaskType;
	}

	public void setIntTaskType(Integer intTaskType) {
		this.intTaskType = intTaskType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtOrderTime", length = 23)
	public Date getDtOrderTime() {
		return this.dtOrderTime;
	}

	public void setDtOrderTime(Date dtOrderTime) {
		this.dtOrderTime = dtOrderTime;
	}

	@Column(name = "nmTaskOrder", length = 32)
	public String getNmTaskOrder() {
		return this.nmTaskOrder;
	}

	public void setNmTaskOrder(String nmTaskOrder) {
		this.nmTaskOrder = nmTaskOrder;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtStartTime", length = 23)
	public Date getDtStartTime() {
		return this.dtStartTime;
	}

	public void setDtStartTime(Date dtStartTime) {
		this.dtStartTime = dtStartTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtEndTime", length = 23)
	public Date getDtEndTime() {
		return this.dtEndTime;
	}

	public void setDtEndTime(Date dtEndTime) {
		this.dtEndTime = dtEndTime;
	}

	@Column(name = "bitTaskActiveFlag")
	public Boolean getBitTaskActiveFlag() {
		return this.bitTaskActiveFlag;
	}

	public void setBitTaskActiveFlag(Boolean bitTaskActiveFlag) {
		this.bitTaskActiveFlag = bitTaskActiveFlag;
	}

	@Column(name = "intTaskStatus")
	public Integer getIntTaskStatus() {
		return this.intTaskStatus;
	}

	public void setIntTaskStatus(Integer intTaskStatus) {
		this.intTaskStatus = intTaskStatus;
	}
}