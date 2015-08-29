package com.symbol.app.mantoeye.dto.sports;

import java.util.Date;
import java.util.List;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;

public class CommonSport implements java.io.Serializable{
	private String statdate;
	// 拆分ftbOverView_sport表的context字段为context1，context2
	private String context1;
	private String context2;
	private String context;
	private String detail;
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	private String vcNote;
	private Double totalFlushRate;// 流量全网占比
	private Double imsisRate;// 用户数全网占比

	public Double getTotalFlushRate() {
		return totalFlushRate;
	}

	public void setTotalFlushRate(Double totalFlushRate) {
		this.totalFlushRate = totalFlushRate;
	}

	public Double getImsisRate() {
		return imsisRate;
	}

	public void setImsisRate(Double imsisRate) {
		this.imsisRate = imsisRate;
	}

	public String getVcNote() {
		return vcNote;
	}

	public void setVcNote(String vcNote) {
		this.vcNote = vcNote;
	}

	private Date dtUpdateTime;
	private String nmMsisdn;
	private Long id;
	private String vcAreaName;
	private Long nmAreaId;
	private String vcCgiName;
	private List<String> cgiList;// 小区cgi集合

	public List<String> getCgiList() {
		return cgiList;
	}

	public void setCgiList(List<String> cgiList) {
		this.cgiList = cgiList;
	}

	public Long getNmAreaId() {
		return nmAreaId;
	}

	public void setNmAreaId(Long nmAreaId) {
		this.nmAreaId = nmAreaId;
	}

	public String getVcAreaName() {
		return vcAreaName;
	}

	public void setVcAreaName(String vcAreaName) {
		this.vcAreaName = vcAreaName;
	}

	public String getVcCgiName() {
		return vcCgiName;
	}

