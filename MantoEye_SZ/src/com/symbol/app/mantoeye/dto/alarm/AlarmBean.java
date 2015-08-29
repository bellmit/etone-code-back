package com.symbol.app.mantoeye.dto.alarm;

import com.symbol.app.mantoeye.util.CommonUtils;

/**
 * 识别率告警，流量告警DTO
 * 
 * @author Jane
 * 
 */
public class AlarmBean {

	private Integer alarmId;

	private String typeId;
	
	private String alarmRatio; // 识别率
	private String reAlarmRatio; // 未识别率
	private String fullDate;

	// typeName,countLow,countIncrease,countDecrease
	private String typeName;
	/**
	 * 流量偏低
	 */
	private Integer countLow;
	/**
	 * 流量增长率过大
	 */
	private Integer countIncrease;
	/**
	 * 流量下降率过大
	 */
	private Integer countDecrease;

	private Long changeFlush;

	private Double changeFlushKB;
	private Double changeFlushMB;
	private Double changeFlushGB;

	public void bToKMGb() {
		this.changeFlushKB = this.changeFlush == 0 ? 0.0 : CommonUtils
				.formatBToKb(this.changeFlush);
		this.changeFlushMB = this.changeFlush == 0 ? 0.0 : CommonUtils
				.formatBToMb(this.changeFlush);
		this.changeFlushGB = this.changeFlush == 0 ? 0.0 : CommonUtils
				.formatBToGb(this.changeFlush);
	}

	private String isIncrease;
	private String isLow;
	private String isDecrease;
	private String processor;
	private String disposeTime;
	private Integer changeRatio;

	public Integer getAlarmId() {
		return alarmId;
	}

	public AlarmBean() {
	}

	public AlarmBean(String typeName, Integer countLow, Integer countIncrease,
			Integer countDecrease) {
		super();
		this.typeName = typeName;
		this.countLow = countLow;
		this.countIncrease = countIncrease;
		this.countDecrease = countDecrease;
	}

	public void setAlarmId(Integer alarmId) {
		this.alarmId = alarmId;
	}

	public String getAlarmRatio() {
		return alarmRatio;
	}

	public void setAlarmRatio(String alarmRatio) {
		this.alarmRatio = alarmRatio;
	}

	public String getReAlarmRatio() {
		return reAlarmRatio;
	}

	public void setReAlarmRatio(String reAlarmRatio) {
		this.reAlarmRatio = reAlarmRatio;
	}

	public String getFullDate() {
		return fullDate;
	}

	public void setFullDate(String fullDate) {
		this.fullDate = fullDate;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getCountLow() {
		return countLow;
	}

	public void setCountLow(Integer countLow) {
		this.countLow = countLow;
	}

	public Integer getCountIncrease() {
		return countIncrease;
	}

	public void setCountIncrease(Integer countIncrease) {
		this.countIncrease = countIncrease;
	}

	public Integer getCountDecrease() {
		return countDecrease;
	}

	public void setCountDecrease(Integer countDecrease) {
		this.countDecrease = countDecrease;
	}

	public String getIsIncrease() {
		return isIncrease;
	}

	public void setIsIncrease(String isIncrease) {
		this.isIncrease = isIncrease;
	}

	public String getIsLow() {
		return isLow;
	}

	public void setIsLow(String isLow) {
		this.isLow = isLow;
	}

	public String getIsDecrease() {
		return isDecrease;
	}

	public void setIsDecrease(String isDecrease) {
		this.isDecrease = isDecrease;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getDisposeTime() {
		return disposeTime;
	}

	public void setDisposeTime(String disposeTime) {
		this.disposeTime = disposeTime;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Long getChangeFlush() {
		return changeFlush;
	}

	public void setChangeFlush(Long changeFlush) {
		this.changeFlush = changeFlush;
	}

	public Integer getChangeRatio() {
		return changeRatio;
	}

	public void setChangeRatio(Integer changeRatio) {
		this.changeRatio = changeRatio;
	}

	public Double getChangeFlushKB() {
		return changeFlushKB;
	}

	public void setChangeFlushKB(Double changeFlushKB) {
		this.changeFlushKB = changeFlushKB;
	}

	public Double getChangeFlushMB() {
		return changeFlushMB;
	}

	public void setChangeFlushMB(Double changeFlushMB) {
		this.changeFlushMB = changeFlushMB;
	}

	public Double getChangeFlushGB() {
		return changeFlushGB;
	}

	public void setChangeFlushGB(Double changeFlushGB) {
		this.changeFlushGB = changeFlushGB;
	}

}
