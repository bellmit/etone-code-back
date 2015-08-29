package com.symbol.wp.core.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
* TbBaseRoles entity. 系统角色表
*@author ranhualin
*/
@Entity
@Table(name = "tbBaseRoles")
public class TbBaseRoles extends IdEntity implements java.io.Serializable {
 
// Fields
private String vcRolesName;//角色名称
private String txtRolesMemo;//角色说明
private Long nmRolesLevel;//角色级别
private String vcRoleGroupId;//角色分组

// Constructors
/** default constructor */
public TbBaseRoles() {
}

@Column(name = "vcRolesName")
public String getVcRolesName() {
	return this.vcRolesName;
}
@Column(name = "txtRolesMemo")
public String getTxtRolesMemo() {
	return this.txtRolesMemo;
}
@Column(name = "nmRolesLevel")
public Long getNmRolesLevel() {
	return this.nmRolesLevel;
}
@Column(name = "vcRoleGroupId")
public String getVcRoleGroupId() {
	return this.vcRoleGroupId;
}

public void setVcRolesName(String vcRolesName) {
	this.vcRolesName = vcRolesName;
}
public void setTxtRolesMemo(String txtRolesMemo) {
	this.txtRolesMemo = txtRolesMemo;
}
public void setNmRolesLevel(Long nmRolesLevel) {
	this.nmRolesLevel = nmRolesLevel;
}
public void setVcRoleGroupId(String vcRoleGroupId) {
	this.vcRoleGroupId = vcRoleGroupId;
}
}
