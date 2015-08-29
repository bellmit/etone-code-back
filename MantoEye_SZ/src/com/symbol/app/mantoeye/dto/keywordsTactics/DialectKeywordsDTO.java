package com.symbol.app.mantoeye.dto.keywordsTactics;

public class DialectKeywordsDTO {
	private String nmId;
	private String nmMsisdn;// 号码
	private String vcSoName;// 搜索引擎
	private String vcKeyValues;// 关键字集合
	private String isExactMark;// 是否精确匹配
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

	public String getVcSoName() {
		return vcSoName;
	}

	public void setVcSoName(String vcSoName) {
		this.vcSoName = vcSoName;
	}

	public String getVcKeyValues() {
		return vcKeyValues;
	}

	public void setVcKeyValues(String vcKeyValues) {
		this.vcKeyValues = vcKeyValues;
	}

	public String getIsExactMark() {
		return isExactMark;
	}

	public void setIsExactMark(String isExactMark) {
		this.isExactMark = isExactMark;
	}

	public String getUserTime() {
		return userTime;
	}

	public void setUserTime(String userTime) {
		this.userTime = userTime;
	}
}
