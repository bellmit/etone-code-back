package com.symbol.wp.core.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.wp.core.constants.VarConstants;
import com.symbol.wp.core.dao.impl.BaseDepartmentDAO;
import com.symbol.wp.core.dao.impl.BaseRolesDAO;
import com.symbol.wp.core.dao.impl.BaseUserInfoDAO;
import com.symbol.wp.core.dao.impl.BaseUserRoleDAO;
import com.symbol.wp.core.dto.VBaseUserInfo;
import com.symbol.wp.core.entity.TbBaseDepartment;
import com.symbol.wp.core.entity.TbBaseRoles;
import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.entity.TbBaseUserRole;
import com.symbol.wp.core.listener.LoginListener;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.core.util.PropertiesUtil;
import com.symbol.wp.core.util.Utils;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

/**
 * 
 * @author rankin 用户管理
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class BaseUserInfoManager extends EntityManager<TbBaseUserInfo, String> {

	@Autowired
	private BaseUserInfoDAO baseUserInfoDAO;
	@Autowired
	private BaseUserRoleDAO baseUserRoleDAO;
	@Autowired
	private BaseRolesDAO baseRolesDAO;
	@Autowired
	private BaseDepartmentDAO baseDepartmentDAO;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected BaseUserInfoDAO getEntityDao() {
		return baseUserInfoDAO;
	}

	/***************************************************************************
	 * 组装分页数据
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<TbBaseUserInfo> search(final Page<TbBaseUserInfo> page,
			final List<PropertyFilter> filters) {
		Page<TbBaseUserInfo> apage = getEntityDao().find(page, filters);
		List viewList = new ArrayList();
		List<TbBaseUserInfo> dateList = apage.getResult();
		for (TbBaseUserInfo bean : dateList) {
			viewList.add(converBeanToView(bean));
		}
		apage.setVresult(viewList);
		return apage;
	}

	/**
	 * 视图与对象的转换
	 * 
	 * @param bean
	 * @return
	 */
	public VBaseUserInfo converBeanToView(TbBaseUserInfo bean) {
		VBaseUserInfo view = new VBaseUserInfo();
		TbBaseDepartment dept = null;
		List<TbBaseRoles> roles = null;
		StringBuffer sb = null;
		sb = new StringBuffer();
		dept = baseDepartmentDAO.findByUnique("id", bean.getVcDeptId());
		roles = baseRolesDAO.getRolesByLoginName(bean.getVcLoginName());
		try {
			BeanUtils.copyProperties(view, bean);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		if (dept != null) {
			view.setDeptName(dept.getVcDeptName());
		}
		if (roles != null && roles.size() > 0) {
			String roleNames = "";
			for (TbBaseRoles r : roles) {
				sb.append(r.getVcRolesName() + ",");
			}
			roleNames = sb.toString();
			if (roleNames.indexOf(",") > -1) {
				roleNames = roleNames.substring(0,roleNames.lastIndexOf(","));
			}
			view.setRoleName(roleNames);
		}
		return view;
	}

	/**
	 * 更新用户密码
	 */
	public boolean updatePwd(String oldPwd, String newPwd,
			HttpServletRequest request) {
		boolean bo = false;
		String usemd5 = PropertiesUtil.getInstance().getProperty(
				"system.password.save.encry");// 数据库保存密码是否要经md5加密
		String oldpw = oldPwd;
		String newpw = newPwd;
		if (usemd5 != null && usemd5.equalsIgnoreCase("MD5")) {
			oldpw = Utils.getMD5String(oldPwd);
			newpw = Utils.getMD5String(newPwd);
		}

		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		String userid = loginListener.getSessionContainer().getUserInfo()
				.getId();
		try {
			TbBaseUserInfo userinfo = baseUserInfoDAO.get(userid);
			if (userinfo != null && userinfo.getVcPassword().equals(oldpw)) {
				userinfo.setVcPassword(newpw);
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			bo = false;
		}

		return bo;
	}

	/**
	 * 为用户增加角色
	 * 
	 * @param userNo
	 * @param rolesNo
	 * @param request
	 * @return
	 */
	public boolean inserUserRoles(String userId, String roleId,
			HttpServletRequest request) {
		LoginListener loginListener = (LoginListener) request.getSession()
				.getAttribute(VarConstants.LOGIN_LISTENER_KEY);
		String login_name = loginListener.getSessionContainer().getUserInfo()
				.getVcUserName();
		try {
			// 删除原角色再增加新角色(现支持一个用户一个角色)
			baseUserRoleDAO.deleteByUserId(userId);
			TbBaseUserRole baseUserRole = new TbBaseUserRole();
			baseUserRole.setVcRoleId(roleId);
			baseUserRole.setVcUserId(userId);
			baseUserRoleDAO.save(baseUserRole);
			return true;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return false;
		}

	}

	public TbBaseRoles getBaseRole(String vcUserNo) {
		TbBaseRoles baseRoles = null;
		List rolesList = baseUserRoleDAO.getRoles(vcUserNo);
		if (rolesList != null && !rolesList.isEmpty()) {
			String rolesId = rolesList.get(0).toString();
			baseRoles = baseRolesDAO.get(rolesId);
		}
		return baseRoles;
	}

	public List getRoles(String vcUserNo) {
		List rolesList = baseUserRoleDAO.getRoles(vcUserNo);
		return rolesList;
	}

	public boolean deleteMutilUserRoles(String userNos, Long rolesId,
			HttpServletRequest request) {
		try {
			String[] idarr = userNos.split(",");
			baseUserRoleDAO.deleteByUserId(idarr);
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	public boolean inserMutilUserRoles(String userIds, String rolesNo,
			HttpServletRequest request) {
		String[] users = userIds.split(",");
		int count = 0;
		for (int i = 0; i < users.length; i++) {
			if (Common.judgeString(users[i])) {
				this.inserUserRoles(users[i], rolesNo, request);
				count++;
			}
		}
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 重置密码初始值为000000(6个零)
	 * 
	 * @param userids
	 * @param request
	 * @return
	 */
	public boolean updatePwdReset(String userids, HttpServletRequest request) {
		String usemd5 = PropertiesUtil.getInstance().getProperty(
				"system.password.save.encry");// 数据库保存密码是否要经md5加密
		String newpw = VarConstants.INIT_PASSWORD;
		StringBuffer sb = new StringBuffer();
		String[] tempIds = userids.split(",");
		for (int i = 0; i < tempIds.length; i++) {
			if (i == tempIds.length - 1) {
				sb.append("'");
				sb.append(tempIds[i]);
				sb.append("'");
			} else {
				sb.append("'");
				sb.append(tempIds[i]);
				sb.append("'");
				sb.append(",");
			}

		}
		String ids = sb.toString();
		if (usemd5 != null && usemd5.equalsIgnoreCase("MD5")) {
			newpw = Utils.getMD5String(VarConstants.INIT_PASSWORD);
		}
		try {
			baseUserInfoDAO.updatePwdReset(ids, newpw);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据角色标识获取用户标识符
	 */
	public String getUserIdByRoleId(String vcRoleNo) {
		return baseUserInfoDAO.getUserIdByRoleId(vcRoleNo);
	}
}