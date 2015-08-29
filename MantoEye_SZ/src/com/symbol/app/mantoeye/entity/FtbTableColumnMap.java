package com.symbol.app.mantoeye.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.symbol.app.mantoeye.entity.business.FtbBusinessMainVetor;

/**
 * FtbTableColumnMap entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbTableColumnMap", schema = "DBA", catalog = "iqwrite")
public class FtbTableColumnMap implements java.io.Serializable {

	// Fields

	private Integer nmTableColumnMapId;
	private FtbTableMap ftbTableMap;
	private String vcColumnName;
	private Integer intColumnType;
	private String vcColumnNickName;

	private Set<FtbOutColumnMapTask> ftbOutColumnMapTasks = new HashSet<FtbOutColumnMapTask>(
			0);
	private Set<FtbFilterColumnMapTask> ftbFilterColumnMapTasks = new HashSet<FtbFilterColumnMapTask>(
			0);

	// Constructors

	/** default constructor */
	public FtbTableColumnMap() {
	}

	/** minimal constructor */
	public FtbTableColumnMap(FtbTableMap ftbTableMap, String vcColumnName,
			Integer intColumnType, String vcColumnNickName) {
		this.ftbTableMap = ftbTableMap;
		this.vcColumnName = vcColumnName;
		this.intColumnType = intColumnType;
		this.vcColumnNickName = vcColumnNickName;
	}

	/** full constructor */
	public FtbTableColumnMap(FtbTableMap ftbTableMap, String vcColumnName,
			Integer intColumnType, String vcColumnNickName,
			Set<FtbOutColumnMapTask> ftbOutColumnMapTasks,
			Set<FtbFilterColumnMapTask> ftbFilterColumnMapTasks) {
		this.ftbTableMap = ftbTableMap;
		this.vcColumnName = vcColumnName;
		this.intColumnType = intColumnType;
		this.vcColumnNickName = vcColumnNickName;
		this.ftbOutColumnMapTasks = ftbOutColumnMapTasks;
		this.ftbFilterColumnMapTasks = ftbFilterColumnMapTasks;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmTableColumnMapId", unique = true, nullable = false, precision = 5, scale = 0)
	public Integer getNmTableColumnMapId() {
		return this.nmTableColumnMapId;
	}

	public void setNmTableColumnMapId(Integer nmTableColumnMapId) {
		this.nmTableColumnMapId = nmTableColumnMapId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nmTableMapId")
	public FtbTableMap getFtbTableMap() {
		return this.ftbTableMap;
	}

	public void setFtbTableMap(FtbTableMap ftbTableMap) {
		this.ftbTableMap = ftbTableMap;
	}

	@Column(name = "vcColumnName", nullable = false, length = 50)
	public String getVcColumnName() {
		return this.vcColumnName;
	}

	public void setVcColumnName(String vcColumnName) {
		this.vcColumnName = vcColumnName;
	}

	@Column(name = "intColumnType", nullable = false)
	public Integer getIntColumnType() {
		return this.intColumnType;
	}

	public void setIntColumnType(Integer intColumnType) {
		this.intColumnType = intColumnType;
	}

	@Column(name = "vcColumnNickName", nullable = false, length = 100)
	public String getVcColumnNickName() {
		return this.vcColumnNickName;
	}

	public void setVcColumnNickName(String vcColumnNickName) {
		this.vcColumnNickName = vcColumnNickName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ftbTableColumnMap")
	public Set<FtbOutColumnMapTask> getFtbOutColumnMapTasks() {
		return this.ftbOutColumnMapTasks;
	}

	public void setFtbOutColumnMapTasks(
			Set<FtbOutColumnMapTask> ftbOutColumnMapTasks) {
		this.ftbOutColumnMapTasks = ftbOutColumnMapTasks;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ftbTableColumnMap")
	public Set<FtbFilterColumnMapTask> getFtbFilterColumnMapTasks() {
		return this.ftbFilterColumnMapTasks;
	}

	public void setFtbFilterColumnMapTasks(
			Set<FtbFilterColumnMapTask> ftbFilterColumnMapTasks) {
		this.ftbFilterColumnMapTasks = ftbFilterColumnMapTasks;
	}

}