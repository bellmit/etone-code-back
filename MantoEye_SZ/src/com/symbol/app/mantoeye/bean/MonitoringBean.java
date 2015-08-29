package com.symbol.app.mantoeye.bean;

import com.symbol.app.mantoeye.util.CommonUtils;

/**
 * 数据库监控告警BEAN
 * 
 * @author Jane
 * 
 */
public class MonitoringBean {

	public MonitoringBean() {
	}

	private Long mainSpaceSize;
	private Long usedMainSpaceSize;
	private Integer mainSpaceRate;
	private Integer connMax;
	private Integer curConn;
	private Long otherVersion;
	private String currentTime;
	private String unit;

	private Double mainSpaceSizeGB;
	private Double usedMainSpaceSizeGB;
	private Double otherVersionGB;
	private String fullDate;

	public void setGB() {

		String unit = this.unit;

		if (null == unit || "".equals(unit)) {
			unit = "m";
		}

		if ("m".equalsIgnoreCase(unit)) {

			this.mainSpaceSizeGB = CommonUtils
					.formatBToTb(this.mainSpaceSize * 1024 * 1024);
			this.otherVersionGB = CommonUtils
					.formatBToGb(this.otherVersion * 1024 * 1024);
			this.usedMainSpaceSizeGB = CommonUtils
					.formatBToTb(this.usedMainSpaceSize * 1024 * 1024);
		} else if ("k".equalsIgnoreCase(unit)) {
			this.mainSpaceSizeGB = CommonUtils
					.formatBToGb(this.mainSpaceSize * 1024);
			this.otherVersionGB = CommonUtils
					.formatBToGb(this.otherVersion * 1024);
			this.usedMainSpaceSizeGB = CommonUtils
					.formatBToGb(this.usedMainSpaceSize * 1024);
		} else if ("b".equalsIgnoreCase(unit)) {
			this.mainSpaceSizeGB = CommonUtils.formatBToGb(this.mainSpaceSize);
			this.otherVersionGB = CommonUtils.formatBToGb(this.otherVersion);
			this.usedMainSpaceSizeGB = CommonUtils
					.formatBToGb(this.usedMainSpaceSize);
			// } else if ("b".equalsIgnoreCase(unit)) {
			// this.mainSpaceSizeGB = Double.parseDouble(String
			// .valueOf(this.mainSpaceSize));
			// this.otherVersionGB = Double.parseDouble(String
			// .valueOf(this.otherVersion));
			// this.usedMainSpaceSizeGB = Double.parseDouble(String
			// .valueOf(this.usedMainSpaceSize));
		} else {
			this.mainSpaceSizeGB = CommonUtils
					.formatBToGb(this.mainSpaceSize * 1024 * 1024);
			this.otherVersionGB = CommonUtils
					.formatBToGb(this.otherVersion * 1024 * 1024);
			this.usedMainSpaceSizeGB = CommonUtils
					.formatBToGb(this.usedMainSpaceSize * 1024 * 1024);
		}

	}

	public Long getMainSpaceSize() {
		return mainSpaceSize;
	}

	public void setMainSpaceSize(Long mainSpaceSize) {
		this.mainSpaceSize = mainSpaceSize;
	}

	public Long getUsedMainSpaceSize() {
		return usedMainSpaceSize;
	}

	public void setUsedMainSpaceSize(Long usedMainSpaceSize) {
		this.usedMainSpaceSize = usedMainSpaceSize;
	}

	public Integer getMainSpaceRate() {
		return mainSpaceRate;
	}

	public void setMainSpaceRate(Integer mainSpaceRate) {
		this.mainSpaceRate = mainSpaceRate;
	}

	public Integer getConnMax() {
		return connMax;
	}

	public void setConnMax(Integer connMax) {
		this.connMax = connMax;
	}

	public Integer getCurConn() {
		return curConn;
	}

	public void setCurConn(Integer curConn) {
		this.curConn = curConn;
	}

	public Long getOtherVersion() {
		return otherVersion;
	}

	public void setOtherVersion(Long otherVersion) {
		this.otherVersion = otherVersion;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getMainSpaceSizeGB() {
		return mainSpaceSizeGB;
	}

	public void setMainSpaceSizeGB(Double mainSpaceSizeGB) {
		this.mainSpaceSizeGB = mainSpaceSizeGB;
	}

	public Double getUsedMainSpaceSizeGB() {
		return usedMainSpaceSizeGB;
	}

	public void setUsedMainSpaceSizeGB(Double usedMainSpaceSizeGB) {
		this.usedMainSpaceSizeGB = usedMainSpaceSizeGB;
	}

	public Double getOtherVersionGB() {
		return otherVersionGB;
	}

	public void setOtherVersionGB(Double otherVersionGB) {
		this.otherVersionGB = otherVersionGB;
	}

	public String getFullDate() {
		return fullDate;
	}

	public void setFullDate(String fullDate) {
		this.fullDate = fullDate;
	}

}
