package com.symbol.wp.core.dao.impl;

/* 所有BO的父类 */

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.symbol.wp.core.constants.SqlrConstants;
import com.symbol.wp.core.dto.VBaseDepartment;
import com.symbol.wp.core.entity.TbBaseDepartment;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;
//Spring DAO Bean的标识
@Repository
public class BaseDepartmentDAO extends HibernateDao<TbBaseDepartment, String>{


	/**
	 * @return 参数设定:() 功能描述:获取权限表信息 逻辑判断: 返回值:
	 * @author:wendy
	 */
	public List<VBaseDepartment> getDept() {
		List<VBaseDepartment> list = null;
		List list2 = super.getAll();
		if (list2 != null && !list2.isEmpty()) {
			list = new ArrayList();
			for (Iterator it = list2.iterator(); it.hasNext();) {
				VBaseDepartment deptInfoView = new VBaseDepartment();
				TbBaseDepartment department = (TbBaseDepartment) it.next();
				try {
					BeanUtils.copyProperties(deptInfoView, department);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage());
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage());
				}
				deptInfoView = this.parentName(
						deptInfoView.getVcParentId(), deptInfoView);
				list.add(deptInfoView);
			}
		}
		return list;
	}

	/**
	 * @param session
	 * @param parentPermId
	 * @param basePermisView
	 * @return 参数设定:() 功能描述: 逻辑判断: 返回值:
	 * @author:wendy
	 */
	public VBaseDepartment parentName(String parentNo, VBaseDepartment deptInfoView) {

		TbBaseDepartment entity = super.findByUnique("id",parentNo);
		if (entity != null) {
			deptInfoView.setNmParentDeptName(entity.getVcDeptName());
		}
		return deptInfoView;
	}
	
	/**
	 * 根据部门编号获取部门名称
	 * @author caifaxiang
	 */
	public String getDeptNameByDeptId(String deptId){
		String sql="select vcDeptName from TbBaseDepartment where id=?";
		Query query =createQuery(sql,deptId);
		List list = query.list();
		Iterator it = list.iterator();
		String deptName = "";
		if (it.hasNext())
		{
			deptName = (String)it.next();
		}
		return deptName;
	}
}
