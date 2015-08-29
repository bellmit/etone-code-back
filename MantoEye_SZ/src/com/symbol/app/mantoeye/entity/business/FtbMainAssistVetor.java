package com.symbol.app.mantoeye.entity.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * FtbMainAssistVetor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbMainAssistVetor", schema = "DBA", catalog = "iqwrite")
public class FtbMainAssistVetor implements java.io.Serializable {

	// Fields

	private Integer nmMainAssistVetorId;
	private FtbBusinessAssistVetor ftbBusinessAssistVetor;
	private FtbBusinessMainVetor ftbBusinessMainVetor;

	// Constructors

	/** default constructor */
	public FtbMainAssistVetor() {
	}

	/** full constructor */
	public FtbMainAssistVetor(FtbBusinessAssistVetor ftbBusinessAssistVetor,
			FtbBusinessMainVetor ftbBusinessMainVetor) {
		this.ftbBusinessAssistVetor = ftbBusinessAssistVetor;
		this.ftbBusinessMainVetor = ftbBusinessMainVetor;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmMainAssistVetorId", unique = true, nullable = false, precision = 6, scale = 0)
	public Integer getNmMainAssistVetorId() {
		return this.nmMainAssistVetorId;
	}

	public void setNmMainAssistVetorId(Integer nmMainAssistVetorId) {
		this.nmMainAssistVetorId = nmMainAssistVetorId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nmBusinessAssistVetorId")
	public FtbBusinessAssistVetor getFtbBusinessAssistVetor() {
		return this.ftbBusinessAssistVetor;
	}

	public void setFtbBusinessAssistVetor(
			FtbBusinessAssistVetor ftbBusinessAssistVetor) {
		this.ftbBusinessAssistVetor = ftbBusinessAssistVetor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nmBusinessMainVetorId")
	public FtbBusinessMainVetor getFtbBusinessMainVetor() {
		return this.ftbBusinessMainVetor;
	}

	public void setFtbBusinessMainVetor(
			FtbBusinessMainVetor ftbBusinessMainVetor) {
		this.ftbBusinessMainVetor = ftbBusinessMainVetor;
	}

}