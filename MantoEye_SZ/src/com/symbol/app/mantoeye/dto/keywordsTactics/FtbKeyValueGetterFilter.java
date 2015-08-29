package com.symbol.app.mantoeye.dto.keywordsTactics;

public class FtbKeyValueGetterFilter{
	private long nmDataGetterFilterId;
	private String vcFilterKeyValue;
	private long nmDataGetterTaskId;
	private long nmSoTypeId;
	private boolean isExactMark;
	private String vcSoName;
	public long getNmDataGetterFilterId() {
		return nmDataGetterFilterId;
	}

	public void setNmDataGetterFilterId(long nmDataGetterFilterId) {
		this.nmDataGetterFilterId = nmDataGetterFilterId;
	}

	public String getVcFilterKeyValue() {
		return vcFilterKeyValue;
	}

	public void setVcFilterKeyValue(String vcFilterKeyValue) {
		this.vcFilterKeyValue = vcFilterKeyValue;
	}

	public long getNmDataGetterTaskId() {
		return nmDataGetterTaskId;
	}

	public void setNmDataGetterTaskId(long nmDataGetterTaskId) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
	}

	public long getNmSoTypeId() {
		return nmSoTypeId;
	}

	public void setNmSoTypeId(long nmSoTypeId) {
		this.nmSoTypeId = nmSoTypeId;
	}

	public boolean isExactMark() {
		return isExactMark;
	}

	public void setExactMark(boolean isExactMark) {
		this.isExactMark = isExactMark;
	}

	public String getVcSoName() {
		return vcSoName;
	}

	public void setVcSoName(String vcSoName) {
		this.vcSoName = vcSoName;
	}
}
