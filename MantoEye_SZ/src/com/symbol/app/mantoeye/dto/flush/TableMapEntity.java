package com.symbol.app.mantoeye.dto.flush;

import java.io.Serializable;
import java.util.List;

public class TableMapEntity implements Serializable {
	
	private String nmTableMapId;
	private String vcTableName;//表名
	private String vcTableNickName;//表别名
	private String intTableType; //表类型
	private String intBusinessType;//业务类型
	private List<String> dimensions;//维度
	public String getNmTableMapId() {
		return nmTableMapId;
	}
	public void setNmTableMapId(String nmTableMapId) {
		this.nmTableMapId = nmTableMapId;
	}
	public String getVcTableName() {
		return vcTableName;
	}
	public void setVcTableName(String vcTableName) {
		this.vcTableName = vcTableName;
	}
	public String getVcTableNickName() {
		return vcTableNickName;
	}
	public void setVcTableNickName(String vcTableNickName) {
		this.vcTableNickName = vcTableNickName;
	}
	public String getIntTableType() {
		return intTableType;
	}
	public void setIntTableType(String intTableType) {
		this.intTableType = intTableType;
	}
	public String getIntBusinessType() {
		return intBusinessType;
	}
	public void setIntBusinessType(String intBusinessType) {
		this.intBusinessType = intBusinessType;
	}
	public List<String> getDimensions() {
		return dimensions;
	}
	public void setDimensions(List<String> dimensions) {
		this.dimensions = dimensions;
	}	
}
