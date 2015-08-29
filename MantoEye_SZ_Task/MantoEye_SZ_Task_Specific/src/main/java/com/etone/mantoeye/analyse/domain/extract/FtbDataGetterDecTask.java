package com.etone.mantoeye.analyse.domain.extract;

import java.io.Serializable;
import java.util.Date;

/**
 * 解码数据提取任务
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月24日22:27:58
 * 
 */
public class FtbDataGetterDecTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7693133115303024615L;

	/**
	 * 解码数据提取任务Id
	 */
	private Long nmDataGetterTaskId;

	/**
	 * 解码数据提取任务文件服務IP
	 */
	private String vcFileServerIp;

	/**
	 * 解码数据提取任务文件path
	 */
	private String vcFilePath;

	/**
	 * 解码数据提取任务文件大小
	 */
	private long nmFileSize;

	/**
	 * 解码数据提取任务文件格式
	 */
	private String vcFileFormat;

	/**
	 * 解码数据提取任务文件生成時間
	 */
	private Date dtETime;

	/**
	 * @return the nmDataGetterTaskId
	 */
	public Long getNmDataGetterTaskId() {
		return nmDataGetterTaskId;
	}

	/**
	 * @param nmDataGetterTaskId
	 *            the nmDataGetterTaskId to set
	 */
	public void setNmDataGetterTaskId(Long nmDataGetterTaskId) {
		this.nmDataGetterTaskId = nmDataGetterTaskId;
	}

	/**
	 * @return the vcFileServerIp
	 */
	public String getVcFileServerIp() {
		return vcFileServerIp;
	}

	/**
	 * @param vcFileServerIp
	 *            the vcFileServerIp to set
	 */
	public void setVcFileServerIp(String vcFileServerIp) {
		this.vcFileServerIp = vcFileServerIp;
	}

	/**
	 * @return the vcFilePath
	 */
	public String getVcFilePath() {
		return vcFilePath;
	}

	/**
	 * @param vcFilePath
	 *            the vcFilePath to set
	 */
	public void setVcFilePath(String vcFilePath) {
		this.vcFilePath = vcFilePath;
	}

	/**
	 * @return the nmFileSize
	 */
	public long getNmFileSize() {
		return nmFileSize;
	}

	/**
	 * @param nmFileSize
	 *            the nmFileSize to set
	 */
	public void setNmFileSize(long nmFileSize) {
		this.nmFileSize = nmFileSize;
	}

	/**
	 * @return the vcFileFormat
	 */
	public String getVcFileFormat() {
		return vcFileFormat;
	}

	/**
	 * @param vcFileFormat
	 *            the vcFileFormat to set
	 */
	public void setVcFileFormat(String vcFileFormat) {
		this.vcFileFormat = vcFileFormat;
	}

	/**
	 * @return the dtETime
	 */
	public Date getDtETime() {
		return dtETime;
	}

	/**
	 * @param dtETime
	 *            the dtETime to set
	 */
	public void setDtETime(Date dtETime) {
		this.dtETime = dtETime;
	}

	/**
	 * @param nmDataGetterTaskId
	 * @param vcFileServerIp
	 * @param vcFilePath
	 * @param nmFileSize
	 * @param vcFileFormat
	 * @param dtETime
	 */
	public FtbDataGetterDecTask(Long nmDataGetterTaskId, String vcFileServerIp,
			String vcFilePath, long nmFileSize, String vcFileFormat,
			Date dtETime) {
		super();
		this.nmDataGetterTaskId = nmDataGetterTaskId;
		this.vcFileServerIp = vcFileServerIp;
		this.vcFilePath = vcFilePath;
		this.nmFileSize = nmFileSize;
		this.vcFileFormat = vcFileFormat;
		this.dtETime = dtETime;
	}

	/**
	 * 無慘構造方法
	 */
	public FtbDataGetterDecTask() {
	}

}
