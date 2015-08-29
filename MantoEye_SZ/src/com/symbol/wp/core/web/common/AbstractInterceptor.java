package com.symbol.wp.core.web.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public abstract class AbstractInterceptor implements Interceptor, ServletRequestAware
{

	// ---- HttpServletRequest----
	private HttpServletRequest	servletRequest;

	public HttpServletRequest getServletRequest()
	{
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest)
	{
		this.servletRequest = servletRequest;
	}

	
	public void destroy()
	{

	}


	public void init()
	{

	}

	public abstract String intercept(ActionInvocation invocation) throws Exception;

}
