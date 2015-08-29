package com.symbol.wp.core.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.wp.core.constants.SqlrConstants;
import com.symbol.wp.core.dao.impl.BaseRoleGroupDAO;
import com.symbol.wp.core.dao.impl.BaseRolePermisDAO;
import com.symbol.wp.core.dao.impl.BaseRolesDAO;
import com.symbol.wp.core.dto.VBaseRoles;
import com.symbol.wp.core.entity.TbBaseRoleGroup;
import com.symbol.wp.core.entity.TbBaseRolePermis;
import com.symbol.wp.core.entity.TbBaseRoles;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class BaseRoleManager extends EntityManager<TbBaseRoles, String> {

	@Autowired
	private BaseRolesDAO baseRolesDAO;
	@Autowired
	private BaseRolePermisDAO baseRolePermisDAO;

	@Autowired
	private BaseRoleGroupDAO baseRoleGroupDAO;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected BaseRolesDAO getEntityDao() {
		return baseRolesDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TbBaseRoles> search(final Page<TbBaseRoles> page,
			final List<PropertyFilter> filters) {

		Page<TbBaseRoles> age = baseRolesDAO.find(page, filters);
		List<TbBaseRoles> list = age.getResult();
		List<VBaseRoles> viewList = new ArrayList<VBaseRoles>();
		VBaseRoles vBaseRole = null;
		for (TbBaseRoles tbBaseRole : list) {
			vBaseRole = new VBaseRoles();
			try {
				BeanUtils.copyProperties(vBaseRole, tbBaseRole);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			} catch (InvocationTargetException e) {

				logger.error(e.getMessage());
			}
			viewList.add(vBaseRole);
		}

		age.setVresult(viewList);
		return age;

	}

	/**
	 * 获取角色对应的菜单集合对象
	 */
	public List getRolePermis(String role_list) {
		return baseRolePermisDAO.getRolePermis(role_list);
	}

	/*
	 * 业务方法
	 */

	public boolean deleteRecord(String[] keys, HttpServletRequest request) {
		boolean flag = false;
		try {
			for (String id : keys) {
				super.delete(id);
			}
			flag = true;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			// 写入日志
			return false;
		}
		return flag;
	}

	public void setRolePermis(List<String> list, String role_no) {
		if (baseRolePermisDAO.setRolePermis(list, role_no)) {
			// 写入日志

		} else {
			// 写入日志

		}
	}

	public boolean checkSymbol(String fieldName, Object symbol, Object oldsymbol) {
		return baseRolesDAO.isPropertyUnique(fieldName, symbol, oldsymbol);
	}

	public List getBeanList(TbBaseRoles searchBean) {
		return baseRolesDAO.getBeanList(searchBean);
	}

	public boolean saveEntity(TbBaseRoles bean, HttpServletRequest request) {

		try {
			super.save(bean);

			return true;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			// 写入日志

			return false;
		}
	}

	public boolean update(TbBaseRoles bean, HttpServletRequest request) {
		try {
			super.save(bean);
			return true;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return false;
		}
	}

	public String findRoleByGroup(String roleGroupId) {
		List<TbBaseRoleGroup> list = baseRoleGroupDAO
				.findRoleGroupByNo1(roleGroupId);
		StringBuffer sb = new StringBuffer();
		sb.append(roleGroupId);
		sb.append(",");
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i).getId());
				sb.append(",");
			}
		}
		return sb.substring(0, sb.lastIndexOf(","));
	}

	public TbBaseRoleGroup findGroupByName(String name) {
		return baseRoleGroupDAO.findByName(name);
	}

}