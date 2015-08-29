package com.symbol.app.mantoeye.entity.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * DtbBusinessSite entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dtbBusinessSite", schema = "DBA", catalog = "iqwrite")
public class DtbBusinessSite implements java.io.Serializable {

	// Fields

	private Integer nmBussinessId;
	private DtbBusinessType dtbBusinessType;
	private DtbBusinessCompany dtbBusinessCompany;
	private String vcBussinessName;
	private String vcBussinessNote;
	private String vcBussinessPicPath;

	// Constructors

	/** default constructor */
	public DtbBusinessSite() {
	}

	/** full constructor */
	public DtbBusinessSite(DtbBusinessType dtbBusinessType,
			DtbBusinessCompany dtbBusinessCompany, String vcBussinessName,
			String vcBussinessNote, String vcBussinessPicPath) {
		this.dtbBusinessType = dtbBusinessType;
		this.dtbBusinessCompany = dtbBusinessCompany;
		this.vcBussinessName = vcBussinessName;
		this.vcBussinessNote = vcBussinessNote;
		this.vcBussinessPicPath = vcBussinessPicPath;
	}

	// Property accessors
	@Id   
	@GeneratedValue
	@Column(name = "nmBussinessId", unique = true, nullable = false, precision = 6, scale = 0)
	public Integer getNmBussinessId() {
		return this.nmBussinessId;
	}

	public void setNmBussinessId(Integer nmBussinessId) {
		this.nmBussinessId = nmBussinessId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nmBussinessTypeId")
	public DtbBusinessType getDtbBusinessType() {
		return this.dtbBusinessType;
	}

	public void setDtbBusinessType(DtbBusinessType dtbBusinessType) {
		this.dtbBusinessType = dtbBusinessType;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nmCompanyId")
	public DtbBusinessCompany getDtbBusinessCompany() {
		return this.dtbBusinessCompany;
	}

	public void setDtbBusinessCompany(DtbBusinessCompany dtbBusinessCompany) {
		this.dtbBusinessCompany = dtbBusinessCompany;
	}

	@Column(name = "vcBussinessName", length = 100)
	public String getVcBussinessName() {
		return this.vcBussinessName;
	}

	public void setVcBussinessName(String vcBussinessName) {
		this.vcBussinessName = vcBussinessName;
	}

	@Column(name = "vcBussinessNote", length = 100)
	public String getVcBussinessNote() {
		return this.vcBussinessNote;
	}

	public void setVcBussinessNote(String vcBussinessNote) {
		this.vcBussinessNote = vcBussinessNote;
	}

	@Column(name = "vcBussinessPicPath")
	public String getVcBussinessPicPath() {
		return this.vcBussinessPicPath;
	}

	public void setVcBussinessPicPath(String vcBussinessPicPath) {
		this.vcBussinessPicPath = vcBussinessPicPath;
	}

}