package com.symbol.wp.core.dto;

import com.symbol.wp.core.entity.TbBaseDepartment;



public class VBaseDepartment extends TbBaseDepartment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String nmParentDeptName = null;

	private String roles = null;

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getNmParentDeptName() {
		return nmParentDeptName;
	}

	public void setNmParentDeptName(String nmParentDeptName) {
		this.nmParentDeptName = nmParentDeptName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

}