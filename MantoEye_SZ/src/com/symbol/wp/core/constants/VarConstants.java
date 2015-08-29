package com.symbol.wp.core.constants;

/**
 * 此文件用于定义系统全局静态变量
 * @Title: VarConstants.java
 * @Description: <br>
 *               <br>
 * @Company: etonetech
 * @Created Mar 20, 2009 2:21:26 PM
 * @author ranhualin
 * @version 1.0
 * @since 1.0
 */
public class VarConstants {

	/*
	 * 分页变量
	 */
	/**
	 * 分页变量 每页的行数为15 
	 */
	public static final int PAGE_LONG_ROW_SIZE = 15;
	/**
	 * 分页变量 每页的行数为7 
	 */
	public static final int PAGE_MIDDEL_ROW_SIZE = 7;
	/**
	 * 分页变量 每页的行数为5 
	 */
	public static final int PAGE_SHORT_ROW_SIZE = 5;
    /**
     * 第一次登入系统时，左边默认显示的树
     */
    public static final String DEFAULT_VIEW_TREE = "系统管理";
	/**
	 * 默认初始化后的密码'000000' 
	 */
	public static final String INIT_PASSWORD = "000000";
	/**
	 * sessionContainer
	 */
	public static final String SESSION_CONTAINER_KEY = "sessionContainer";
	/**
	 * serviceFactory
	 */
	public static final String SERVICE_FACTORY_KEY = "serviceFactory";
	/**
	 * loginListener
	 */
	public static final String LOGIN_LISTENER_KEY = "loginListener";		
	
	/**
	 * url
	 */
	public static final String URL = "url";
	/**
	 * errorCode 错误代号
	 */
	public static final String ERROR_CODE = "errorCode";
	/**
	 * successCode 成功代号
	 */
	public static final String SUCC_CODE = "successCode";
	
	/**
	 * 日志级别 ERROR 0
	 */
	public static final short ERROR_NO = 0;
	/**
	 * 日志级别 WARNING 1
	 */
	public static final short WARNING_NO = 1;
	/**
	 * 日志级别 INFO 2
	 */
	public static final short INFO_NO = 2;
	/**
	 * 日志该内容为空时 保存日志为 '--'
	 */
	public static final String LOG_CONTENT_NULL = "--";//日志该内容为空
	
	public static final int	BUFFER_SIZE			= 16 * 1024;//
		
	/**
	 * 操作类型 添加
	 */
	public static final int	USER_OPERATE_TYPE = 16 * 1024;//
	
	public static final String LIST_URL = "url";
	
	public static final String NEW_URL = "newurl";
	
	/**
	 * 权限项类型 菜单
	 */
	public static final short PERM_TYPE_MEMU = 1;
	/**
	 * 权限项类型 按钮
	 */
	public static final short PERM_TYPE_BUTTON = 2;
	/**
	 * 权限项类型 快捷页面
	 */
	public static final short PERM_TYPE_PAGE = 3;
	/**
	 * 权限项类型 其他
	 */
	public static final short PERM_TYPE_OTHERS = 4;
	
	/**
	 * 
	 */
	public static final String SORT_IMAGE_ALL="/skin/Default/images/btn/order.gif";
	
	public static final String SORT_IMAGE_UP="/skin/Default/images/btn/up.gif";
	
	public static final String SORT_IMAGE_DOWN="/skin/Default/images/btn/down.gif";
	
}
