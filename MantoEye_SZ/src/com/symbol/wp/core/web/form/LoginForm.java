package com.symbol.wp.core.web.form;

/**
 *  登录信息对象
 * @Title: LoginForm.java
 * @Description: <br>
 *               <br>
 * @Company: etonetech
 * @Created Mar 23, 2009 9:27:35 AM
 * @author ranhualin
 * @version 1.0
 * @since 1.0
 */
public class LoginForm {
	/**
	 * 登陆模块
	 */
	private String loginName;	
	private String password;
	private String validatecCode;
	
	/**
	 * 0-该用户为本地用户和密码，需要验证
	 * 1-该用户应经通过单点登录验证了合法性，不用过数据库的用户名密码验证
	 */
	private String ssocheck;//
	
	/**
	 * 0-本地用户
	 * 1-统一网管平台用户
	 */
	private String usertype;//是否是单点登录用户
	
	public String getValidatecCode() {
		return validatecCode;
	}
	public void setValidatecCode(String validatecCode) {
		this.validatecCode = validatecCode;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSsocheck() {
		return ssocheck;
	}
	public void setSsocheck(String ssocheck) {
		this.ssocheck = ssocheck;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
}
