package com.symbol.app.mantoeye.dto.blackberry;

import java.text.NumberFormat;
import java.util.Locale;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;


/**
 * 运营商
 * @author rankin
 *
 */
public class LdcUsersBean {
	private String fullDate;
	private String timeLevel;
	private Long intUpFlush = 0l;
	private Long intDownFlush = 0l;
	private Long totalFlush = 0l;
	private Long intImsis = 0l;// 用户量
	private Long visit = 0l;// 访问次数
	private Long activeCount = 0l;// 激活次数
	private int intYear;
	private int intMonth;
	private int intDay;
	private int intWeek;
	private int intHour;
	private String ldcId;// 运营商ID
	private String ldcName;// 运营商名称

	private String countryId;//国家ID
	private String countryName;//国家名
	
	private Double nmAveFlush = 0.0;// 平均流量

//	private String strUpFlush;// 单位为MB
//	private String strDownFlush;
//	private String strTotalFlush;
//	
//	private String strUpFlushKB;
//	private String strDownFlushKB;
//	private String strTotalFlushKB;
//	private String strUpFlushGB;
//	private String strDownFlushGB;
//	private String strTotalFlushGB;
//	private String strnmAveFlushKB;
//	private String strnmAveFlushMB;
//	private String strImsi;

	private Double upFlushKB;
	private Double downFlushKB;
	private Double totalFlushKB;
	private Double upFlushMB;
	private Double downFlushMB;
	private Double totalFlushMB;
	private Double upFlushGB;
	private Double downFlushGB;
	private Double totalFlushGB;
	private Double nmAveFlushKB;// 平均流量KB
	private Double nmAveFlushMB;// 平均流量MB
	
	private Double imsisWan;//用户数(万)
	private Double visitWan;//访问次数(万)
	private Double visitYi;//访问次数(亿)
	private Double upFlushTB;
	private Double downFlushTB;
	private Double totalFlushTB;
	private Double totalSendWan;//发送量(万)
	private Double totalSendYi;//发送量(亿)
	
	private Double totalFlushRate;//流量全网占比
	private Double imsisRate;//用户数全网占比
	/**
	 * 计算流量用户数的全网占比
	 */
	public void calculateFlushRate(double sumTotalFlush,double sumImsis){
		totalFlushRate = Common.StringTodouble(CommonUtils.formatPercent(totalFlush, sumTotalFlush));
		imsisRate = Common.StringTodouble(CommonUtils.formatPercent(intImsis, sumImsis));
	}
	public void calculateData() {
		
		this.upFlushMB = this.intUpFlush == 0 ? 0.0
				: CommonUtils.formatBToMb(this.intUpFlush );
		this.downFlushMB = this.intDownFlush == 0 ? 0.0
				: CommonUtils.formatBToMb(this.intDownFlush);
		this.totalFlushMB = this.totalFlush == 0 ? 0.0
				: CommonUtils.formatBToMb(this.totalFlush);
		this.upFlushKB = this.intUpFlush == 0 ? 0.0
				: CommonUtils.formatBToKb(this.intUpFlush );
		this.downFlushKB = this.intDownFlush == 0 ? 0.0
				: CommonUtils.formatBToKb(this.intDownFlush );
		this.totalFlushKB = this.totalFlush == 0 ? 0.0
				: CommonUtils.formatBToKb(this.totalFlush);
		this.upFlushGB = this.intUpFlush == 0 ? 0.0
				: CommonUtils.formatBToGb(this.intUpFlush);
		this.downFlushGB = this.intDownFlush == 0 ? 0.0
				: CommonUtils.formatBToGb(this.intDownFlush );
		this.totalFlushGB = this.totalFlush == 0 ? 0.0
				: CommonUtils.formatBToGb(this.totalFlush );

		this.nmAveFlushKB = this.nmAveFlush == 0 ? 0.0
				: CommonUtils.formatBToKb(this.nmAveFlush );
		this.nmAveFlushMB = this.nmAveFlush == 0 ? 0.0
				: CommonUtils.formatBToMb(this.nmAveFlush );
		
		
		this.imsisWan = this.intImsis == 0 ? 0.0
				: CommonUtils.formatToWan(this.intImsis*1.0 );
		this.visitWan = this.visit == 0 ? 0.0
				: CommonUtils.formatToWan(this.visit*1.0 );
		this.visitYi = this.visit == 0 ? 0.0
				: CommonUtils.formatToYi(this.visit*1.0 );
		this.upFlushTB = this.intUpFlush == 0 ? 0.0
				: CommonUtils.formatBToTb(this.intUpFlush );
		this.downFlushTB = this.intDownFlush == 0 ? 0.0
				: CommonUtils.formatBToTb(this.intDownFlush);
		this.totalFlushTB = this.totalFlush == 0 ? 0.0
				: CommonUtils.formatBToTb(this.totalFlush);
		
	}


