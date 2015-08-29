package com.etone.mantoeye.analyse.domain.network;

import java.io.Serializable;

/**
 * 業務健康度結果集統計
 * 
 * @author Wu Zhenzhen
 * @version 2.0 2011年11月17日11:06:07
 * 
 */
public class FtbNetworkConfigure  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5722320454970717335L;

	/**
	 * 配置表ID
	 */
	private Long nmNetworkConfigureId;

	/**
	 * 指标名称
	 */
	private String vcName;

	/**
	 * 指标类型
	 */
	private Integer intType;

	/**
	 * 最低值
	 */
	private Double intLow;

	/**
	 * 一般值
	 */
	private Double intCommon;

	/**
	 * 优良值
	 */
	private Double intExcellent;

	/**
	 * 最高值
	 */
	private Double intTop;

	/**
	 * 权重
	 */
	private Integer intScale;

	/**
	 * 备注
	 */
	private String vcNote;

	/**
	 * @return the nmNetworkConfigureId
	 */
	public Long getNmNetworkConfigureId() {
		return nmNetworkConfigureId;
	}

	/**
	 * @param nmNetworkConfigureId
	 *            the nmNetworkConfigureId to set
	 */
	public void setNmNetworkConfigureId(Long nmNetworkConfigureId) {
		this.nmNetworkConfigureId = nmNetworkConfigureId;
	}

	/**
	 * @return the vcName
	 */
	public String getVcName() {
		return vcName;
	}

	/**
	 * @param vcName
	 *            the vcName to set
	 */
	public void setVcName(String vcName) {
		this.vcName = vcName;
	}

	/**
	 * @return the intType
	 */
	public Integer getIntType() {
		return intType;
	}

	/**
	 * @param intType
	 *            the intType to set
	 */
	public void setIntType(Integer intType) {
		this.intType = intType;
	}

	/**
	 * @return the intLow
	 */
	public Double getIntLow() {
		return intLow;
	}

	/**
	 * @param intLow
	 *            the intLow to set
	 */
	public void setIntLow(Double intLow) {
		this.intLow = intLow;
	}

	/**
	 * @return the intCommon
	 */
	public Double getIntCommon() {
		return intCommon;
	}

	/**
	 * @param intCommon
	 *            the intCommon to set
	 */
	public void setIntCommon(Double intCommon) {
		this.intCommon = intCommon;
	}

	/**
	 * @return the intExcellent
	 */
	public Double getIntExcellent() {
		return intExcellent;
	}

	/**
	 * @param intExcellent
	 *            the intExcellent to set
	 */
	public void setIntExcellent(Double intExcellent) {
		this.intExcellent = intExcellent;
	}

	/**
	 * @return the intTop
	 */
	public Double getIntTop() {
		return intTop;
	}

	/**
	 * @param intTop
	 *            the intTop to set
	 */
	public void setIntTop(Double intTop) {
		this.intTop = intTop;
	}

	/**
	 * @return the intScale
	 */
	public Integer getIntScale() {
		return intScale;
	}

	/**
	 * @param intScale
	 *            the intScale to set
	 */
	public void setIntScale(Integer intScale) {
		this.intScale = intScale;
	}

	/**
	 * @return the vcNote
	 */
	public String getVcNote() {
		return vcNote;
	}

	/**
	 * @param vcNote
	 *            the vcNote to set
	 */
	public void setVcNote(String vcNote) {
		this.vcNote = vcNote;
	}

	/**
	 * @param nmNetworkConfigureId
	 * @param vcName
	 * @param intType
	 * @param intLow
	 * @param intCommon
	 * @param intExcellent
	 * @param intTop
	 * @param intScale
	 * @param vcNote
	 */
	public FtbNetworkConfigure(Long nmNetworkConfigureId, String vcName,
			Integer intType, Double intLow, Double intCommon,
			Double intExcellent, Double intTop, Integer intScale, String vcNote) {
		this.nmNetworkConfigureId = nmNetworkConfigureId;
		this.vcName = vcName;
		this.intType = intType;
		this.intLow = intLow;
		this.intCommon = intCommon;
		this.intExcellent = intExcellent;
		this.intTop = intTop;
		this.intScale = intScale;
		this.vcNote = vcNote;
	}

	/**
	 * 無慘構造方法
	 */
	public FtbNetworkConfigure() {
	}

}
