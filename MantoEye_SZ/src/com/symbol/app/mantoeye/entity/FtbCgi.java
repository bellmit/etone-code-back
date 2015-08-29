package com.symbol.app.mantoeye.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class FtbCgi implements java.io.Serializable {
	private Integer intId;
	private Integer intBranchId;
	private Integer intAreaId;
	private Integer intBscId;
	private Integer intSgsnId;
	private Integer intStreetId;
	private Integer intSaleAreaId;
	private String vcCGI;
	private Integer intLac;
	private String vcCgiEnName;
	private String vcCgiChName;
	private String vcCgiAddr;
	private Date dtUTime;
	
	/** default constructor */
	public FtbCgi() {
	}

	public Integer getIntId() {
		return intId;
	}

	public void setIntId(Integer intId) {
		this.intId = intId;
	}

	public Integer getIntBranchId() {
		return intBranchId;
	}

	public void setIntBranchId(Integer intBranchId) {
		this.intBranchId = intBranchId;
	}

	public Integer getIntAreaId() {
		return intAreaId;
	}

	public void setIntAreaId(Integer intAreaId) {
		this.intAreaId = intAreaId;
	}

	public Integer getIntBscId() {
		return intBscId;
	}

	public void setIntBscId(Integer intBscId) {
		this.intBscId = intBscId;
	}

	public Integer getIntSgsnId() {
		return intSgsnId;
	}

	public void setIntSgsnId(Integer intSgsnId) {
		this.intSgsnId = intSgsnId;
	}

	public Integer getIntStreetId() {
		return intStreetId;
	}

	public void setIntStreetId(Integer intStreetId) {
		this.intStreetId = intStreetId;
	}

	public Integer getIntSaleAreaId() {
		return intSaleAreaId;
	}

	public void setIntSaleAreaId(Integer intSaleAreaId) {
		this.intSaleAreaId = intSaleAreaId;
	}

	public String getVcCGI() {
		return vcCGI;
	}

	public void setVcCGI(String vcCGI) {
		this.vcCGI = vcCGI;
	}

	public Integer getIntLac() {
		return intLac;
	}

	public void setIntLac(Integer intLac) {
		this.intLac = intLac;
	}

	public String getVcCgiEnName() {
		return vcCgiEnName;
	}

	public void setVcCgiEnName(String vcCgiEnName) {
		this.vcCgiEnName = vcCgiEnName;
	}

	public String getVcCgiChName() {
		return vcCgiChName;
	}

	public void setVcCgiChName(String vcCgiChName) {
		this.vcCgiChName = vcCgiChName;
	}

	public String getVcCgiAddr() {
		return vcCgiAddr;
	}

	public void setVcCgiAddr(String vcCgiAddr) {
		this.vcCgiAddr = vcCgiAddr;
	}

	public Date getDtUTime() {
		return dtUTime;
	}

	public void setDtUTime(Date dtUTime) {
		this.dtUTime = dtUTime;
	}

	

}