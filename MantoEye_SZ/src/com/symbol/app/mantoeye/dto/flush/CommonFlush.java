package com.symbol.app.mantoeye.dto.flush;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;

public class CommonFlush {
	private String fullDate;
	private String timeLevel;
	private String context;// 二期新增
	private Long nmParentId;// 二期新增
	private Long nmGroupId;// 二期新增

	public Long getNmParentId() {
		return nmParentId;
	}

	public Long getNmGroupId() {
		return nmGroupId;
	}

	public void setNmGroupId(Long nmGroupId) {
		this.nmGroupId = nmGroupId;
	}

	public void setNmParentId(Long nmParentId) {
		this.nmParentId = nmParentId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	private int intYear;
	private int intMonth;
	private int intDay;
	private int intWeek;
	private int intHour;
	private int top;
	private String yysId;// 运营商ID
	private String yysName;// 运营商名称
	private Double nmAveFlush = 0.0;// 平均流量

	private String lineName = "";
	private String business = "其他";
	private String businessType = "其他";
	private String businessName;
	private String port = ""; // 端口
	private String title = "";// 主题
	private String upRate = "";// 上行占比
	private String downRate = "";// 下行占比
	private String flushRate = "";// 占比
	private String allFlushRate = "";// 全网占比
	private Long upSendFlush = 0l;// 上发送量
	private Long downSendFlush = 0l;// 下发送量
	private Long totalSendFlush = 0l;// 总发送量
	private Double upSendFlushKB = 0.0;
	private Double downSendFlushKB = 0.0;
	private Double totalSendFlushKB = 0.0;
	private Double upSendFlushMB = 0.0;
	private Double downSendFlushMB = 0.0;
	private Double totalSendFlushMB = 0.0;

	private String area;
	private String cgi;
	private String bsc;// bsc
	private String sgsn;// sgsn
	private String apnName;
	private String userBelong;// 用户归属
	private String company;// 公司

	private String cgiName;// CGI
	private String street;// 街道
	private String subsidiaryCompany;// 分公司
	private String saleArea;// 营销片区

	// private String strMmsUpSend;
	// private String strMmsDownSend;
	// private String strMmsTotalSend;

	// private String strUpFlush;// 单位为MB
	// private String strDownFlush;
	// private String strTotalFlush;
	// private String strVisit;
	// private String strActiveCount;
	// private String strImsi;
	// private String strUpFlushKB;
	// private String strDownFlushKB;
	// private String strTotalFlushKB;
	// private String strUpFlushGB;
	// private String strDownFlushGB;
	// private String strTotalFlushGB;
	// private String strnmAveFlushKB;
	// private String strnmAveFlushMB;

	private Long intUpFlush = 0l;
	private Long intDownFlush = 0l;
	private Long totalFlush = 0l;
	private Long localtotalFlush = 0l;
	private Long intImsis = 0l;// 用户量
	private Long localintImsis = 0l;// 用户量
	private Long visit = 0l;// 访问次数
	private Long activeCount = 0l;// 激活次数
	private Double upFlushKB;
	private Double downFlushKB;
	private Double totalFlushKB;
	private Double localtotalFlushKB;
	private Double upFlushMB;
	private Double downFlushMB;
	private Double totalFlushMB;
	private Double localtotalFlushMB;
	private Double upFlushGB;
	private Double downFlushGB;
	private Double totalFlushGB;
	private Double localtotalFlushGB;

	private Double nmAveFlushKB;// 平均流量KB
	private Double nmAveFlushMB;// 平均流量MB

	private Double imsisWan;// 用户数(万)
	private Double visitWan;// 访问次数(万)
	private Double visitYi;// 访问次数(亿)
	private Double upFlushTB;
	private Double downFlushTB;
	private Double totalFlushTB;
	private Double localtotalFlushTB;
	private Double totalSendWan;// 发送量(万)
	private Double totalSendYi;// 发送量(亿)

	private Double totalFlushRate;// 流量全网占比
	private Double imsisRate;// 用户数全网占比

	public void calculateData() {

		this.upFlushMB = this.intUpFlush == 0 ? 0.0 : CommonUtils
				.formatBToMb(this.intUpFlush);
		this.downFlushMB = this.intDownFlush == 0 ? 0.0 : CommonUtils
				.formatBToMb(this.intDownFlush);
		this.totalFlushMB = this.totalFlush == 0 ? 0.0 : CommonUtils
				.formatBToMb(this.totalFlush);
		this.localtotalFlushMB = this.localtotalFlush == 0 ? 0.0 : CommonUtils
				.formatBToMb(this.localtotalFlush);

		this.upFlushKB = this.intUpFlush == 0 ? 0.0 : CommonUtils
				.formatBToKb(this.intUpFlush);
		this.downFlushKB = this.intDownFlush == 0 ? 0.0 : CommonUtils
				.formatBToKb(this.intDownFlush);
		this.totalFlushKB = this.totalFlush == 0 ? 0.0 : CommonUtils
				.formatBToKb(this.totalFlush);
		this.localtotalFlushKB = this.localtotalFlush == 0 ? 0.0 : CommonUtils
				.formatBToKb(this.localtotalFlush);

		this.upFlushGB = this.intUpFlush == 0 ? 0.0 : CommonUtils
				.formatBToGb(this.intUpFlush);
		this.downFlushGB = this.intDownFlush == 0 ? 0.0 : CommonUtils
				.formatBToGb(this.intDownFlush);
		this.totalFlushGB = this.totalFlush == 0 ? 0.0 : CommonUtils
				.formatBToGb(this.totalFlush);
		this.localtotalFlushGB = this.localtotalFlush == 0 ? 0.0 : CommonUtils
				.formatBToGb(this.localtotalFlush);

		this.nmAveFlushKB = this.nmAveFlush == 0 ? 0.0 : CommonUtils
				.formatBToKb(this.nmAveFlush);
		this.nmAveFlushMB = this.nmAveFlush == 0 ? 0.0 : CommonUtils
				.formatBToMb(this.nmAveFlush);

		this.imsisWan = this.intImsis == 0 ? 0.0 : CommonUtils
				.formatToWan(this.intImsis * 1.0);
		this.visitWan = this.visit == 0 ? 0.0 : CommonUtils
				.formatToWan(this.visit * 1.0);
		this.visitYi = this.visit == 0 ? 0.0 : CommonUtils
				.formatToYi(this.visit * 1.0);
		this.upFlushTB = this.intUpFlush == 0 ? 0.0 : CommonUtils
				.formatBToTb(this.intUpFlush);
		this.downFlushTB = this.intDownFlush == 0 ? 0.0 : CommonUtils
				.formatBToTb(this.intDownFlush);
		this.totalFlushTB = this.totalFlush == 0 ? 0.0 : CommonUtils
				.formatBToTb(this.totalFlush);
		this.localtotalFlushTB = this.localtotalFlush == 0 ? 0.0 : CommonUtils
				.formatBToTb(this.localtotalFlush);

		this.totalSendWan = this.totalSendFlush == 0 ? 0.0 : CommonUtils
				.formatToWan(this.totalSendFlush * 1.0);
		this.totalSendYi = this.totalSendFlush == 0 ? 0.0 : CommonUtils
				.formatToYi(this.totalSendFlush * 1.0);
	}

	/**
	 * 计算流量用户数的全网占比
	 */
	public void calculateFlushRate(double sumTotalFlush, double sumImsis) {
		totalFlushRate = Common.StringTodouble(CommonUtils.formatPercent(
				totalFlush, sumTotalFlush));
		imsisRate = Common.StringTodouble(CommonUtils.formatPercent(intImsis,
				sumImsis));
	}

	/**
	 * 设置跨度时间
	 * 
	 */
	public void setSpanDate(int timeLevel) {
		String year = this.intYear + "";
		String month = "0";
		if (timeLevel != 3) {
			month = (this.intMonth < 10) ? "0" + this.intMonth : this.intMonth
					+ "";
		}
		// String week = (this.intWeek < 10) ? "0" + this.intWeek : this.intWeek
		// + "";
		String day = (this.intDay < 10) ? "0" + this.intDay : this.intDay + "";
		String hour = (this.intHour < 10) ? "0" + this.intHour : this.intHour
				+ "";
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			this.fullDate = year + "-" + month + "-" + day + " " + hour + ":00";
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

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the fullDate
	 */
	public String getFullDate() {
		return fullDate;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param fullDate
	 *            the fullDate to set
	 */
	public void setFullDate(String fullDate) {
		this.fullDate = fullDate;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the timeLevel
	 */
	public String getTimeLevel() {
		return timeLevel;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param timeLevel
	 *            the timeLevel to set
	 */
	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the intYear
	 */
	public int getIntYear() {
		return intYear;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param intYear
	 *            the intYear to set
	 */
	public void setIntYear(int intYear) {
		this.intYear = intYear;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the intMonth
	 */
	public int getIntMonth() {
		return intMonth;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param intMonth
	 *            the intMonth to set
	 */
	public void setIntMonth(int intMonth) {
		this.intMonth = intMonth;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the intDay
	 */
	public int getIntDay() {
		return intDay;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param intDay
	 *            the intDay to set
	 */
	public void setIntDay(int intDay) {
		this.intDay = intDay;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the intWeek
	 */
	public int getIntWeek() {
		return intWeek;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param intWeek
	 *            the intWeek to set
	 */
	public void setIntWeek(int intWeek) {
		this.intWeek = intWeek;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the intHour
	 */
	public int getIntHour() {
		return intHour;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param intHour
	 *            the intHour to set
	 */
	public void setIntHour(int intHour) {
		this.intHour = intHour;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the top
	 */
	public int getTop() {
		return top;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param top
	 *            the top to set
	 */
	public void setTop(int top) {
		this.top = top;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the yysId
	 */
	public String getYysId() {
		return yysId;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param yysId
	 *            the yysId to set
	 */
	public void setYysId(String yysId) {
		this.yysId = yysId;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the yysName
	 */
	public String getYysName() {
		return yysName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param yysName
	 *            the yysName to set
	 */
	public void setYysName(String yysName) {
		this.yysName = yysName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the nmAveFlush
	 */
	public Double getNmAveFlush() {
		return nmAveFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param nmAveFlush
	 *            the nmAveFlush to set
	 */
	public void setNmAveFlush(Double nmAveFlush) {
		this.nmAveFlush = nmAveFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the lineName
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param lineName
	 *            the lineName to set
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the business
	 */
	public String getBusiness() {
		return business;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param business
	 *            the business to set
	 */
	public void setBusiness(String business) {
		this.business = business;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the businessType
	 */
	public String getBusinessType() {
		return businessType;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param businessType
	 *            the businessType to set
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param businessName
	 *            the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param port
	 *            the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the upRate
	 */
	public String getUpRate() {
		return upRate;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param upRate
	 *            the upRate to set
	 */
	public void setUpRate(String upRate) {
		this.upRate = upRate;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the downRate
	 */
	public String getDownRate() {
		return downRate;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param downRate
	 *            the downRate to set
	 */
	public void setDownRate(String downRate) {
		this.downRate = downRate;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the flushRate
	 */
	public String getFlushRate() {
		return flushRate;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param flushRate
	 *            the flushRate to set
	 */
	public void setFlushRate(String flushRate) {
		this.flushRate = flushRate;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the allFlushRate
	 */
	public String getAllFlushRate() {
		return allFlushRate;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param allFlushRate
	 *            the allFlushRate to set
	 */
	public void setAllFlushRate(String allFlushRate) {
		this.allFlushRate = allFlushRate;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the upSendFlush
	 */
	public Long getUpSendFlush() {
		return upSendFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param upSendFlush
	 *            the upSendFlush to set
	 */
	public void setUpSendFlush(Long upSendFlush) {
		this.upSendFlush = upSendFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the downSendFlush
	 */
	public Long getDownSendFlush() {
		return downSendFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param downSendFlush
	 *            the downSendFlush to set
	 */
	public void setDownSendFlush(Long downSendFlush) {
		this.downSendFlush = downSendFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the totalSendFlush
	 */
	public Long getTotalSendFlush() {
		return totalSendFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param totalSendFlush
	 *            the totalSendFlush to set
	 */
	public void setTotalSendFlush(Long totalSendFlush) {
		this.totalSendFlush = totalSendFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the upSendFlushKB
	 */
	public Double getUpSendFlushKB() {
		return upSendFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param upSendFlushKB
	 *            the upSendFlushKB to set
	 */
	public void setUpSendFlushKB(Double upSendFlushKB) {
		this.upSendFlushKB = upSendFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the downSendFlushKB
	 */
	public Double getDownSendFlushKB() {
		return downSendFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param downSendFlushKB
	 *            the downSendFlushKB to set
	 */
	public void setDownSendFlushKB(Double downSendFlushKB) {
		this.downSendFlushKB = downSendFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the totalSendFlushKB
	 */
	public Double getTotalSendFlushKB() {
		return totalSendFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param totalSendFlushKB
	 *            the totalSendFlushKB to set
	 */
	public void setTotalSendFlushKB(Double totalSendFlushKB) {
		this.totalSendFlushKB = totalSendFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the upSendFlushMB
	 */
	public Double getUpSendFlushMB() {
		return upSendFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param upSendFlushMB
	 *            the upSendFlushMB to set
	 */
	public void setUpSendFlushMB(Double upSendFlushMB) {
		this.upSendFlushMB = upSendFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the downSendFlushMB
	 */
	public Double getDownSendFlushMB() {
		return downSendFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param downSendFlushMB
	 *            the downSendFlushMB to set
	 */
	public void setDownSendFlushMB(Double downSendFlushMB) {
		this.downSendFlushMB = downSendFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the totalSendFlushMB
	 */
	public Double getTotalSendFlushMB() {
		return totalSendFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param totalSendFlushMB
	 *            the totalSendFlushMB to set
	 */
	public void setTotalSendFlushMB(Double totalSendFlushMB) {
		this.totalSendFlushMB = totalSendFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the cgi
	 */
	public String getCgi() {
		return cgi;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param cgi
	 *            the cgi to set
	 */
	public void setCgi(String cgi) {
		this.cgi = cgi;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the bsc
	 */
	public String getBsc() {
		return bsc;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param bsc
	 *            the bsc to set
	 */
	public void setBsc(String bsc) {
		this.bsc = bsc;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the sgsn
	 */
	public String getSgsn() {
		return sgsn;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param sgsn
	 *            the sgsn to set
	 */
	public void setSgsn(String sgsn) {
		this.sgsn = sgsn;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the apnName
	 */
	public String getApnName() {
		return apnName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param apnName
	 *            the apnName to set
	 */
	public void setApnName(String apnName) {
		this.apnName = apnName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the userBelong
	 */
	public String getUserBelong() {
		return userBelong;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param userBelong
	 *            the userBelong to set
	 */
	public void setUserBelong(String userBelong) {
		this.userBelong = userBelong;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param company
	 *            the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the cgiName
	 */
	public String getCgiName() {
		return cgiName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param cgiName
	 *            the cgiName to set
	 */
	public void setCgiName(String cgiName) {
		this.cgiName = cgiName;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param street
	 *            the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the subsidiaryCompany
	 */
	public String getSubsidiaryCompany() {
		return subsidiaryCompany;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param subsidiaryCompany
	 *            the subsidiaryCompany to set
	 */
	public void setSubsidiaryCompany(String subsidiaryCompany) {
		this.subsidiaryCompany = subsidiaryCompany;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the saleArea
	 */
	public String getSaleArea() {
		return saleArea;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param saleArea
	 *            the saleArea to set
	 */
	public void setSaleArea(String saleArea) {
		this.saleArea = saleArea;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the intUpFlush
	 */
	public Long getIntUpFlush() {
		return intUpFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param intUpFlush
	 *            the intUpFlush to set
	 */
	public void setIntUpFlush(Long intUpFlush) {
		this.intUpFlush = intUpFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the intDownFlush
	 */
	public Long getIntDownFlush() {
		return intDownFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param intDownFlush
	 *            the intDownFlush to set
	 */
	public void setIntDownFlush(Long intDownFlush) {
		this.intDownFlush = intDownFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the totalFlush
	 */
	public Long getTotalFlush() {
		return totalFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param totalFlush
	 *            the totalFlush to set
	 */
	public void setTotalFlush(Long totalFlush) {
		this.totalFlush = totalFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the localtotalFlush
	 */
	public Long getLocaltotalFlush() {
		return localtotalFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param localtotalFlush
	 *            the localtotalFlush to set
	 */
	public void setLocaltotalFlush(Long localtotalFlush) {
		this.localtotalFlush = localtotalFlush;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the intImsis
	 */
	public Long getIntImsis() {
		return intImsis;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param intImsis
	 *            the intImsis to set
	 */
	public void setIntImsis(Long intImsis) {
		this.intImsis = intImsis;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the localintImsis
	 */
	public Long getLocalintImsis() {
		return localintImsis;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param localintImsis
	 *            the localintImsis to set
	 */
	public void setLocalintImsis(Long localintImsis) {
		this.localintImsis = localintImsis;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the visit
	 */
	public Long getVisit() {
		return visit;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param visit
	 *            the visit to set
	 */
	public void setVisit(Long visit) {
		this.visit = visit;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the activeCount
	 */
	public Long getActiveCount() {
		return activeCount;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param activeCount
	 *            the activeCount to set
	 */
	public void setActiveCount(Long activeCount) {
		this.activeCount = activeCount;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the upFlushKB
	 */
	public Double getUpFlushKB() {
		return upFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param upFlushKB
	 *            the upFlushKB to set
	 */
	public void setUpFlushKB(Double upFlushKB) {
		this.upFlushKB = upFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the downFlushKB
	 */
	public Double getDownFlushKB() {
		return downFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param downFlushKB
	 *            the downFlushKB to set
	 */
	public void setDownFlushKB(Double downFlushKB) {
		this.downFlushKB = downFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the totalFlushKB
	 */
	public Double getTotalFlushKB() {
		return totalFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param totalFlushKB
	 *            the totalFlushKB to set
	 */
	public void setTotalFlushKB(Double totalFlushKB) {
		this.totalFlushKB = totalFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the localtotalFlushKB
	 */
	public Double getLocaltotalFlushKB() {
		return localtotalFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param localtotalFlushKB
	 *            the localtotalFlushKB to set
	 */
	public void setLocaltotalFlushKB(Double localtotalFlushKB) {
		this.localtotalFlushKB = localtotalFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the upFlushMB
	 */
	public Double getUpFlushMB() {
		return upFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param upFlushMB
	 *            the upFlushMB to set
	 */
	public void setUpFlushMB(Double upFlushMB) {
		this.upFlushMB = upFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the downFlushMB
	 */
	public Double getDownFlushMB() {
		return downFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param downFlushMB
	 *            the downFlushMB to set
	 */
	public void setDownFlushMB(Double downFlushMB) {
		this.downFlushMB = downFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the totalFlushMB
	 */
	public Double getTotalFlushMB() {
		return totalFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param totalFlushMB
	 *            the totalFlushMB to set
	 */
	public void setTotalFlushMB(Double totalFlushMB) {
		this.totalFlushMB = totalFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the localtotalFlushMB
	 */
	public Double getLocaltotalFlushMB() {
		return localtotalFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param localtotalFlushMB
	 *            the localtotalFlushMB to set
	 */
	public void setLocaltotalFlushMB(Double localtotalFlushMB) {
		this.localtotalFlushMB = localtotalFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the upFlushGB
	 */
	public Double getUpFlushGB() {
		return upFlushGB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param upFlushGB
	 *            the upFlushGB to set
	 */
	public void setUpFlushGB(Double upFlushGB) {
		this.upFlushGB = upFlushGB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the downFlushGB
	 */
	public Double getDownFlushGB() {
		return downFlushGB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param downFlushGB
	 *            the downFlushGB to set
	 */
	public void setDownFlushGB(Double downFlushGB) {
		this.downFlushGB = downFlushGB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the totalFlushGB
	 */
	public Double getTotalFlushGB() {
		return totalFlushGB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param totalFlushGB
	 *            the totalFlushGB to set
	 */
	public void setTotalFlushGB(Double totalFlushGB) {
		this.totalFlushGB = totalFlushGB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the localtotalFlushGB
	 */
	public Double getLocaltotalFlushGB() {
		return localtotalFlushGB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param localtotalFlushGB
	 *            the localtotalFlushGB to set
	 */
	public void setLocaltotalFlushGB(Double localtotalFlushGB) {
		this.localtotalFlushGB = localtotalFlushGB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the nmAveFlushKB
	 */
	public Double getNmAveFlushKB() {
		return nmAveFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param nmAveFlushKB
	 *            the nmAveFlushKB to set
	 */
	public void setNmAveFlushKB(Double nmAveFlushKB) {
		this.nmAveFlushKB = nmAveFlushKB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the nmAveFlushMB
	 */
	public Double getNmAveFlushMB() {
		return nmAveFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param nmAveFlushMB
	 *            the nmAveFlushMB to set
	 */
	public void setNmAveFlushMB(Double nmAveFlushMB) {
		this.nmAveFlushMB = nmAveFlushMB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the imsisWan
	 */
	public Double getImsisWan() {
		return imsisWan;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param imsisWan
	 *            the imsisWan to set
	 */
	public void setImsisWan(Double imsisWan) {
		this.imsisWan = imsisWan;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the visitWan
	 */
	public Double getVisitWan() {
		return visitWan;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param visitWan
	 *            the visitWan to set
	 */
	public void setVisitWan(Double visitWan) {
		this.visitWan = visitWan;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the visitYi
	 */
	public Double getVisitYi() {
		return visitYi;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param visitYi
	 *            the visitYi to set
	 */
	public void setVisitYi(Double visitYi) {
		this.visitYi = visitYi;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the upFlushTB
	 */
	public Double getUpFlushTB() {
		return upFlushTB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param upFlushTB
	 *            the upFlushTB to set
	 */
	public void setUpFlushTB(Double upFlushTB) {
		this.upFlushTB = upFlushTB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the downFlushTB
	 */
	public Double getDownFlushTB() {
		return downFlushTB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param downFlushTB
	 *            the downFlushTB to set
	 */
	public void setDownFlushTB(Double downFlushTB) {
		this.downFlushTB = downFlushTB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the totalFlushTB
	 */
	public Double getTotalFlushTB() {
		return totalFlushTB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param totalFlushTB
	 *            the totalFlushTB to set
	 */
	public void setTotalFlushTB(Double totalFlushTB) {
		this.totalFlushTB = totalFlushTB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the localtotalFlushTB
	 */
	public Double getLocaltotalFlushTB() {
		return localtotalFlushTB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param localtotalFlushTB
	 *            the localtotalFlushTB to set
	 */
	public void setLocaltotalFlushTB(Double localtotalFlushTB) {
		this.localtotalFlushTB = localtotalFlushTB;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the totalSendWan
	 */
	public Double getTotalSendWan() {
		return totalSendWan;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param totalSendWan
	 *            the totalSendWan to set
	 */
	public void setTotalSendWan(Double totalSendWan) {
		this.totalSendWan = totalSendWan;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the totalSendYi
	 */
	public Double getTotalSendYi() {
		return totalSendYi;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param totalSendYi
	 *            the totalSendYi to set
	 */
	public void setTotalSendYi(Double totalSendYi) {
		this.totalSendYi = totalSendYi;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the totalFlushRate
	 */
	public Double getTotalFlushRate() {
		return totalFlushRate;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param totalFlushRate
	 *            the totalFlushRate to set
	 */
	public void setTotalFlushRate(Double totalFlushRate) {
		this.totalFlushRate = totalFlushRate;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @return the imsisRate
	 */
	public Double getImsisRate() {
		return imsisRate;
	}

	/**
	 * <br>
	 * Created on: 2013-10-28 上午10:58:08
	 * 
	 * @param imsisRate
	 *            the imsisRate to set
	 */
	public void setImsisRate(Double imsisRate) {
		this.imsisRate = imsisRate;
	}

	// /**
	// * 流量数据格式
	// */
	// public void numberFormatData() {
	// NumberFormat germanformat = NumberFormat.getInstance(Locale.CHINA);
	// setStrActiveCount(germanformat.format(getActiveCount()));
	// setStrVisit(germanformat.format(this.getVisit()));
	// setStrImsi(germanformat.format(this.getIntImsis()));
	// setStrDownFlushGB(germanformat.format(this.downFlushGB));
	// setStrDownFlush(germanformat.format(this.downFlushMB));
	// setStrDownFlushKB(germanformat.format(this.downFlushKB));
	// setStrUpFlushGB(germanformat.format(this.upFlushGB));
	// setStrUpFlush(germanformat.format(this.upFlushMB));
	// setStrUpFlushKB(germanformat.format(this.upFlushKB));
	// setStrTotalFlushGB(germanformat.format(this.totalFlushGB));
	// setStrTotalFlush(germanformat.format(this.totalFlushMB));
	// setStrTotalFlushKB(germanformat.format(this.totalFlushKB));
	// setStrnmAveFlushKB(germanformat.format(this.nmAveFlushKB));
	// setStrnmAveFlushMB(germanformat.format(this.nmAveFlushMB));
	// }

	// /**
	// * 彩信数据格式
	// */
	// public void formatMmsData() {
	// NumberFormat germanformat = NumberFormat.getInstance(Locale.CHINA);
	// this.setStrMmsUpSend(germanformat.format(this.upSendFlush));
	// this.setStrMmsDownSend(germanformat.format(this.downSendFlush));
	// this.setStrMmsTotalSend(germanformat.format(this.totalSendFlush));
	//
	// }

}