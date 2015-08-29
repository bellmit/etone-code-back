package com.symbol.wp.core.web.action;

import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseAction;

/**
 * 
 * 判断当前用户是否已经超时（页面ajax方法使用）
 * @author rankin
 *
 */
public class LogonCheckAction extends BaseAction{
	
	public void check(){
		String logon = "true";
		LoginListener loginListener = (LoginListener) this.getServletRequest().getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		if (loginListener == null|| loginListener.getSessionContainer() == null) {
			logon ="false";
		}
		Struts2Utils.renderText(logon);
	}
}
