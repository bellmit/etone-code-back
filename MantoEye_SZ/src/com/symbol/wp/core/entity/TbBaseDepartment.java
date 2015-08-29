package com.symbol.wp.core.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
* TbBaseDepartment entity. 部门表
*@author ranhualin
*/
@Entity
@Table(name = "tbBaseDepartment")
public class TbBaseDepartment extends IdEntity implements java.io.Serializable {
 
// Fields
private String vcParentId;//上级部门
private String vcDeptName;//部门名称
private String vcDeptMemo;//部门描述

// Constructors
/** default constructor */
public TbBaseDepartment() {
}

@Column(name = "vcParentId")
public String getVcParentId() {
	return this.vcParentId;
}
@Column(name = "vcDeptName")
public String getVcDeptName() {
	return this.vcDeptName;
}
@Column(name = "vcDeptMemo")
public String getVcDeptMemo() {
	return this.vcDeptMemo;
}

public void setVcParentId(String vcParentId) {
	this.vcParentId = vcParentId;
}
public void setVcDeptName(String vcDeptName) {
	this.vcDeptName = vcDeptName;
}
public void setVcDeptMemo(String vcDeptMemo) {
	this.vcDeptMemo = vcDeptMemo;
}
}
