package com.symbol.app.mantoeye.bean;

/**
 * 数据提取功能，保存条件的bean
 * @author rankin
 *
 */
public class DataFilterBean {
	//表内字段对应id
	private Integer columnId;
	//过滤条件值
	private String value;
	//过滤操作符
	private String condition;
	public Integer getColumnId() {
		return columnId;
	}
	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	
}
