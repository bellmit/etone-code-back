package com.symbol.app.mantoeye.entity;

public class DropIQConnectionDto {

	private String lastReqTime;
	private String lastIQCmdTime;
	private String connectionIp; 
	private String connectionId;
	private String createTime;

	public String getLastReqTime() {
		return lastReqTime;
	}

	public void setLastReqTime(String lastReqTime) {
		this.lastReqTime = lastReqTime;
	}

	public String getLastIQCmdTime() {
		return lastIQCmdTime;
	}

	public void setLastIQCmdTime(String lastIQCmdTime) {
		this.lastIQCmdTime = lastIQCmdTime;
	}

	public String getConnectionIp() {
		return connectionIp;
	}

	public void setConnectionIp(String connectionIp) {
		this.connectionIp = connectionIp;
	}

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
