package com.symbol.wp.modules.struts2.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.listener.LoginListener;

/**
 * 定义了一些Action类常用的方法
 * 
 * @Title: BaseDispatchAction.java
 * @Description: <br>
 * <br>
 * @Company: etonetech
 * @Created Mar 20, 2009 2:46:43 PM
 * @author ranhualin
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public abstract class BaseDispatchAction extends BaseAction {
	
	
	/**
	 * 进行增删改操作后,以redirect方式重新打开action默认页的result名.
	 */
	public static final String RELOAD = "reload";
	
	public static final String SUCCESS = "success";
	
	public static final String ERROR = "error";
	
	public static final String INDEX = "index";
	
	public static final String INPUT = "input";
	
	public static final String ADD = "add";
	
	public static final String EDIT = "edit";
	
	public static final String DETAIL = "detail";
	
	public static final String ASSOCIATE = "associate";
	
	public static final String PERMIS="permis";

	private String keys;// 当前选中的多条记录的id的字符串 以','分割
	
	@Override
	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		if (loginListener == null
				|| loginListener.getSessionContainer() == null) {
			request.setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.LOGIN_TIMEOUT_MSG);
			return ERROR;
		}
		return super.execute();
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

}
