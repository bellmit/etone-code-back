package com.etone.mantoeye.analyse.domain.terminal;

import java.io.Serializable;

/**
 * 終端業務撥測DOMAIN
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月24日16:29:16
 */
public class TerminalPolicy  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7198438360727433964L;

	/**
	 * 策略订制表ID
	 */
	private Long nmTerminalPolicyId;

	/**
	 * 策略订制表策略名稱
	 */
	private String vcPolicyName;

	/**
	 * 策略订制表說明
	 */
	private String vcPolicyNote;

	/**
	 * 策略订制表開始時間
	 */
	private String dtSTime;

	/**
	 * 策略订制表結束時間
	 */
	private String dtETime;
	/**
	 * 策略订制表狀態
	 */
	private Integer intStatus;

	/**
	 * @return the nmTerminalPolicyId
	 */
	public Long getNmTerminalPolicyId() {
		return nmTerminalPolicyId;
	}

	/**
	 * @param nmTerminalPolicyId
	 *            the nmTerminalPolicyId to set
	 */
	public void setNmTerminalPolicyId(Long nmTerminalPolicyId) {
		this.nmTerminalPolicyId = nmTerminalPolicyId;
	}

	/**
	 * @return the vcPolicyName
	 */
	public String getVcPolicyName() {
		return vcPolicyName;
	}

	/**
	 * @param vcPolicyName
	 *            the vcPolicyName to set
	 */
	public void setVcPolicyName(String vcPolicyName) {
		this.vcPolicyName = vcPolicyName;
	}

	/**
	 * @return the vcPolicyNote
	 */
	public String getVcPolicyNote() {
		return vcPolicyNote;
	}

	/**
	 * @param vcPolicyNote
	 *            the vcPolicyNote to set
	 */
	public void setVcPolicyNote(String vcPolicyNote) {
		this.vcPolicyNote = vcPolicyNote;
	}

	/**
	 * @return the dtSTime
	 */
	public String getDtSTime() {
		return dtSTime;
	}

	/**
	 * @param dtSTime
	 *            the dtSTime to set
	 */
	public void setDtSTime(String dtSTime) {
		this.dtSTime = dtSTime;
	}

	/**
	 * @return the dtETime
	 */
	public String getDtETime() {
		return dtETime;
	}

	/**
	 * @param dtETime
	 *            the dtETime to set
	 */
	public void setDtETime(String dtETime) {
		this.dtETime = dtETime;
	}

	/**
	 * @return the intStatus
	 */
	public Integer getIntStatus() {
		return intStatus;
	}

	/**
	 * @param intStatus
	 *            the intStatus to set
	 */
	public void setIntStatus(Integer intStatus) {
		this.intStatus = intStatus;
	}

	/**
	 * @param nmTerminalPolicyId
	 * @param vcPolicyName
	 * @param vcPolicyNote
	 * @param dtSTime
	 * @param dtETime
	 * @param intStatus
	 */
	public TerminalPolicy(Long nmTerminalPolicyId, String vcPolicyName,
			String vcPolicyNote, String dtSTime, String dtETime,
			Integer intStatus) {
		super();
		this.nmTerminalPolicyId = nmTerminalPolicyId;
		this.vcPolicyName = vcPolicyName;
		this.vcPolicyNote = vcPolicyNote;
		this.dtSTime = dtSTime;
		this.dtETime = dtETime;
		this.intStatus = intStatus;
	}

	/**
	 * 空參構造方法
	 */
	public TerminalPolicy() {
	}

}
