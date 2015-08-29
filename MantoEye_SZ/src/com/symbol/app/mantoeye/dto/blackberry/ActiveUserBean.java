package com.symbol.app.mantoeye.dto.blackberry;

import java.text.NumberFormat;
import java.util.Locale;

import com.symbol.app.mantoeye.util.CommonUtils;

/**
 * 活跃用户
 * @author rankin
 *
 */
public class ActiveUserBean {
//IMSI、MSISDN、流量、业务使用次数、出现天数、统计时间
	
	private String imsi;
	private String msisdn;
	private String cgi;
	private Long upFlush;
	private Long downFlush;
	private Long totalFlush;
	private Long count;//次数
	private Integer days;//天数
	private int year;
	private int month;
	private String fullDate;
	
	
	//private String vcApnName;
	private String vcIdc;
	private String vcCountryName;
	private String vcBrandName;
	
	/////////////////////////////////////////////////////////////
	
	private Double upFlushKb;
	
	private Double upFlushMb;
	
	private Double upFlushGb;
	
	private Double downFlushKb;
	
	private Double downFlushMb;
	
	private Double downFlushGb;
	
	private Double totalFlushKb;
	
	private Double totalFlushMb;
	
	private Double totalFlushGb;
	
	private String formatUpFlush;//
	private String formatDownFlush;
	private String formatTotalFlush;
	
	private String formatUpFlushKb;//
	private String formatDownFlushKb;
	private String formatTotalFlushKb;
	
	private String formatUpFlushMb;//
	private String formatDownFlushMb;
	private String formatTotalFlushMb;
	
	private String formatUpFlushGb;//
	private String formatDownFlushGb;
	private String formatTotalFlushGb;
	
	private String formatCount;
	
	public Long getUpFlush() {
		return upFlush;
	}
	public void setUpFlush(Long upFlush) {
		this.upFlush = upFlush;
	}
	public Long getDownFlush() {
		return downFlush;
	}
	public void setDownFlush(Long downFlush) {
		this.downFlush = downFlush;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public Long getTotalFlush() {
		return totalFlush;
	}
	public void setTotalFlush(Long totalFlush) {
		this.totalFlush = totalFlush;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String getFullDate() {
		return fullDate;
	}
	public void setFullDate(String fullDate) {
		this.fullDate = fullDate;
	}
	public String getCgi() {
		return cgi;
	}
	public void setCgi(String cgi) {
		this.cgi = cgi;
	}
	
	
	////////////////////计算//////////////
	
	
	public String getVcBrandName() {
		return vcBrandName;
	}
	public void setVcBrandName(String vcBrandName) {
		this.vcBrandName = vcBrandName;
	}
	public void calculateData(){

		this.upFlushKb = CommonUtils.formatBToKb(this.upFlush);
		this.downFlushKb = CommonUtils.formatBToKb(this.downFlush);
		this.totalFlushKb = CommonUtils.formatBToKb(this.totalFlush);
		
		this.upFlushMb = CommonUtils.formatBToMb(this.upFlush);
		this.downFlushMb = CommonUtils.formatBToMb(this.downFlush);
		this.totalFlushMb = CommonUtils.formatBToMb(this.totalFlush);
		
		this.upFlushGb = CommonUtils.formatBToGb(this.upFlush);
		this.downFlushGb = CommonUtils.formatBToGb(this.downFlush);
		this.totalFlushGb = CommonUtils.formatBToGb(this.totalFlush);
		
		NumberFormat germanformat = NumberFormat.getInstance(Locale.CHINA);
		
		this.formatUpFlush = germanformat.format(this.upFlush);
		this.formatUpFlushKb = germanformat.format(this.upFlushKb);
		this.formatUpFlushMb = germanformat.format(this.upFlushMb);
		this.formatUpFlushGb = germanformat.format(this.upFlushGb);
		
		this.formatDownFlush = germanformat.format(this.downFlush);
		this.formatDownFlushKb = germanformat.format(this.downFlushKb);
		this.formatDownFlushMb = germanformat.format(this.downFlushMb);
		this.formatDownFlushGb = germanformat.format(this.downFlushGb);
		
		this.formatTotalFlush = germanformat.format(this.totalFlush);
		this.formatTotalFlushKb = germanformat.format(this.totalFlushKb);
		this.formatTotalFlushMb = germanformat.format(this.totalFlushMb);
		this.formatTotalFlushGb = germanformat.format(this.totalFlushGb);
		
		this.formatCount = germanformat.format(this.count);
		
		this.fullDate = this.year + "-" + this.month;
	}
	public Double getTotalFlushKb() {
		return totalFlushKb;
	}
	public Double getTotalFlushMb() {
		return totalFlushMb;
	}
	public Double getTotalFlushGb() {
		return totalFlushGb;
	}
	public Double getUpFlushKb() {
		return upFlushKb;
	}
	public Double getUpFlushMb() {
		return upFlushMb;
	}
	public Double getUpFlushGb() {
		return upFlushGb;
	}
	public Double getDownFlushKb() {
		return downFlushKb;
	}
	public Double getDownFlushMb() {
		return downFlushMb;
	}
	public Double getDownFlushGb() {
		return downFlushGb;
	}
	public String getFormatUpFlush() {
		return formatUpFlush;
	}
	public String getFormatDownFlush() {
		return formatDownFlush;
	}
	public String getFormatTotalFlush() {
		return formatTotalFlush;
	}
	public String getFormatUpFlushKb() {
		return formatUpFlushKb;
	}
	public String getFormatDownFlushKb() {
		return formatDownFlushKb;
	}
	public String getFormatTotalFlushKb() {
		return formatTotalFlushKb;
	}
	public String getFormatUpFlushMb() {
		return formatUpFlushMb;
	}
	public String getFormatDownFlushMb() {
		return formatDownFlushMb;
	}
	public String getFormatTotalFlushMb() {
		return formatTotalFlushMb;
	}
	public String getFormatUpFlushGb() {
		return formatUpFlushGb;
	}
	public String getFormatDownFlushGb() {
		return formatDownFlushGb;
	}
	public String getFormatTotalFlushGb() {
		return formatTotalFlushGb;
	}
	public String getFormatCount() {
		return formatCount;
	}
//	public String getVcApnName() {
//		return vcApnName;
//	}
//	public void setVcApnName(String vcApnName) {
//		this.vcApnName = vcApnName;
//	}
	public String getVcIdc() {
		return vcIdc;
	}
	public void setVcIdc(String vcIdc) {
		this.vcIdc = vcIdc;
	}
	public String getVcCountryName() {
		return vcCountryName;
	}
	public void setVcCountryName(String vcCountryName) {
		this.vcCountryName = vcCountryName;
	}
	
}
