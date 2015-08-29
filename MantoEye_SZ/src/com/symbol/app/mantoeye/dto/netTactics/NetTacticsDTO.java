package com.symbol.app.mantoeye.dto.netTactics;

import com.symbol.app.mantoeye.dto.TreeDTO;

public class NetTacticsDTO extends TreeDTO{
	private int nmDataGetterTaskId;//任务ID
	private String tacticsName;//策略名称
	private String userName;//定制人
	private String createTime;//定制时间
	private String startDate;//任务开始日期
	private String endDate;//任务结束日期
	private String missionTime;//执行日期
	private String startHour;//任务开始时间段
	private String endHour;//任务结束时间段
	private String missionHour;//执行时间段
	private String status;//状态
	private String vcContents;//备注
	private String nmParentId;
	private String vcParentGroupName;//一级内容
	private String nmGroupId;
	private String vcGroupName;//二级内容
	private String treeId;
	private String startTime;//开始时间
	private String endTime;//结束时间

	public int getNmDataGetterTaskId() {
		return nmDataGetterTaskId;
	}

	public void setNmDataGetterTaskId(int nmDataGetterTaskId) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
	}

	public String getTacticsName() {
		return tacticsName;
	}

	public void setTacticsName(String tacticsName) {
		this.tacticsName = tacticsName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMissionTime() {
		return missionTime;
	}

	public void setMissionTime(String missionTime) {
		this.missionTime = missionTime;
	}

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getMissionHour() {
		return missionHour;
	}

	public void setMissionHour(String missionHour) {
		this.missionHour = missionHour;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVcContents() {
		return vcContents;
	}

	public void setVcContents(String vcContents) {
		this.vcContents = vcContents;
	}

	public String getNmParentId() {
		return nmParentId;
	}

	public void setNmParentId(String nmParentId) {
		this.nmParentId = nmParentId;
	}

	public String getVcParentGroupName() {
		return vcParentGroupName;
	}

	public void setVcParentGroupName(String vcParentGroupName) {
		this.vcParentGroupName = vcParentGroupName;
	}

	public String getNmGroupId() {
		return nmGroupId;
	}

	public void setNmGroupId(String nmGroupId) {
		this.nmGroupId = nmGroupId;
	}

	public String getVcGroupName() {
		return vcGroupName;
	}

	public void setVcGroupName(String vcGroupName) {
		this.vcGroupName = vcGroupName;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
