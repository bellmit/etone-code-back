package com.symbol.app.mantoeye.dto.blackberry;

import java.text.NumberFormat;
import java.util.Locale;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;

public class BlackBerryCountryBean {

	private Long upFlush;// 上行流量

	private Long downFlush;// 下行流量

	private Long totalFlush;// 总流量
	
	private Double averageFlush;// 平均流量
	
	private Long imsis;// 用户数

	private Long dimensionId;//

	private String dimensionName;// 名称

	private Long countryId;//归属地ID

	private String countryName;// 归属地名称
	
	private int year;

	private int month;

	private int week;

	private int day;
	
	private int hour;
	
	private String fullDate;
	
	private String timeLevel;//时间粒度
	
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
	
	private Double averageFlushKb;
	
	private Double averageFlushMb;
	
	private Double averageFlushGb;
	
	private String formatUpFlush;//
	private String formatDownFlush;
	private String formatTotalFlush;
	private String formatAverageFlush;//
	
	private String formatUpFlushKb;//
	private String formatDownFlushKb;
	private String formatTotalFlushKb;
	
	private String formatUpFlushMb;//
	private String formatDownFlushMb;
	private String formatTotalFlushMb;
	
	private String formatUpFlushGb;//
	private String formatDownFlushGb;
	private String formatTotalFlushGb;
	
	private String formatAverageFlushKb;	
	private String formatAverageFlushMb;
	private String formatAverageFlushGb;
	
	private String formatImsis;
	
	private Double downFlushTb;
	private Double upFlushTb;
	private Double totalFlushTb;
	private Double imsisYi;
	private Double imsisWan;
		
	private Double totalFlushRate;//流量全网占比
	private Double imsisRate;//用户数全网占比
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
	
	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getTimeLevel() {
		return timeLevel;
	}

	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

	public Double getAverageFlushKb() {
		return averageFlushKb;
	}

	public Double getAverageFlushMb() {
		return averageFlushMb;
	}

	public Double getAverageFlushGb() {
		return averageFlushGb;
	}

	public Double getAverageFlush() {
		return averageFlush;
	}

	public void setAverageFlush(Double averageFlush) {
		this.averageFlush = averageFlush;
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
	/**
	 * 计算流量用户数的全网占比
	 */
	public void calculateFlushRate(double sumTotalFlush,double sumImsis){
		totalFlushRate = Common.StringTodouble(CommonUtils.formatPercent(totalFlush, sumTotalFlush));
		imsisRate = Common.StringTodouble(CommonUtils.formatPercent(imsis, sumImsis));
	}
	////////////////////计算//////////////
	
	public void calculateData(){
		this.upFlushKb = CommonUtils.formatBToKb(this.upFlush);
		this.downFlushKb = CommonUtils.formatBToKb(this.downFlush);
		this.totalFlushKb = CommonUtils.formatBToKb(this.totalFlush);
		this.averageFlushKb = CommonUtils.formatBToKb(this.averageFlush.longValue());
		
		this.upFlushMb = CommonUtils.formatBToMb(this.upFlush);
		this.downFlushMb = CommonUtils.formatBToMb(this.downFlush);
		this.totalFlushMb = CommonUtils.formatBToMb(this.totalFlush);
		this.averageFlushMb = CommonUtils.formatBToMb(this.averageFlush.longValue());
		
		this.upFlushGb = CommonUtils.formatBToGb(this.upFlush);
		this.downFlushGb = CommonUtils.formatBToGb(this.downFlush);
		this.totalFlushGb = CommonUtils.formatBToGb(this.totalFlush);
		this.averageFlushGb = CommonUtils.formatBToGb(this.averageFlush.longValue());
		
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
		
		this.formatAverageFlush = germanformat.format(this.averageFlush);
		this.formatAverageFlushKb = germanformat.format(this.averageFlushKb);
		this.formatAverageFlushMb = germanformat.format(this.averageFlushMb);
		this.formatAverageFlushGb = germanformat.format(this.averageFlushGb);
		
		this.formatImsis = germanformat.format(this.imsis);
		
		this.upFlushTb = CommonUtils.formatBToTb(this.upFlush);
		this.downFlushTb = CommonUtils.formatBToTb(this.downFlush);
		this.totalFlushTb = CommonUtils.formatBToTb(this.totalFlush);
		this.imsisWan = this.imsis == 0 ? 0.0
				: CommonUtils.formatToWan(this.imsis*1.0 );
		this.imsisYi = this.imsis == 0 ? 0.0
				: CommonUtils.formatToYi(this.imsis*1.0 );
	
		//时间
		if(timeLevel!=null){
			if(timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_MONTH+""){
				this.fullDate = this.year +"-"+(this.month>10?this.month:("0"+this.month));
			}else if(timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK+""){
				this.fullDate = CommonUtils.getWeekRange(this.year,
						this.month, this.week);
			}else if(timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY+""){
				this.fullDate = this.year +"-"+(this.month>10?this.month:("0"+this.month))+"-"+(this.day>10?this.day:("0"+this.day));
			}else if(timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR+""){
				this.fullDate = this.year +"-"+(this.month>10?this.month:("0"+this.month))+"-"+(this.day>10?this.day:("0"+this.day))+" "+(this.hour>10?this.hour:("0"+this.hour))+":00:00";
			}else{
				this.fullDate = " ";
			}
		}
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

	public String getFormatAverageFlush() {
		return formatAverageFlush;
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

	public String getFormatAverageFlushKb() {
		return formatAverageFlushKb;
	}

	public String getFormatAverageFlushMb() {
		return formatAverageFlushMb;
	}

	public String getFormatAverageFlushGb() {
		return formatAverageFlushGb;
	}

	public String getFormatImsis() {
		return formatImsis;
	}

	public Double getDownFlushTb() {
		return downFlushTb;
	}

	public Double getUpFlushTb() {
		return upFlushTb;
	}

	public Double getTotalFlushTb() {
		return totalFlushTb;
	}

	public Double getImsisYi() {
		return imsisYi;
	}

	public Double getImsisWan() {
		return imsisWan;
	}

	public Double getTotalFlushRate() {
		return totalFlushRate;
	}

	public Double getImsisRate() {
		return imsisRate;
	}
	
	
}
