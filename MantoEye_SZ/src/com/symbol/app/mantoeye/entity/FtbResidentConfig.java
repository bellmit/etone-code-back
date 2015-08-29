package com.symbol.app.mantoeye.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * FtbTableMap entity. @author MyEclipse Persistence Tools
 */
//@Entity
//@Table(name = "ftbResidentConfig", schema = "DBA", catalog = "iqwrite")
public class FtbResidentConfig implements java.io.Serializable {

	// Fields

	private Long nmResidentId;
	private String intType;
	private Integer intDay;
	private String vcNote;

	/** default constructor */
	public FtbResidentConfig() {
	}

	//@Id
	//@GeneratedValue
	//@Column(name = "nmResidentId", unique = true, nullable = false, precision = 3, scale = 0)
	public Long getNmResidentId() {
		return nmResidentId;
	}

	public void setNmResidentId(Long nmResidentId) {
		this.nmResidentId = nmResidentId;
	}



	public String getIntType() {
		return intType;
	}

	public void setIntType(String intType) {
		this.intType = intType;
	}

	//@Column(name = "intDay")
	public Integer getIntDay() {
		return intDay;
	}

	public void setIntDay(Integer intDay) {
		this.intDay = intDay;
	}

	//@Column(name = "vcNote")
	public String getVcNote() {
		return vcNote;
	}

	public void setVcNote(String vcNote) {
		this.vcNote = vcNote;
	}

}