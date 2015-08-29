package com.symbol.app.mantoeye.entity.business;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * VstatDayMainRuleFlushId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class VstatDayMainRuleFlushId implements java.io.Serializable {

	// Fields

	private Integer nmBusinessMainVetorId;
	private Date dtStatTime;
	private Long nmFlush;
	private Long lnmFlush;
	private Double flushRate;
	private Double conflictCountRate;
	private Long nmConflictCount;
	private Long lnmConflictCount;
	private Integer intWeek;

	// Constructors

	/** default constructor */
	public VstatDayMainRuleFlushId() {
	}

	/** minimal constructor */
	public VstatDayMainRuleFlushId(Long lnmFlush, Long lnmConflictCount) {
		this.lnmFlush = lnmFlush;
		this.lnmConflictCount = lnmConflictCount;
	}

	/** full constructor */
	public VstatDayMainRuleFlushId(Integer nmBusinessMainVetorId,
			Date dtStatTime, Long nmFlush, Long lnmFlush,
			Double flushRate, Double conflictCountRate, Long nmConflictCount,
			Long lnmConflictCount, Integer intWeek) {
		this.nmBusinessMainVetorId = nmBusinessMainVetorId;
		this.dtStatTime = dtStatTime;
		this.nmFlush = nmFlush;
		this.lnmFlush = lnmFlush;
		this.flushRate = flushRate;
		this.conflictCountRate = conflictCountRate;
		this.nmConflictCount = nmConflictCount;
		this.lnmConflictCount = lnmConflictCount;
		this.intWeek = intWeek;
	}

	// Property accessors

	@Column(name = "nmBusinessMainVetorId", precision = 6, scale = 0)
	public Integer getNmBusinessMainVetorId() {
		return this.nmBusinessMainVetorId;
	}

	public void setNmBusinessMainVetorId(Integer nmBusinessMainVetorId) {
		this.nmBusinessMainVetorId = nmBusinessMainVetorId;
	}

	@Column(name = "dtStatTime", length = 23)
	public Date getDtStatTime() {
		return this.dtStatTime;
	}

	public void setDtStatTime(Date dtStatTime) {
		this.dtStatTime = dtStatTime;
	}

	@Column(name = "nmFlush", precision = 15, scale = 0)
	public Long getNmFlush() {
		return this.nmFlush;
	}

	public void setNmFlush(Long nmFlush) {
		this.nmFlush = nmFlush;
	}

	@Column(name = "LnmFlush", nullable = false, precision = 15, scale = 0)
	public Long getLnmFlush() {
		return this.lnmFlush;
	}

	public void setLnmFlush(Long lnmFlush) {
		this.lnmFlush = lnmFlush;
	}

	@Column(name = "flushRate", precision = 32, scale = 16)
	public Double getFlushRate() {
		return this.flushRate;
	}

	public void setFlushRate(Double flushRate) {
		this.flushRate = flushRate;
	}

	@Column(name = "ConflictCountRate", precision = 32, scale = 16)
	public Double getConflictCountRate() {
		return this.conflictCountRate;
	}

	public void setConflictCountRate(Double conflictCountRate) {
		this.conflictCountRate = conflictCountRate;
	}

	@Column(name = "nmConflictCount", precision = 15, scale = 0)
	public Long getNmConflictCount() {
		return this.nmConflictCount;
	}

	public void setNmConflictCount(Long nmConflictCount) {
		this.nmConflictCount = nmConflictCount;
	}

	@Column(name = "LnmConflictCount", nullable = false, precision = 15, scale = 0)
	public Long getLnmConflictCount() {
		return this.lnmConflictCount;
	}

	public void setLnmConflictCount(Long lnmConflictCount) {
		this.lnmConflictCount = lnmConflictCount;
	}

	@Column(name = "intWeek")
	public Integer getIntWeek() {
		return this.intWeek;
	}

	public void setIntWeek(Integer intWeek) {
		this.intWeek = intWeek;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VstatDayMainRuleFlushId))
			return false;
		VstatDayMainRuleFlushId castOther = (VstatDayMainRuleFlushId) other;

		return ((this.getNmBusinessMainVetorId() == castOther
				.getNmBusinessMainVetorId()) || (this
				.getNmBusinessMainVetorId() != null
				&& castOther.getNmBusinessMainVetorId() != null && this
				.getNmBusinessMainVetorId().equals(
						castOther.getNmBusinessMainVetorId())))
				&& ((this.getDtStatTime() == castOther.getDtStatTime()) || (this
						.getDtStatTime() != null
						&& castOther.getDtStatTime() != null && this
						.getDtStatTime().equals(castOther.getDtStatTime())))
				&& ((this.getNmFlush() == castOther.getNmFlush()) || (this
						.getNmFlush() != null
						&& castOther.getNmFlush() != null && this.getNmFlush()
						.equals(castOther.getNmFlush())))
				&& ((this.getLnmFlush() == castOther.getLnmFlush()) || (this
						.getLnmFlush() != null
						&& castOther.getLnmFlush() != null && this
						.getLnmFlush().equals(castOther.getLnmFlush())))
				&& ((this.getFlushRate() == castOther.getFlushRate()) || (this
						.getFlushRate() != null
						&& castOther.getFlushRate() != null && this
						.getFlushRate().equals(castOther.getFlushRate())))
				&& ((this.getConflictCountRate() == castOther
						.getConflictCountRate()) || (this
						.getConflictCountRate() != null
						&& castOther.getConflictCountRate() != null && this
						.getConflictCountRate().equals(
								castOther.getConflictCountRate())))
				&& ((this.getNmConflictCount() == castOther
						.getNmConflictCount()) || (this.getNmConflictCount() != null
						&& castOther.getNmConflictCount() != null && this
						.getNmConflictCount().equals(
								castOther.getNmConflictCount())))
				&& ((this.getLnmConflictCount() == castOther
						.getLnmConflictCount()) || (this.getLnmConflictCount() != null
						&& castOther.getLnmConflictCount() != null && this
						.getLnmConflictCount().equals(
								castOther.getLnmConflictCount())))
				&& ((this.getIntWeek() == castOther.getIntWeek()) || (this
						.getIntWeek() != null
						&& castOther.getIntWeek() != null && this.getIntWeek()
						.equals(castOther.getIntWeek())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getNmBusinessMainVetorId() == null ? 0 : this
						.getNmBusinessMainVetorId().hashCode());
		result = 37
				* result
				+ (getDtStatTime() == null ? 0 : this.getDtStatTime()
						.hashCode());
		result = 37 * result
				+ (getNmFlush() == null ? 0 : this.getNmFlush().hashCode());
		result = 37 * result
				+ (getLnmFlush() == null ? 0 : this.getLnmFlush().hashCode());
		result = 37 * result
				+ (getFlushRate() == null ? 0 : this.getFlushRate().hashCode());
		result = 37
				* result
				+ (getConflictCountRate() == null ? 0 : this
						.getConflictCountRate().hashCode());
		result = 37
				* result
				+ (getNmConflictCount() == null ? 0 : this.getNmConflictCount()
						.hashCode());
		result = 37
				* result
				+ (getLnmConflictCount() == null ? 0 : this
						.getLnmConflictCount().hashCode());
		result = 37 * result
				+ (getIntWeek() == null ? 0 : this.getIntWeek().hashCode());
		return result;
	}

}