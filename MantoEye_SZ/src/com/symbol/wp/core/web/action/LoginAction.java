package com.symbol.wp.core.web.action;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.symbol.app.mantoeye.service.terminalmanager.TerminalManager;
import com.symbol.app.mantoeye.sso.common.UnmpConstants;
import com.symbol.app.mantoeye.unmp.sso.SSO;
import com.symbol.app.mantoeye.unmp.sso.SSOCheckLoginIn;
import com.symbol.app.mantoeye.unmp.sso.SSOLocator;
import com.symbol.app.mantoeye.unmp.sso.SSOSoap_BindingStub;
import com.symbol.app.mantoeye.unmp.sso.SSOSoap_PortType;
import com.symbol.app.mantoeye.unmp.sso.UNMPCallResult;
import com.symbol.app.mantoeye.unmp.sso.holders.SSOCheckLogonOutHolder;
import com.symbol.app.mantoeye.unmp.sso.holders.UNMPCallResultHolder;
import com.symbol.app.mantoeye.web.action.Phone;
import com.symbol.wp.core.constants.MsgConstants;
import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.exceptions.ServiceStartupException;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.listener.SessionContainer;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.util.PropertiesUtil;
import com.symbol.wp.core.web.form.LoginForm;
import com.symbol.wp.modules.struts2.Struts2Utils;
import com.symbol.wp.modules.struts2.base.BaseAction;

