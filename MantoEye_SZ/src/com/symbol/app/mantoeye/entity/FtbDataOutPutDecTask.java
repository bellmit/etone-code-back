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
 * FtbDataOutPutDecTask entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbDataOutPutDecTask", schema = "DBA", catalog = "iqwrite")

public class FtbDataOutPutDecTask implements java.io.Serializable {

	/**
	 * 主键ID
	 */
	private Long nmDataOutPutDecTaskId;
	private Long nmDataOutPutTaskId;
	private String vcFileServerIp;
	private String vcFilePath;
	private String vcFileName;
	private Long nmFileSize;
	private String vcFileFormat;

	public FtbDataOutPutDecTask() {
	}

	/** full constructor */
	public FtbDataOutPutDecTask(Long nmDataOutPutTaskId,String vcFileServerIp,String vcFilePath,String vcFileName,Long nmFileSize,String vcFileFormat) {
		this.nmDataOutPutTaskId = nmDataOutPutTaskId;
		this.vcFileServerIp = vcFileServerIp;
		this.vcFilePath = vcFilePath;
		this.vcFileName = vcFileName;
		this.nmFileSize = nmFileSize;
		this.vcFileFormat = vcFileFormat;
	}

	@Id
	@GeneratedValue
	@Column(name = "nmDataOutPutDecTaskId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmDataOutPutDecTaskId() {
		return nmDataOutPutDecTaskId;
	}

	public void setNmDataOutPutDecTaskId(Long nmDataOutPutDecTaskId) {
		this.nmDataOutPutDecTaskId = nmDataOutPutDecTaskId;
	}

	@Column(name = "nmDataOutPutTaskId")
	public Long getNmDataOutPutTaskId() {
		return nmDataOutPutTaskId;
	}

	public void setNmDataOutPutTaskId(Long nmDataOutPutTaskId) {
		this.nmDataOutPutTaskId = nmDataOutPutTaskId;
	}

	@Column(name = "vcFileServerIp")
	public String getVcFileServerIp() {
		return vcFileServerIp;
	}

	public void setVcFileServerIp(String vcFileServerIp) {
		this.vcFileServerIp = vcFileServerIp;
	}

	@Column(name = "vcFilePath")
	public String getVcFilePath() {
		return vcFilePath;
	}

	public void setVcFilePath(String vcFilePath) {
		this.vcFilePath = vcFilePath;
	}

	@Column(name = "vcFileName")
	public String getVcFileName() {
		return vcFileName;
	}

	public void setVcFileName(String vcFileName) {
		this.vcFileName = vcFileName;
	}

	@Column(name = "nmFileSize")
	public Long getNmFileSize() {
		return nmFileSize;
	}

	public void setNmFileSize(Long nmFileSize) {
		this.nmFileSize = nmFileSize;
	}

	@Column(name = "vcFileFormat")
	public String getVcFileFormat() {
		return vcFileFormat;
	}

	public void setVcFileFormat(String vcFileFormat) {
		this.vcFileFormat = vcFileFormat;
	}

	
}