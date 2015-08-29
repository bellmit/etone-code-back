package com.symbol.wp.core.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
* TbBaseUserRole entity. 用户角色关联表
*@author ranhualin
*/
@Entity
@Table(name = "tbBaseUserRole")
public class TbBaseUserRole extends IdEntity implements java.io.Serializable {
 
// Fields
private String vcRoleId;//角色Id
private String vcUserId;//用户Id

// Constructors
/** default constructor */
public TbBaseUserRole() {
}

@Column(name = "vcRoleId")
public String getVcRoleId() {
	return this.vcRoleId;
}
@Column(name = "vcUserId")
public String getVcUserId() {
	return this.vcUserId;
}

public void setVcRoleId(String vcRoleId) {
	this.vcRoleId = vcRoleId;
}
public void setVcUserId(String vcUserId) {
	this.vcUserId = vcUserId;
}
}
