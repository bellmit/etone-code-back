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
 * FtbStatDayAssistRule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbStatDayAssistRule")
public class FtbStatDayAssistRule implements java.io.Serializable {

	// Fields

	private Long nmAssistRuleId;
	private FtbMainAssistVetor ftbMainAssistVetor;
	private Date dtStatTime;
	private Long nmUnMatchCount;
	private Integer intYear;
	private Integer intMonth;
	private Integer intDay;
	private Integer intWeek;

	// Constructors

	/** default constructor */
	public FtbStatDayAssistRule() {
	}

	/** full constructor */
	public FtbStatDayAssistRule(FtbMainAssistVetor ftbMainAssistVetor,
			Date dtStatTime, Integer intUnMatchCount, Integer intYear,
			Integer intMonth, Integer intDay, Integer intWeek) {
		this.ftbMainAssistVetor = ftbMainAssistVetor;
		this.dtStatTime = dtStatTime;
		this.nmUnMatchCount = nmUnMatchCount;
		this.intYear = intYear;
		this.intMonth = intMonth;
		this.intDay = intDay;
		this.intWeek = intWeek;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmAssistRuleId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmAssistRuleId() {
		return this.nmAssistRuleId;
	}

	public void setNmAssistRuleId(Long nmAssistRuleId) {
		this.nmAssistRuleId = nmAssistRuleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nmMainAssistVetorId")
	public FtbMainAssistVetor getFtbMainAssistVetor() {
		return this.ftbMainAssistVetor;
	}

	public void setFtbMainAssistVetor(FtbMainAssistVetor ftbMainAssistVetor) {
		this.ftbMainAssistVetor = ftbMainAssistVetor;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtStatTime", length = 23)
	public Date getDtStatTime() {
		return this.dtStatTime;
	}

	public void setDtStatTime(Date dtStatTime) {
		this.dtStatTime = dtStatTime;
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

	@Column(name = "nmUnMatchCount")
	public Long getNmUnMatchCount() {
		return nmUnMatchCount;
	}

	public void setNmUnMatchCount(Long nmUnMatchCount) {
		this.nmUnMatchCount = nmUnMatchCount;
	}

}