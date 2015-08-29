package com.symbol.wp.core.entity;

import java.util.Date;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
* TbBaseOpLog entity. 系统操作日志
*@author ranhualin
*/
@Entity
@Table(name = "tbBaseOpLog")
public class TbBaseOpLog extends IdEntity implements java.io.Serializable {
 
// Fields
private Date dtRecordTime;//操作时间
private String vcLoginIp;//登陆IP
private String vcLoginUser;//登陆用户
private String vcLogContent;//日志内容
private String vcOpMenu;//操作菜单
private String vcOpSubAction;//操作子动作
private String vcOpObject;//被操作对象
private String vcSysLogType;//日志类型

// Constructors
/** default constructor */
public TbBaseOpLog() {
}

@Column(name = "dtRecordTime")
@Temporal(TemporalType.TIMESTAMP)
public Date getDtRecordTime() {
	return this.dtRecordTime;
}
@Column(name = "vcLoginIp")
public String getVcLoginIp() {
	return this.vcLoginIp;
}
@Column(name = "vcLoginUser")
public String getVcLoginUser() {
	return this.vcLoginUser;
}
@Column(name = "vcLogContent")
public String getVcLogContent() {
	return this.vcLogContent;
}
@Column(name = "vcOpMenu")
public String getVcOpMenu() {
	return this.vcOpMenu;
}
@Column(name = "vcOpSubAction")
public String getVcOpSubAction() {
	return this.vcOpSubAction;
}
@Column(name = "vcOpObject")
public String getVcOpObject() {
	return this.vcOpObject;
}
@Column(name = "vcSysLogType")
public String getVcSysLogType() {
	return this.vcSysLogType;
}

public void setDtRecordTime(Date dtRecordTime) {
	this.dtRecordTime = dtRecordTime;
}
public void setVcLoginIp(String vcLoginIp) {
	this.vcLoginIp = vcLoginIp;
}
public void setVcLoginUser(String vcLoginUser) {
	this.vcLoginUser = vcLoginUser;
}
public void setVcLogContent(String vcLogContent) {
	this.vcLogContent = vcLogContent;
}
public void setVcOpMenu(String vcOpMenu) {
	this.vcOpMenu = vcOpMenu;
}
public void setVcOpSubAction(String vcOpSubAction) {
	this.vcOpSubAction = vcOpSubAction;
}
public void setVcOpObject(String vcOpObject) {
	this.vcOpObject = vcOpObject;
}
public void setVcSysLogType(String vcSysLogType) {
	this.vcSysLogType = vcSysLogType;
}
}
