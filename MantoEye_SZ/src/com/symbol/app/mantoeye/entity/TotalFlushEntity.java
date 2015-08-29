package com.symbol.app.mantoeye.entity;

public class TotalFlushEntity {

	private String fullDate;// 时间
	private int intUpFlush;// 上行流量
	private int intDownFlush;// 下行流量
	private int totalFlush;// 总流量
	private int intImsis;// 用户数
	private int visit;// 访问次数
	private String business;// 业务
	private String flushRate;// 流量占比
	private String area;// 小区
	private String cgi; // CGI
	private String bsc; // BSC
	private String sgsn; // SGSN

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getFullDate() {
		return fullDate;
	}

	public void setFullDate(String fullDate) {
		this.fullDate = fullDate;
	}

	public int getIntUpFlush() {
		return intUpFlush;
	}

	public void setIntUpFlush(int intUpFlush) {
		this.intUpFlush = intUpFlush;
	}

	public int getIntDownFlush() {
		return intDownFlush;
	}

	public void setIntDownFlush(int intDownFlush) {
		this.intDownFlush = intDownFlush;
	}

	public int getTotalFlush() {
		return totalFlush;
	}

	public void setTotalFlush(int totalFlush) {
		this.totalFlush = totalFlush;
	}

	public int getIntImsis() {
		return intImsis;
	}

	public void setIntImsis(int intImsis) {
		this.intImsis = intImsis;
	}

	public String getFlushRate() {
		return flushRate;
	}

	public void setFlushRate(String flushRate) {
		this.flushRate = flushRate;
	}

	public int getVisit() {
		return visit;
	}

	public void setVisit(int visit) {
		this.visit = visit;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCgi() {
		return cgi;
	}

	public void setCgi(String cgi) {
		this.cgi = cgi;
	}

	public String getBsc() {
		return bsc;
	}

	public void setBsc(String bsc) {
		this.bsc = bsc;
	}

	public String getSgsn() {
		return sgsn;
	}

	public void setSgsn(String sgsn) {
		this.sgsn = sgsn;
	}

}
