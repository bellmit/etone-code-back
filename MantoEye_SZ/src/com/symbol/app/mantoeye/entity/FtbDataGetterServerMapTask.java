package com.symbol.app.mantoeye.entity;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * FtbDataGetterServerMapTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbDataGetterServerMapTask", schema = "DBA", catalog = "iqwrite")
public class FtbDataGetterServerMapTask implements java.io.Serializable {

	// Fields

	private Long nmServerMapTaskId;
	private FtbServerList ftbServerList;
	private FtbDataGetterTask ftbDataGetterTask;
	private Integer intServerStatus;
	private Date dtStime;
	private Date dtEtime;
	private Set<FtbDataGetterTaskFileInfo> ftbDataGetterTaskFileInfos = new HashSet<FtbDataGetterTaskFileInfo>(
			0);

	// Constructors

	/** default constructor */
	public FtbDataGetterServerMapTask() {
	}

	/** full constructor */
	public FtbDataGetterServerMapTask(FtbServerList ftbServerList,
			FtbDataGetterTask ftbDataGetterTask, Integer intServerStatus,
			Date dtStime, Date dtEtime,
			Set<FtbDataGetterTaskFileInfo> ftbDataGetterTaskFileInfos) {
		this.ftbServerList = ftbServerList;
		this.ftbDataGetterTask = ftbDataGetterTask;
		this.intServerStatus = intServerStatus;
		this.dtStime = dtStime;
		this.dtEtime = dtEtime;
		this.ftbDataGetterTaskFileInfos = ftbDataGetterTaskFileInfos;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmServerMapTaskId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmServerMapTaskId() {
		return this.nmServerMapTaskId;
	}

	public void setNmServerMapTaskId(Long nmServerMapTaskId) {
		this.nmServerMapTaskId = nmServerMapTaskId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nmServerListId")
	public FtbServerList getFtbServerList() {
		return this.ftbServerList;
	}

	public void setFtbServerList(FtbServerList ftbServerList) {
		this.ftbServerList = ftbServerList;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nmDataGetterTaskId")
	public FtbDataGetterTask getFtbDataGetterTask() {
		return this.ftbDataGetterTask;
	}

	public void setFtbDataGetterTask(FtbDataGetterTask ftbDataGetterTask) {
		this.ftbDataGetterTask = ftbDataGetterTask;
	}
	

	@Column(name = "intServerStatus")
	public Integer getIntServerStatus() {
		return this.intServerStatus;
	}

	public void setIntServerStatus(Integer intServerStatus) {
		this.intServerStatus = intServerStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtSTime", length = 23)
	public Date getDtStime() {
		return this.dtStime;
	}

	public void setDtStime(Date dtStime) {
		this.dtStime = dtStime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtETime", length = 23)
	public Date getDtEtime() {
		return this.dtEtime;
	}

	public void setDtEtime(Date dtEtime) {
		this.dtEtime = dtEtime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ftbDataGetterServerMapTask")
	public Set<FtbDataGetterTaskFileInfo> getFtbDataGetterTaskFileInfos() {
		return this.ftbDataGetterTaskFileInfos;
	}

	public void setFtbDataGetterTaskFileInfos(
			Set<FtbDataGetterTaskFileInfo> ftbDataGetterTaskFileInfos) {
		this.ftbDataGetterTaskFileInfos = ftbDataGetterTaskFileInfos;
	}

}