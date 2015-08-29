package com.symbol.app.mantoeye.entity.business;

import java.math.BigDecimal;
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

import com.symbol.app.mantoeye.entity.FtbDataGetterTask;

/**
 * FtbDataGetterBusiness entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbDataGetterBusiness", schema = "DBA", catalog = "iqwrite")
public class FtbDataGetterBusiness implements java.io.Serializable {

	// Fields

	private Long nmDataGetterBusinessId;
	private FtbDataGetterTask ftbDataGetterTask;
	private BigDecimal nmImsi;
	private BigDecimal nmMsisdn;
	private String vcServerIp;
	private Integer intPort;
	private String vcUrl;
	private String vcUserAgent;
	private String vcApn;
	private Date dtTime;
	private Integer nmBussinessId;
	private Integer intType;
	private Long nmUpFlush;
	private Long nmDownFlush;

	// Constructors

	/** default constructor */
	public FtbDataGetterBusiness() {
	}

	/** full constructor */
	public FtbDataGetterBusiness(FtbDataGetterTask ftbDataGetterTask,
			BigDecimal nmImsi, BigDecimal nmMsisdn, String vcServerIp,
			Integer intPort, String vcUrl, String vcUserAgent, String vcApn,
			Date dtTime, Integer nmBussinessId, Integer intType,
			Long nmUpFlush, Long nmDownFlush) {
		this.ftbDataGetterTask = ftbDataGetterTask;
		this.nmImsi = nmImsi;
		this.nmMsisdn = nmMsisdn;
		this.vcServerIp = vcServerIp;
		this.intPort = intPort;
		this.vcUrl = vcUrl;
		this.vcUserAgent = vcUserAgent;
		this.vcApn = vcApn;
		this.dtTime = dtTime;
		this.nmBussinessId = nmBussinessId;
		this.intType = intType;
		this.nmUpFlush = nmUpFlush;
		this.nmDownFlush = nmDownFlush;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmDataGetterBusinessId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmDataGetterBusinessId() {
		return this.nmDataGetterBusinessId;
	}

	public void setNmDataGetterBusinessId(Long nmDataGetterBusinessId) {
		this.nmDataGetterBusinessId = nmDataGetterBusinessId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nmDataGetterTaskId")
	public FtbDataGetterTask getFtbDataGetterTask() {
		return this.ftbDataGetterTask;
	}

	public void setFtbDataGetterTask(FtbDataGetterTask ftbDataGetterTask) {
		this.ftbDataGetterTask = ftbDataGetterTask;
	}

	@Column(name = "nmImsi", precision = 20, scale = 0)
	public BigDecimal getNmImsi() {
		return this.nmImsi;
	}

	public void setNmImsi(BigDecimal nmImsi) {
		this.nmImsi = nmImsi;
	}

	@Column(name = "nmMsisdn", precision = 20, scale = 0)
	public BigDecimal getNmMsisdn() {
		return this.nmMsisdn;
	}

	public void setNmMsisdn(BigDecimal nmMsisdn) {
		this.nmMsisdn = nmMsisdn;
	}

	@Column(name = "vcServerIp", length = 20)
	public String getVcServerIp() {
		return this.vcServerIp;
	}

	public void setVcServerIp(String vcServerIp) {
		this.vcServerIp = vcServerIp;
	}

	@Column(name = "intPort")
	public Integer getIntPort() {
		return this.intPort;
	}

	public void setIntPort(Integer intPort) {
		this.intPort = intPort;
	}

	@Column(name = "vcUrl", length = 1000)
	public String getVcUrl() {
		return this.vcUrl;
	}

	public void setVcUrl(String vcUrl) {
		this.vcUrl = vcUrl;
	}

	@Column(name = "vcUserAgent", length = 50)
	public String getVcUserAgent() {
		return this.vcUserAgent;
	}

	public void setVcUserAgent(String vcUserAgent) {
		this.vcUserAgent = vcUserAgent;
	}

	@Column(name = "vcApn", length = 50)
	public String getVcApn() {
		return this.vcApn;
	}

	public void setVcApn(String vcApn) {
		this.vcApn = vcApn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtTime", length = 23)
	public Date getDtTime() {
		return this.dtTime;
	}

	public void setDtTime(Date dtTime) {
		this.dtTime = dtTime;
	}

	@Column(name = "nmBussinessId", precision = 6, scale = 0)
	public Integer getNmBussinessId() {
		return this.nmBussinessId;
	}

	public void setNmBussinessId(Integer nmBussinessId) {
		this.nmBussinessId = nmBussinessId;
	}

	@Column(name = "intType")
	public Integer getIntType() {
		return this.intType;
	}

	public void setIntType(Integer intType) {
		this.intType = intType;
	}

	@Column(name = "nmUpFlush", precision = 10, scale = 0)
	public Long getNmUpFlush() {
		return this.nmUpFlush;
	}

	public void setNmUpFlush(Long nmUpFlush) {
		this.nmUpFlush = nmUpFlush;
	}

	@Column(name = "nmDownFlush", precision = 10, scale = 0)
	public Long getNmDownFlush() {
		return this.nmDownFlush;
	}

	public void setNmDownFlush(Long nmDownFlush) {
		this.nmDownFlush = nmDownFlush;
	}

}