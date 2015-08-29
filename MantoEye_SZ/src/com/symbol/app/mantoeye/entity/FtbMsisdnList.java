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
@Entity
@Table(name = "ftbMsisdnList", schema = "DBA", catalog = "iqwrite")
public class FtbMsisdnList implements java.io.Serializable {

	// Fields

	private Long nmMsisdnListId;
	private Date dtUpdateTime;
	private Long nmMsisdn;
	private Integer intType;

	/** default constructor */
	public FtbMsisdnList() {
	}

	@Id
	@GeneratedValue
	@Column(name = "nmMsisdnListId", unique = true, nullable = false, precision = 3, scale = 0)
	public Long getNmMsisdnListId() {
		return nmMsisdnListId;
	}


	public void setNmMsisdnListId(Long nmMsisdnListId) {
		this.nmMsisdnListId = nmMsisdnListId;
	}


	// Constructors
	@Column(name = "dtUpdateTime")
	@Temporal(TemporalType.DATE)
	public Date getDtUpdateTime() {
		return dtUpdateTime;
	}

	public void setDtUpdateTime(Date dtUpdateTime) {
		this.dtUpdateTime = dtUpdateTime;
	}
	@Column(name = "nmMsisdn")
	public Long getNmMsisdn() {
		return nmMsisdn;
	}

	public void setNmMsisdn(Long nmMsisdn) {
		this.nmMsisdn = nmMsisdn;
	}
	@Column(name = "intType")
	public Integer getIntType() {
		return intType;
	}

	public void setIntType(Integer intType) {
		this.intType = intType;
	}

}