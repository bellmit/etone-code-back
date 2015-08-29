package com.etone.mantoeye.analyse.domain.ui;

import java.io.Serializable;
import java.util.Date;

/**
 * 统一平台日志表的BEAN
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月25日16:01:14
 */
public class TbUDTimeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1582274976079726515L;

	/**
	 * 時間
	 */
	private Date dtStatTime;

	/**
	 * 類型
	 */
	private int intType;

	/**
	 * 是否已上傳
	 */
	private int bitIsUploaded;

	/**
	 * 狀態
	 */
	private int intStatus;

	public int getIntStatus() {
		return intStatus;
	}

	public void setIntStatus(int intStatus) {
		this.intStatus = intStatus;
	}

	/**
	 * @return the dtStatTime
	 */
	public Date getDtStatTime() {
		return dtStatTime;
	}

	/**
	 * @param dtStatTime
	 *            the dtStatTime to set
	 */
	public void setDtStatTime(Date dtStatTime) {
		this.dtStatTime = dtStatTime;
	}

	/**
	 * @return the intType
	 */
	public int getIntType() {
		return intType;
	}

	/**
	 * @param intType
	 *            the intType to set
	 */
	public void setIntType(int intType) {
		this.intType = intType;
	}

	/**
	 * @return the bitIsUploaded
	 */
	public int getBitIsUploaded() {
		return bitIsUploaded;
	}

	/**
	 * @param bitIsUploaded
	 *            the bitIsUploaded to set
	 */
	public void setBitIsUploaded(int bitIsUploaded) {
		this.bitIsUploaded = bitIsUploaded;
	}

	/**
	 * @param dtStatTime
	 * @param intType
	 * @param bitIsUploaded
	 */
	public TbUDTimeBean(Date dtStatTime, int intType, int bitIsUploaded) {
		this.dtStatTime = dtStatTime;
		this.intType = intType;
		this.bitIsUploaded = bitIsUploaded;
	}

	/**
	 * 無慘構造方法
	 */
	public TbUDTimeBean() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TbUDTimeBean [dtStatTime=" + dtStatTime + ", intType="
				+ intType + ", bitIsUploaded=" + bitIsUploaded + ", intStatus="
				+ intStatus + ", getIntStatus()=" + getIntStatus()
				+ ", getDtStatTime()=" + getDtStatTime() + ", getIntType()="
				+ getIntType() + ", getBitIsUploaded()=" + getBitIsUploaded()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
