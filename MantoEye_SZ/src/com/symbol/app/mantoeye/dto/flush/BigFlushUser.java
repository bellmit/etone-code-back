package com.symbol.app.mantoeye.dto.flush;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class BigFlushUser implements java.io.Serializable{

	public static final Log log = LogFactory.getLog(BaseFlush.class);
	private long id;
	private long imsi;
	private String imei;
	private long msisdn;
	private String strImsi;
	private String strMissdn;
	private String isBlackUser;
	private long upFlush=0;
	private long downFlush=0;
	private long totalFlush=0;
	private String time;
	private String vcUserBelong;
	private Double upFlushKB;
	private Double downFlushKB;
	private Double totalFlushKB;
	private Double upFlushMB;
	private Double downFlushMB;
	private Double totalFlushMB;
	
	Long forMB = Long.valueOf(1048576L);
	Long forKB = Long.valueOf(1024L);
	
	private String url;
	private int hour;
	private String apnName;
	private String userAgent;
	private String contentType;
	private String street;
	
	private String cellName;//小区名
	private String branchName;//分公司名
	
	
	public void calculateData() {
		this.upFlushMB = this.upFlush == 0 ? 0.0
				: (this.upFlush * 100 / this.forMB) / 100.0;
		this.downFlushMB = this.downFlush == 0 ? 0.0
				: (this.downFlush * 100 / this.forMB) / 100.0;
		this.totalFlushMB = this.totalFlush == 0 ? 0.0
				: (this.totalFlush * 100 / this.forMB) / 100.0;
		this.upFlushKB = this.upFlush == 0 ? 0.0
				: (this.upFlush * 100 / this.forKB) / 100.0;
		this.downFlushKB = this.downFlush == 0 ? 0.0
				: (this.downFlush * 100 / this.forKB) / 100.0;
		this.totalFlushKB = this.totalFlush == 0 ? 0.0
				: (this.totalFlush * 100 / this.forKB) / 100.0;
		this.strImsi = this.imsi+"";
//		this.strImei = this.imei+"";
	}
	
	public long getImsi() {
		return imsi;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public String getApnName() {
		return apnName;
	}
	public void setApnName(String apnName) {
		this.apnName = apnName;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public void setImsi(long imsi) {
		this.imsi = imsi;
		this.strImsi=imsi+"";
	}
	
	public long getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(long msisdn) {
		this.msisdn = msisdn;
		this.strMissdn=msisdn+"";
	}
	
	public String getIsBlackUser() {
		return isBlackUser;
	}
	public void setIsBlackUser(String isBlackUser) {
		this.isBlackUser = isBlackUser;
	}
	public long getUpFlush() {
		return upFlush;
	}
	public void setUpFlush(long upFlush) {
		this.upFlush = upFlush;
	}
	public long getDownFlush() {
		return downFlush;
	}
	public void setDownFlush(long downFlush) {
		this.downFlush = downFlush;
	}
	public long getTotalFlush() {
		return totalFlush;
	}
	public void setTotalFlush(long totalFlush) {
		this.totalFlush = totalFlush;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Double getUpFlushKB() {
		return upFlushKB;
	}
	public void setUpFlushKB(Double upFlushKB) {
		this.upFlushKB = upFlushKB;
	}
	public Double getDownFlushKB() {
		return downFlushKB;
	}
	public void setDownFlushKB(Double downFlushKB) {
		this.downFlushKB = downFlushKB;
	}
	public Double getTotalFlushKB() {
		return totalFlushKB;
	}
	public void setTotalFlushKB(Double totalFlushKB) {
		this.totalFlushKB = totalFlushKB;
	}
	public Double getUpFlushMB() {
		return upFlushMB;
	}
	public void setUpFlushMB(Double upFlushMB) {
		this.upFlushMB = upFlushMB;
	}
	public Double getDownFlushMB() {
		return downFlushMB;
	}
	public void setDownFlushMB(Double downFlushMB) {
		this.downFlushMB = downFlushMB;
	}
	public Double getTotalFlushMB() {
		return totalFlushMB;
	}
	public void setTotalFlushMB(Double totalFlushMB) {
		this.totalFlushMB = totalFlushMB;
	}
	public Long getForMB() {
		return forMB;
	}
	public void setForMB(Long forMB) {
		this.forMB = forMB;
	}

	public String getStrImsi() {
		return strImsi;
	}

	public void setStrImsi(String strImsi) {
		this.strImsi = strImsi;
	}

	public String getStrMissdn() {
		return strMissdn;
	}

	public void setStrMissdn(String strMissdn) {
		this.strMissdn = strMissdn;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

//	public String getStrImei() {
//		return strImei;
//	}
//
//	public void setStrImei(String strImei) {
//		this.strImei = strImei;
//	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getVcUserBelong() {
		return vcUserBelong;
	}

	public void setVcUserBelong(String vcUserBelong) {
		this.vcUserBelong = vcUserBelong;
	}
}
