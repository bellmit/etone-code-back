package com.symbol.app.mantoeye.dto.flush;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TerminalTaskEntity implements Serializable {
	private Integer nmTerminalPolicyId;
	private String taskName;
	private String taskDescribe;
	private String beginTime;
	private String endTime;
	private String taskStatus;
	private int intTaskStatus=1;
	private Map<String,String> listTerminal;
	private Map<String,String> listBuss;
	
	private String businessNames;
	private String bisAndBisTypeId;
	private String terminalNames;
	private String brandAndType;
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDescribe() {
		return taskDescribe;
	}
	public void setTaskDescribe(String taskDescribe) {
		this.taskDescribe = taskDescribe;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public Map<String, String> getListTerminal() {
		return listTerminal;
	}
	public void setListTerminal(Map<String, String> listTerminal) {
		this.listTerminal = listTerminal;
	}
	public Map<String, String> getListBuss() {
		return listBuss;
	}
	public void setListBuss(Map<String, String> listBuss) {
		this.listBuss = listBuss;
	}
	public Integer getNmTerminalPolicyId() {
		return nmTerminalPolicyId;
	}
	public void setNmTerminalPolicyId(Integer nmTerminalPolicyId) {
		this.nmTerminalPolicyId = nmTerminalPolicyId;
	}
	public int getIntTaskStatus() {
		return intTaskStatus;
	}
	public void setIntTaskStatus(int intTaskStatus) {
		this.intTaskStatus = intTaskStatus;
	}
	public String getBusinessNames() {
		return businessNames;
	}
	public void setBusinessNames(String businessNames) {
		this.businessNames = businessNames;
	}
	public String getBisAndBisTypeId() {
		return bisAndBisTypeId;
	}
	public void setBisAndBisTypeId(String bisAndBisTypeId) {
		this.bisAndBisTypeId = bisAndBisTypeId;
	}
	public String getTerminalNames() {
		return terminalNames;
	}
	public void setTerminalNames(String terminalNames) {
		this.terminalNames = terminalNames;
	}
	public String getBrandAndType() {
		return brandAndType;
	}
	public void setBrandAndType(String brandAndType) {
		this.brandAndType = brandAndType;
	}
	
}
