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
 * FtbStatDayMainRuleFlush entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbStatDayMainRuleFlush")
public class FtbStatDayMainRuleFlush implements java.io.Serializable {

	// Fields

	private Long nmMainRuleFlushId;
	private FtbBusinessMainVetor ftbBusinessMainVetor;
	private Date dtStatTime;
	private Long nmFlush;
	private Long nmConflictCount;
	private Float flFlushRate;
	private Float flConflictCountRate;
	private Integer intYear;
	private Integer intMonth;
	private Integer intDay;
	private Integer intWeek;

	// Constructors

	/** default constructor */
	public FtbStatDayMainRuleFlush() {
	}

	/** full constructor */
	public FtbStatDayMainRuleFlush(FtbBusinessMainVetor ftbBusinessMainVetor,
			Date dtStatTime, Integer intFlush, Integer intConflictCount,
			Float flFlushRate, Float flConflictCountRate, Integer intYear,
			Integer intMonth, Integer intDay, Integer intWeek) {
		this.ftbBusinessMainVetor = ftbBusinessMainVetor;
		this.dtStatTime = dtStatTime;
		this.nmFlush = nmFlush;
		this.nmConflictCount = nmConflictCount;
		this.flFlushRate = flFlushRate;
		this.flConflictCountRate = flConflictCountRate;
		this.intYear = intYear;
		this.intMonth = intMonth;
		this.intDay = intDay;
		this.intWeek = intWeek;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmMainRuleFlushId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmMainRuleFlushId() {
		return this.nmMainRuleFlushId;
	}

	public void setNmMainRuleFlushId(Long nmMainRuleFlushId) {
		this.nmMainRuleFlushId = nmMainRuleFlushId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "nmBusinessMainVetorId")
	public FtbBusinessMainVetor getFtbBusinessMainVetor() {
		return this.ftbBusinessMainVetor;
	}

	public void setFtbBusinessMainVetor(
			FtbBusinessMainVetor ftbBusinessMainVetor) {
		this.ftbBusinessMainVetor = ftbBusinessMainVetor;
	}

	@Column(name = "dtStatTime")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDtStatTime() {
		return this.dtStatTime;
	}

	public void setDtStatTime(Date dtStatTime) {
		this.dtStatTime = dtStatTime;
	}

	@Column(name = "flFlushRate", precision = 7, scale = 0)
	public Float getFlFlushRate() {
		return this.flFlushRate;
	}

	public void setFlFlushRate(Float flFlushRate) {
		this.flFlushRate = flFlushRate;
	}

	@Column(name = "flConflictCountRate", precision = 7, scale = 0)
	public Float getFlConflictCountRate() {
		return this.flConflictCountRate;
	}

	public void setFlConflictCountRate(Float flConflictCountRate) {
		this.flConflictCountRate = flConflictCountRate;
	}

	@Column(name = "intYear")
	public Integer getIntYear() {
		return this.intYear;
	}

	public void setIntYear(Integer intYear) {
		this.intYear = intYear;
	}

	@Column(name = "intMonth")
	public Integer getIntMonth() {
		return this.intMonth;
	}

	public void setIntMonth(Integer intMonth) {
		this.intMonth = intMonth;
	}

	@Column(name = "intDay")
	public Integer getIntDay() {
		return this.intDay;
	}

	public void setIntDay(Integer intDay) {
		this.intDay = intDay;
	}

	@Column(name = "intWeek")
	public Integer getIntWeek() {
		return this.intWeek;
	}

	public void setIntWeek(Integer intWeek) {
		this.intWeek = intWeek;
	}

	@Column(name = "nmFlush")
	public Long getNmFlush() {
		return nmFlush;
	}

	public void setNmFlush(Long nmFlush) {
		this.nmFlush = nmFlush;
	}

	@Column(name = "nmConflictCount")
	public Long getNmConflictCount() {
		return nmConflictCount;
	}

	public void setNmConflictCount(Long nmConflictCount) {
		this.nmConflictCount = nmConflictCount;
	}

}