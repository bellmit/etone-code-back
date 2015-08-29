package com.symbol.wp.core.dto;

import com.symbol.wp.core.entity.TbBaseUserInfo;

public class VBaseUserInfo extends TbBaseUserInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2707154479200747498L;
  
	private String deptName;
	private String roleName;
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
