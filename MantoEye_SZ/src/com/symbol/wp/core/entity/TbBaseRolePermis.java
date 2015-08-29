package com.symbol.wp.core.entity;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
* TbBaseRolePermis entity. 模块角色关联表
*@author ranhualin
*/
@Entity
@Table(name = "tbBaseRolePermis")
public class TbBaseRolePermis extends IdEntity implements java.io.Serializable {
 
// Fields
private String vcPermId;//模块Id
private String vcRoleId;//角色Id

// Constructors
/** default constructor */
public TbBaseRolePermis() {
}

@Column(name = "vcPermId")
public String getVcPermId() {
	return this.vcPermId;
}
@Column(name = "vcRoleId")
public String getVcRoleId() {
	return this.vcRoleId;
}

public void setVcPermId(String vcPermId) {
	this.vcPermId = vcPermId;
}
public void setVcRoleId(String vcRoleId) {
	this.vcRoleId = vcRoleId;
}
}
