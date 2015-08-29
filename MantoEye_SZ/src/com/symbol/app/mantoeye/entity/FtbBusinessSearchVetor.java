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
@Table(name = "ftbBusinessSearchVetor", schema = "DBA", catalog = "iqwrite")
public class FtbBusinessSearchVetor implements java.io.Serializable {
	private Long nmBussinessSearchId;
	private String vcTitleType;
	private String vcTitle;
	private String vcKeyword;

	/** default constructor */
	public FtbBusinessSearchVetor() {
	}

	@Id
	@GeneratedValue
	@Column(name = "nmBussinessSearchId", unique = true, nullable = false, precision = 3, scale = 0)
	public Long getNmBussinessSearchId() {
		return nmBussinessSearchId;
	}

	public void setNmBussinessSearchId(Long nmBussinessSearchId) {
		this.nmBussinessSearchId = nmBussinessSearchId;
	}

	@Column(name = "vcTitleType")
	public String getVcTitleType() {
		return vcTitleType;
	}

	public void setVcTitleType(String vcTitleType) {
		this.vcTitleType = vcTitleType;
	}

	@Column(name = "vcTitle")
	public String getVcTitle() {
		return vcTitle;
	}

	public void setVcTitle(String vcTitle) {
		this.vcTitle = vcTitle;
	}

	@Column(name = "vcKeyword")
	public String getVcKeyword() {
		return vcKeyword;
	}

	public void setVcKeyword(String vcKeyword) {
		this.vcKeyword = vcKeyword;
	}
}