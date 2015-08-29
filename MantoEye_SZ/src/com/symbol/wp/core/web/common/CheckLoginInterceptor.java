package com.symbol.wp.core.web.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.web.action.LoginAction;
import com.symbol.wp.core.web.action.LoginSsoAction;
import com.symbol.wp.core.web.action.LogonCheckAction;

public class CheckLoginInterceptor extends AbstractInterceptor
{
	
	private static final long	serialVersionUID	= 1L;
	public static final String	ERROR				= "error";
	public static final Log	log		= LogFactory.getLog(CheckLoginInterceptor.class);

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception
	{

		log.info("begin check login interceptor!");
		
		Object action = actionInvocation.getAction();
		//如果是登录页面的提交,则跳过检查
		if (action instanceof LoginAction)
		{
			log.info("exit check login, because this is login action.");
			return actionInvocation.invoke();
		}
		//如果是单点登录页面的提交,则跳过检查
		if (action instanceof LoginSsoAction)
		{
			log.info("exit check login, because this is sso login action.");
			return actionInvocation.invoke();
		}
		//如果是页面的Ajax权限检查,则跳过检查
		if(action instanceof LogonCheckAction){
			//log.info("ajax logon check.");
			return actionInvocation.invoke();
		}
		LoginListener loginListener = (LoginListener)actionInvocation.getInvocationContext().getSession().get(VarConstants.LOGIN_LISTENER_KEY);
		if (loginListener != null && loginListener.getSessionContainer() != null)
		{
			//已经登录
			return actionInvocation.invoke();
		}
		else
		{
			//没有登录,跳转到登录页面
			log.info("no login, exit and close window!");
			return ERROR;
		}
	}

}
