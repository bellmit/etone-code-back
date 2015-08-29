package com.etone.mantoeye.analyse.domain.extract;

import java.io.Serializable;

/**
 * 自定義輸出任務DTO
 * 
 * @author Wu Zhenzhen
 * @version 3.0 2011年11月24日19:44:58
 * 
 */
public class FtbDataOutPutTask implements Serializable {

	/**
	 *  
	 */
	private static final long serialVersionUID = 5135266398970254185L;
	/**
	 * 任務ID
	 */
	private Long nmDataOutPutTaskId;
	private Long nmDataOutPutTaskPreId;

	/**
	 * <br>
	 * Created on: 2014-1-9 下午04:14:16
	 * 
	 * @return the nmDataOutPutTaskPreId
	 */
	public Long getNmDataOutPutTaskPreId() {
		return nmDataOutPutTaskPreId;
	}

	/**
	 * <br>
	 * Created on: 2014-1-9 下午04:14:16
	 * 
	 * @param nmDataOutPutTaskPreId
	 *            the nmDataOutPutTaskPreId to set
	 */
	public void setNmDataOutPutTaskPreId(Long nmDataOutPutTaskPreId) {
		this.nmDataOutPutTaskPreId = nmDataOutPutTaskPreId;
	}

	/**
	 * 任务所属模块标识:<br />
	 * 1、数据提取模块<br />
	 * 2、业务拨测模块<br />
	 */
	private Integer intTaskDelong;
	/**
	 * 任务名
	 */
	private String vcTaskName;
	private String vcTableName;

	/**
	 * <br>
	 * Created on: 2014-1-9 下午04:13:22
	 * 
	 * @param vcTableName
	 */
	public FtbDataOutPutTask(String vcTableName) {
		super();
		this.vcTableName = vcTableName;
	}

	/**
	 * <br>
	 * Created on: 2014-1-9 下午04:13:11
	 * 
	 * @return the vcTableName
	 */
	public String getVcTableName() {
		return vcTableName;
	}

	/**
	 * <br>
	 * Created on: 2014-1-9 下午04:13:11
	 * 
	 * @param vcTableName
	 *            the vcTableName to set
	 */
	public void setVcTableName(String vcTableName) {
		this.vcTableName = vcTableName;
	}

	/**
	 * 任务类别<br />
	 * 1.根据号码提取历史原始信令 <br />
	 * 2.根据号码提取实时原始信令<br />
	 * 3.根据号码提取历史解析数据 <br />
	 * 4.根据区域及业务提取用户号码 <br />
	 * 5.已有业务规则拨测原始信令<br />
	 * 6.新增业务规则拨测原始信令<br />
	 * 7.已有业务规则拨测解码数据 <br />
	 * 8.新增业务规则拨测解码数据
	 */
	private Integer intTaskType;
	/**
	 * 任务订制时间
	 */
	private String dtOrderTime;
	/**
	 * 任务定制人
	 */
	private String vcTaskOrder;
	/**
	 * 任务开始时间
	 */
	private String dtStartTime;
	/**
	 * 任务结束时间
	 */
	private String dtEndTime;
	/**
	 * 任务激活标识 <br />
	 * 0.非激活 <br />
	 * 1.激活
	 */
	private Integer bitTaskActiveFlag;
	/**
	 * 任务状态 <br />
	 * 0.未开始 <br />
	 * 1.进行中 <br />
	 * 2.任务自然结束 <br />
	 * 3.任务手工结束 <br />
	 * 4.停止中 <br />
	 * 5.任务异常停止
	 */
	private Integer intTaskStatus;

	/**
	 * IP值
	 */
	private String vcLocalIP;

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
	 * @return the intTaskDelong
	 */
	public Integer getIntTaskDelong() {
		return intTaskDelong;
	}

	/**
	 * @param intTaskDelong
	 *            the intTaskDelong to set
	 */
	public void setIntTaskDelong(Integer intTaskDelong) {
		this.intTaskDelong = intTaskDelong;
	}

	/**
	 * @return the vcTaskName
	 */
	public String getVcTaskName() {
		return vcTaskName;
	}

