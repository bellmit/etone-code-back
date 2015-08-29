package com.etone.mantoeye.analyse.domain.extract;

import java.io.Serializable;

/**
 * 自定義輸出功能臨時參數DOMAIN
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月24日19:44:37
 * 
 */
public class DataOutPutParam  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6469650363981337870L;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 字段名
	 */
	private String columnName;

	/**
	 * 任務ID
	 */
	private Long nmDataOutPutTaskId;

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 *            the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName
	 *            the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the nmDataOutPutTaskId
	 */
	public Long getNmDataOutPutTaskId() {
		return nmDataOutPutTaskId;
	}

	/**
	 * @param nmDataOutPutTaskId
	 *            the nmDataOutPutTaskId to set
	 */
	public void setNmDataOutPutTaskId(Long nmDataOutPutTaskId) {
		this.nmDataOutPutTaskId = nmDataOutPutTaskId;
	}

	/**
	 * @param tableName
	 * @param columnName 
	 * @param nmDataOutPutTaskId
	 */
	public DataOutPutParam(String tableName, String columnName,
			Long nmDataOutPutTaskId) {
		super();
		this.tableName = tableName;
		this.columnName = columnName;
		this.nmDataOutPutTaskId = nmDataOutPutTaskId;
	}

	/**
	 * 無慘構造方法
	 */
	public DataOutPutParam() {
	}

}
