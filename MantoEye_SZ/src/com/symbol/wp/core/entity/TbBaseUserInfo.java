package com.symbol.wp.core.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
* TbBaseUserInfo entity. 系统用户表
*@author ranhualin
*/
@Entity
@Table(name = "tbBaseUserInfo")
public class TbBaseUserInfo extends IdEntity implements java.io.Serializable {
 
// Fields
private String vcLoginName;//登录名
private String vcDeptId;//所属部门
private String vcUserName;//用户姓名
private String vcMobel;//移动电话
private String vcEmail;//电子邮件
private String vcTelephone;//固定电话
private Long tiUserType;//用户类型
private String vcPassword;//密码

// Constructors
/** default constructor */
public TbBaseUserInfo() {
}

@Column(name = "vcLoginName")
public String getVcLoginName() {
	return this.vcLoginName;
}
@Column(name = "vcDeptId")
public String getVcDeptId() {
	return this.vcDeptId;
}
@Column(name = "vcUserName")
public String getVcUserName() {
	return this.vcUserName;
}
@Column(name = "vcMobel")
public String getVcMobel() {
	return this.vcMobel;
}
@Column(name = "vcEmail")
public String getVcEmail() {
	return this.vcEmail;
}
@Column(name = "vcTelephone")
public String getVcTelephone() {
	return this.vcTelephone;
}
@Column(name = "tiUserType")
public Long getTiUserType() {
	return this.tiUserType;
}
@Column(name = "vcPassword")
public String getVcPassword() {
	return this.vcPassword;
}

public void setVcLoginName(String vcLoginName) {
	this.vcLoginName = vcLoginName;
}
public void setVcDeptId(String vcDeptId) {
	this.vcDeptId = vcDeptId;
}
public void setVcUserName(String vcUserName) {
	this.vcUserName = vcUserName;
}
public void setVcMobel(String vcMobel) {
	this.vcMobel = vcMobel;
}
public void setVcEmail(String vcEmail) {
	this.vcEmail = vcEmail;
}
public void setVcTelephone(String vcTelephone) {
	this.vcTelephone = vcTelephone;
}
public void setTiUserType(Long tiUserType) {
	this.tiUserType = tiUserType;
}
public void setVcPassword(String vcPassword) {
	this.vcPassword = vcPassword;
}
}
