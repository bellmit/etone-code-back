package com.symbol.app.mantoeye.dto;

public class VFtbDataOutPutTask implements java.io.Serializable {

	/**
	 * 主键ID
	 */
	private Long nmDataOutPutTaskId;
	/**
	 * 所属模块标识 1、数据提取模块2、业务拨测模块
	 */
	private Integer intTaskDelong;
	/**
	 * 任务名称
	 */
	private String vcTaskName;
	/**
	 * 1.根据号码提取历史原始信令 2.根据号码提取实时原始信令 3.根据号码提取历史解析数据 4.根据区域及业务提取用户号码
	 * 5.已有业务规则拨测(信令数据) 6.新增业务规则拨测(信令数据)7.已有业务规则拨测(解码数据)8.新增业务规则拨测(解码数据)
	 */
	private Integer intTaskType;
	/**
	 * 定制时间
	 */
	private String dtOrderTime;
	/**
	 * 定制人
	 */
	private String vcTaskOrder;
	/**
	 * 开始时间
	 */
	private String dtStartTime;
	/**
	 * 结束时间
	 */
	private String dtEndTime;
	/**
	 * 激活标识 0.非激活 1.激活
	 */
	private Boolean bitTaskActiveFlag;
	/**
	 * 任务状态 0.未开始 1.进行中 2.任务自然结束 3.任务手工结束 4.停止中
	 */
	private Integer intTaskStatus;

	/** default constructor */
	public VFtbDataOutPutTask() {
	}

	public Long getNmDataOutPutTaskId() {
		return this.nmDataOutPutTaskId;
	}

	public void setNmDataOutPutTaskId(Long nmDataOutPutTaskId) {
		this.nmDataOutPutTaskId = nmDataOutPutTaskId;
	}

	public Integer getIntTaskDelong() {
		return this.intTaskDelong;
	}

	public void setIntTaskDelong(Integer intTaskDelong) {
		this.intTaskDelong = intTaskDelong;
	}

	public String getVcTaskName() {
		return this.vcTaskName;
	}

	public void setVcTaskName(String vcTaskName) {
		this.vcTaskName = vcTaskName;
	}

	public Integer getIntTaskType() {
		return this.intTaskType;
	}

	public void setIntTaskType(Integer intTaskType) {
		this.intTaskType = intTaskType;
	}

	public String getVcTaskOrder() {
		return vcTaskOrder;
	}

	public void setVcTaskOrder(String vcTaskOrder) {
		this.vcTaskOrder = vcTaskOrder;
	}

	public String getDtOrderTime() {
		return dtOrderTime;
	}

	public void setDtOrderTime(String dtOrderTime) {
		this.dtOrderTime = dtOrderTime;
	}

	public String getDtStartTime() {
		return dtStartTime;
	}

	public void setDtStartTime(String dtStartTime) {
		this.dtStartTime = dtStartTime;
	}

	public String getDtEndTime() {
		return dtEndTime;
	}

	public void setDtEndTime(String dtEndTime) {
		this.dtEndTime = dtEndTime;
	}

	public Boolean getBitTaskActiveFlag() {
		return this.bitTaskActiveFlag;
	}

	public void setBitTaskActiveFlag(Boolean bitTaskActiveFlag) {
		this.bitTaskActiveFlag = bitTaskActiveFlag;
	}

	public Integer getIntTaskStatus() {
		return this.intTaskStatus;
	}

	public void setIntTaskStatus(Integer intTaskStatus) {
		this.intTaskStatus = intTaskStatus;
	}
}