/**
 * 用户登录处理
 * 
 * @Title: LoginAction.java
 * @Description: <br>
 * <br>
 * @Company: etonetech
 * @Created Mar 23, 2009 9:38:30 AM
 * @author ranhualin
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public class LoginAction extends BaseAction {
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

	@Autowired
	private TerminalManager baseTerminalManager;

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
	 * 执行用户登录处理
	 * 
	 * @throws IOException
	 */
	public String login_in() throws ServiceStartupException, IOException {
		String validate_code = loginForm.getValidatecCode();
		HttpSession sesssion = Struts2Utils.getSession();
		String checked = PropertiesUtil.getInstance().getProperty(
				"system.validatecode.interference");

		boolean vc = false;// 验证码是否正确
		if (checked.equals("false")) {
			logger.debug("不检查验证码！");
			vc = true;
		} else {
			if (Common.judgeString(validate_code)) {
				String iscase = PropertiesUtil.getInstance().getProperty(
						"system.validatecode.case");
				if (iscase != null && "true".equals(iscase)) {// 验证码区分大小写
					logger.debug("验证码区分大小写");

					vc = validate_code.equals((String) sesssion
							.getAttribute("LOGIN_VALIDATE_CODE"));
				} else {// 验证码不区分大小写
					logger.debug("验证码不区分大小写");
					vc = validate_code.equalsIgnoreCase((String) sesssion
							.getAttribute("LOGIN_VALIDATE_CODE"));
				}

			}
		}
		// 验证码正确时
		if (vc) {

			if (loginForm.getUsertype() != null
					&& loginForm.getUsertype().equals("1")) {// 统一网管平台用户，需要通过单点登录验证
				boolean b = checkSsoLogin(loginForm.getLoginName(),
						loginForm.getPassword());
				if (!b) {
					this.getServletRequest().setAttribute(
							VarConstants.ERROR_CODE,
							MsgConstants.LOGIN_ERROR_MSG);
					return ERROR;
				} else {
					// 验证已通过
					loginForm.setSsocheck("1");
				}
			}
			/**
			 * session 容器进行用户登录验证
			 */
			SessionContainer sessionContainer = (SessionContainer) commonManagerImpl
					.authenticate(loginForm, this.getServletRequest());

			/**
			 * 登陆成功
			 */
			if (sessionContainer != null) {
				LoginListener loginListener = this.getLoginListener();
				loginListener.setSessionContainer(sessionContainer);
				// Session超时限制为30分钟"
				this.getServletRequest()
						.getSession()
						.setAttribute("baseRoles",
								sessionContainer.getBaseRoles().get(0));

				this.getServletRequest()
						.getSession()
						.setAttribute("baseUserName",
								sessionContainer.getUserName());
				this.getServletRequest()
						.getSession()
						.setAttribute("baseUserId",
								sessionContainer.getUserId());
				this.getServletRequest()
						.getSession()
						.setAttribute("file_Down_Flag",
								sessionContainer.getFile_Down_Flag());
				this.getServletRequest().getSession()
						.setMaxInactiveInterval(72000);
				logger.info("登陆成功!");
				return SUCCESS;
			} else {
				logger.info("登录失败,用户名或者密码错误或该用户没有登录权限！");
				this.getServletRequest().setAttribute(VarConstants.ERROR_CODE,
						MsgConstants.LOGIN_ERROR_MSG);
				return ERROR;
			}

		} else {
			logger.info("登录失败,验证码错误！");
			this.getServletRequest().setAttribute(VarConstants.ERROR_CODE,
					MsgConstants.ERROR_CODE_00001);
			return ERROR;
		}
	}

	/**
	 * 通过统一网管平台验证用户名密码
	 * 
	 * @return
	 */
	private boolean checkSsoLogin(String loginId, String pwd) {
		boolean b = false;
		SSO sso = new SSOLocator();
		SSOSoap_PortType stub = null;
		;
		try {
			stub = sso.getSSOSoap();
		} catch (ServiceException e3) {

			e3.printStackTrace();
		}
		if (stub != null) {

			SSOCheckLoginIn in = new SSOCheckLoginIn();
			in.setGmccSystemID(UnmpConstants.UNMP_SYSTEM_ID);
			in.setLoginID(loginId);
			in.setPassWord(pwd);

			UNMPCallResultHolder resultHolder = new UNMPCallResultHolder();
			SSOCheckLogonOutHolder outHolder = new SSOCheckLogonOutHolder();

			UNMPCallResult nmp = null;
			try {
				stub.loginCheck(UnmpConstants.UNMP_SYSTEM_ID,
						UnmpConstants.UNMP_SYS_ACCOUNT,
						UnmpConstants.UNMP_SYS_PASSWORD, in, resultHolder,
						outHolder);
				nmp = resultHolder.value;
			} catch (RemoteException e) {
				logger.info("调用统一网管平台验证接口出错!");
				return false;
			}
			if (nmp != null && nmp.getReturnCode() == 0) {
				// logger.info("验证通过!");
				return true;
			} else {
				logger.info("验证不通过!");
				logger.info("Code:" + nmp.getReturnCode() + "----Msg:"
						+ nmp.getReturnMessage());
			}
		} else {
			logger.info("调用统一网管平台验证接口出错!");
		}
		return b;
	}

	/**
	 * 业务对话框
	 */
	public void initBusiness() {
		HttpSession sesssion = Struts2Utils.getSession();
		HttpServletRequest request = Struts2Utils.getRequest();
		sesssion.setAttribute("phoneInfoList", packBusinessInfo());

	}

	public List<Phone> packBusinessInfo() {
		List<Phone> list = new ArrayList<Phone>();
		Phone p = new Phone();
		p.setType("websize");
		p.setName("网站");
		p.setDetailModel("腾讯:易查:3G门户:乐讯:新浪:空中网:动感论坛:百度:移动梦网:友度:其他");

		/*
		 * Phone p1 = new Phone(); p1.setType("appType"); p1.setName("应用软件");
		 * p1.setDetailModel("IM软件:视频软件:音频软件:证券软件");
		 */
		Phone p2 = new Phone();
		p2.setType("IM");
		p2.setName("IM业务");
		p2.setDetailModel("IM_QQ:IM_Fetion:IM_PICA:IM_MSN");

		Phone p3 = new Phone();
		p3.setType("video");
		p3.setName("视频业务");
		p3.setDetailModel("GGLIVE:万花筒");

		Phone p4 = new Phone();
		p4.setType("audio");
		p4.setName("音频业务");
		p4.setDetailModel("GGMUSIC:POP音乐:音乐随身听:百灵鸟");

		Phone p5 = new Phone();
		p5.setType("bond");
		p5.setName("证券业务");
		p5.setDetailModel("3G财神通:大智慧:同花顺");

		list.add(p);
		// list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5);
		return list;
	}

	/**
	 * 初始化终端管理
	 */
	public void initTerminal() {
		HttpSession sesssion = Struts2Utils.getSession();
		HttpServletRequest request = Struts2Utils.getRequest();
		sesssion.setAttribute("phoneInfoTerminalList", packTerminalInfo());
	}

	/**
	 * 组装终端信息
	 */
	public List<Phone> packTerminalInfo() {
		List<Phone> list = new ArrayList<Phone>();
		Map<String, Map<String, Integer>> map = null;
		List<String> brandList = new ArrayList<String>(map.keySet());
		Phone p = null;
		if (brandList != null && !brandList.isEmpty()) {
			for (int i = 0; i < brandList.size(); i++) {
				p = new Phone();
				String brandAnadId = brandList.get(i);
				Map<String, Integer> mapType = map.get(brandAnadId);
				String[] str = brandAnadId.split(":");// 分离品牌名称和Id
				p.setName(str[0].toString());// 品牌名称
				p.setBrandId(str[1]);// 品牌ID
				Set setType = mapType.keySet();
				Collection con = mapType.values();
				if (setType != null) {
					String modle = setType.toString().replace("[", "")
							.replace("]", "").replaceAll(",", ":");
					p.setDetailModel(modle);// 型号名称
					String typeIds = con.toString().replace("[", "")
							.replace("]", "").replaceAll(",", ":");// 型号IDs
					p.setTypeId(typeIds);
				}
				list.add(p);
			}

		}

		return list;
	}
}
