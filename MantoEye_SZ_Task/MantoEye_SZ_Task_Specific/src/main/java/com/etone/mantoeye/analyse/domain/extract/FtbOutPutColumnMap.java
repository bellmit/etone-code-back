package com.etone.mantoeye.analyse.domain.extract;

import java.io.Serializable;

/**
 * 自定義輸出條件過濾
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011-9-8 10:15:45
 * 
 */
public class FtbOutPutColumnMap  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7077711794225615651L;
 
	/**
	 * 过滤条件与任务对照表ID
	 */
	private Long nmDataOutPutTaskId;

	/**
	 * 过滤条件与任务对照表msisdn類型
	 */
	private Integer intMsisdnStatus;

	/**
	 * 过滤条件与任务对照表url類型
	 */
	private Integer intURLStatus;

	/**
	 * 过滤条件与任务对照表apn類型
	 */
	private Integer intApnStatus;

	/**
	 * 过滤条件与任务对照表cgi類型
	 */
	private Integer intCgiStatus;

	/**
	 * 过滤条件与任务对照表imei類型
	 */
	private Integer intImeiStatus;

	/**
	 * 过滤条件与任务对照表業務類型
	 */
	private Integer intbussinessStatus;

	/**
	 * @return the nmDataOutPutTaskId
	 */
	public Long getNmDataOutPutTaskId() {
		return nmDataOutPutTaskId;
	}

	/**
	 * @param nmDataOutPutTaskId
	 *            the nmDataOutPutTaskId to set
	 */
	public void setNmDataOutPutTaskId(Long nmDataOutPutTaskId) {
		this.nmDataOutPutTaskId = nmDataOutPutTaskId;
	}

	/**
	 * @return the intMsisdnStatus
	 */
	public Integer getIntMsisdnStatus() {
		return intMsisdnStatus;
	}

	/**
	 * @param intMsisdnStatus
	 *            the intMsisdnStatus to set
	 */
	public void setIntMsisdnStatus(Integer intMsisdnStatus) {
		this.intMsisdnStatus = intMsisdnStatus;
	}

	/**
	 * @return the intURLStatus
	 */
	public Integer getIntURLStatus() {
		return intURLStatus;
	}

	/**
	 * @param intURLStatus
	 *            the intURLStatus to set
	 */
	public void setIntURLStatus(Integer intURLStatus) {
		this.intURLStatus = intURLStatus;
	}

	/**
	 * @return the intApnStatus
	 */
	public Integer getIntApnStatus() {
		return intApnStatus;
	}

	/**
	 * @param intApnStatus
	 *            the intApnStatus to set
	 */
	public void setIntApnStatus(Integer intApnStatus) {
		this.intApnStatus = intApnStatus;
	}

	/**
	 * @return the intCgiStatus
	 */
	public Integer getIntCgiStatus() {
		return intCgiStatus;
	}

	/**
	 * @param intCgiStatus
	 *            the intCgiStatus to set
	 */
	public void setIntCgiStatus(Integer intCgiStatus) {
		this.intCgiStatus = intCgiStatus;
	}

	/**
	 * @return the intImeiStatus
	 */
	public Integer getIntImeiStatus() {
		return intImeiStatus;
	}

	/**
	 * @param intImeiStatus
	 *            the intImeiStatus to set
	 */
	public void setIntImeiStatus(Integer intImeiStatus) {
		this.intImeiStatus = intImeiStatus;
	}

	/**
	 * @return the intbussinessStatus
	 */
	public Integer getIntbussinessStatus() {
		return intbussinessStatus;
	}

	/**
	 * @param intbussinessStatus
	 *            the intbussinessStatus to set
	 */
	public void setIntbussinessStatus(Integer intbussinessStatus) {
		this.intbussinessStatus = intbussinessStatus;
	}

	/**
	 * @param nmDataOutPutTaskId
	 * @param intMsisdnStatus
	 * @param intURLStatus
	 * @param intApnStatus
	 * @param intCgiStatus
	 * @param intImeiStatus
	 * @param intbussinessStatus
	 */
	public FtbOutPutColumnMap(Long nmDataOutPutTaskId, Integer intMsisdnStatus,
			Integer intURLStatus, Integer intApnStatus, Integer intCgiStatus,
			Integer intImeiStatus, Integer intbussinessStatus) {
		super();
		this.nmDataOutPutTaskId = nmDataOutPutTaskId;
		this.intMsisdnStatus = intMsisdnStatus;
		this.intURLStatus = intURLStatus;
		this.intApnStatus = intApnStatus;
		this.intCgiStatus = intCgiStatus;
		this.intImeiStatus = intImeiStatus;
		this.intbussinessStatus = intbussinessStatus;
	}

	/**
	 * 午餐構造方法
	 */
	public FtbOutPutColumnMap() {
	}

}
