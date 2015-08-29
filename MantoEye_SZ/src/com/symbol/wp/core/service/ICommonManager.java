package com.symbol.wp.core.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.symbol.wp.core.dto.VBasePermissions;
import com.symbol.wp.core.listener.SessionContainer;

/**
 * 业务层公共接口 
 * 提供日志记录,获取用户权限验证,菜单导航信息,用户权限信息等待操作
 * @Title: ICommonService.java
 * @Description: <br>
 *               <br>
 * @Company: etonetech
 * @Created Mar 20, 2009 4:58:28 PM
 * @author ranhualin
 * @version 1.0
 * @since 1.0
 */

public interface ICommonManager {
	/**
	 * 登陆验证对象
	 * @param object
	 * @param request
	 * @return
	 */
	public Object authenticate(Object object, HttpServletRequest request);

	/**
	 * 写日志
	 * @param request
	 * @param message 日志内容
	 */
	public void log(HttpServletRequest request, String message);

	/**
	 * 退出系统
	 * @param sessionContainer
	 * @param request
	 */
	public void loginOut(SessionContainer sessionContainer,
			HttpServletRequest request);

	/**
	 * 得到左侧子菜单
	 * 
	 * @param userId
	 * @param obj
	 * @return
	 */
	public String getSubMenu(String userNo);

	/**
	 * 获取当前用户的模块权限
	 * @param userId
	 * @return
	 */
	public Map getRolePermisMap(String userNo);

	/**
	 * 获取当前用户拥有权限的模块
	 * @param symbol
	 *            权限标识符
	 * @param userId
	 *            用户Id
	 * @return	 
	 */
	public String getPermissions(String symbol, String userNo);

	/**
	 * 取得左边菜单树
	 * 
	 * @param userId
	 * @param Title
	 * @param Url
	 * @param Event
	 * @param TargetFrame
	 * @return
	 */
	public String getLeftMenu(String userNo, String Title, String Url,
			String Event, String TargetFrame,String pid);

	/**
	 * 获取顶部快捷菜单 
	 * @param userNo
	 * @return
	 */
	public List<VBasePermissions> getTopMenu(String userNo);
	
	/**
	 * 获取左侧二级菜单
	 * @param userNo
	 * @return
	 */
	public List<VBasePermissions> getSubMenuList(String userNo,Object tabid);
}
