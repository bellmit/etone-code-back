package com.symbol.app.mantoeye.dto.flush;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.util.CommonUtils;

/**
 * top100小区的流量和用户数bean
 * @author rankin
 *
 */
public class TopStat {
	
	public static final Log log = LogFactory.getLog(TopStat.class);
	
	private Long statId;//对应统计表ID
	
	private int top;//排名
	
	private String viewdata;//页面显示的日期表现形式
	
	private String cellName;
	
	private String cgi;
	
//	private String bscName;
//	
//	private String sgsnName;
	
	private Long flush;//总流量(单位B)
	
	private Double flushKb;//总流量(单位MB)
	
	private Double flushMb;//总流量(单位MB)
	
	private Double flushGb;//总流量(单位MB)
	
	private Long imsis;
	
	private int year;
	
	private int month;
	
	private int day;
	
	private int hour;
	
	private int week;
	
	private String statType;//统计粒度
	
	private String formatFlush;//
	
	private String formatFlushKb;//
	
	private String formatFlushMb;//
	
	private String formatFlushGb;//
	
	private String formatImsis;
		
	private String area;
	private String bsc;//bsc
	private String sgsn;//sgsn	
	private String street;//街道
	private String subsidiaryCompany;//分公司
	private String saleArea;//营销片区
	
	private Double nmAveFlush = 0.0;// 平均流量
	private Double nmAveFlushKB;// 平均流量KB
	private Double nmAveFlushMB;// 平均流量MB

	public Long getStatId() {
		return statId;
	}

	public void setStatId(Long statId) {
		this.statId = statId;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public String getViewdata() {
		return viewdata;
	}

	public void setViewdata(String viewdata) {
		this.viewdata = viewdata;
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

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public String getStatType() {
		return statType;
	}

	public void setStatType(String statType) {
		this.statType = statType;
	}

	public static Log getLog() {
		return log;
	}

	public String getCellName() {
		return cellName;
	}

	public void setCellName(String cellName) {
		this.cellName = cellName;
	}

	public String getCgi() {
		return cgi;
	}

	public void setCgi(String cgi) {
		this.cgi = cgi;
	}

//	public String getBscName() {
//		return bscName;
//	}
//
//	public void setBscName(String bscName) {
//		this.bscName = bscName;
//	}
//
//	public String getSgsnName() {
//		return sgsnName;
//	}
//
//	public void setSgsnName(String sgsnName) {
//		this.sgsnName = sgsnName;
//	}

	public Long getFlush() {
		return flush;
	}

	public void setFlush(Long flush) {
		this.flush = flush;
	}

	public Double getFlushMb() {
		return flushMb;
	}

	public void setFlushMb(Double flushMb) {
		this.flushMb = flushMb;
	}

	public Long getImsis() {
		return imsis;
	}

	public void setImsis(Long imsis) {
		this.imsis = imsis;
	}
	
	/**
	 * 通过计算获取统计值 当流量、用户等重数据库查询所得数据设置完成后 调用一次此方法计算统计值
	 * 
	 * @return ishour 是否是按小时统计
	 */
	public void calculate() {
		try {
			this.flushKb = CommonUtils.formatBToKb(flush);
			this.flushMb = CommonUtils.formatBToMb(flush);
			this.flushGb = CommonUtils.formatBToGb(flush);
			NumberFormat germanformat = NumberFormat.getInstance(Locale.CHINA);
			
			this.formatFlush = germanformat.format(flush);
			this.formatFlushKb = germanformat.format(flushKb);
			this.formatFlushMb = germanformat.format(flushMb);
			this.formatFlushGb = germanformat.format(flushGb);
			this.formatImsis = germanformat.format(imsis);
			
			this.nmAveFlushKB = this.nmAveFlush == 0 ? 0.0
					: CommonUtils.formatBToKb(this.nmAveFlush );
			this.nmAveFlushMB = this.nmAveFlush == 0 ? 0.0
					: CommonUtils.formatBToMb(this.nmAveFlush );
			
			
			String	month = (this.month < 10) ? "0" + this.month
							: this.month + "";
			String week = (this.week < 10) ? "0" + this.week : this.week
					+ "";
			String day = (this.day < 10) ? "0" + this.day : this.day + "";
			String hour = (this.hour < 10) ? "0" + this.hour : this.hour
					+ "";
						
			if(statType!=null){
				if(statType == CommonConstants.MANTOEYE_TIME_LEVEL_MONTH+""){
					this.viewdata = this.year +"-"+month;
				}else if(statType == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK+""){
					this.viewdata = this.year+"年第"+week+"周";
				}else if(statType == CommonConstants.MANTOEYE_TIME_LEVEL_DAY+""){
					this.viewdata = this.year +"-"+month+"-"+day;
				}else{
					this.viewdata = this.year +"-"+month+"-"+day+" "+hour+":00";
				}			
			}else{
				throw new Exception();
			}				
		} catch (Exception e) {
			log.error("计算统计值发生未知错误！");
		}
	}

	public Double getFlushKb() {
		return flushKb;
	}

	public Double getFlushGb() {
		return flushGb;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSubsidiaryCompany() {
		return subsidiaryCompany;
	}

	public void setSubsidiaryCompany(String subsidiaryCompany) {
		this.subsidiaryCompany = subsidiaryCompany;
	}

	public String getSaleArea() {
		return saleArea;
	}

	public void setSaleArea(String saleArea) {
		this.saleArea = saleArea;
	}

	public Double getNmAveFlush() {
		return nmAveFlush;
	}

	public void setNmAveFlush(Double nmAveFlush) {
		this.nmAveFlush = nmAveFlush;
	}

	public Double getNmAveFlushKB() {
		return nmAveFlushKB;
	}

	public void setNmAveFlushKB(Double nmAveFlushKB) {
		this.nmAveFlushKB = nmAveFlushKB;
	}

	public Double getNmAveFlushMB() {
		return nmAveFlushMB;
	}

	public void setNmAveFlushMB(Double nmAveFlushMB) {
		this.nmAveFlushMB = nmAveFlushMB;
	}
	
}
