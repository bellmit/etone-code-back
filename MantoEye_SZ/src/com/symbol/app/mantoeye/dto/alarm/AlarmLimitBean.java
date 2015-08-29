package com.symbol.app.mantoeye.dto.alarm;

public class AlarmLimitBean {

	private String alarmType; // 阀值类型
	private String alarmName; // 阀值类型名称
	private String operator; // 操作符
	private String alarmLimit; // 阀值
	private String timeLevel; // 时间粒度

	public AlarmLimitBean() {
	}

	public AlarmLimitBean(String alarmType, String alarmName, String operator,
			String alarmLimit, String timeLevel) {
		super();
		this.alarmType = alarmType;
		this.alarmName = alarmName;
		this.operator = operator;
		this.alarmLimit = alarmLimit;
		this.timeLevel = timeLevel;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getAlarmLimit() {
		return alarmLimit;
	}

	public void setAlarmLimit(String alarmLimit) {
		this.alarmLimit = alarmLimit;
	}

	public String getTimeLevel() {
		return timeLevel;
	}

	public void setTimeLevel(String timeLevel) {
		this.timeLevel = timeLevel;
	}

}