	/**
	 * 设置跨度时间
	 * 
	 */
	public void setSpanDate( int timeLevel) {
		String year = this.intYear + "";
		String month = (this.intMonth < 10) ? "0" + this.intMonth
				: this.intMonth + "";
		String week = (this.intWeek < 10) ? "0" + this.intWeek : this.intWeek
				+ "";
		String day = (this.intDay < 10) ? "0" + this.intDay : this.intDay + "";
		String hour = (this.intHour < 10) ? "0" + this.intHour : this.intHour
				+ "";
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			this.fullDate = year + "-" + month + "-" + day + " " + hour
					+ ":00:00";
			this.timeLevel = "小时";
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			this.fullDate = year + "-" + month + "-" + day;
			this.timeLevel = "天";
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			this.fullDate = CommonUtils.getWeekRange(this.intYear,
					this.intMonth, this.intWeek);
			this.timeLevel = "周";
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			this.fullDate = year + "-" + month;
			this.timeLevel = "月";
			break;
		}
	}

//	/**
//	 * 流量数据格式
//	 */
//	public void numberFormatData() {
//		NumberFormat germanformat = NumberFormat.getInstance(Locale.CHINA);
//		setStrDownFlushGB(germanformat.format(this.downFlushGB));
//		setStrDownFlush(germanformat.format(this.downFlushMB));
//		setStrDownFlushKB(germanformat.format(this.downFlushKB));
//		setStrUpFlushGB(germanformat.format(this.upFlushGB));
//		setStrUpFlush(germanformat.format(this.upFlushMB));
//		setStrUpFlushKB(germanformat.format(this.upFlushKB));
//		setStrTotalFlushGB(germanformat.format(this.totalFlushGB));
//		setStrTotalFlush(germanformat.format(this.totalFlushMB));
//		setStrTotalFlushKB(germanformat.format(this.totalFlushKB));
//		setStrnmAveFlushKB(germanformat.format(this.nmAveFlushKB));
//		setStrnmAveFlushMB(germanformat.format(this.nmAveFlushMB));
//		setStrImsi(germanformat.format(this.getIntImsis()));
//	}
//	
//	
//	public String getStrImsi() {
//		return strImsi;
//	}
//
//
//	public void setStrImsi(String strImsi) {
//		this.strImsi = strImsi;
//	}


	public Double getUpFlushKB() {
		return upFlushKB;
	}

	public void setUpFlushKB(Double upFlushKB) {
		this.upFlushKB = upFlushKB;
	}

//	public String getStrUpFlushKB() {
//		return strUpFlushKB;
//	}
//
//	public void setStrUpFlushKB(String strUpFlushKB) {
//		this.strUpFlushKB = strUpFlushKB;
//	}
//
//	public String getStrDownFlushKB() {
//		return strDownFlushKB;
//	}
//
//	public void setStrDownFlushKB(String strDownFlushKB) {
//		this.strDownFlushKB = strDownFlushKB;
//	}
//
//	public String getStrTotalFlushKB() {
//		return strTotalFlushKB;
//	}
//
//	public void setStrTotalFlushKB(String strTotalFlushKB) {
//		this.strTotalFlushKB = strTotalFlushKB;
//	}
//
//	public String getStrUpFlushGB() {
//		return strUpFlushGB;
//	}
//
//	public void setStrUpFlushGB(String strUpFlushGB) {
//		this.strUpFlushGB = strUpFlushGB;
//	}
//
//	public String getStrDownFlushGB() {
//		return strDownFlushGB;
//	}
//
//	public void setStrDownFlushGB(String strDownFlushGB) {
//		this.strDownFlushGB = strDownFlushGB;
//	}
//
//	public String getStrTotalFlushGB() {
//		return strTotalFlushGB;
//	}
//
//	public void setStrTotalFlushGB(String strTotalFlushGB) {
//		this.strTotalFlushGB = strTotalFlushGB;
//	}

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

	public Double getUpFlushGB() {
		return upFlushGB;
	}

	public void setUpFlushGB(Double upFlushGB) {
		this.upFlushGB = upFlushGB;
	}

	public Double getDownFlushGB() {
		return downFlushGB;
	}

	public void setDownFlushGB(Double downFlushGB) {
		this.downFlushGB = downFlushGB;
	}

	public Double getTotalFlushGB() {
		return totalFlushGB;
	}

	public void setTotalFlushGB(Double totalFlushGB) {
		this.totalFlushGB = totalFlushGB;
	}

	public Long getIntUpFlush() {
		return this.intUpFlush;
	}

	public void setIntUpFlush(Long intUpFlush) {
		this.intUpFlush = intUpFlush;
	}

	public Long getIntDownFlush() {
		return this.intDownFlush;
	}

	public void setIntDownFlush(Long intDownFlush) {
		this.intDownFlush = intDownFlush;
	}

	public Long getTotalFlush() {
		return this.totalFlush;
	}

	public void setTotalFlush(Long totalFlush) {
		this.totalFlush = totalFlush;
	}

	public Long getIntImsis() {
		return this.intImsis;
	}

	public void setIntImsis(Long intImsis) {
		this.intImsis = intImsis;
	}

	public Long getVisit() {
		return this.visit;
	}

	public void setVisit(Long visit) {
		this.visit = visit;
	}

	public int getIntYear() {
		return this.intYear;
	}

	public void setIntYear(int intYear) {
		this.intYear = intYear;
	}

	public int getIntMonth() {
		return this.intMonth;
	}

	public void setIntMonth(int intMonth) {
		this.intMonth = intMonth;
	}

	public int getIntDay() {
		return this.intDay;
	}

	public void setIntDay(int intDay) {
		this.intDay = intDay;
	}

	public int getIntWeek() {
		return this.intWeek;
	}

	public void setIntWeek(int intWeek) {
		this.intWeek = intWeek;
	}

	public int getIntHour() {
		return this.intHour;
	}

	public void setIntHour(int intHour) {
		this.intHour = intHour;
	}

	public String getFullDate() {
		return this.fullDate;
	}

	public void setFullDate(String fullDate) {
		this.fullDate = fullDate;
	}

	public Long getActiveCount() {
		return this.activeCount;
	}

	public void setActiveCount(Long activeCount) {
		this.activeCount = activeCount;
	}

	public String getTimeLevel() {
		return this.timeLevel;
	}

	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

