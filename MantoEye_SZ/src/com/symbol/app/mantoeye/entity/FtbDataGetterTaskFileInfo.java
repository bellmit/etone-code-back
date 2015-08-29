package com.symbol.app.mantoeye.entity;

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
 * FtbDataGetterTaskFileInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbDataGetterTaskFileInfo", schema = "DBA", catalog = "iqwrite")
public class FtbDataGetterTaskFileInfo implements java.io.Serializable {

	// Fields

	private Long nmTaskFileInfoId;
	private FtbDataGetterServerMapTask ftbDataGetterServerMapTask;
	private String vcFileServerIp;
	private String vcFilePath;
	private Long nmFileSize;
	private String vcFileFormat;
	private Date dtEtime;

	// Constructors

	/** default constructor */
	public FtbDataGetterTaskFileInfo() {
	}

	/** full constructor */
	public FtbDataGetterTaskFileInfo(
			FtbDataGetterServerMapTask ftbDataGetterServerMapTask,
			String vcFileServerIp, String vcFilePath, Long nmFileSize,
			String vcFileFormat, Date dtEtime) {
		this.ftbDataGetterServerMapTask = ftbDataGetterServerMapTask;
		this.vcFileServerIp = vcFileServerIp;
		this.vcFilePath = vcFilePath;
		this.nmFileSize = nmFileSize;
		this.vcFileFormat = vcFileFormat;
		this.dtEtime = dtEtime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmTaskFileInfoId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmTaskFileInfoId() {
		return this.nmTaskFileInfoId;
	}

	public void setNmTaskFileInfoId(Long nmTaskFileInfoId) {
		this.nmTaskFileInfoId = nmTaskFileInfoId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nmServerMapTaskId", nullable = false)
	public FtbDataGetterServerMapTask getFtbDataGetterServerMapTask() {
		return this.ftbDataGetterServerMapTask;
	}

	public void setFtbDataGetterServerMapTask(
			FtbDataGetterServerMapTask ftbDataGetterServerMapTask) {
		this.ftbDataGetterServerMapTask = ftbDataGetterServerMapTask;
	}

	@Column(name = "vcFileServerIp", nullable = false, length = 16)
	public String getVcFileServerIp() {
		return this.vcFileServerIp;
	}

	public void setVcFileServerIp(String vcFileServerIp) {
		this.vcFileServerIp = vcFileServerIp;
	}

	@Column(name = "vcFilePath", nullable = false)
	public String getVcFilePath() {
		return this.vcFilePath;
	}

	public void setVcFilePath(String vcFilePath) {
		this.vcFilePath = vcFilePath;
	}

	@Column(name = "nmFileSize", nullable = false, precision = 15, scale = 0)
	public Long getNmFileSize() {
		return this.nmFileSize;
	}

	public void setNmFileSize(Long nmFileSize) {
		this.nmFileSize = nmFileSize;
	}

	@Column(name = "vcFileFormat", nullable = false, length = 10)
	public String getVcFileFormat() {
		return this.vcFileFormat;
	}

	public void setVcFileFormat(String vcFileFormat) {
		this.vcFileFormat = vcFileFormat;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtETime", nullable = false, length = 23)
	public Date getDtEtime() {
		return this.dtEtime;
	}

	public void setDtEtime(Date dtEtime) {
		this.dtEtime = dtEtime;
	}

}