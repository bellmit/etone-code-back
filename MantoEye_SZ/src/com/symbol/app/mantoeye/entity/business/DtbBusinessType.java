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
 * DtbBusinessType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dtbBusinessType", schema = "DBA", catalog = "iqwrite")
public class DtbBusinessType implements java.io.Serializable {

	// Fields

	private Integer nmBussinessTypeId;
	private String vcBussinessTypeName;
	private String vcNote;
	
	// Constructors

	/** default constructor */
	public DtbBusinessType() {
	}

	/** full constructor */
	public DtbBusinessType(String vcBussinessTypeName, String vcNote) {
		this.vcBussinessTypeName = vcBussinessTypeName;
		this.vcNote = vcNote;
	}

	// Property accessors
	@Id   
	@GeneratedValue 
	@Column(name = "nmBussinessTypeId", unique = true, nullable = false, precision = 3, scale = 0)
	public Integer getNmBussinessTypeId() {
		return this.nmBussinessTypeId;
	}

	public void setNmBussinessTypeId(Integer nmBussinessTypeId) {
		this.nmBussinessTypeId = nmBussinessTypeId;
	}

	@Column(name = "vcBussinessTypeName", length = 100)
	public String getVcBussinessTypeName() {
		return this.vcBussinessTypeName;
	}

	public void setVcBussinessTypeName(String vcBussinessTypeName) {
		this.vcBussinessTypeName = vcBussinessTypeName;
	}

	@Column(name = "vcNote", length = 100)
	public String getVcNote() {
		return this.vcNote;
	}

	public void setVcNote(String vcNote) {
		this.vcNote = vcNote;
	}

}