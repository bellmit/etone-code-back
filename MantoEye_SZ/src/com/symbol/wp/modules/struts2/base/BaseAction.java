package com.symbol.wp.modules.struts2.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.listener.LoginListener;

/**
 * 简单封装Struts ActionSupport的基类. 提供Log4j支持及用户会话信息服务
 */
public class BaseAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware {
	// ---- HttpServletRequest----
	/**
	 * 设置日志记录器log4j
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 数据列表
	 */
	public List dataList = new ArrayList();
	/**
	 * 模块权限ID
	 */
	private String permId;

	/**
	 * 获取权限ID
	 * 
	 * @return
	 */
	public String getPermId() {
		return permId;
	}

	/**
	 * 设置权限ID
	 * 
	 * @param permId
	 */
	public void setPermId(String permId) {
		this.permId = permId;
	}

	// ---- HttpServletResponse----
	private HttpServletResponse servletResponse;

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	// ---- HttpServletRequest----
	private HttpServletRequest servletRequest;

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	/**
	 * <p>
	 * Field to store application context or its proxy.
	 * </p>
	 * <p/>
	 * <p>
	 * The application context lasts for the life of the application. A
	 * reference to the database is stored in the application context at
	 * startup.
	 * </p>
	 */
	private Map application;

	/**
	 * <p>
	 * Store a new application context.
	 * </p>
	 * 
	 * @param value
	 *            A Map representing application state
	 */
	public void setApplication(Map value) {
		application = value;
	}

	/**
	 * <p>
	 * Provide application context.
	 * </p>
	 */
	public Map getApplication() {
		return application;
	}

	// ---- SessionAware ----

	/**
	 * <p>
	 * Field to store session context, or its proxy.
	 * </p>
	 */
	private Map session;

	/**
	 * <p>
	 * Store a new session context.
	 * </p>
	 * 
	 * @param value
	 *            A Map representing session state
	 */
	public void setSession(Map value) {
		session = value;
	}

	/**
	 * <p>
	 * Provide session context.
	 * </p>
	 * 
	 * @return session context
	 */
	public Map getSession() {
		return session;
	}

	/**
	 * 用户会话信息
	 * 
	 * @return
	 */
	protected LoginListener getLoginListener() {
		LoginListener loginListener = (LoginListener) getServletRequest()
				.getSession().getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		if (loginListener == null) {
			loginListener = new LoginListener();
			loginListener.setLocale(getServletRequest().getLocale());
			getServletRequest().getSession().setAttribute(
					VarConstants.LOGIN_LISTENER_KEY, loginListener);
			this.getSession().put(VarConstants.LOGIN_LISTENER_KEY,
					loginListener);
		}
		return loginListener;
	}

}
