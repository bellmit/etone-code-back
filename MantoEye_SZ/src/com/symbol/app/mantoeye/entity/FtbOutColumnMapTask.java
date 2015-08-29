package com.symbol.app.mantoeye.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * FtbOutColumnMapTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbOutColumnMapTask", schema = "DBA", catalog = "iqwrite")
public class FtbOutColumnMapTask implements java.io.Serializable {

	// Fields

	private Integer nmOutColumnMapTaskId;
	private FtbDataGetterTask ftbDataGetterTask;
	private FtbTableColumnMap ftbTableColumnMap;

	// Constructors

	/** default constructor */
	public FtbOutColumnMapTask() {
	}

	/** full constructor */
	public FtbOutColumnMapTask(FtbDataGetterTask ftbDataGetterTask,
			FtbTableColumnMap ftbTableColumnMap) {
		this.ftbDataGetterTask = ftbDataGetterTask;
		this.ftbTableColumnMap = ftbTableColumnMap;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmOutColumnMapTaskId", unique = true, nullable = false, precision = 8, scale = 0)
	public Integer getNmOutColumnMapTaskId() {
		return this.nmOutColumnMapTaskId;
	}

	public void setNmOutColumnMapTaskId(Integer nmOutColumnMapTaskId) {
		this.nmOutColumnMapTaskId = nmOutColumnMapTaskId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nmDataGetterTaskId", nullable = false)
	public FtbDataGetterTask getFtbDataGetterTask() {
		return this.ftbDataGetterTask;
	}

	public void setFtbDataGetterTask(FtbDataGetterTask ftbDataGetterTask) {
		this.ftbDataGetterTask = ftbDataGetterTask;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nmTableColumnMapId", nullable = false)
	public FtbTableColumnMap getFtbTableColumnMap() {
		return this.ftbTableColumnMap;
	}

	public void setFtbTableColumnMap(FtbTableColumnMap ftbTableColumnMap) {
		this.ftbTableColumnMap = ftbTableColumnMap;
	}

}