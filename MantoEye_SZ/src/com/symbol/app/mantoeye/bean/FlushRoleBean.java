package com.symbol.app.mantoeye.bean;

public class FlushRoleBean {

	private String vcBussinessName;
	private Long flush;
	private Long lastflush;
	private Double flushRate;
	private Long conflictCount;
	private Long lastconflictCount;
	private Double conflictCountRate;
	private String fulldate;
	private String ip;

	public String getVcBussinessName() {
		return vcBussinessName;
	}

	public void setVcBussinessName(String vcBussinessName) {
		this.vcBussinessName = vcBussinessName;
	}

	public Long getFlush() {
		return flush;
	}

	public void setFlush(Long flush) {
		this.flush = flush;
	}

	public Long getLastflush() {
		return lastflush;
	}

	public void setLastflush(Long lastflush) {
		this.lastflush = lastflush;
	}

	public Double getFlushRate() {
		return flushRate;
	}

	public void setFlushRate(Double flushRate) {
		this.flushRate = flushRate;
	}

	public Long getConflictCount() {
		return conflictCount;
	}

	public void setConflictCount(Long conflictCount) {
		this.conflictCount = conflictCount;
	}

	public Long getLastconflictCount() {
		return lastconflictCount;
	}

	public void setLastconflictCount(Long lastconflictCount) {
		this.lastconflictCount = lastconflictCount;
	}

	public Double getConflictCountRate() {
		return conflictCountRate;
	}

	public void setConflictCountRate(Double conflictCountRate) {
		this.conflictCountRate = conflictCountRate;
	}

	public String getFulldate() {
		return fulldate;
	}

	public void setFulldate(String fulldate) {
		this.fulldate = fulldate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
