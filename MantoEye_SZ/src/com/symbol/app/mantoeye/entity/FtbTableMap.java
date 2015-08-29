package com.symbol.app.mantoeye.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FtbTableMap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbTableMap", schema = "DBA", catalog = "iqwrite")
public class FtbTableMap implements java.io.Serializable {

	// Fields

	private Short nmTableMapId;
	private String vcTableName;
	private String vcTableNickName;
	private Integer intViewFlag;
	private Integer intTableType;
	private Integer intBusinessType;
	private Integer intIsShow;
	private String vcDimension;

	// Constructors
	
	/** default constructor */
	public FtbTableMap() {
	}

	/** full constructor */
	public FtbTableMap(String vcTableName, String vcTableNickName,
			Integer intViewFlag, Integer intTableType, Integer intBusinessType,
			Integer intIsShow) {
		this.vcTableName = vcTableName;
		this.vcTableNickName = vcTableNickName;
		this.intViewFlag = intViewFlag;
		this.intTableType = intTableType;
		this.intBusinessType = intBusinessType;
		this.intIsShow = intIsShow;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmTableMapId", unique = true, nullable = false, precision = 3, scale = 0)
	public Short getNmTableMapId() {
		return this.nmTableMapId;
	}

	public void setNmTableMapId(Short nmTableMapId) {
		this.nmTableMapId = nmTableMapId;
	}

	@Column(name = "vcTableName", length = 50)
	public String getVcTableName() {
		return this.vcTableName;
	}

	public void setVcTableName(String vcTableName) {
		this.vcTableName = vcTableName;
	}

	@Column(name = "vcTableNickName", length = 100)
	public String getVcTableNickName() {
		return this.vcTableNickName;
	}

	public void setVcTableNickName(String vcTableNickName) {
		this.vcTableNickName = vcTableNickName;
	}

	@Column(name = "intViewFlag")
	public Integer getIntViewFlag() {
		return this.intViewFlag;
	}

	public void setIntViewFlag(Integer intViewFlag) {
		this.intViewFlag = intViewFlag;
	}

	@Column(name = "intTableType")
	public Integer getIntTableType() {
		return this.intTableType;
	}

	public void setIntTableType(Integer intTableType) {
		this.intTableType = intTableType;
	}

	@Column(name = "intBusinessType")
	public Integer getIntBusinessType() {
		return this.intBusinessType;
	}

	public void setIntBusinessType(Integer intBusinessType) {
		this.intBusinessType = intBusinessType;
	}

	@Column(name = "intIsShow")
	public Integer getIntIsShow() {
		return this.intIsShow;
	}

	public void setIntIsShow(Integer intIsShow) {
		this.intIsShow = intIsShow;
	}
	@Column(name = "vcDimension")
	public String getVcDimension() {
		return vcDimension;
	}

	public void setVcDimension(String vcDimension) {
		this.vcDimension = vcDimension;
	}

}