//	public String getStrUpFlush() {
//		return this.strUpFlush;
//	}
//
//	public void setStrUpFlush(String strUpFlush) {
//		this.strUpFlush = strUpFlush;
//	}
//
//	public String getStrDownFlush() {
//		return this.strDownFlush;
//	}
//
//	public void setStrDownFlush(String strDownFlush) {
//		this.strDownFlush = strDownFlush;
//	}
//
//	public String getStrTotalFlush() {
//		return this.strTotalFlush;
//	}
//
//	public void setStrTotalFlush(String strTotalFlush) {
//		this.strTotalFlush = strTotalFlush;
//	}
	public Double getNmAveFlush() {
		return nmAveFlush;
	}

	public void setNmAveFlush(Double nmAveFlush) {
		this.nmAveFlush = nmAveFlush;
	}

//	public String getStrnmAveFlushKB() {
//		return strnmAveFlushKB;
//	}
//
//	public void setStrnmAveFlushKB(String strnmAveFlushKB) {
//		this.strnmAveFlushKB = strnmAveFlushKB;
//	}
//
//	public String getStrnmAveFlushMB() {
//		return strnmAveFlushMB;
//	}
//
//	public void setStrnmAveFlushMB(String strnmAveFlushMB) {
//		this.strnmAveFlushMB = strnmAveFlushMB;
//	}

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

	public Double getImsisWan() {
		return imsisWan;
	}


	public Double getVisitWan() {
		return visitWan;
	}


	public Double getVisitYi() {
		return visitYi;
	}


	public Double getUpFlushTB() {
		return upFlushTB;
	}


	public Double getDownFlushTB() {
		return downFlushTB;
	}


	public Double getTotalFlushTB() {
		return totalFlushTB;
	}


	public void setImsisWan(Double imsisWan) {
		this.imsisWan = imsisWan;
	}


	public void setVisitWan(Double visitWan) {
		this.visitWan = visitWan;
	}


	public void setVisitYi(Double visitYi) {
		this.visitYi = visitYi;
	}


	public void setUpFlushTB(Double upFlushTB) {
		this.upFlushTB = upFlushTB;
	}


	public void setDownFlushTB(Double downFlushTB) {
		this.downFlushTB = downFlushTB;
	}


	public void setTotalFlushTB(Double totalFlushTB) {
		this.totalFlushTB = totalFlushTB;
	}


	public Double getTotalSendWan() {
		return totalSendWan;
	}


	public void setTotalSendWan(Double totalSendWan) {
		this.totalSendWan = totalSendWan;
	}


	public Double getTotalSendYi() {
		return totalSendYi;
	}

	public void setTotalSendYi(Double totalSendYi) {
		this.totalSendYi = totalSendYi;
	}


	public String getLdcId() {
		return ldcId;
	}


	public void setLdcId(String ldcId) {
		this.ldcId = ldcId;
	}


	public String getLdcName() {
		return ldcName;
	}


	public void setLdcName(String ldcName) {
		this.ldcName = ldcName;
	}


	public String getCountryId() {
		return countryId;
	}


	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}


	public String getCountryName() {
		return countryName;
	}


	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Double getTotalFlushRate() {
		return totalFlushRate;
	}
	public Double getImsisRate() {
		return imsisRate;
	}

}