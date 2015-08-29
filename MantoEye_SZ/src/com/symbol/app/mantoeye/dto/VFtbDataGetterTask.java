package com.symbol.app.mantoeye.dto;

public class VFtbDataGetterTask implements // extends FtbDataGetterTask
		java.io.Serializable {
	private Long nmDataGetterTaskId;// 任务表ID
	private Integer intTaskDelong;// 1、数据提取模块 2、业务拨测模块
	private String vcTaskName;// 任务名
	/**
	 * 1.根据号码提取历史原始信令 2.根据号码提取实时原始信令 3.根据号码提取历史解析数据 4.根据区域及业务提取用户号码 5.已有业务规则拨测
	 * 6.新增业务规则拨测
	 */
	private Integer intTaskType;
	private String dtOrderTime;// 定制时间
	private String nmTaskOrder;// 定制人
	private String dtStartTime;// 开始时间
	private String dtEndTime;// 结束时间
	private Boolean bitTaskActiveFlag;// 是否激活 0.非激活1.激活
	/**
	 * 任务状态 0.未开始 1.进行中 2.任务自然结束 3.任务手工结束 4.停止中
	 */
	private Integer intTaskStatus;

	/**
	 * 提取号码 插入数据库时需要加上86前缀
	 */
	private String msisdn = "";

	private String businessName = "";// 业务名称
	private Integer businessId = 0;// 业务Id
	private String areaName = "";// 区域名称 多个以逗号隔开

	/**
	 * 文件信息
	 */
	private String fileInfo = "";

	public Long getNmDataGetterTaskId() {
		return nmDataGetterTaskId;
	}

	public void setNmDataGetterTaskId(Long nmDataGetterTaskId) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
	}

	public Integer getIntTaskDelong() {
		return intTaskDelong;
	}

	public void setIntTaskDelong(Integer intTaskDelong) {
		this.intTaskDelong = intTaskDelong;
	}

	public String getVcTaskName() {
		return vcTaskName;
	}

	public void setVcTaskName(String vcTaskName) {
		this.vcTaskName = vcTaskName;
	}

	public Integer getIntTaskType() {
		return intTaskType;
	}

	public void setIntTaskType(Integer intTaskType) {
		this.intTaskType = intTaskType;
	}

	public String getDtOrderTime() {
		return dtOrderTime;
	}

	public void setDtOrderTime(String dtOrderTime) {
		this.dtOrderTime = dtOrderTime;
	}

	public String getNmTaskOrder() {
		return nmTaskOrder;
	}

	public void setNmTaskOrder(String nmTaskOrder) {
		this.nmTaskOrder = nmTaskOrder;
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
		return bitTaskActiveFlag;
	}

	public void setBitTaskActiveFlag(Boolean bitTaskActiveFlag) {
		this.bitTaskActiveFlag = bitTaskActiveFlag;
	}

	public Integer getIntTaskStatus() {
		return intTaskStatus;
	}

	public void setIntTaskStatus(Integer intTaskStatus) {
		this.intTaskStatus = intTaskStatus;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
