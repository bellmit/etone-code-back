package com.symbol.app.mantoeye.dto.netTactics;

public class DialectNetDTO {
	private String nmId;
	private String nmMsisdn;// 号码
	private String vcGroupName;// 二级内容
	private String vcParentGroupName;// 一级内容
	private String userTime;// 用户时间

	public String getNmId() {
		return nmId;
	}

	public void setNmId(String nmId) {
		this.nmId = nmId;
	}

	public String getNmMsisdn() {
		return nmMsisdn;
	}

	public void setNmMsisdn(String nmMsisdn) {
		this.nmMsisdn = nmMsisdn;
	}

	public String getVcGroupName() {
		return vcGroupName;
	}

	public void setVcGroupName(String vcGroupName) {
		this.vcGroupName = vcGroupName;
	}

	public String getVcParentGroupName() {
		return vcParentGroupName;
	}

	public void setVcParentGroupName(String vcParentGroupName) {
		this.vcParentGroupName = vcParentGroupName;
	}

	public String getUserTime() {
		return userTime;
	}

	public void setUserTime(String userTime) {
		this.userTime = userTime;
	}

}
