package com.symbol.app.mantoeye.dto.flush;

import java.io.Serializable;

public class TerminalBusiness implements Serializable {
	
	private String nmTermBussId;
	private String businessName;
	private String businessTypeName;
	private String terminalBrandName;
	private String terminalTypeName;
	private Long intUsers;
	private Long intFlush;
	private String flUsersRate;
	private String flFlustRate;
	
	private Long terminalTypeId;
	
	Long forMB = Long.valueOf(1048576L);
	Long forKB = Long.valueOf(1024L);
	private Double intFlushKB;
	private Double intFlushMB;
	
	public void calculateData() {
		long lFlush=(long)this.intFlush * 100;
		
		this.intFlushMB = this.intFlush == 0 ? 0.0
				: (lFlush/ this.forMB) / 100.0;
		
		this.intFlushKB = this.intFlush == 0 ? 0.0
				: (lFlush / this.forKB) / 100.0;
		
	}
	
	
	public Double getIntFlushKB() {
		return intFlushKB;
	}


	public void setIntFlushKB(Double intFlushKB) {
		this.intFlushKB = intFlushKB;
	}


	public Double getIntFlushMB() {
		return intFlushMB;
	}


	public void setIntFlushMB(Double intFlushMB) {
		this.intFlushMB = intFlushMB;
	}


	public String getNmTermBussId() {
		return nmTermBussId;
	}
	public void setNmTermBussId(String nmTermBussId) {
		this.nmTermBussId = nmTermBussId;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessTypeName() {
		return businessTypeName;
	}
	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}
	public String getTerminalBrandName() {
		return terminalBrandName;
	}
	public void setTerminalBrandName(String terminalBrandName) {
		this.terminalBrandName = terminalBrandName;
	}
	public String getTerminalTypeName() {
		return terminalTypeName;
	}
	public void setTerminalTypeName(String terminalTypeName) {
		this.terminalTypeName = terminalTypeName;
	}
	
	public String getFlUsersRate() {
		return flUsersRate;
	}
	public void setFlUsersRate(String flUsersRate) {
		this.flUsersRate = flUsersRate;
	}
	public String getFlFlustRate() {
		return flFlustRate;
	}
	public void setFlFlustRate(String flFlustRate) {
		this.flFlustRate = flFlustRate;
	}


	public Long getIntUsers() {
		return intUsers;
	}


	public void setIntUsers(Long intUsers) {
		this.intUsers = intUsers;
	}


	public Long getIntFlush() {
		return intFlush;
	}


	public void setIntFlush(Long intFlush) {
		this.intFlush = intFlush;
	}


	public Long getTerminalTypeId() {
		return terminalTypeId;
	}


	public void setTerminalTypeId(Long terminalTypeId) {
		this.terminalTypeId = terminalTypeId;
	}
}
