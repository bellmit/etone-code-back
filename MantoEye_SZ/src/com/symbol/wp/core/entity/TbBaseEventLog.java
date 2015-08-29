package com.symbol.wp.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "tbBaseEventLog")
public class TbBaseEventLog extends IdEntity implements java.io.Serializable {
 
// Fields
private Date dtRecordTime;//记录时间
private String vcRecorder;//记录用户
private String vcEventContent;//事件内容
private String vcEventAffect;//时间影响
private String vcEventType;//事件类型


// Constructors
/** default constructor */
public TbBaseEventLog() {
}

@Column(name = "dtRecordTime")
@Temporal(TemporalType.TIMESTAMP)
public Date getDtRecordTime() {
	return this.dtRecordTime;
}
@Column(name = "vcRecorder")
public String getVcRecorder() {
	return this.vcRecorder;
}
@Column(name = "vcEventContent")
public String getVcEventContent() {
	return this.vcEventContent;
}
@Column(name = "vcEventAffect")
public String getVcEventAffect() {
	return this.vcEventAffect;
}
@Column(name = "vcEventType")
public String getVcEventType() {
	return this.vcEventType;
}

public void setDtRecordTime(Date dtRecordTime) {
	this.dtRecordTime = dtRecordTime;
}

public void setVcRecorder(String vcRecorder) {
	this.vcRecorder = vcRecorder;
}

public void setVcEventContent(String vcEventContent) {
	this.vcEventContent = vcEventContent;
}

public void setVcEventAffect(String vcEventAffect) {
	this.vcEventAffect = vcEventAffect;
}

public void setVcEventType(String vcEventType) {
	this.vcEventType = vcEventType;
}

}
