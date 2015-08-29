package com.symbol.wp.core.entity;

import java.util.Date;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
* TbBasePermissions entity. 系统模块表
*@author ranhualin
*/
@Entity
@Table(name = "tbBasePermissions")
public class TbBasePermissions extends IdEntity implements java.io.Serializable {
 
// Fields
private String vcParentId;//父模块Id
private String vcMenuName;//菜单名称
private String vcRedirectUrl;//映射地址
private Long nmOrderNum;//排序号
private String txtPermMemo;//说明
private String btDeleted;//删除标识(0-未删除,1-已删除)
private Long tiPermType;//模块类型
private String btShow;//是否可见(0-不可见,1-可见)
private String vcPermSymbol;//模块权限标识

// Constructors
/** default constructor */
public TbBasePermissions() {
}

@Column(name = "vcParentId")
public String getVcParentId() {
	return this.vcParentId;
}
@Column(name = "vcMenuName")
public String getVcMenuName() {
	return this.vcMenuName;
}
@Column(name = "vcRedirectUrl")
public String getVcRedirectUrl() {
	return this.vcRedirectUrl;
}
@Column(name = "nmOrderNum")
public Long getNmOrderNum() {
	return this.nmOrderNum;
}
@Column(name = "txtPermMemo")
public String getTxtPermMemo() {
	return this.txtPermMemo;
}
@Column(name = "btDeleted")
public String getBtDeleted() {
	return this.btDeleted;
}
@Column(name = "tiPermType")
public Long getTiPermType() {
	return this.tiPermType;
}
@Column(name = "btShow")
public String getBtShow() {
	return this.btShow;
}
@Column(name = "vcPermSymbol")
public String getVcPermSymbol() {
	return this.vcPermSymbol;
}

public void setVcParentId(String vcParentId) {
	this.vcParentId = vcParentId;
}
public void setVcMenuName(String vcMenuName) {
	this.vcMenuName = vcMenuName;
}
public void setVcRedirectUrl(String vcRedirectUrl) {
	this.vcRedirectUrl = vcRedirectUrl;
}
public void setNmOrderNum(Long nmOrderNum) {
	this.nmOrderNum = nmOrderNum;
}
public void setTxtPermMemo(String txtPermMemo) {
	this.txtPermMemo = txtPermMemo;
}
public void setBtDeleted(String btDeleted) {
	this.btDeleted = btDeleted;
}
public void setTiPermType(Long tiPermType) {
	this.tiPermType = tiPermType;
}
public void setBtShow(String btShow) {
	this.btShow = btShow;
}
public void setVcPermSymbol(String vcPermSymbol) {
	this.vcPermSymbol = vcPermSymbol;
}
}
