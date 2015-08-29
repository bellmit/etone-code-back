package com.symbol.app.mantoeye.dto.flush;

import java.io.Serializable;

public class TableColumnMapEntity implements Serializable {

	private String nmTableMapId;//数据表映射表序号 外键
	private String vcColumnName;//字段名
	private String intColumnType;//字段类型
	private String strColumnType;//字段类型
	private String vcColumnNickName;//字段别名
	public String getNmTableMapId() {
		return nmTableMapId;
	}
	public void setNmTableMapId(String nmTableMapId) {
		this.nmTableMapId = nmTableMapId;
	}
	public String getVcColumnName() {
		return vcColumnName;
	}
	public void setVcColumnName(String vcColumnName) {
		this.vcColumnName = vcColumnName;
	}
	public String getIntColumnType() {
		return intColumnType;
	}
	public void setIntColumnType(String intColumnType) {
		this.intColumnType = intColumnType;
		this.setStrColumnType(this.getColumnType(intColumnType));
	}
	public String getVcColumnNickName() {
		return vcColumnNickName;
	}
	public void setVcColumnNickName(String vcColumnNickName) {
		this.vcColumnNickName = vcColumnNickName;
	}
	public String getStrColumnType() {
		return strColumnType;
	}
	public void setStrColumnType(String strColumnType) {
		this.strColumnType = strColumnType;
	}
	
	public String getColumnType(String columnName){
		if("1".equals(columnName)){
			return "数字类型";
		}else if("2".equals(columnName)){
			return "字符类型";
		}else if("3".equals(columnName)){
			return "日期类型";
		}
		return "字符类型";
	}
	
}
