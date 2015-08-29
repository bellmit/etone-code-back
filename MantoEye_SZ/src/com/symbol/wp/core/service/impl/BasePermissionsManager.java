package com.symbol.wp.core.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.wp.core.dao.impl.BasePermissionsDAO;
import com.symbol.wp.core.dto.VBasePermissions;
import com.symbol.wp.core.entity.TbBasePermissions;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class BasePermissionsManager extends
		EntityManager<TbBasePermissions, String> {

	private static final Log log = LogFactory
			.getLog(BasePermissionsManager.class);
	@Autowired
	private BasePermissionsDAO basePermissionsDAO;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected BasePermissionsDAO getEntityDao() {
		return basePermissionsDAO;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TbBasePermissions> search(final Page<TbBasePermissions> page,
			final List<PropertyFilter> filters) {

		Page<TbBasePermissions> age = basePermissionsDAO.find(page, filters);
		List<TbBasePermissions> dateList = age.getResult();
		List<VBasePermissions> viewList = new ArrayList<VBasePermissions>();
		for (TbBasePermissions bean : dateList) {
			viewList.add(converBeanToView(bean));
		}
		age.setVresult(viewList);
		return age;
	}

	/**
	 * 视图与对象的转换
	 * 
	 * @param bean
	 * @return
	 */
	public VBasePermissions converBeanToView(TbBasePermissions bean) {
		VBasePermissions vBasePermissions = new VBasePermissions();
		try {
			BeanUtils.copyProperties(vBasePermissions, bean);
			basePermissionsDAO.parentMenuName(bean.getVcParentId(),
					vBasePermissions);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		return vBasePermissions;
	}

	/**
	 * 获取全部的权限信息
	 */
	public List<VBasePermissions> getAllPermis(boolean btButton) {
		return basePermissionsDAO.getAllPermis(btButton);
	}
}