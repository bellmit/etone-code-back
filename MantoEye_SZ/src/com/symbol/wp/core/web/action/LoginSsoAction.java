package com.symbol.wp.core.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.RemoteException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.sso.common.UnmpConstants;
import com.symbol.app.mantoeye.unmp.sso.SSO;
import com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonIn;
import com.symbol.app.mantoeye.unmp.sso.SSOCheckLogonOut;
import com.symbol.app.mantoeye.unmp.sso.SSOLocator;
import com.symbol.app.mantoeye.unmp.sso.SSOSoap_BindingStub;
import com.symbol.app.mantoeye.unmp.sso.SSOSoap_PortType;
import com.symbol.app.mantoeye.unmp.sso.UNMPCallResult;
import com.symbol.app.mantoeye.unmp.sso.holders.SSOCheckLogonOutHolder;
import com.symbol.app.mantoeye.unmp.sso.holders.UNMPCallResultHolder;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.exceptions.ServiceStartupException;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.listener.SessionContainer;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.web.form.LoginForm;
import com.symbol.wp.modules.struts2.base.BaseAction;

/**
 *单点登录处理
 * 
 * @author rankin
 * 
 */
@SuppressWarnings("serial")
public class LoginSsoAction extends BaseAction {
	/**
	 * 用户登录验证,用户会话信息服务
	 */
	@Autowired
	@Qualifier("commonManagerImpl")
	/*
	 * @Autowired和@Qualifier注释使得接口可以被容器注入， 当Man接口存在两个实现类的时候必须指定其中一个来注入，
	 * 使用实现类首字母小写的字符串来注入。否则可以省略，只写@Autowired
	 */
	private ICommonManager commonManagerImpl;

	/**
	 * 用户登录实体
	 */
	private LoginForm loginForm;

	/**
	 * 用户登录信息
	 * 
	 * @return
	 */
	public LoginForm getLoginForm() {
		return loginForm;
	}

	/**
	 * 设置用户登录信息
	 * 
	 * @param loginForm
	 */
	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}

	/**
	 * 单点登录处理 从统一网管平台跳转的用户
	 * 
	 * @throws IOException
	 */
	public String login_sso() throws ServiceStartupException, IOException {

		logger.info("sso login at["+Common.getNow()+"]");
		HttpServletRequest request = this.getServletRequest();
		String userID = null;
		SSO sso = new SSOLocator();
		SSOSoap_PortType stub = null;
		logger.info("before check connection["+Common.getNow()+"]");
		try {
			stub = sso.getSSOSoap();
		} catch (ServiceException e3) {

			e3.printStackTrace();
		}
		logger.info("after check connection["+Common.getNow()+"]");
		if (stub != null) {

			SSOCheckLogonIn ssocheckIn = new SSOCheckLogonIn();
			ssocheckIn.setIp(request.getRemoteAddr());
			Cookie[] cookies = request.getCookies();
			int index = 3;
			if (cookies != null && cookies.length >= 3) {
				for (int i = 0; i < cookies.length; i++) {
//					logger.info("cookies["
//							+ i
//							+ "]:["
//							+ cookies[i].getName()
//							+ "]"
//							+ URLDecoder.decode(cookies[i].getValue(),
//									"US-ASCII"));
					if(cookies[i].getName().indexOf("Login-Passport")!=-1){
						index = i;
					}
				}
				String kk = null;
				try {
					kk = URLDecoder.decode(cookies[index].getValue(), "US-ASCII");				
					logger.info("TokenEx=" + kk);
				} catch (UnsupportedEncodingException e2) {
					e2.printStackTrace();
				}
				ssocheckIn.setToken("");
				ssocheckIn.setTokenEx(kk);
				UNMPCallResultHolder resultHolder = new UNMPCallResultHolder();
				SSOCheckLogonOutHolder outHolder = new SSOCheckLogonOutHolder();
				UNMPCallResult nmp = null;
				SSOCheckLogonOut ssoOut = null;
				try {
					logger.info("before check logon ["+Common.getNow()+"]");
					stub.unmpAndPortalSSOCheckLogon2(UnmpConstants.UNMP_SYSTEM_ID,
							UnmpConstants.UNMP_SYS_ACCOUNT,
							UnmpConstants.UNMP_SYS_PASSWORD, ssocheckIn,
							resultHolder, outHolder);
					logger.info("after check logon["+Common.getNow()+"]");
					nmp = resultHolder.value;
					ssoOut = outHolder.value;
				} catch (RemoteException e) {
					logger.error(e.getMessage());
				}
				if (nmp != null && nmp.getReturnCode() == 0) {
//					logger.info("--Code--" + nmp.getReturnCode() + "--Msg--"
//							+ nmp.getReturnMessage());
					userID = ssoOut.getUserID();
					String loginID = ssoOut.getLoginID();
					String fullName = ssoOut.getFullName();
					LoginForm loginForm = new LoginForm();
					loginForm.setLoginName(loginID);
					loginForm.setPassword("");
					loginForm.setSsocheck("1");
					logger.info("LoginID:" + loginID);
					logger.info("LoginName:" + fullName);
					logger.info("after sso login["+Common.getNow()+"]");
					/**
					 * session 容器进行用户登录验证
					 */
					SessionContainer sessionContainer = (SessionContainer) commonManagerImpl
							.authenticate(loginForm, this.getServletRequest());
					logger.info("after mantoeye authenticate["+Common.getNow()+"]");
					/**
					 * 登陆成功
					 */
					if (sessionContainer != null) {
						LoginListener loginListener = this.getLoginListener();
						loginListener.setSessionContainer(sessionContainer);
						// Session超时限制为30分钟"
						this.getServletRequest().getSession().setAttribute(
								"baseRoles",
								sessionContainer.getBaseRoles().get(0));
						this.getServletRequest().getSession().setAttribute("file_Down_Flag",
								sessionContainer.getFile_Down_Flag());
						this.getServletRequest().getSession().setAttribute("baseUserName",
								sessionContainer.getUserName());
						this.getServletRequest().getSession().setAttribute("baseUserId",
								sessionContainer.getUserId());
						this.getServletRequest().getSession()
								.setMaxInactiveInterval(72000);
						logger.info("登陆成功!");
						logger.info("login success["+Common.getNow()+"]");
						return SUCCESS;
					} else {
						logger.info("登录失败,该用户没有本系统权限！");
						this.getServletRequest().setAttribute(
								VarConstants.ERROR_CODE,
								MsgConstants.LOGIN_ERROR_SSO_MSG);
						return ERROR;
					}

				} else {
					logger.info("无法调用统一网管平台登录验证接口,登录验证失败");
					logger.info("Code:" + nmp.getReturnCode() + "----Msg:"
							+ nmp.getReturnMessage());
					this.getServletRequest().setAttribute(
							VarConstants.ERROR_CODE,
							MsgConstants.LOGIN_ERROR_SSOCNT_MSG);
					return ERROR;
				}
			} else {
				logger.info("Cookie size:" + cookies.length);
				logger.info("无法从统一网管平台获取用户信息");
				return ERROR;
			}
		} else {
			logger.info("调用统一网管平台出错");
			return ERROR;
		}
	}
}
