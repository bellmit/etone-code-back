package com.symbol.app.mantoeye.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ftbArea", schema = "DBA", catalog = "iqwrite")
public class FtbArea implements java.io.Serializable {
	private Long nmAreaId;
	private String vcAreaName;
	private Date dtUpdateTime;
	private Integer intType;
	private String vcNote;

	/** default constructor */
	public FtbArea() {
	}

	@Id
	@GeneratedValue
	@Column(name = "nmAreaId", unique = true, nullable = false, precision = 3, scale = 0)
	public Long getNmAreaId() {
		return nmAreaId;
	}

	public void setNmAreaId(Long nmAreaId) {
		this.nmAreaId = nmAreaId;
	}

	@Column(name = "dtUpdateTime")
	@Temporal(TemporalType.DATE)
	public Date getDtUpdateTime() {
		return dtUpdateTime;
	}

	public void setDtUpdateTime(Date dtUpdateTime) {
		this.dtUpdateTime = dtUpdateTime;
	}

	@Column(name = "vcAreaName")
	public String getVcAreaName() {
		return vcAreaName;
	}

	public void setVcAreaName(String vcAreaName) {
		this.vcAreaName = vcAreaName;
	}

	@Column(name = "intType")
	public Integer getIntType() {
		return intType;
	}

	public void setIntType(Integer intType) {
		this.intType = intType;
	}

	@Column(name = "vcNote")
	public String getVcNote() {
		return vcNote;
	}

	public void setVcNote(String vcNote) {
		this.vcNote = vcNote;
	}

}