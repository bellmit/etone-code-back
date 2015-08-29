package com.symbol.app.mantoeye.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FtbDataGetterFilter entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbDataGetterFilter", schema = "DBA", catalog = "iqwrite")
/**
 * 任务提取过滤条件表,与任务提取表是N对1关系。过滤条件的值根据任务表中的任务类别而定 当任务类型为1、2、3时过滤条件存储的是用户号码
 */
public class FtbDataGetterFilter implements java.io.Serializable {

	// Fields

	private Long nmDataGetterFilterId;
	/**
	 * 任务提取表ID
	 */
	private Long nmDataGetterTaskId;
	/**
	 * 过滤条件类型 1.用户号码 2.CGI 3.业务ID 4.BSCID 5.SGSNID 6.分公司ID 7.营销片区ID 8.街道ID
	 */
	private Integer intFilterType;
	/**
	 * 过滤条件
	 */
	private String vcFilterValue;

	// Constructors

	/** default constructor */
	public FtbDataGetterFilter() {
	}

	/** full constructor */
	public FtbDataGetterFilter(Long nmDataGetterTaskId, Integer intFilterType,
			String vcFilterValue) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
		this.intFilterType = intFilterType;
		this.vcFilterValue = vcFilterValue;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmDataGetterFilterId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmDataGetterFilterId() {
		return this.nmDataGetterFilterId;
	}

	public void setNmDataGetterFilterId(Long nmDataGetterFilterId) {
		this.nmDataGetterFilterId = nmDataGetterFilterId;
	}

	@Column(name = "nmDataGetterTaskId", precision = 10, scale = 0)
	public Long getNmDataGetterTaskId() {
		return this.nmDataGetterTaskId;
	}

	public void setNmDataGetterTaskId(Long nmDataGetterTaskId) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
	}

	@Column(name = "intFilterType")
	public Integer getIntFilterType() {
		return this.intFilterType;
	}

	public void setIntFilterType(Integer intFilterType) {
		this.intFilterType = intFilterType;
	}

	@Column(name = "vcFilterValue", length = 32)
	public String getVcFilterValue() {
		return this.vcFilterValue;
	}

	public void setVcFilterValue(String vcFilterValue) {
		this.vcFilterValue = vcFilterValue;
	}

}