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
 * FtbDataGetterDecTask entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ftbDataGetterDecTask", schema = "DBA", catalog = "iqwrite")
/**
 * 解码提取任务文件表，只适用解码后的任务，只生成一个文件
 */
public class FtbDataGetterDecTask implements java.io.Serializable {

	/**
	 * 文件表ID
	 */
	private Long nmDataGetterDecTaskId;
	/**
	 * 关联任务表ID
	 */
	private Long nmDataGetterTaskId;
	/**
	 * 文件储存服务器IP
	 */
	private String vcFileServerIp;
	/**
	 * 文件路径
	 */
	private String vcFilePath;
	/**
	 * 文件大小
	 */
	private Long nmFileSize;
	/**
	 * 文件格式
	 */
	private String vcFileFormat;
	/**
	 * 完成时间
	 */
	private Date dtEtime;

	/** default constructor */
	public FtbDataGetterDecTask() {
	}

	/** full constructor */
	public FtbDataGetterDecTask(Long nmDataGetterTaskId, String vcFileServerIp,
			String vcFilePath, Long nmFileSize, String vcFileFormat,
			Date dtEtime) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
		this.vcFileServerIp = vcFileServerIp;
		this.vcFilePath = vcFilePath;
		this.nmFileSize = nmFileSize;
		this.vcFileFormat = vcFileFormat;
		this.dtEtime = dtEtime;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "nmDataGetterDecTaskId", unique = true, nullable = false, precision = 10, scale = 0)
	public Long getNmDataGetterDecTaskId() {
		return this.nmDataGetterDecTaskId;
	}

	public void setNmDataGetterDecTaskId(Long nmDataGetterDecTaskId) {
		this.nmDataGetterDecTaskId = nmDataGetterDecTaskId;
	}

	@Column(name = "nmDataGetterTaskId", precision = 10, scale = 0)
	public Long getNmDataGetterTaskId() {
		return this.nmDataGetterTaskId;
	}

	public void setNmDataGetterTaskId(Long nmDataGetterTaskId) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
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