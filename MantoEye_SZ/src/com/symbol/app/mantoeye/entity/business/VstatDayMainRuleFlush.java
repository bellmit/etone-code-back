package com.symbol.app.mantoeye.entity.business;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * VstatDayMainRuleFlush entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "vStatDayMainRuleFlush", schema = "DBA", catalog = "iqwrite")
public class VstatDayMainRuleFlush implements java.io.Serializable {

	// Fields

	private VstatDayMainRuleFlushId id;

	// Constructors

	/** default constructor */
	public VstatDayMainRuleFlush() {
	}

	/** full constructor */
	public VstatDayMainRuleFlush(VstatDayMainRuleFlushId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "nmBusinessMainVetorId", column = @Column(name = "nmBusinessMainVetorId", precision = 6, scale = 0)),
			@AttributeOverride(name = "dtStatTime", column = @Column(name = "dtStatTime", length = 23)),
			@AttributeOverride(name = "nmFlush", column = @Column(name = "nmFlush", precision = 15, scale = 0)),
			@AttributeOverride(name = "lnmFlush", column = @Column(name = "LnmFlush", nullable = false, precision = 15, scale = 0)),
			@AttributeOverride(name = "flushRate", column = @Column(name = "flushRate", precision = 32, scale = 16)),
			@AttributeOverride(name = "conflictCountRate", column = @Column(name = "ConflictCountRate", precision = 32, scale = 16)),
			@AttributeOverride(name = "nmConflictCount", column = @Column(name = "nmConflictCount", precision = 15, scale = 0)),
			@AttributeOverride(name = "lnmConflictCount", column = @Column(name = "LnmConflictCount", nullable = false, precision = 15, scale = 0)),
			@AttributeOverride(name = "intWeek", column = @Column(name = "intWeek")) })
	public VstatDayMainRuleFlushId getId() {
		return this.id;
	}

	public void setId(VstatDayMainRuleFlushId id) {
		this.id = id;
	}

}