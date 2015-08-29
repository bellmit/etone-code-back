package com.symbol.wp.core.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.symbol.wp.core.entity.TbBasePermissions;
import com.symbol.wp.core.entity.TbBaseRoles;
import com.symbol.wp.core.entity.TbBaseUserInfo;


/**
 * 
 * @Title: SessionContainer.java
 * @Description: <br>此对象包含了当前登录用户的重要信息
 *               <br>此对象在登录时赋值并放入Session中
 * @Company: etonetech
 * @Created Mar 20, 2009 2:15:09 PM
 * @author ranhualin
 * @version 1.0
 * @since 1.0
 */
public class SessionContainer {
	
	private TbBaseUserInfo userInfo;//用户
	
	private List<TbBaseRoles> baseRoles;//角色列表
	
	private List<TbBasePermissions> permList;
	
	private Map<String,String> btnPerm;//有权限的按钮列表(key - 菜单标识,value - 该菜单包含的有权限的按钮以逗号‘,’分隔)
	
	private String menuPerm;//有权限的菜单标识列表（用‘,’分隔）
	private String file_Down_Flag;
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public List<TbBasePermissions> getPermList() {
		return permList;
	}
	public void setPermList(List<TbBasePermissions> permList) {
		this.permList = permList;
	}
	
	public Map<String, String> getBtnPerm() {
		return btnPerm;
	}
	public void setBtnPerm(Map<String, String> btnPerm) {
		this.btnPerm = btnPerm;
	}
	
	public String getMenuPerm() {
		return menuPerm;
	}
	public void setMenuPerm(String menuPerm) {
		this.menuPerm = menuPerm;
	}
	
	

	private String userId;
	
	private String userName;
	
	private String loginName;
	
	private String userNo ;
	
	private String userType ;//用户类型
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public TbBaseUserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(TbBaseUserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public List<TbBaseRoles> getBaseRoles() {
		return baseRoles;
	}
	public void setBaseRoles(List<TbBaseRoles> baseRoles) {
		this.baseRoles = baseRoles;
	}
	public void putRole(TbBaseRoles role) {
		if(baseRoles == null){
			baseRoles = new ArrayList<TbBaseRoles>();
		}
		baseRoles.add(role);
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getFile_Down_Flag() {
		return file_Down_Flag;
	}
	public void setFile_Down_Flag(String file_Down_Flag) {
		this.file_Down_Flag = file_Down_Flag;
	}
	
}