	public void setVcCgiName(String vcCgiName) {
		this.vcCgiName = vcCgiName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDtUpdateTime() {
		return dtUpdateTime;
	}

	public void setDtUpdateTime(Date dtUpdateTime) {
		this.dtUpdateTime = dtUpdateTime;
	}

	public String getNmMsisdn() {
		return nmMsisdn;
	}

	public void setNmMsisdn(String nmMsisdn) {
		this.nmMsisdn = nmMsisdn;
	}

	public Long getNmUsers() {
		return nmUsers;
	}

	public void setNmUsers(Long nmUsers) {
		this.nmUsers = nmUsers;
	}

	public Long getNmFlush() {
		return nmFlush;
	}

	public void setNmFlush(Long nmFlush) {
		this.nmFlush = nmFlush;
	}

	public int getIntYear() {
		return intYear;
	}

	public void setIntYear(int intYear) {
		this.intYear = intYear;
	}

	public int getIntMonth() {
		return intMonth;
	}

	public void setIntMonth(int intMonth) {
		this.intMonth = intMonth;
	}

	public int getIntWeek() {
		return intWeek;
	}

	public void setIntWeek(int intWeek) {
		this.intWeek = intWeek;
	}

	public int getIntDay() {
		return intDay;
	}

	public void setIntDay(int intDay) {
		this.intDay = intDay;
	}

	public int getIntHour() {
		return intHour;
	}

	public void setIntHour(int intHour) {
		this.intHour = intHour;
	}

	public String getVcCGI() {
		return vcCGI;
	}

	public void setVcCGI(String vcCGI) {
		this.vcCGI = vcCGI;
	}

	private String terminalSystem;// 终端系统名称
	private Long nmUsers;// 用户数
	private String nmFlushLevel;// 流量层次
	private Long nmOFlush;// 旧终端 流量
	private Double nmOFlushKB;
	private Double nmOFlushMB;
	private Double nmOFlushGB;
	private Long nmFlush;// 总流量
	private Double nmFlushKB;
	private Double nmFlushMB;
	private Double nmFlushGB;
	private int intYear;// 年
	private int intMonth;// 月
	private int intWeek;// 周
	private int intDay;// 日
	private int intHour;// 小时
	private String vcCGI;// 小区
	private String intType;// 业务类型
	private String OTreminal;// 旧终端
	private String NTerminal;// 新终端
	private int State;// 状态
	private String business;// 业务
	private Long businessId;

	//////////////////////////////////以下终端升级 11-11-3（zhf）/////////////////////////////////////////////////////////
	private long nmTerminalChangeId;//终端升级表ID
	private long nmTerminalChangeIdTaskId;//终端升级任务表ID
	private int dataType;//时间类型，即时间粒度
	
	
	
	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public long getNmTerminalChangeId() {
		return nmTerminalChangeId;
	}

	public void setNmTerminalChangeId(long nmTerminalChangeId) {
		this.nmTerminalChangeId = nmTerminalChangeId;
	}

	public long getNmTerminalChangeIdTaskId() {
		return nmTerminalChangeIdTaskId;
	}

	public void setNmTerminalChangeIdTaskId(long nmTerminalChangeIdTaskId) {
		this.nmTerminalChangeIdTaskId = nmTerminalChangeIdTaskId;
	}

	//////////////////////////////////以上终端升级 11-11-3（zhf）/////////////////////////////////////////////////////////
	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	private String task;// 任务名称
	private long nmDataOutPutTaskId;// 自定义输出任务表ID
	private int intTaskDelong;// 任务所属模块标识
	private String vcTaskName;// 任务名
	private int intTaskType;// 任务类别
	private String dtOrderTime;// 任务订制时间
	private String vcTaskOrder;// 任务定制人
	private String dtStartTime;// 任务开始时间
	private String dtEndTime;// 任务结束时间
	private int bitTaskActiveFlag;// 任务激活标识
	private int intTaskStatus;// 任务状态

	public void calculateData() {

		this.nmFlushKB = this.nmFlush == 0 ? 0.0 : CommonUtils
				.formatBToKb(this.nmFlush);

		this.nmFlushMB = this.nmFlush == 0 ? 0.0 : CommonUtils
				.formatBToMb(this.nmFlush);

		this.nmFlushGB = this.nmFlush == 0 ? 0.0 : CommonUtils
				.formatBToGb(this.nmFlush);
		if (this.nmOFlush != null) {
			this.nmOFlushKB = this.nmOFlush == 0 ? 0.0 : CommonUtils
					.formatBToKb(this.nmOFlush);

			this.nmOFlushMB = this.nmOFlush == 0 ? 0.0 : CommonUtils
					.formatBToMb(this.nmOFlush);

			this.nmOFlushGB = this.nmOFlush == 0 ? 0.0 : CommonUtils
					.formatBToGb(this.nmOFlush);
		}

	}

	public long getNmDataOutPutTaskId() {
		return nmDataOutPutTaskId;
	}

	public void setNmDataOutPutTaskId(long nmDataOutPutTaskId) {
		this.nmDataOutPutTaskId = nmDataOutPutTaskId;
	}

	public int getIntTaskDelong() {
		return intTaskDelong;
	}

	public void setIntTaskDelong(int intTaskDelong) {
		this.intTaskDelong = intTaskDelong;
	}

	public String getVcTaskName() {
		return vcTaskName;
	}

	public void setVcTaskName(String vcTaskName) {
		this.vcTaskName = vcTaskName;
	}

	public int getIntTaskType() {
		return intTaskType;
	}

	public void setIntTaskType(int intTaskType) {
		this.intTaskType = intTaskType;
	}

	public String getDtOrderTime() {
		return dtOrderTime;
	}

	public void setDtOrderTime(String dtOrderTime) {
		this.dtOrderTime = dtOrderTime;
	}

	public String getVcTaskOrder() {
		return vcTaskOrder;
	}

	public void setVcTaskOrder(String vcTaskOrder) {
		this.vcTaskOrder = vcTaskOrder;
	}

	public String getDtStartTime() {
		return dtStartTime;
	}

	public void setDtStartTime(String dtStartTime) {
		this.dtStartTime = dtStartTime;
	}

	public String getDtEndTime() {
		return dtEndTime;
	}

	public void setDtEndTime(String dtEndTime) {
		this.dtEndTime = dtEndTime;
	}

	public int getBitTaskActiveFlag() {
		return bitTaskActiveFlag;
	}

	public void setBitTaskActiveFlag(int bitTaskActiveFlag) {
		this.bitTaskActiveFlag = bitTaskActiveFlag;
	}

	public int getIntTaskStatus() {
		return intTaskStatus;
	}

	public void setIntTaskStatus(int intTaskStatus) {
		this.intTaskStatus = intTaskStatus;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getTerminalSystem() {
		return terminalSystem;
	}

	public void setTerminalSystem(String terminalSystem) {
		this.terminalSystem = terminalSystem;
	}

	public String getNmFlushLevel() {
		return nmFlushLevel;
	}

	public void setNmFlushLevel(String nmFlushLevel) {
		this.nmFlushLevel = nmFlushLevel;
	}

	public Long getNmOFlush() {
		return nmOFlush;
	}

	public void setNmOFlush(Long nmOFlush) {
		this.nmOFlush = nmOFlush;
	}

	public Double getNmOFlushKB() {
		return nmOFlushKB;
	}

	public void setNmOFlushKB(Double nmOFlushKB) {
		this.nmOFlushKB = nmOFlushKB;
	}

	public Double getNmOFlushMB() {
		return nmOFlushMB;
	}

	public void setNmOFlushMB(Double nmOFlushMB) {
		this.nmOFlushMB = nmOFlushMB;
	}

	public Double getNmOFlushGB() {
		return nmOFlushGB;
	}

	public void setNmOFlushGB(Double nmOFlushGB) {
		this.nmOFlushGB = nmOFlushGB;
	}

	public String getOTreminal() {
		return OTreminal;
	}

	public void setOTreminal(String oTreminal) {
		OTreminal = oTreminal;
	}

	public String getNTerminal() {
		return NTerminal;
	}

	public void setNTerminal(String nTerminal) {
		NTerminal = nTerminal;
	}

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public Double getNmFlushKB() {
		return nmFlushKB;
	}

	public void setNmFlushKB(Double nmFlushKB) {
		this.nmFlushKB = nmFlushKB;
	}

	public Double getNmFlushMB() {
		return nmFlushMB;
	}

	public void setNmFlushMB(Double nmFlushMB) {
		this.nmFlushMB = nmFlushMB;
	}

	public Double getNmFlushGB() {
		return nmFlushGB;
	}

	public void setNmFlushGB(Double nmFlushGB) {
		this.nmFlushGB = nmFlushGB;
	}

	public String getIntType() {
		return intType;
	}

	public void setIntType(String intType) {
		this.intType = intType;
	}

	public String getContext1() {
		return context1;
	}

	public void setContext1(String context1) {
		this.context1 = context1;
	}

	public String getContext2() {
		return context2;
	}

	public void setContext2(String context2) {
		this.context2 = context2;
	}

	public String getStatdate() {
		return statdate;
	}

	public void setStatdate(String statdate) {
		this.statdate = statdate;
	}

	public void setSpanDate(int timeLevel) {
		String year = this.intYear + "";
		String month = "0";
		String day = "0";
		String hour = "0";
		String week = "0";
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			month = (this.intMonth < 10) ? "0" + this.intMonth : this.intMonth
					+ "";
			day = (this.intDay < 10) ? "0" + this.intDay : this.intDay + "";
			hour = (this.intHour < 10) ? "0" + this.intHour : this.intHour + "";
			this.statdate = year + "-" + month + "-" + day + " " + hour
					+ ":00:00";

			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			month = (this.intMonth < 10) ? "0" + this.intMonth : this.intMonth
					+ "";
			day = (this.intDay < 10) ? "0" + this.intDay : this.intDay + "";
			this.statdate = year + "-" + month + "-" + day;

			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:

			week = (this.intWeek < 10) ? "0" + this.intWeek : this.intWeek + "";
			this.statdate = CommonUtils.getWeekRange(this.intYear, 0,
					this.intWeek);

			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			month = (this.intMonth < 10) ? "0" + this.intMonth : this.intMonth
					+ "";
			this.statdate = year + "-" + month;

			break;
		}
	}

	// 终端区域分布
	private String area;
	private String terminalBrand;
	private String systemType;

	public String getSystemType() {
		return systemType;
	}

	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	private String businessName;
	private String terminalType;
	private String fullDate;
	private String timeLevel;
	private Long intUpFlush = 0l;
	private Long intDownFlush = 0l;
	private Long totalFlush = 0l;
	private Long intImsis = 0l;// 用户量
	private Long visit = 0l;// 访问次数
	private Long activeCount = 0l;// 激活次数
	private Double upFlushKB;
	private Double downFlushKB;
	private Double totalFlushKB;
	private Double upFlushMB;
	private Double downFlushMB;
	private Double totalFlushMB;
	private Double upFlushGB;
	private Double downFlushGB;
	private Double totalFlushGB;
	private Double nmAveFlush = 0.0;// 平均流量
	private Double nmAveFlushKB;// 平均流量KB
	private Double nmAveFlushMB;// 平均流量MB

	private Double imsisWan;// 用户数(万)
	private Double visitWan;// 访问次数(万)
	private Double visitYi;// 访问次数(亿)
	private Double upFlushTB;
	private Double downFlushTB;
	private Double totalFlushTB;
	private String flushRate = "";// 占比

	public String getFlushRate() {
		return flushRate;
	}

	public void setFlushRate(String flushRate) {
		this.flushRate = flushRate;
	}

	/**
	 * 设置跨度时间
	 * 
	 */
	public void setSpanDate1(int timeLevel) {
		String year = this.intYear + "";
		String month = "0";
		if (timeLevel != 3) {
			month = (this.intMonth < 10) ? "0" + this.intMonth : this.intMonth
					+ "";
		}
		String week = (this.intWeek < 10) ? "0" + this.intWeek : this.intWeek
				+ "";
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

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTerminalBrand() {
		return terminalBrand;
	}

	public void setTerminalBrand(String terminalBrand) {
		this.terminalBrand = terminalBrand;
	}

	public String getFullDate() {
		return fullDate;
	}

	public void setFullDate(String fullDate) {
		this.fullDate = fullDate;
	}

	public String getTimeLevel() {
		return timeLevel;
	}

	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

	public Double getUpFlushTB() {
		return upFlushTB;
	}

	public void setUpFlushTB(Double upFlushTB) {
		this.upFlushTB = upFlushTB;
	}

	public Double getDownFlushTB() {
		return downFlushTB;
	}

	public void setDownFlushTB(Double downFlushTB) {
		this.downFlushTB = downFlushTB;
	}

	public Double getTotalFlushTB() {
		return totalFlushTB;
	}

	public void setTotalFlushTB(Double totalFlushTB) {
		this.totalFlushTB = totalFlushTB;
	}

	public void calculateData1() {

		this.upFlushMB = this.intUpFlush == 0 ? 0.0 : CommonUtils
				.formatBToMb(this.intUpFlush);
		this.downFlushMB = this.intDownFlush == 0 ? 0.0 : CommonUtils
				.formatBToMb(this.intDownFlush);
		this.totalFlushMB = this.totalFlush == 0 ? 0.0 : CommonUtils
				.formatBToMb(this.totalFlush);
		this.upFlushKB = this.intUpFlush == 0 ? 0.0 : CommonUtils
				.formatBToKb(this.intUpFlush);
		this.downFlushKB = this.intDownFlush == 0 ? 0.0 : CommonUtils
				.formatBToKb(this.intDownFlush);
		this.totalFlushKB = this.totalFlush == 0 ? 0.0 : CommonUtils
				.formatBToKb(this.totalFlush);
		this.upFlushGB = this.intUpFlush == 0 ? 0.0 : CommonUtils
				.formatBToGb(this.intUpFlush);
		this.downFlushGB = this.intDownFlush == 0 ? 0.0 : CommonUtils
				.formatBToGb(this.intDownFlush);
		this.totalFlushGB = this.totalFlush == 0 ? 0.0 : CommonUtils
				.formatBToGb(this.totalFlush);

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

	public void setImsisWan(Double imsisWan) {
		this.imsisWan = imsisWan;
	}

	public Double getVisitWan() {
		return visitWan;
	}

	public void setVisitWan(Double visitWan) {
		this.visitWan = visitWan;
	}

	public Double getVisitYi() {
		return visitYi;
	}

	public void setVisitYi(Double visitYi) {
		this.visitYi = visitYi;
	}

	public Long getIntUpFlush() {
		return intUpFlush;
	}

	public void setIntUpFlush(Long intUpFlush) {
		this.intUpFlush = intUpFlush;
	}

	public Long getIntDownFlush() {
		return intDownFlush;
	}

	public void setIntDownFlush(Long intDownFlush) {
		this.intDownFlush = intDownFlush;
	}

	public Long getTotalFlush() {
		return totalFlush;
	}

	public void setTotalFlush(Long totalFlush) {
		this.totalFlush = totalFlush;
	}

	public Long getIntImsis() {
		return intImsis;
	}

	public void setIntImsis(Long intImsis) {
		this.intImsis = intImsis;
	}

	public Long getVisit() {
		return visit;
	}

	public void setVisit(Long visit) {
		this.visit = visit;
	}

	public Long getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(Long activeCount) {
		this.activeCount = activeCount;
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

	public Double getNmAveFlush() {
		return nmAveFlush;
	}

	public void setNmAveFlush(Double nmAveFlush) {
		this.nmAveFlush = nmAveFlush;
	}

	// 自定义数据输出
	private String vcUrl;
	private String vcImei;

	public String getVcUrl() {
		return vcUrl;
	}

	public void setVcUrl(String vcUrl) {
		this.vcUrl = vcUrl;
	}

	public String getVcImei() {
		return vcImei;
	}

	public void setVcImei(String vcImei) {
		this.vcImei = vcImei;
	}

	// ///////////////////业务健康度/////////////////////
	private long nmNetworkTaskId;
	private int intSuccess;//PDP成功次数
	private int	intTimes;//PDP成功总次数
	private int intAppSuccessful;//回话连接成功次数
	private int intAppTimes;//会话连接成功总次数
	private long intAppAckTime;// 响应时间总和
	private long intAppAckTimes;//响应时间总条数
	private int intActiveDay;//活跃天数
	private long nmActiveUsers;//业务活跃用户数
	private long nmAllUsers;//业务总用户数
	private long nmBussinessId;//业务ID
	
	public long getNmNetworkTaskId() {
		return nmNetworkTaskId;
	}

	public void setNmNetworkTaskId(long nmNetworkTaskId) {
		this.nmNetworkTaskId = nmNetworkTaskId;
	}

	public int getIntSuccess() {
		return intSuccess;
	}

	public void setIntSuccess(int intSuccess) {
		this.intSuccess = intSuccess;
	}

	public int getIntTimes() {
		return intTimes;
	}

	public void setIntTimes(int intTimes) {
		this.intTimes = intTimes;
	}

	public int getIntAppSuccessful() {
		return intAppSuccessful;
	}

	public void setIntAppSuccessful(int intAppSuccessful) {
		this.intAppSuccessful = intAppSuccessful;
	}

	public int getIntAppTimes() {
		return intAppTimes;
	}

	public void setIntAppTimes(int intAppTimes) {
		this.intAppTimes = intAppTimes;
	}

	public long getIntAppAckTime() {
		return intAppAckTime;
	}

	public void setIntAppAckTime(long intAppAckTime) {
		this.intAppAckTime = intAppAckTime;
	}

	public long getIntAppAckTimes() {
		return intAppAckTimes;
	}

	public void setIntAppAckTimes(long intAppAckTimes) {
		this.intAppAckTimes = intAppAckTimes;
	}

	public int getIntActiveDay() {
		return intActiveDay;
	}

	public void setIntActiveDay(int intActiveDay) {
		this.intActiveDay = intActiveDay;
	}

	public long getNmActiveUsers() {
		return nmActiveUsers;
	}

	public void setNmActiveUsers(long nmActiveUsers) {
		this.nmActiveUsers = nmActiveUsers;
	}

	public long getNmAllUsers() {
		return nmAllUsers;
	}

	public void setNmAllUsers(long nmAllUsers) {
		this.nmAllUsers = nmAllUsers;
	}

	public long getNmBussinessId() {
		return nmBussinessId;
	}

	public void setNmBussinessId(long nmBussinessId) {
		this.nmBussinessId = nmBussinessId;
	}
	
	/*
	 * 业务健康度新加字段 2011-11-11 zhf
	 * */
	private String intScale;//指标权重
	private String intCount;//计算值
	private String intMark;//T0值
	private double sum;
	
	/*
	 * 2011-12-23 获取时间字段的年月日小时分钟
	 * 
	 * */
	public void convertFullDate(){
		if (this.fullDate!=null&&!("").equals(this.fullDate)) {
			String ymd=this.fullDate.split(" ")[0];
			String hm=this.fullDate.split(" ")[1];
			String[] hmdates=hm.split(":");
			String hour=hmdates[0];
			String mm=hmdates[1];
		this.fullDate=ymd+" "+hour+":"+mm;
		}
		
	}
	
	
	
	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public String getIntScale() {
		return intScale;
	}

	public void setIntScale(String intScale) {
		this.intScale = intScale;
	}

	public String getIntCount() {
		return intCount;
	}

	public void setIntCount(String intCount) {
		this.intCount = intCount;
	}

	public String getIntMark() {
		return intMark;
	}

	public void setIntMark(String intMark) {
		this.intMark = intMark;
	}
	
	/////////////////////////////////以上为任务健康度//////////////////////////////////////////
	
	

	private long nmTerminalId;
	private String vcTerminalTac;
	private String vcTerminalBrand;
	private String vcTerminalName;
	private String vcTerminalNetType;
	private String vcTerminalOS;
	private String vcName;
	private String vcBussinessName;
	private String flushlevel;
	private long nmStatFlushLayeringId;
	


	public long getNmStatFlushLayeringId() {
		return nmStatFlushLayeringId;
	}

	public void setNmStatFlushLayeringId(long nmStatFlushLayeringId) {
		this.nmStatFlushLayeringId = nmStatFlushLayeringId;
	}

	public String getFlushlevel() {
		return flushlevel;
	}

	public void setFlushlevel(String flushlevel) {
		this.flushlevel = flushlevel;
	}

	public String getVcBussinessName() {
		return vcBussinessName;
	}

	public void setVcBussinessName(String vcBussinessName) {
		this.vcBussinessName = vcBussinessName;
	}

	public String getVcName() {
		return vcName;
	}

	public void setVcName(String vcName) {
		this.vcName = vcName;
	}

	public String getVcTerminalNetType() {
		return vcTerminalNetType;
	}

	public void setVcTerminalNetType(String vcTerminalNetType) {
		this.vcTerminalNetType = vcTerminalNetType;
	}

	public String getVcTerminalOS() {
		return vcTerminalOS;
	}

	public void setVcTerminalOS(String vcTerminalOS) {
		this.vcTerminalOS = vcTerminalOS;
	}

	public long getNmTerminalId() {
		return nmTerminalId;
	}

	public void setNmTerminalId(long nmTerminalId) {
		this.nmTerminalId = nmTerminalId;
	}

	public String getVcTerminalTac() {
		return vcTerminalTac;
	}

	public void setVcTerminalTac(String vcTerminalTac) {
		this.vcTerminalTac = vcTerminalTac;
	}

	public String getVcTerminalBrand() {
		return vcTerminalBrand;
	}

	public void setVcTerminalBrand(String vcTerminalBrand) {
		this.vcTerminalBrand = vcTerminalBrand;
	}

	public String getVcTerminalName() {
		return vcTerminalName;
	}

	public void setVcTerminalName(String vcTerminalName) {
		this.vcTerminalName = vcTerminalName;
	}
	
	public String bitType;

	public String getBitType() {
		return bitType;
	}

	public void setBitType(String bitType) {
		this.bitType = bitType;
	}

	public String vcTitleType;
	public String vcTitle;
	public String vcKeyword;

	public String getVcTitleType() {
		return vcTitleType;
	}

	public void setVcTitleType(String vcTitleType) {
		this.vcTitleType = vcTitleType;
	}

	public String getVcTitle() {
		return vcTitle;
	}

	public void setVcTitle(String vcTitle) {
		this.vcTitle = vcTitle;
	}

	public String getVcKeyword() {
		return vcKeyword;
	}

	public void setVcKeyword(String vcKeyword) {
		this.vcKeyword = vcKeyword;
	}
	
}