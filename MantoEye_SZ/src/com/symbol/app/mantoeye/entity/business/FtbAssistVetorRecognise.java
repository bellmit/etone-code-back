package com.symbol.app.mantoeye.entity.business;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * FtbAssistVetorRecognise entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbAssistVetorRecognise")
public class FtbAssistVetorRecognise implements java.io.Serializable {

	// Fields

	private Long nmAssistVetorRecogniseId;
	private DtbBusinessSite dtbBusinessSite;
	private String vcServerIp;
	private Integer intPort;
	private String vcUrl;
	private String vcUserAgent;
	private String vcApn;
	private Date dtFirstTime;
	private Date dtLastTime;

	// Constructors

	/** default constructor */
	public FtbAssistVetorRecognise() {
	}

	/** full constructor */
	public FtbAssistVetorRecognise(DtbBusinessSite dtbBusinessSite,
			String vcServerIp, Integer intPort, String vcUrl,
			String vcUserAgent, String vcApn, Date dtFirstTime,
			Date dtLastTime) {
		this.dtbBusinessSite = dtbBusinessSite;
		this.vcServerIp = vcServerIp;
		this.intPort = intPort;
		this.vcUrl = vcUrl;
		this.vcUserAgent = vcUserAgent;
		this.vcApn = vcApn;
		this.dtFirstTime = dtFirstTime;
		this.dtLastTime = dtLastTime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmAssistVetorRecogniseId", unique = true, nullable = false, precision = 20, scale = 0)
	public Long getNmAssistVetorRecogniseId() {
		return this.nmAssistVetorRecogniseId;
	}

	public void setNmAssistVetorRecogniseId(Long nmAssistVetorRecogniseId) {
		this.nmAssistVetorRecogniseId = nmAssistVetorRecogniseId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nmBussinessId", nullable = false)
	public DtbBusinessSite getDtbBusinessSite() {
		return this.dtbBusinessSite;
	}

	public void setDtbBusinessSite(DtbBusinessSite dtbBusinessSite) {
		this.dtbBusinessSite = dtbBusinessSite;
	}

	@Column(name = "vcServerIp", nullable = false, length = 20)
	public String getVcServerIp() {
		return this.vcServerIp;
	}

	public void setVcServerIp(String vcServerIp) {
		this.vcServerIp = vcServerIp;
	}

	@Column(name = "intPort", nullable = false)
	public Integer getIntPort() {
		return this.intPort;
	}

	public void setIntPort(Integer intPort) {
		this.intPort = intPort;
	}

	@Column(name = "vcUrl", nullable = false, length = 100)
	public String getVcUrl() {
		return this.vcUrl;
	}

	public void setVcUrl(String vcUrl) {
		this.vcUrl = vcUrl;
	}

	@Column(name = "vcUserAgent", nullable = false, length = 50)
	public String getVcUserAgent() {
		return this.vcUserAgent;
	}

	public void setVcUserAgent(String vcUserAgent) {
		this.vcUserAgent = vcUserAgent;
	}

	@Column(name = "vcApn", nullable = false, length = 50)
	public String getVcApn() {
		return this.vcApn;
	}

	public void setVcApn(String vcApn) {
		this.vcApn = vcApn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtFirstTime")
	public Date getDtFirstTime() {
		return this.dtFirstTime;
	}

	public void setDtFirstTime(Date dtFirstTime) {
		this.dtFirstTime = dtFirstTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtLastTime")
	public Date getDtLastTime() {
		return this.dtLastTime;
	}

	public void setDtLastTime(Date dtLastTime) {
		this.dtLastTime = dtLastTime;
	}

}