	/**
	 * @param vcTaskName
	 *            the vcTaskName to set
	 */
	public void setVcTaskName(String vcTaskName) {
		this.vcTaskName = vcTaskName;
	}

	/**
	 * @return the intTaskType
	 */
	public Integer getIntTaskType() {
		return intTaskType;
	}

	/**
	 * @param intTaskType
	 *            the intTaskType to set
	 */
	public void setIntTaskType(Integer intTaskType) {
		this.intTaskType = intTaskType;
	}

	/**
	 * @return the dtOrderTime
	 */
	public String getDtOrderTime() {
		return dtOrderTime;
	}

	/**
	 * @param dtOrderTime
	 *            the dtOrderTime to set
	 */
	public void setDtOrderTime(String dtOrderTime) {
		this.dtOrderTime = dtOrderTime;
	}

	/**
	 * @return the vcTaskOrder
	 */
	public String getVcTaskOrder() {
		return vcTaskOrder;
	}

	/**
	 * @param vcTaskOrder
	 *            the vcTaskOrder to set
	 */
	public void setVcTaskOrder(String vcTaskOrder) {
		this.vcTaskOrder = vcTaskOrder;
	}

	/**
	 * @return the dtStartTime
	 */
	public String getDtStartTime() {
		return dtStartTime;
	}

	/**
	 * @param dtStartTime
	 *            the dtStartTime to set
	 */
	public void setDtStartTime(String dtStartTime) {
		this.dtStartTime = dtStartTime;
	}

	/**
	 * @return the dtEndTime
	 */
	public String getDtEndTime() {
		return dtEndTime;
	}

	/**
	 * @param dtEndTime
	 *            the dtEndTime to set
	 */
	public void setDtEndTime(String dtEndTime) {
		this.dtEndTime = dtEndTime;
	}

	/**
	 * @return the bitTaskActiveFlag
	 */
	public Integer getBitTaskActiveFlag() {
		return bitTaskActiveFlag;
	}

	/**
	 * @param bitTaskActiveFlag
	 *            the bitTaskActiveFlag to set
	 */
	public void setBitTaskActiveFlag(Integer bitTaskActiveFlag) {
		this.bitTaskActiveFlag = bitTaskActiveFlag;
	}

	/**
	 * @return the intTaskStatus
	 */
	public Integer getIntTaskStatus() {
		return intTaskStatus;
	}

	/**
	 * @param intTaskStatus
	 *            the intTaskStatus to set
	 */
	public void setIntTaskStatus(Integer intTaskStatus) {
		this.intTaskStatus = intTaskStatus;
	}

	/**
	 * @param nmDataOutPutTaskId
	 * @param intTaskDelong
	 * @param vcTaskName
	 * @param intTaskType
	 * @param dtOrderTime
	 * @param vcTaskOrder
	 * @param dtStartTime
	 * @param dtEndTime
	 * @param bitTaskActiveFlag
	 * @param intTaskStatus
	 */
	public FtbDataOutPutTask(Long nmDataOutPutTaskId, Integer intTaskDelong,
			String vcTaskName, Integer intTaskType, String dtOrderTime,
			String vcTaskOrder, String dtStartTime, String dtEndTime,
			Integer bitTaskActiveFlag, Integer intTaskStatus) {
		super();
		this.nmDataOutPutTaskId = nmDataOutPutTaskId;
		this.intTaskDelong = intTaskDelong;
		this.vcTaskName = vcTaskName;
		this.intTaskType = intTaskType;
		this.dtOrderTime = dtOrderTime;
		this.vcTaskOrder = vcTaskOrder;
		this.dtStartTime = dtStartTime;
		this.dtEndTime = dtEndTime;
		this.bitTaskActiveFlag = bitTaskActiveFlag;
		this.intTaskStatus = intTaskStatus;
	}

	/**
	 * 無慘構造方法
	 */
	public FtbDataOutPutTask() {
	}

	/**
	 * @return the vcLocalIP
	 */
	public String getVcLocalIP() {
		return vcLocalIP;
	}

	/**
	 * @param vcLocalIP
	 *            the vcLocalIP to set
	 */
	public void setVcLocalIP(String vcLocalIP) {
		this.vcLocalIP = vcLocalIP;
	}

}
