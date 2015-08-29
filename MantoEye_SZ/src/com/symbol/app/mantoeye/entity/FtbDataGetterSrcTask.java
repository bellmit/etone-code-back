package com.symbol.app.mantoeye.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * FtbDataGetterSrcTask entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbDataGetterSrcTask", schema = "DBA", catalog = "iqwrite")
public class FtbDataGetterSrcTask implements java.io.Serializable {

	// Fields

	private Long nmDataGetterSrcTaskId;
	private Long nmDataGetterTaskId;
	private String vcDecServerIp;
	private String vcFileServerIp;
	private String vcFilePath;
	private Long nmFileSize;
	private String vcFileFormat;
	private Date dtEtime;

	// Constructors

	/** default constructor */
	public FtbDataGetterSrcTask() {
	}

	/** full constructor */
	public FtbDataGetterSrcTask(Long nmDataGetterTaskId, String vcDecServerIp,
			String vcFileServerIp, String vcFilePath, Long nmFileSize,
			String vcFileFormat, Date dtEtime) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
		this.vcDecServerIp = vcDecServerIp;
		this.vcFileServerIp = vcFileServerIp;
		this.vcFilePath = vcFilePath;
		this.nmFileSize = nmFileSize;
		this.vcFileFormat = vcFileFormat;
		this.dtEtime = dtEtime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmDataGetterSrcTaskId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmDataGetterSrcTaskId() {
		return this.nmDataGetterSrcTaskId;
	}

	public void setNmDataGetterSrcTaskId(Long nmDataGetterSrcTaskId) {
		this.nmDataGetterSrcTaskId = nmDataGetterSrcTaskId;
	}

	@Column(name = "nmDataGetterTaskId", precision = 10, scale = 0)
	public Long getNmDataGetterTaskId() {
		return this.nmDataGetterTaskId;
	}

	public void setNmDataGetterTaskId(Long nmDataGetterTaskId) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
	}

	@Column(name = "vcDecServerIp", length = 16)
	public String getVcDecServerIp() {
		return this.vcDecServerIp;
	}

	public void setVcDecServerIp(String vcDecServerIp) {
		this.vcDecServerIp = vcDecServerIp;
	}

	@Column(name = "vcFileServerIp", length = 16)
	public String getVcFileServerIp() {
		return this.vcFileServerIp;
	}

	public void setVcFileServerIp(String vcFileServerIp) {
		this.vcFileServerIp = vcFileServerIp;
	}

	@Column(name = "vcFilePath")
	public String getVcFilePath() {
		return this.vcFilePath;
	}

	public void setVcFilePath(String vcFilePath) {
		this.vcFilePath = vcFilePath;
	}

	@Column(name = "nmFileSize", precision = 15, scale = 0)
	public Long getNmFileSize() {
		return this.nmFileSize;
	}

	public void setNmFileSize(Long nmFileSize) {
		this.nmFileSize = nmFileSize;
	}

	@Column(name = "vcFileFormat", length = 10)
	public String getVcFileFormat() {
		return this.vcFileFormat;
	}

	public void setVcFileFormat(String vcFileFormat) {
		this.vcFileFormat = vcFileFormat;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dtETime", length = 23)
	public Date getDtEtime() {
		return this.dtEtime;
	}

	public void setDtEtime(Date dtEtime) {
		this.dtEtime = dtEtime;
	}

}