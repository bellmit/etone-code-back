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
 * FtbFilterColumnMapTask entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbFilterColumnMapTask", schema = "DBA", catalog = "iqwrite")
public class FtbFilterColumnMapTask implements java.io.Serializable {

	// Fields

	private Integer nmFilterColumnMapTaskId;
	private FtbDataGetterTask ftbDataGetterTask;
	private FtbTableColumnMap ftbTableColumnMap;
	private String vcFilterValue;
	/**
	 * 过滤条件取值范围: > < =
	 */
	private String vcCondition;

	// Constructors

	/** default constructor */
	public FtbFilterColumnMapTask() {
	}

	/** full constructor */
	public FtbFilterColumnMapTask(FtbDataGetterTask ftbDataGetterTask,
			FtbTableColumnMap ftbTableColumnMap, String vcFilterValue) {
		this.ftbDataGetterTask = ftbDataGetterTask;
		this.ftbTableColumnMap = ftbTableColumnMap;
		this.vcFilterValue = vcFilterValue;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmFilterColumnMapTaskId", unique = true, nullable = false, precision = 8, scale = 0)
	public Integer getNmFilterColumnMapTaskId() {
		return this.nmFilterColumnMapTaskId;
	}

	public void setNmFilterColumnMapTaskId(Integer nmFilterColumnMapTaskId) {
		this.nmFilterColumnMapTaskId = nmFilterColumnMapTaskId;
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

	@Column(name = "vcFilterValue", nullable = false, length = 60)
	public String getVcFilterValue() {
		return this.vcFilterValue;
	}

	public void setVcFilterValue(String vcFilterValue) {
		this.vcFilterValue = vcFilterValue;
	}

	@Column(name = "vcCondition", nullable = false, length = 10)
	public String getVcCondition() {
		return this.vcCondition;
	}

	public void setVcCondition(String vcCondition) {
		this.vcCondition = vcCondition;
	}

}