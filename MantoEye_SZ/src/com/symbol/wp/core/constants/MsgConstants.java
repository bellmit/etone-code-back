package com.symbol.wp.core.constants;

/**
 * 定义提示信息常量
 * 
 * @Title: MsgConstants.java
 * @Description: <br>
 * <br>
 * @Company: etonetech
 * @Created Mar 20, 2009 2:48:46 PM
 * @author ranhualin
 * @version 1.0
 * @since 1.0
 */
public class MsgConstants {

	public static final String LOGIN_TIMEOUT_MSG = "您的登录已超时，请重新登入!";

	public static final String LOGIN_ERROR_MSG = "登录失败,用户名或密码错误,或该用户没有权限!";

	public static final String LOGIN_ERROR_SSO_MSG = "登录失败,您没有本系统权限!";

	public static final String LOGIN_ERROR_SSOCNT_MSG = "统一网管平台登录验证接口无法调用,登录验证失败!";

	/*
	 * 错误代号
	 */
	public static final String ERROR_CODE_00001 = "错误代号：00001；验证码错误";
	public static final String ERROR_CODE_00002 = "错误代号：00002；用户名或密码错误";
	public static final String ERROR_CODE_00004 = "错误代号：00004；保存数据失败！";
	public static final String ERROR_CODE_00005 = "错误代号：00005；删除数据失败！";
	public static final String ERROR_CODE_00007 = "错误代号：00007；密码初始化失败！";
	public static final String ERROR_CODE_00008 = "错误代号：00008；密码修改失败！";
	public static final String ERROR_CODE_00009 = "错误代号：00009；关联角色用户失败！";
	public static final String ERROR_CODE_00010 = "错误代号：00010；权限分配失败！";
	public static final String ERROR_CODE_00011 = "错误代号：00011；文件上传失败！";
	public static final String ERROR_CODE_00012 = "错误代号：00012；清除终端失败！";
	public static final String ERROR_CODE_00013 = "错误代号：00013；文件上传失败,文件必须包含标题！";

	public static final String ERROR_CODE_00023 = "错误代号：00023；该登录名的用户已经存在，请重新输入！";//
	public static final String ERROR_CODE_10000 = "错误代号：10000；取消告警操作失败！";//

	/*
	 * 成功代号
	 */
	public static final String SUCC_CODE_00001 = "00001"; // 添加成功
	public static final String SUCC_CODE_00002 = "00002";// 编辑成功
	public static final String SUCC_CODE_00003 = "00003"; // 密码初始化为'000000'
	public static final String SUCC_CODE_00004 = "00004";
	public static final String SUCC_CODE_00005 = "00005";
	public static final String SUCC_CODE_00006 = "00006";// 文件上传成功
	public static final String SUCC_CODE_10000 = "10000";// 取消告警操作完成！
	public static final String SUCC_CODE_00007 = "00007";// 角色关联用户成功
	public static final String SUCC_CODE_00008 = "00008";// 删除数据成功

	public static final String SUCC_CODE_00026 = "00026";// 密码修改成功
	public static final String SUCC_CODE_00027 = "00027";// 删除角色用户成功
	public static final String SUCC_CODE_00028 = "00028";// 关联角色用户成功
	public static final String SUCC_CODE_00029 = "00029";// 权限分配成功

	public static final String SUCC_CODE_00030 = "00030";// 添加模块成功
	public static final String SUCC_CODE_00031 = "00031";// 删除模块成功
	public static final String SUCC_CODE_00032 = "00032";// 编辑模块成功
	public static final String SUCC_CODE_00033 = "00033";// 删除日志成功
	public static final String SUCC_CODE_00038 = "00038";// 删除角色组存在子角色组

	public static final String ERROR_CODE_00034 = "00034";// 添加模块失败
	public static final String ERROR_CODE_00035 = "00035";// 删除模块失败
	public static final String ERROR_CODE_00037 = "00037";// 删除日志失败

	// public static final String SUCC_CODE_00030 = "00030";//添加模块成功
	// public static final String SUCC_CODE_00031 = "00031";//删除模块成功
	// public static final String SUCC_CODE_00032 = "00032";//编辑模块成功
	// public static final String SUCC_CODE_00033 = "00033";//删除日志成功
	//
	// public static final String ERROR_CODE_00034 = "00034";//添加模块失败
	// public static final String ERROR_CODE_00035 = "00035";//删除模块失败
	// public static final String ERROR_CODE_00037 = "00037";//删除日志失败

	// //////////////////////////MantoEye New////////////////////////////////
	public static final String SUCC_CODE_00101 = "00101"; // 添加成功
	public static final String SUCC_CODE_00102 = "00102";// 编辑成功
	public static final String SUCC_CODE_00103 = "00103";// 编辑成功
	public static final String SUCC_CODE_00104 = "00104";// 权限设置成功
	public static final String SUCC_CODE_00105 = "00105"; // 回复问题反馈成功
	public static final String SUCC_CODE_00106 = "00106"; // 修改密码成功
	public static final String SUCC_CODE_00107 = "00107"; // 导入成功

	public static final String SUCC_CODE_00202 = "00202";// 编辑拨测任务失败，该任务已经开始

}
