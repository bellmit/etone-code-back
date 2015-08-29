package com.symbol.app.mantoeye.entity.business;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "ftbBlackListUsers")
public class BlackUser  implements java.io.Serializable{
	private long id;
	private long imsi;

	private long msisdn;
	private Date firstTime;
	private String describe;
	private Date lastTime;
	private Long lastFlush;
	private int overDay;
	private Long preMonth;
	
	private Double lastFlushKB;
	private Double lastFlushMB;
	private Double preMonthKB;
	private Double preMonthMB;
	
	Long forMB = Long.valueOf(1048576L);
	Long forKB = Long.valueOf(1024L);
	
	private String imei;
	private String strImsi;
	
	private String strFirstTime;
	private String strLastTime;
	
	public void calculateData() {
		long lFlush=(long)this.lastFlush * 100;
		long pFlush=(long)this.preMonth * 100;
		this.lastFlushMB = this.lastFlush == 0 ? 0.0
				: (lFlush/ this.forMB) / 100.0;
		this.preMonthMB = this.preMonth == 0 ? 0.0
				: (pFlush/ this.forMB) / 100.0;
	
		this.lastFlushKB = this.lastFlush == 0 ? 0.0
				: (lFlush / this.forKB) / 100.0;
		this.preMonthKB = this.preMonth == 0 ? 0.0
				: (pFlush / this.forKB) / 100.0;
		this.strImsi = this.imsi+"";
	}
	
	@Transient
	public String getStrFirstTime() {
		return strFirstTime;
	}
	public void setStrFirstTime(String strFirstTime) {
		this.strFirstTime = strFirstTime;
	}
	
	@Transient
	public String getStrLastTime() {
		return strLastTime;
	}
	public void setStrLastTime(String strLastTime) {
		this.strLastTime = strLastTime;
	}
	@Column(name = "nmImsi")
	public long getImsi() {
		return imsi;
	}
	public void setImsi(long imsi) {
		this.imsi = imsi;
	}
	
	@Column(name = "nmMsisdn")
	public long getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(long msisdn) {
		this.msisdn = msisdn;
	}
	
	@Column(name = "dtFirstTime")
	public Date getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}
	
	@Column(name = "vcUserAction")
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	@Column(name = "dtLastTime")
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	@Column(name = "intLastFlush")
	public Long getLastFlush() {
		return lastFlush;
	}
	public void setLastFlush(Long lastFlush) {
		this.lastFlush = lastFlush;
	}
	@Column(name = "intOverDate")
	public int getOverDay() {
		return overDay;
	}
	public void setOverDay(int overDay) {
		this.overDay = overDay;
	}
	@Column(name = "intLastMonthFlush")
	public Long getPreMonth() {
		return preMonth;
	}
	public void setPreMonth(Long preMonth) {
		this.preMonth = preMonth;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "nmBlackListUsersId", unique = true, nullable = false, precision = 5, scale = 0)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Transient
	public Double getLastFlushKB() {
		return lastFlushKB;
	}
	public void setLastFlushKB(Double lastFlushKB) {
		this.lastFlushKB = lastFlushKB;
	}
	@Transient
	public Double getLastFlushMB() {
		return lastFlushMB;
	}
	public void setLastFlushMB(Double lastFlushMB) {
		this.lastFlushMB = lastFlushMB;
	}
	@Transient
	public Double getPreMonthKB() {
		return preMonthKB;
	}
	public void setPreMonthKB(Double preMonthKB) {
		this.preMonthKB = preMonthKB;
	}
	@Transient
	public Double getPreMonthMB() {
		return preMonthMB;
	}
	public void setPreMonthMB(Double preMonthMB) {
		this.preMonthMB = preMonthMB;
	}
	@Transient
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	@Transient
	public String getStrImsi() {
		return strImsi;
	}
	public void setStrImsi(String strImsi) {
		this.strImsi = strImsi;
	}
}
