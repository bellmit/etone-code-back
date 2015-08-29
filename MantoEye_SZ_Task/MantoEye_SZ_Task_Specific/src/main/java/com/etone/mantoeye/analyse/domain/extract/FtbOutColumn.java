package com.etone.mantoeye.analyse.domain.extract;

import java.io.Serializable;

/**
 * 任務提取輸出列DOMAIN
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月24日22:02:32
 */
public class FtbOutColumn  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2348369326784162889L;

	/**
	 * 表ID
	 */
	private Long nmTableMapId;

	/**
	 * 字段名
	 */
	private String vcColumnName;

	/**
	 * 字段別名
	 */
	private String vcColumnNickName;

	/**
	 * 分組標識
	 */
	private int bitGroupBy;

	/**
	 * @return the nmTableMapId
	 */
	public Long getNmTableMapId() {
		return nmTableMapId;
	}

	/**
	 * @param nmTableMapId
	 *            the nmTableMapId to set
	 */
	public void setNmTableMapId(Long nmTableMapId) {
		this.nmTableMapId = nmTableMapId;
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
	 * @return the bitGroupBy
	 */
	public int getBitGroupBy() {
		return bitGroupBy;
	}

	/**
	 * @param bitGroupBy
	 *            the bitGroupBy to set
	 */
	public void setBitGroupBy(int bitGroupBy) {
		this.bitGroupBy = bitGroupBy;
	}

	/**
	 * 無慘構造方法
	 */
	public FtbOutColumn() {
	}

	/**
	 * @param nmTableMapId
	 * @param vcColumnName
	 * @param vcColumnNickName
	 * @param bitGroupBy
	 */
	public FtbOutColumn(Long nmTableMapId, String vcColumnName,
			String vcColumnNickName, int bitGroupBy) {
		super();
		this.nmTableMapId = nmTableMapId;
		this.vcColumnName = vcColumnName;
		this.vcColumnNickName = vcColumnNickName;
		this.bitGroupBy = bitGroupBy;
	}
}
