package com.symbol.app.mantoeye.dto.blackberry;

import java.text.NumberFormat;
import java.util.Locale;

import com.symbol.app.mantoeye.util.CommonUtils;

/**
 * 用户归属Top100 bean
 * @author rankin
 *
 */
public class CountryUserTopBean {
	
	private String cgi;
	private String cellName;
	private Long countryId;
	private String countryName;
	private Long flush;
	private Long imsis;
//	private Double aveFlush;
	private int year;
	private int month;
	private String formatFlush;//
	
	private String formatFlushKb;//
	
	private String formatFlushMb;//
	
	private String formatFlushGb;//
	
	private String formatImsis;
	
	//////////////////////////////////////////

	
	private String fullDate;
	
	private Double flushKb;
	private Double flushMb;
	private Double flushGb;
	
//	private Double aveFlushKb;
//	private Double aveFlushMb;
//	private Double aveFlushGb;
//	
//	private String formatAveFlush;
//	private String formatAveFlushKb;
//	private String formatAveFlushMb;
//	private String formatAveFlushGb;
	

	public String getCgi() {
		return cgi;
	}
	public void setCgi(String cgi) {
		this.cgi = cgi;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Long getFlush() {
		return flush;
	}
	public void setFlush(Long flush) {
		this.flush = flush;
	}
	public Long getImsis() {
		return imsis;
	}
	public void setImsis(Long imsis) {
		this.imsis = imsis;
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
	public Double getFlushKb() {
		return flushKb;
	}
	public Double getFlushMb() {
		return flushMb;
	}
	public Double getFlushGb() {
		return flushGb;
	}
	
	public void calculateData(){

		this.flushKb = CommonUtils.formatBToKb(this.flush);
		this.flushMb = CommonUtils.formatBToMb(this.flush);
		this.flushGb = CommonUtils.formatBToGb(this.flush);
		
		NumberFormat germanformat = NumberFormat.getInstance(Locale.CHINA);
		
		this.formatFlush = germanformat.format(flush);
		this.formatFlushKb = germanformat.format(flushKb);
		this.formatFlushMb = germanformat.format(flushMb);
		this.formatFlushGb = germanformat.format(flushGb);
		this.formatImsis = germanformat.format(imsis);
		
	
		this.fullDate = this.year + "-" + (this.month>10?this.month:("0"+this.month));
	}
	public String getFormatFlush() {
		return formatFlush;
	}
	public String getFormatFlushKb() {
		return formatFlushKb;
	}
	public String getFormatFlushMb() {
		return formatFlushMb;
	}
	public String getFormatFlushGb() {
		return formatFlushGb;
	}
	public String getFormatImsis() {
		return formatImsis;
	}
	public String getCellName() {
		return cellName;
	}
	public void setCellName(String cellName) {
		this.cellName = cellName;
	}
	

}
