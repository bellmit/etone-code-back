package com.symbol.wp.core.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.exceptions.ServiceStartupException;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.modules.struts2.base.BaseAction;


/**
 * 用户登出
 * @Title: LoginOutAction.java
 * @Description: <br>
 *               <br>
 * @Company: etonetech
 * @Created Mar 23, 2009 9:47:31 AM
 * @author ranhualin
 * @version 1.0
 * @since 1.0
 */
public class LoginOutAction extends BaseAction
{
	@Autowired
	private ICommonManager commonService = null;

    

    @Override
	public String execute() throws ServiceStartupException
    {
        LoginListener loginListener = (LoginListener) getServletRequest().getSession().getAttribute(VarConstants.LOGIN_LISTENER_KEY);
        if (loginListener != null && loginListener.getSessionContainer() != null)
        {
        	commonService.loginOut(loginListener.getSessionContainer(), getServletRequest());
        }
        getServletRequest().getSession().invalidate();
        return SUCCESS;
    }
}
