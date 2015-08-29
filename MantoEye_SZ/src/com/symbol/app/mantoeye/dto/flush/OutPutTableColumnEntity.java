package com.symbol.app.mantoeye.dto.flush;

import java.io.Serializable;

public class OutPutTableColumnEntity implements Serializable {

	private String nmTableMapId;//数据表映射表序号 外键
	private String vcColumnName;//字段名
	private String outType;//输出
	private String inType;//输出

	public String getInType() {
		return inType;
	}
	public void setInType(String inType) {
		this.inType = inType;
	}
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
	public String getOutType() {
		return outType;
	}
	public void setOutType(String outType) {
		this.outType = outType;
	}
	public String getVcColumnNickName() {
		return vcColumnNickName;
	}
	public void setVcColumnNickName(String vcColumnNickName) {
		this.vcColumnNickName = vcColumnNickName;
	}

}
