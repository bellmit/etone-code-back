package com.symbol.wp.core.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
* TbBaseRoleGroup entity. 角色分组表
*@author ranhualin
*/
@Entity
@Table(name = "tbBaseRoleGroup")
public class TbBaseRoleGroup extends IdEntity implements java.io.Serializable {
 
// Fields
private String vcName;//名称
private String vcParentId;//上级组
private String vcGroupMemo;//说明

// Constructors
/** default constructor */
public TbBaseRoleGroup() {
}

@Column(name = "vcName")
public String getVcName() {
	return this.vcName;
}
@Column(name = "vcParentId")
public String getVcParentId() {
	return this.vcParentId;
}
@Column(name = "vcGroupMemo")
public String getVcGroupMemo() {
	return this.vcGroupMemo;
}

public void setVcName(String vcName) {
	this.vcName = vcName;
}
public void setVcParentId(String vcParentId) {
	this.vcParentId = vcParentId;
}
public void setVcGroupMemo(String vcGroupMemo) {
	this.vcGroupMemo = vcGroupMemo;
}
}
