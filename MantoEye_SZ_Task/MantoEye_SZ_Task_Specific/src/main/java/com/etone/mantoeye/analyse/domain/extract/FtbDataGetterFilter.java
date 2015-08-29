package com.etone.mantoeye.analyse.domain.extract;

import java.io.Serializable;

/**
 * 数据提取过滤条件表
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月24日22:34:21
 */
public class FtbDataGetterFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8101931629650139258L;

	/**
	 * 数据提取过滤条件表ID
	 */
	private Long nmDataGetterFilterId;

	/**
	 * 数据提取过滤条件表任務ID
	 */
	private Long nmDataGetterTaskId;

	/**
	 * 数据提取过滤条件表過濾類型
	 */
	private int intFilterType;

	/**
	 * 数据提取过滤条件表過濾值
	 */
	private String vcFilterValue;

	/**
	 * @return the nmDataGetterFilterId
	 */
	public Long getNmDataGetterFilterId() {
		return nmDataGetterFilterId;
	}

	/**
	 * @param nmDataGetterFilterId
	 *            the nmDataGetterFilterId to set
	 */
	public void setNmDataGetterFilterId(Long nmDataGetterFilterId) {
		this.nmDataGetterFilterId = nmDataGetterFilterId;
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
	 * @return the intFilterType
	 */
	public int getIntFilterType() {
		return intFilterType;
	}

	/**
	 * @param intFilterType
	 *            the intFilterType to set
	 */
	public void setIntFilterType(int intFilterType) {
		this.intFilterType = intFilterType;
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
	 * @param nmDataGetterFilterId
	 * @param nmDataGetterTaskId
	 * @param intFilterType
	 * @param vcFilterValue
	 */
	public FtbDataGetterFilter(Long nmDataGetterFilterId,
			Long nmDataGetterTaskId, int intFilterType, String vcFilterValue) {
		super();
		this.nmDataGetterFilterId = nmDataGetterFilterId;
		this.nmDataGetterTaskId = nmDataGetterTaskId;
		this.intFilterType = intFilterType;
		this.vcFilterValue = vcFilterValue;
	}

	/**
	 * 無慘構造方法
	 */
	public FtbDataGetterFilter() {
	}

}
