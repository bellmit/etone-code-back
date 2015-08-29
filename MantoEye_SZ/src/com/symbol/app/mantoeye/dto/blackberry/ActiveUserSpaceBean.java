package com.symbol.app.mantoeye.dto.blackberry;

import java.text.NumberFormat;
import java.util.Locale;

import com.symbol.app.mantoeye.util.CommonUtils;
/**
 * 活跃用户空间分布
 * @author rankin
 *
 */
public class ActiveUserSpaceBean {
	 
	private Long upFlush;// 上行流量

	private Long downFlush;// 下行流量

	private Long totalFlush;// 总流量
	
	private Long imsis;// 用户数

	private Long dimensionId;//

	private String dimensionName;// 名称

	private int year;

	private int month;
	
	private String fullDate;
	
	private Long countryId;
	private String countryName;//国家名称
	private Long ldcId;
	private String ldcName;//运营商名称
	
	/////////////////////////////////////////////////////////////
	
	private Double upFlushKb;
	
	private Double upFlushMb;
	
	private Double upFlushGb;
	private Double upFlushTb;
	
	private Double downFlushKb;
	
	private Double downFlushMb;
	
	private Double downFlushGb;
	private Double downFlushTb;
	
	private Double totalFlushKb;
	
	private Double totalFlushMb;
	
	private Double totalFlushGb;
	private Double totalFlushTb;
	
	private Double imsisYi;
	private Double imsisWan;
	
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
	
	
	private String formatImsis;
		

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

	public Long getTotalFlush() {
		return totalFlush;
	}

	public void setTotalFlush(Long totalFlush) {
		this.totalFlush = totalFlush;
	}

	public Long getImsis() {
		return imsis;
	}

	public void setImsis(Long imsis) {
		this.imsis = imsis;
	}

	public Long getDimensionId() {
		return dimensionId;
	}

	public void setDimensionId(Long dimensionId) {
		this.dimensionId = dimensionId;
	}

	public String getDimensionName() {
		return dimensionName;
	}

	public void setDimensionName(String dimensionName) {
		this.dimensionName = dimensionName;
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
	////////////////getter/////////////////////
	
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

	public Double getTotalFlushKb() {
		return totalFlushKb;
	}

	public Double getTotalFlushMb() {
		return totalFlushMb;
	}

	public Double getTotalFlushGb() {
		return totalFlushGb;
	}
	
	////////////////////计算//////////////
	
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
		
		this.upFlushTb = CommonUtils.formatBToTb(this.upFlush);
		this.downFlushTb = CommonUtils.formatBToTb(this.downFlush);
		this.totalFlushTb = CommonUtils.formatBToTb(this.totalFlush);
		
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
		
		this.formatImsis = germanformat.format(this.imsis);
		
		this.imsisWan = this.imsis == 0 ? 0.0
				: CommonUtils.formatToWan(this.imsis*1.0 );
		this.imsisYi = this.imsis == 0 ? 0.0
				: CommonUtils.formatToYi(this.imsis*1.0 );
		
		this.fullDate = this.year + "-" + this.month;
	}

	public Double getImsisYi() {
		return imsisYi;
	}

	public Double getImsisWan() {
		return imsisWan;
	}

	public Double getUpFlushTb() {
		return upFlushTb;
	}

	public Double getDownFlushTb() {
		return downFlushTb;
	}

	public Double getTotalFlushTb() {
		return totalFlushTb;
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

	public String getFormatImsis() {
		return formatImsis;
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

	public Long getLdcId() {
		return ldcId;
	}

	public void setLdcId(Long ldcId) {
		this.ldcId = ldcId;
	}

	public String getLdcName() {
		return ldcName;
	}

	public void setLdcName(String ldcName) {
		this.ldcName = ldcName;
	}
	
	

}
