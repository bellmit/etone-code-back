package com.symbol.app.mantoeye.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FtbTaskForVetor entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbTaskForVetor", schema = "DBA", catalog = "iqwrite")
public class FtbTaskForVetor implements java.io.Serializable {

	// Fields

	private Long nmTaskForVetorId;
	private String vcServerIp;
	private Integer intPort;
	private String vcUrl;
	private String vcUserAgent;
	private String vcApn;

	// Constructors

	/** default constructor */
	public FtbTaskForVetor() {
	}

	/** full constructor */
	public FtbTaskForVetor(String vcServerIp, Integer intPort, String vcUrl,
			String vcUserAgent, String vcApn) {
		this.vcServerIp = vcServerIp;
		this.intPort = intPort;
		this.vcUrl = vcUrl;
		this.vcUserAgent = vcUserAgent;
		this.vcApn = vcApn;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmTaskForVetorId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmTaskForVetorId() {
		return this.nmTaskForVetorId;
	}

	public void setNmTaskForVetorId(Long nmTaskForVetorId) {
		this.nmTaskForVetorId = nmTaskForVetorId;
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

}