package com.symbol.app.mantoeye.entity.business;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * FtbSrcDataResult entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbSrcDataResult")
public class FtbSrcDataResult implements java.io.Serializable {

	// Fields

	private Long nmSrcDataResultId;
	private String vcFilePath;
	
	// Constructors

	/** default constructor */
	public FtbSrcDataResult() {
	}

	/** minimal constructor */
	public FtbSrcDataResult(String vcFilePath) {
		this.vcFilePath = vcFilePath;
	}


	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmSrcDataResultId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmSrcDataResultId() {
		return this.nmSrcDataResultId;
	}

	public void setNmSrcDataResultId(Long nmSrcDataResultId) {
		this.nmSrcDataResultId = nmSrcDataResultId;
	}

	@Column(name = "vcFilePath", nullable = false)
	public String getVcFilePath() {
		return this.vcFilePath;
	}

	public void setVcFilePath(String vcFilePath) {
		this.vcFilePath = vcFilePath;
	}

	

}