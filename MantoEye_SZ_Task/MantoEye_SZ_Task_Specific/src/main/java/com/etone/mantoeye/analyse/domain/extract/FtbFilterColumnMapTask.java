package com.etone.mantoeye.analyse.domain.extract;

import java.io.Serializable;

/**
 * 任務提取过滤条件字段与任务对照表Domain
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月24日22:14:10
 * 
 */
public class FtbFilterColumnMapTask  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6167152710655784289L;

	/**
	 * 过滤条件字段与任务对照表ID
	 */
	private Long nmTableColumnMapId;

	/**
	 * 过滤条件字段与任务对照表列名
	 */
	private String ColumnName;

	/**
	 * 过滤条件字段与任务对照表列類型
	 */
	private int intColumnType;

	/**
	 * 过滤条件字段与任务对照表任務ID
	 */
	private Long nmDataGetterTaskId;

	/**
	 * 过滤条件字段与任务对照表條件
	 */
	private String vccondition;

	/**
	 * 过滤条件字段与任务对照表條件值
	 */
	private String vcFilterValue;

	/**
	 * @return the nmTableColumnMapId
	 */
	public Long getNmTableColumnMapId() {
		return nmTableColumnMapId;
	}

	/**
	 * @param nmTableColumnMapId
	 *            the nmTableColumnMapId to set
	 */
	public void setNmTableColumnMapId(Long nmTableColumnMapId) {
		this.nmTableColumnMapId = nmTableColumnMapId;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return ColumnName;
	}

	/**
	 * @param columnName
	 *            the columnName to set
	 */
	public void setColumnName(String columnName) {
		ColumnName = columnName;
	}

	/**
	 * @return the intColumnType
	 */
	public int getIntColumnType() {
		return intColumnType;
	}

	/**
	 * @param intColumnType
	 *            the intColumnType to set
	 */
	public void setIntColumnType(int intColumnType) {
		this.intColumnType = intColumnType;
	}

	/**
	 * @return the nmDataGetterTaskId
	 */
	public Long getNmDataGetterTaskId() {
		return nmDataGetterTaskId;
	}

	/**
	 * @param nmDataGetterTaskId
	 *            the nmDataGetterTaskId to set
	 */
	public void setNmDataGetterTaskId(Long nmDataGetterTaskId) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
	}

	/**
	 * @return the vccondition
	 */
	public String getVccondition() {
		return vccondition;
	}

	/**
	 * @param vccondition
	 *            the vccondition to set
	 */
	public void setVccondition(String vccondition) {
		this.vccondition = vccondition;
	}

	/**
	 * @return the vcFilterValue
	 */
	public String getVcFilterValue() {
		return vcFilterValue;
	}

	/**
	 * @param vcFilterValue
	 *            the vcFilterValue to set
	 */
	public void setVcFilterValue(String vcFilterValue) {
		this.vcFilterValue = vcFilterValue;
	}

	/**
	 * @param nmTableColumnMapId
	 * @param columnName
	 * @param intColumnType
	 * @param nmDataGetterTaskId
	 * @param vccondition
	 * @param vcFilterValue
	 */
	public FtbFilterColumnMapTask(Long nmTableColumnMapId, String columnName,
			int intColumnType, Long nmDataGetterTaskId, String vccondition,
			String vcFilterValue) {
		super();
		this.nmTableColumnMapId = nmTableColumnMapId;
		ColumnName = columnName;
		this.intColumnType = intColumnType;
		this.nmDataGetterTaskId = nmDataGetterTaskId;
		this.vccondition = vccondition;
		this.vcFilterValue = vcFilterValue;
	}

	/**
	 * 空參構造方法
	 */
	public FtbFilterColumnMapTask() {
	}
}
