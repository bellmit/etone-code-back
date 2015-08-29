package com.symbol.app.mantoeye.dto.flush;

/**
 * 存放拨测结果按Msisdn流量统计的bean
 * @author rankin
 *
 */
public class TestFlush {
	private Long taskid;
	private Long upFlush;
	private Long downFlush;
	private Long totalFlush;
	private String msisdn;
	private String businessName;
	public Long getUpFlush() {
		return upFlush;
	}
	public void setUpFlush(Long upFlush) {
		this.upFlush = upFlush;
	}
	public Long getDownFlush() {
		return downFlush;
	}
	public void setDownFlush(Long downFlush) {
		this.downFlush = downFlush;
	}
	public Long getTotalFlush() {
		return totalFlush;
	}
	public void setTotalFlush(Long totalFlush) {
		this.totalFlush = totalFlush;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Long getTaskid() {
		return taskid;
	}
	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}
	
}
