package com.symbol.app.mantoeye.dto;

/**
 * 终端导入任务实体
 * 
 * @author rankin
 * 
 */
public class TermDataLoadTask {
	// vcServerIp,vcFilePath,intStatus,dtSTime,dtETime
	private String vcServerIp;// 文件所在服务器IP
	private String vcFilePath;// 文件路径
	// 导入状态
	// 1.文件已上传完成
	// 2.数据导入中
	// 3.数据导入成功
	// 4.数据导入异常
	private int intStatus;
	private String dtSTime;// 定制时间
	private String dtETime;// 完成时间

	public String getVcServerIp() {
		return vcServerIp;
	}

	public void setVcServerIp(String vcServerIp) {
		this.vcServerIp = vcServerIp;
	}

	public String getVcFilePath() {
		return vcFilePath;
	}

	public void setVcFilePath(String vcFilePath) {
		this.vcFilePath = vcFilePath;
	}

	public int getIntStatus() {
		return intStatus;
	}

	public void setIntStatus(int intStatus) {
		this.intStatus = intStatus;
	}

	public String getDtSTime() {
		return dtSTime;
	}

	public void setDtSTime(String dtSTime) {
		this.dtSTime = dtSTime;
	}

	public String getDtETime() {
		return dtETime;
	}

	public void setDtETime(String dtETime) {
		this.dtETime = dtETime;
	}

}
