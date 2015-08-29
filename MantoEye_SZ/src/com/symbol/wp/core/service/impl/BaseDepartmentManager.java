package com.symbol.wp.core.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.wp.core.dao.impl.BaseDepartmentDAO;
import com.symbol.wp.core.dto.VBaseDepartment;
import com.symbol.wp.core.entity.TbBaseDepartment;
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
public class BaseDepartmentManager extends
		EntityManager<TbBaseDepartment, String> {

	@Autowired
	private BaseDepartmentDAO baseDepartmentDAO;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected BaseDepartmentDAO getEntityDao() {
		return baseDepartmentDAO;
	}

	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<TbBaseDepartment> search(final Page<TbBaseDepartment> page,
			final List<PropertyFilter> filters) {
		Page<TbBaseDepartment> apage = getEntityDao().find(page, filters);
		List<TbBaseDepartment> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}
	
	/**
	 * 实体与视图的转换
	 */
	@Transactional(readOnly = true)
	public VBaseDepartment convertBeanToView(TbBaseDepartment bean){
		VBaseDepartment view = new VBaseDepartment();
		try {
			BeanUtils.copyProperties(view, bean);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		view.setNmParentDeptName(baseDepartmentDAO.getDeptNameByDeptId(bean.getVcParentId()));
		return view;
	}
}