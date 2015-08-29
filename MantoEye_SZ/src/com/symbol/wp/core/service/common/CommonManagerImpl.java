package com.symbol.wp.core.service.common;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.dao.impl.BaseOpLogDAO;
import com.symbol.wp.core.dao.impl.BasePermissionsDAO;
import com.symbol.wp.core.dao.impl.BaseRolePermisDAO;
import com.symbol.wp.core.dao.impl.SysdataDAO;
import com.symbol.wp.core.dto.UserAllinfoView;
import com.symbol.wp.core.dto.VBasePermissions;
import com.symbol.wp.core.entity.TbBaseOpLog;
import com.symbol.wp.core.entity.TbBasePermissions;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.listener.SessionContainer;
import com.symbol.wp.core.service.ICommonManager;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.web.form.LoginForm;

/**
 * @author 业务逻辑实现
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class CommonManagerImpl implements ICommonManager {

	public static final Log logger = LogFactory.getLog(CommonManagerImpl.class);
	@Autowired
	private SysdataDAO sysdataDAO;
	@Autowired
	private BaseRolePermisDAO baseRolePermisDAO;
	@Autowired
	private BasePermissionsDAO basePermissionsDAO;
	@Autowired
	private BaseOpLogDAO baseOpLogDAO;

	/*
	 * 业务方法
	 */
	/**
	 * 登陆验证
	 * 
	 * @see jo.com.service.ICommonService#authenticate(java.lang.Object)
	 */
	public Object authenticate(Object object, HttpServletRequest request) {

		LoginForm loginForm = (LoginForm) object;

		/*
		 * 0-该用户为本地用户和密码，需要验证 1-该用户密码为本地页面输入的统一网管平台用户，应该调用单点登录的验证方法验证
		 */
		String ssoflag = loginForm.getSsocheck();// 验证标识
		SessionContainer sessionContainer = null;

		String loginName = Common.OutConvert(loginForm.getLoginName());
		String passwd = Common.OutConvert(loginForm.getPassword());
		UserAllinfoView userAllinfo = null;

		if (ssoflag != null && ssoflag.equals("1")) {
			logger.info("单点登录用户获取本地用户数据...");
			// 已经通过单点登录验证了用户的合法型，本地不再进行密码验证
			userAllinfo = sysdataDAO.authenticatebyVmUserAllinfo(loginName,
					passwd, false);
		} else {
			logger.info("本地用户登录密码验证...");
			userAllinfo = sysdataDAO.authenticatebyVmUserAllinfo(loginName,
					passwd, true);
		}

		if (userAllinfo != null) {
			logger.debug("在本地获取用户数据成功...");
			sessionContainer = new SessionContainer();
			sessionContainer.setUserInfo(userAllinfo.userinfo);
			sessionContainer.setUserId(userAllinfo.userinfo.getId() + "");
			sessionContainer.setUserName(userAllinfo.userinfo.getVcUserName());
			sessionContainer
					.setLoginName(userAllinfo.userinfo.getVcLoginName());
			sessionContainer.setUserNo(userAllinfo.userinfo.getId());
			sessionContainer.putRole(userAllinfo.baseRoles);

			List<TbBasePermissions> permlist = sysdataDAO
					.getFirstMenu(userAllinfo.userinfo.getId());
			String permSymbols = "";
			if (permlist != null && permlist.size() > 0) {
				for (int i = 0; i < permlist.size(); i++) {
					permSymbols = permSymbols + ","
							+ permlist.get(i).getVcPermSymbol();
				}
				permSymbols = permSymbols.substring(1, permSymbols.length());
			}
			// log.info(permlist.size()+"----------"+permSymbols);
			sessionContainer.setBtnPerm(basePermissionsDAO
					.getAllButtonPermis(userAllinfo.userinfo.getId()));
			sessionContainer.setFile_Down_Flag(basePermissionsDAO
					.getAllOtherPermis(userAllinfo.userinfo.getId()));
			// 菜单列表
			sessionContainer.setPermList(permlist);
			sessionContainer.setMenuPerm(permSymbols);
			// 用户类型
			sessionContainer.setUserType(userAllinfo.userinfo.getTiUserType()
					+ "");

			// 写入日志
			this.log(VarConstants.INFO_NO, loginName, request, "成功登陆");
		} else {
			// 写系统日志，记录用户登陆的时间
			if (ssoflag != null && ssoflag.equals("1")) {
				this.log(VarConstants.WARNING_NO, loginName, request,
						"登陆失败，该用户没有本系统的权限！");
			} else {
				this.log(VarConstants.WARNING_NO, loginName, request,
						"登陆失败！用户名或密码错误或系统管理员没有为用户设置权限");
			}
		}

		return sessionContainer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jo.com.service.CommonService#log(java.lang.Object)
	 * 
	 * @function:把日志记录到数据库 @author:wendy
	 */
	public void log(Object object) {
		TbBaseOpLog sysLog = (TbBaseOpLog) object;
		baseOpLogDAO.log(sysLog, 0);
	}

	public void loginOut(SessionContainer sessionContainer,
			HttpServletRequest request) {
		// 写入日志
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		String loginName = loginListener.getSessionContainer().getUserInfo()
				.getVcUserName();

		TbBaseOpLog syslog = new TbBaseOpLog();
		syslog.setDtRecordTime(Timestamp.valueOf(Common.getNow()));
		syslog.setVcLoginIp(request.getRemoteAddr());
		syslog.setVcLoginUser(loginName);
		syslog.setVcLogContent("成功登出");
		syslog.setVcSysLogType("WEB");

		logger.info(loginName + "已经成功登出系统了。。。");
		request.getSession().removeAttribute(VarConstants.LOGIN_LISTENER_KEY);

		baseOpLogDAO.log(syslog, 0);

	}

	public Map getRolePermisMap(String userId) {

		Map rMap = new HashMap();
		Map pMap = new HashMap();

		List rList = baseRolePermisDAO.getRolePermis(userId);
		List pList = basePermissionsDAO.getAllPermis(true);
		//
		Iterator it = pList.iterator();
		while (it.hasNext()) {
			VBasePermissions basePermis = (VBasePermissions) it.next();
			pMap.put(basePermis.getId().toString(), basePermis.getId());
		}

		Iterator iter = rList.iterator();
		while (iter.hasNext()) {
			String permisId = (String) iter.next();
			rMap.put(pMap.get(permisId), permisId);
		}
		return rMap;
	}

	public String getPermissions(String symbol, String userNo) {

		String content = "";
		String parentPermId = "";
		TbBasePermissions perm = basePermissionsDAO.findByPermSymbol(symbol);
		if (perm != null) {
			parentPermId = perm.getVcParentId();
		}
		List list = basePermissionsDAO.getAllPermis(parentPermId, userNo);// 获取改模块下的所有元素
		// （
		// 包括按钮
		// ）
		if (list != null && !list.isEmpty()) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				TbBasePermissions permissions = (TbBasePermissions) it.next();
				if (permissions.getId().equals(symbol)) {
					content = permissions.getId().toString();
				}
			}

		}
		return content;
	}

	/**
	 * 把日志记录到数据库
	 */
	public void log(HttpServletRequest request, String message) {
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		String loginName = loginListener.getSessionContainer().getUserInfo()
				.getVcLoginName();
		try {
			TbBaseOpLog syslog = new TbBaseOpLog();
			syslog.setDtRecordTime(Timestamp.valueOf(Common.getNow()));
			syslog.setVcLoginIp(request.getRemoteAddr());
			syslog.setVcLoginUser(loginName);
			syslog.setVcLogContent(message);
			syslog.setVcSysLogType("WEB");

			baseOpLogDAO.log(syslog, 0);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("写入日志出错！！");
		}
	}

	public void log(long infoLevel, String loginid, HttpServletRequest request,
			String message) {

		try {
			TbBaseOpLog syslog = new TbBaseOpLog();
			syslog.setDtRecordTime(Timestamp.valueOf(Common.getNow()));
			syslog.setVcLoginIp(request.getRemoteAddr());
			syslog.setVcLoginUser(loginid);
			syslog.setVcLogContent(message);
			syslog.setVcSysLogType("WEB");
			// syslog.setNmLogId(1l);
			baseOpLogDAO.log(syslog, 0);

		} catch (Exception ex) {
			logger.error(ex.getMessage());
			logger.info("写入日志出错！！");
		}
	}

	/**
	 * 获取左侧菜单
	 */
	public String getSubMenu(String userNo) {
		Long pid = 0L;

		List permisList = baseRolePermisDAO.getRolePermisByUserId(userNo);
		// List permList = basePermissionsDAO.getPermis();
		// 只查菜单
		List permList = basePermissionsDAO.getMemuPermis();

		return sysdataDAO.getOldSubMenu(permisList, permList);
	}

	/**
	 * 获取顶部快捷菜单
	 */
	public List<VBasePermissions> getTopMenu(String userNo) {
		Long pid = 0L;

		List<VBasePermissions> permList = basePermissionsDAO
				.getTopMemuPermis(userNo);

		return permList;
	}

	public String getLeftMenu(String userNo, String Title, String Url,
			String Event, String TargetFrame, String pid) {
		Long id = Common.StringToLong(pid);
		List permisList = baseRolePermisDAO.getRolePermisByUserId(userNo);
		List permList = basePermissionsDAO.getPermis();
		String navigation = sysdataDAO.getLeftMenu(Title, Url, Event,
				TargetFrame, permisList, permList, id);
		return navigation;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //
	// //////////////////////////////////////////////////////////////////////////
	// //
	// //////////////////////////////////////////////////////////////////////////
	// //
	public List<VBasePermissions> getSubMenuList(String userNo, Object tabid) {
		String pid = (String) tabid;
		List<VBasePermissions> subList = basePermissionsDAO.getSubMemuPermis(
				userNo, pid);
		return subList;
	}
}