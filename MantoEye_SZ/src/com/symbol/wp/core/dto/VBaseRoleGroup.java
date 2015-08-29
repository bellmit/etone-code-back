package com.symbol.wp.core.dto;

import java.io.Serializable;

import com.symbol.wp.core.entity.TbBaseRoleGroup;

public class VBaseRoleGroup extends TbBaseRoleGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long nmRoleGroupId;
	private String vcName;
	private String vcParentNo;
	private String vcGroupMemo;
	private String vcRoleGroupNo;
	public Long getNmRoleGroupId() {
		return nmRoleGroupId;
	}
	public void setNmRoleGroupId(Long nmRoleGroupId) {
		this.nmRoleGroupId = nmRoleGroupId;
	}
	public String getVcName() {
		return vcName;
	}
	public void setVcName(String vcName) {
		this.vcName = vcName;
	}
	public String getVcParentNo() {
		return vcParentNo;
	}
	public void setVcParentNo(String vcParentNo) {
		this.vcParentNo = vcParentNo;
	}
	public String getVcGroupMemo() {
		return vcGroupMemo;
	}
	public void setVcGroupMemo(String vcGroupMemo) {
		this.vcGroupMemo = vcGroupMemo;
	}
	public String getVcRoleGroupNo() {
		return vcRoleGroupNo;
	}
	public void setVcRoleGroupNo(String vcRoleGroupNo) {
		this.vcRoleGroupNo = vcRoleGroupNo;
	}

	

}
