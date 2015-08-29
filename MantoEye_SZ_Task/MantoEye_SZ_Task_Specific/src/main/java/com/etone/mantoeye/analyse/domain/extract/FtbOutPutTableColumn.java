package com.etone.mantoeye.analyse.domain.extract;

import java.io.Serializable;

/**
 * 自定義輸出相關列DOMAIN
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月24日17:17:00
 * 
 */
public class FtbOutPutTableColumn  implements Serializable{ 

	/**
	 * 
	 */
	private static final long serialVersionUID = -3913670382514917779L;

	/**
	 * 数据表字段映射表ID
	 */
	private Long nmOutPutTableColumnId;

	/**
	 * 数据表字段映射表字段名
	 */
	private String vcColumnName;

	/**
	 * 数据表字段映射表字段別名
	 */
	private String vcColumnNickName;

	/**
	 * @return the nmOutPutTableColumnId
	 */
	public Long getNmOutPutTableColumnId() {
		return nmOutPutTableColumnId;
	}

	/**
	 * @param nmOutPutTableColumnId
	 *            the nmOutPutTableColumnId to set
	 */
	public void setNmOutPutTableColumnId(Long nmOutPutTableColumnId) {
		this.nmOutPutTableColumnId = nmOutPutTableColumnId;
	}

	/**
	 * @return the vcColumnName
	 */
	public String getVcColumnName() {
		return vcColumnName;
	}

	/**
	 * @param vcColumnName
	 *            the vcColumnName to set
	 */
	public void setVcColumnName(String vcColumnName) {
		this.vcColumnName = vcColumnName;
	}

	/**
	 * @return the vcColumnNickName
	 */
	public String getVcColumnNickName() {
		return vcColumnNickName;
	}

	/**
	 * @param vcColumnNickName
	 *            the vcColumnNickName to set
	 */
	public void setVcColumnNickName(String vcColumnNickName) {
		this.vcColumnNickName = vcColumnNickName;
	}

	/**
	 * @param nmOutPutTableColumnId
	 * @param vcColumnName
	 * @param vcColumnNickName
	 */
	public FtbOutPutTableColumn(Long nmOutPutTableColumnId,
			String vcColumnName, String vcColumnNickName) {
		super();
		this.nmOutPutTableColumnId = nmOutPutTableColumnId;
		this.vcColumnName = vcColumnName;
		this.vcColumnNickName = vcColumnNickName;
	}

	/**
	 * 午餐構造方法
	 */
	public FtbOutPutTableColumn() {
	}

}
