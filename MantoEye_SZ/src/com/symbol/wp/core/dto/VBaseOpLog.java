package com.symbol.wp.core.dto;

import com.symbol.wp.core.entity.TbBaseOpLog;



public class VBaseOpLog extends TbBaseOpLog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 373067177876430247L;
	private String viewDate;
	private String startDate; //开始时间 时间段查询专用
	private String endDate; //结束时间 时间段查询专用
	private String userName;//用户名
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getViewDate() {
		return viewDate;
	}
	public void setViewDate(String viewDate) {
		this.viewDate = viewDate;
	}
	
	
}
