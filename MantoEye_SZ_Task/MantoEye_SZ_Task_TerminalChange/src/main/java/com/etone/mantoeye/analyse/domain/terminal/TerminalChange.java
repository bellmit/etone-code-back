package com.etone.mantoeye.analyse.domain.terminal;

import java.io.Serializable;

/**
 * 終端升級統計表domain
 * 
 * @author Wu Zhenzhen
 * @since job 1.0
 * @version 2.0 2011年10月28日14:44:29
 * 
 */
public class TerminalChange implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3016775227226407667L;
	/**
	 * 終端統計ID
	 */
	private Long nmTerminalChangeId;
	/**
	 * 舊終端ID
	 */
	private Long nmOldTerminalId;
	/**
	 * 新終端ID
	 */
	private Long nmNewTerminalId;
	/**
	 * 區域類型<br />
	 * 0.全網<br />
	 * 1.BSC<br />
	 * 2.街道<br />
	 * 3.分公司<br />
	 * 4.營銷片區<br />
	 * 5.SGSN<br />
	 */
	private Integer intAreaType;
	/**
	 * 區域ID
	 */
	private Long intAreaId;
	/**
	 * 用戶數
	 */
	private Long intUserNum;
	/**
	 * 時間
	 */
	private String dtStatTime;
	/**
	 * 時間類型<br />
	 * <1>0:時<br />
	 * <2>1:天<br />
	 * <3>2:周<br />
	 * <4>3:月<br />
	 */
	private Integer intDateType;
	/**
	 * 月
	 */
	private Integer intYear;
	/**
	 * 月
	 */
	private Integer intMonth;
	/**
	 * 周
	 */
	private Integer intWeek;
	/**
	 * 日
	 */
	private Integer intDay;

	/**
	 * @return the nmTerminalChangeId
	 */
	public Long getNmTerminalChangeId() {
		return nmTerminalChangeId;
	}

	/**
	 * @param nmTerminalChangeId
	 *            the nmTerminalChangeId to set
	 */
	public void setNmTerminalChangeId(Long nmTerminalChangeId) {
		this.nmTerminalChangeId = nmTerminalChangeId;
	}

	/**
	 * @return the nmOldTerminalId
	 */
	public Long getNmOldTerminalId() {
		return nmOldTerminalId;
	}

	/**
	 * @param nmOldTerminalId
	 *            the nmOldTerminalId to set
	 */
	public void setNmOldTerminalId(Long nmOldTerminalId) {
		this.nmOldTerminalId = nmOldTerminalId;
	}

	/**
	 * @return the nmNewTerminalId
	 */
	public Long getNmNewTerminalId() {
		return nmNewTerminalId;
	}

	/**
	 * @param nmNewTerminalId
	 *            the nmNewTerminalId to set
	 */
	public void setNmNewTerminalId(Long nmNewTerminalId) {
		this.nmNewTerminalId = nmNewTerminalId;
	}

	/**
	 * @return the intAreaType
	 */
	public Integer getIntAreaType() {
		return intAreaType;
	}

	/**
	 * @param intAreaType
	 *            the intAreaType to set
	 */
	public void setIntAreaType(Integer intAreaType) {
		this.intAreaType = intAreaType;
	}

	/**
	 * @return the intAreaId
	 */
	public Long getIntAreaId() {
		return intAreaId;
	}

	/**
	 * @param intAreaId
	 *            the intAreaId to set
	 */
	public void setIntAreaId(Long intAreaId) {
		this.intAreaId = intAreaId;
	}

	/**
	 * @return the intUserNum
	 */
	public Long getIntUserNum() {
		return intUserNum;
	}

	/**
	 * @param intUserNum
	 *            the intUserNum to set
	 */
	public void setIntUserNum(Long intUserNum) {
		this.intUserNum = intUserNum;
	}

	/**
	 * @return the dtStatTime
	 */
	public String getDtStatTime() {
		return dtStatTime;
	}

	/**
	 * @param dtStatTime
	 *            the dtStatTime to set
	 */
	public void setDtStatTime(String dtStatTime) {
		this.dtStatTime = dtStatTime;
	}

	/**
	 * @return the intDateType
	 */
	public Integer getIntDateType() {
		return intDateType;
	}

	/**
	 * @param intDateType
	 *            the intDateType to set
	 */
	public void setIntDateType(Integer intDateType) {
		this.intDateType = intDateType;
	}

	/**
	 * @return the intYear
	 */
	public Integer getIntYear() {
		return intYear;
	}

	/**
	 * @param intYear
	 *            the intYear to set
	 */
	public void setIntYear(Integer intYear) {
		this.intYear = intYear;
	}

	/**
	 * @return the intMonth
	 */
	public Integer getIntMonth() {
		return intMonth;
	}

	/**
	 * @param intMonth
	 *            the intMonth to set
	 */
	public void setIntMonth(Integer intMonth) {
		this.intMonth = intMonth;
	}

	/**
	 * @return the intWeek
	 */
	public Integer getIntWeek() {
		return intWeek;
	}

	/**
	 * @param intWeek
	 *            the intWeek to set
	 */
	public void setIntWeek(Integer intWeek) {
		this.intWeek = intWeek;
	}

	/**
	 * @return the intDay
	 */
	public Integer getIntDay() {
		return intDay;
	}

	/**
	 * @param intDay
	 *            the intDay to set
	 */
	public void setIntDay(Integer intDay) {
		this.intDay = intDay;
	}

	/**
	 * @param nmTerminalChangeId
	 * @param nmOldTerminalId
	 * @param nmNewTerminalId
	 * @param intAreaType
	 * @param intAreaId
	 * @param intUserNum
	 * @param dtStatTime
	 * @param intDateType
	 * @param intYear
	 * @param intMonth
	 * @param intWeek
	 * @param intDay
	 */
	public TerminalChange(Long nmTerminalChangeId, Long nmOldTerminalId,
			Long nmNewTerminalId, Integer intAreaType, Long intAreaId,
			Long intUserNum, String dtStatTime, Integer intDateType,
			Integer intYear, Integer intMonth, Integer intWeek, Integer intDay) {
		this.nmTerminalChangeId = nmTerminalChangeId;
		this.nmOldTerminalId = nmOldTerminalId;
		this.nmNewTerminalId = nmNewTerminalId;
		this.intAreaType = intAreaType;
		this.intAreaId = intAreaId;
		this.intUserNum = intUserNum;
		this.dtStatTime = dtStatTime;
		this.intDateType = intDateType;
		this.intYear = intYear;
		this.intMonth = intMonth;
		this.intWeek = intWeek;
		this.intDay = intDay;
	}

	/**
	 * 無慘構造方法
	 */
	public TerminalChange() {
	}

}
