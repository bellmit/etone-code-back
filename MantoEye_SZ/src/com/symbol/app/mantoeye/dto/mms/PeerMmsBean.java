package com.symbol.app.mantoeye.dto.mms;

import java.text.NumberFormat;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.util.CommonUtils;

/**
 * 点对点彩信统计
 * 
 * @author rankin
 * 
 */
public class PeerMmsBean {

	// ///////////通过视图获取的数据(有Getter和Setter)///////////////

	public void setFullDate(String fullDate) {
		this.fullDate = fullDate;
	}
	private Long upCount;// 上行数量

	private Long downCount;// 下行数量

	private Long totalCount;// 总数量

	private Long dimensionId;//

	private String dimensionName;// 名称

	private int year;

	private int month;

	private int week;

	private int day;
	
	private int hour;
	
	private String timeLevel;//时间粒度

	// /////////////////通过计算获取的数据(自由Getter)/////////////////////////

	private String upCountFormat;

	private String downCountFormat;

	private String totalCountFormat;

	private String fullDate;

	private String percentUpCount;

	private String percentDownCount;

	private String percentTotalCount;
	
	private Double totalSendWan;//发送量(万)
	private Double totalSendYi;//发送量(亿)
	
	/////////////////Getter and Setter//////////////

	public Long getUpCount() {
		return upCount;
	}

	public void setUpCount(Long upCount) {
		this.upCount = upCount;
	}

	public Long getDownCount() {
		return downCount;
	}

	public void setDownCount(Long downCount) {
		this.downCount = downCount;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
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

	///////////Only Getter////////////////////

	public String getUpCountFormat() {
		return upCountFormat;
	}

	public String getDownCountFormat() {
		return downCountFormat;
	}

	public String getTotalCountFormat() {
		return totalCountFormat;
	}

	public String getFullDate() {
		return fullDate;
	}

	public String getPercentUpCount() {
		return percentUpCount;
	}

	public String getPercentDownCount() {
		return percentDownCount;
	}

	public String getPercentTotalCount() {
		return percentTotalCount;
	}
	//计算
	public void calculateData(Long totalUp,Long totalDown,Long totalTotal){
		this.calculateData();
		
		/////////////////////////////////////////////////////////////////////////////
		//////彩信占比计算方式中上行占比、下行占比的分母也是总数量//////////////////////
		/////////////////////////////////////////////////////////////////////////////
		this.setPercentUpCount(totalTotal);
		this.setPercentDownCount(totalTotal);
		this.setPercentTotalCount(totalTotal);
//		this.setPercentUpCount(totalUp);
//		this.setPercentDownCount(totalDown);
//		this.setPercentTotalCount(totalTotal);
	}
	//计算
	public void calculateData() {
		
		//时间
		if(timeLevel!=null){
			if(timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_MONTH+""){
					this.fullDate = this.year +"-"+(this.month>9?this.month:("0"+this.month));
			}else if(timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK+""){
				this.fullDate = CommonUtils.getWeekRange(this.year,
						this.month, this.week);
				this.timeLevel = "周";
			}else if(timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY+""){
					this.fullDate = this.year +"-"+(this.month>9?this.month:("0"+this.month))+"-"+(this.day>9?this.day:("0"+this.day));
			}else if(timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR+""){
				this.fullDate = this.year +"-"+(this.month>9?this.month:("0"+this.month))+"-"+(this.day>9?this.day:("0"+this.day))+" "+(this.hour>9?this.hour:("0"+this.hour))+":00";
			}else{
				this.fullDate = " ";
			}
		}
		NumberFormat nf = NumberFormat.getInstance() ;
		//数量
		upCountFormat = nf.format(upCount);
		downCountFormat = nf.format(downCount);
		totalCountFormat = nf.format(totalCount);
		
		this.totalSendWan = this.totalCount == 0 ? 0.0
				: CommonUtils.formatToWan(this.totalCount*1.0 );
		this.totalSendYi = this.totalCount == 0 ? 0.0
				: CommonUtils.formatToYi(this.totalCount*1.0 );
		
	}
public Double getTotalSendWan() {
		return totalSendWan;
	}

	public Double getTotalSendYi() {
		return totalSendYi;
	}

/**
 * 上行数量占比
 * @param totalUpCount
 */
	public void setPercentUpCount(Long totalUpCount) {
		if(totalUpCount>0){
			this.percentUpCount = (this.upCount*10000/totalUpCount)/100.0+"";
		}else{
			this.percentUpCount = "0";
		}
	}
/**
 * 下行数量占比
 * @param totalDownCount
 */
	public void setPercentDownCount(Long totalDownCount) {
		if(totalDownCount>0){
			this.percentDownCount =  (this.downCount*10000/totalDownCount)/100.0+"";
		}else{
			this.percentDownCount = "0";
		}	
	}
/**
 * 总数量占比
 * @param totalTotalCount
 */
	public void setPercentTotalCount(Long totalTotalCount) {
		if(totalTotalCount>0){
			this.percentTotalCount =  (this.totalCount*10000/totalTotalCount)/100.0+"";
		}else{
			this.percentTotalCount = "0";
		}	
	}
	

}
