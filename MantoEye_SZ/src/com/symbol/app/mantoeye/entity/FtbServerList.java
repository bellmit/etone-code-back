package com.symbol.app.mantoeye.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * FtbServerList entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbServerList", schema = "DBA", catalog = "iqwrite")
public class FtbServerList implements java.io.Serializable {

	// Fields

	private Short nmServerListId;
	private String vcServerName;
	private String vcServerIp;
	private Set<FtbDataGetterServerMapTask> ftbDataGetterServerMapTasks = new HashSet<FtbDataGetterServerMapTask>(
			0);

	// Constructors

	/** default constructor */
	public FtbServerList() {
	}

	/** full constructor */
	public FtbServerList(String vcServerName, String vcServerIp,
			Set<FtbDataGetterServerMapTask> ftbDataGetterServerMapTasks) {
		this.vcServerName = vcServerName;
		this.vcServerIp = vcServerIp;
		this.ftbDataGetterServerMapTasks = ftbDataGetterServerMapTasks;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmServerListId", unique = true, nullable = false, precision = 3, scale = 0)
	public Short getNmServerListId() {
		return this.nmServerListId;
	}

	public void setNmServerListId(Short nmServerListId) {
		this.nmServerListId = nmServerListId;
	}

	@Column(name = "vcServerName", length = 30)
	public String getVcServerName() {
		return this.vcServerName;
	}

	public void setVcServerName(String vcServerName) {
		this.vcServerName = vcServerName;
	}

	@Column(name = "vcServerIp", length = 20)
	public String getVcServerIp() {
		return this.vcServerIp;
	}

	public void setVcServerIp(String vcServerIp) {
		this.vcServerIp = vcServerIp;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ftbServerList")
	public Set<FtbDataGetterServerMapTask> getFtbDataGetterServerMapTasks() {
		return this.ftbDataGetterServerMapTasks;
	}

	public void setFtbDataGetterServerMapTasks(
			Set<FtbDataGetterServerMapTask> ftbDataGetterServerMapTasks) {
		this.ftbDataGetterServerMapTasks = ftbDataGetterServerMapTasks;
	}

}