package com.symbol.app.mantoeye.dto.flush;

import java.io.Serializable;

public class BigFlushUserLimitValue implements Serializable {
	private String Id;
	private int intTimeType;
	private String strTimeType;
	private int intLimitValue;
	private String descrite;
	public int getIntTimeType() {
		return intTimeType;
	}
	public void setIntTimeType(int intTimeType) {
		this.intTimeType = intTimeType;
	}
	public int getIntLimitValue() {
		return intLimitValue;
	}
	public void setIntLimitValue(int intLimitValue) {
		this.intLimitValue = intLimitValue;
	}
	public String getDescrite() {
		return descrite;
	}
	public void setDescrite(String descrite) {
		this.descrite = descrite;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getStrTimeType() {
		return strTimeType;
	}
	public void setStrTimeType(String strTimeType) {
		this.strTimeType = strTimeType;
	}
	
	
}
