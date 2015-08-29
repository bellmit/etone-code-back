package com.symbol.app.mantoeye.entity.business;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * DtbBusinessCompany entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dtbBusinessCompany", schema = "DBA", catalog = "iqwrite")
public class DtbBusinessCompany implements java.io.Serializable {

	// Fields

	private Integer nmCompanyId;
	private String vcName;
	private String vcNote;
	// Constructors

	/** default constructor */
	public DtbBusinessCompany() {
	}

	/** full constructor */
	public DtbBusinessCompany(String vcName, String vcNote) {
		this.vcName = vcName;
		this.vcNote = vcNote;
	}

	// Property accessors
	@Id   
	@GeneratedValue
	@Column(name = "nmCompanyId", unique = true, nullable = false, precision = 5, scale = 0)
	public Integer getNmCompanyId() {
		return this.nmCompanyId;
	}

	public void setNmCompanyId(Integer nmCompanyId) {
		this.nmCompanyId = nmCompanyId;
	}

	@Column(name = "vcName", length = 100)
	public String getVcName() {
		return this.vcName;
	}

	public void setVcName(String vcName) {
		this.vcName = vcName;
	}

	@Column(name = "vcNote", length = 100)
	public String getVcNote() {
		return this.vcNote;
	}

	public void setVcNote(String vcNote) {
		this.vcNote = vcNote;
	}
}