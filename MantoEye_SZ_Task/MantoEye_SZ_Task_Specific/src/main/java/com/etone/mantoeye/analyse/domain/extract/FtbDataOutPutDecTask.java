package com.etone.mantoeye.analyse.domain.extract;

import java.io.Serializable;

/**
 * 自定義輸出的文件存放相關信息
 * 
 * @author Wu Zhenzhen
 * @since 2011年9月8日14:37:30
 * 
 */
public class FtbDataOutPutDecTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7836521060303932826L;

	/**
	 * 文本输出任务定义ID
	 */
	private Long nmDataOutPutDecTaskId;

	/**
	 * 文本输出任务定义任務ID
	 */
	private Long nmDataOutPutTaskId;

	/**
	 * 文本输出任务定义文件存放server
	 */
	private String vcFileServerIp;

	/**
	 * 文本输出任务定义文件路徑
	 */
	private String vcFilePath;

	/**
	 * 文本输出任务定义文件大小
	 */
	private Long nmFileSize;

	/**
	 * 文本输出任务定义文件名
	 */
	private String vcFileName;

	/**
	 * 文本输出任务定义格式
	 */
	private String vcFileFormat;

	/**
	 * @return the nmDataOutPutDecTaskId
	 */
	public Long getNmDataOutPutDecTaskId() {
		return nmDataOutPutDecTaskId;
	}

	/**
	 * @param nmDataOutPutDecTaskId
	 *            the nmDataOutPutDecTaskId to set
	 */
	public void setNmDataOutPutDecTaskId(Long nmDataOutPutDecTaskId) {
		this.nmDataOutPutDecTaskId = nmDataOutPutDecTaskId;
	}

	/**
	 * @return the nmDataOutPutTaskId
	 */
	public Long getNmDataOutPutTaskId() {
		return nmDataOutPutTaskId;
	}

	/**
	 * @param nmDataOutPutTaskId
	 *            the nmDataOutPutTaskId to set
	 */
	public void setNmDataOutPutTaskId(Long nmDataOutPutTaskId) {
		this.nmDataOutPutTaskId = nmDataOutPutTaskId;
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
	 * @return the vcFileName
	 */
	public String getVcFileName() {
		return vcFileName;
	}

	/**
	 * @param vcFileName
	 *            the vcFileName to set
	 */
	public void setVcFileName(String vcFileName) {
		this.vcFileName = vcFileName;
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
	 * @return the nmFileSize
	 */
	public Long getNmFileSize() {
		return nmFileSize;
	}

	/**
	 * @param nmFileSize
	 *            the nmFileSize to set
	 */
	public void setNmFileSize(Long nmFileSize) {
		this.nmFileSize = nmFileSize;
	}

	/**
	 * @param nmDataOutPutDecTaskId
	 * @param nmDataOutPutTaskId
	 * @param vcFileServerIp
	 * @param vcFilePath
	 * @param nmFileSize
	 * @param vcFileName
	 * @param vcFileFormat 
	 */
	public FtbDataOutPutDecTask(Long nmDataOutPutDecTaskId,
			Long nmDataOutPutTaskId, String vcFileServerIp, String vcFilePath,
			Long nmFileSize, String vcFileName, String vcFileFormat) {
		super();
		this.nmDataOutPutDecTaskId = nmDataOutPutDecTaskId;
		this.nmDataOutPutTaskId = nmDataOutPutTaskId;
		this.vcFileServerIp = vcFileServerIp;
		this.vcFilePath = vcFilePath;
		this.nmFileSize = nmFileSize;
		this.vcFileName = vcFileName;
		this.vcFileFormat = vcFileFormat;
	}

	/**
	 * 無慘構造方法
	 */
	public FtbDataOutPutDecTask() {
	}

}
