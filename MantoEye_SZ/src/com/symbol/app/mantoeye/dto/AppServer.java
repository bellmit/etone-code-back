package com.symbol.app.mantoeye.dto;

public class AppServer {
	private String serverName;
	private String serverIP;
	private String userName;
	private String passWd;
	private String serverIpOut ;//外网ip 
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWd() {
		return passWd;
	}
	public void setPassWd(String passWd) {
		this.passWd = passWd;
	}
	public String getServerIpOut() {
		return serverIpOut;
	}
	public void setServerIpOut(String serverIpOut) {
		this.serverIpOut = serverIpOut;
	}
	
	
}
