package com.symbol.app.mantoeye.entity.business;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * FtbStatDayBusFlushOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbStatDayBusFlushOrder", schema = "DBA", catalog = "iqwrite")
public class FtbStatDayBusFlushOrder implements java.io.Serializable {

	// Fields

	private Long nmBusFlushOrderId;
	private String vcServerIp;
	private Integer intPort;
	private Long nmFlush;
	private Integer intOrder;
	private Date dtStatTime;
	private String vcGuessBusiness;
	private String vcMainBussiness;
	private String isFind;
	
//	private String businessName;//业务名称

	// Constructors

	/** default constructor */
	public FtbStatDayBusFlushOrder() {
	}

	/** full constructor */
	public FtbStatDayBusFlushOrder(String vcServerIp, Integer intPort,
			Long nmFlush, Integer intOrder, Date dtStatTime,
			String vcGuessBusiness, String vcMainBussiness, String isFind) {
		this.vcServerIp = vcServerIp;
		this.intPort = intPort;
		this.nmFlush = nmFlush;
		this.intOrder = intOrder;
		this.dtStatTime = dtStatTime;
		this.vcGuessBusiness = vcGuessBusiness;
		this.vcMainBussiness = vcMainBussiness;
		this.isFind = isFind;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmBusFlushOrderId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmBusFlushOrderId() {
		return this.nmBusFlushOrderId;
	}

	public void setNmBusFlushOrderId(Long nmBusFlushOrderId) {
		this.nmBusFlushOrderId = nmBusFlushOrderId;
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

	@Column(name = "nmFlush", nullable = false)
	public Long getNmFlush() {
		return this.nmFlush;
	}

	public void setNmFlush(Long nmFlush) {
		this.nmFlush = nmFlush;
	}

	@Column(name = "intOrder", nullable = false)
	public Integer getIntOrder() {
		return this.intOrder;
	}

	public void setIntOrder(Integer intOrder) {
		this.intOrder = intOrder;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtStatTime", nullable = false, length = 23)
	public Date getDtStatTime() {
		return this.dtStatTime;
	}

	public void setDtStatTime(Date dtStatTime) {
		this.dtStatTime = dtStatTime;
	}

	@Column(name = "vcGuessBusiness", nullable = false, length = 100)
	public String getVcGuessBusiness() {
		return this.vcGuessBusiness;
	}

	public void setVcGuessBusiness(String vcGuessBusiness) {
		this.vcGuessBusiness = vcGuessBusiness;
	}

	@Column(name = "vcMainBussiness", nullable = false, length = 100)
	public String getVcMainBussiness() {
		return this.vcMainBussiness;
	}

	public void setVcMainBussiness(String vcMainBussiness) {
		this.vcMainBussiness = vcMainBussiness;
	}

	@Column(name = "isFind", nullable = false, length = 1)
	public String getIsFind() {
		return this.isFind;
	}

	public void setIsFind(String isFind) {
		this.isFind = isFind;
	}
//	@Transient
//	public String getBusinessName() {
//		return businessName;
//	}
//
//	public void setBusinessName(String businessName) {
//		this.businessName = businessName;
//	}

}