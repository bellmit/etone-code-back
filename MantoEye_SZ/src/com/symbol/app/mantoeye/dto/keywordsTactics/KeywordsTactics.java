package com.symbol.app.mantoeye.dto.keywordsTactics;

import java.util.Date;

public class KeywordsTactics implements java.io.Serializable{
	private long nmDataGetterTaskId;//任务ID
	private String vcTaskName;//策略名称
	private String vcTaskOrder;//定制人
	private String dtOrderTime;//定制时间
	private String dtStartTime;//任务开始日期
	private String dtEndTime;//任务结束日期
	private String intStartHour;//任务开始时间段
	private String intEndHour;//任务结束时间段
	private int bitTaskActiveFlag;//执行时间段
	private int intTaskStatus;//状态
	private int intTaskType;//类别
	private String vcContents;//备注
	
	public long getNmDataGetterTaskId() {
		return nmDataGetterTaskId;
	}
	public void setNmDataGetterTaskId(long nmDataGetterTaskId) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
	}
	
	public String getVcTaskName() {
		return vcTaskName;
	}
	public void setVcTaskName(String vcTaskName) {
		this.vcTaskName = vcTaskName;
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
	public String getIntStartHour() {
		return intStartHour;
	}
	public void setIntStartHour(String intStartHour) {
		this.intStartHour = intStartHour;
	}
	
	public String getIntEndHour() {
		return intEndHour;
	}
	public void setIntEndHour(String intEndHour) {
		this.intEndHour = intEndHour;
	}
	
	
	
	public int getBitTaskActiveFlag() {
		return bitTaskActiveFlag;
	}
	public void setBitTaskActiveFlag(int bitTaskActiveFlag) {
		this.bitTaskActiveFlag = bitTaskActiveFlag;
	}
	public int getIntTaskStatus() {
		return intTaskStatus;
	}
	public void setIntTaskStatus(int intTaskStatus) {
		this.intTaskStatus = intTaskStatus;
	}
	
	public int getIntTaskType() {
		return intTaskType;
	}
	public void setIntTaskType(int intTaskType) {
		this.intTaskType = intTaskType;
	}
	
	public String getVcContents() {
		return vcContents;
	}
	public void setVcContents(String vcContents) {
		this.vcContents = vcContents;
	}

	
}
