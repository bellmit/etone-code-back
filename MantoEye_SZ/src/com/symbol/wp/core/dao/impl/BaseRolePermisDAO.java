package com.symbol.wp.core.dao.impl;

/* 所有BO的父类 */

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.symbol.wp.core.constants.SqlrConstants;
import com.symbol.wp.core.dto.VBaseRolePermis;
import com.symbol.wp.core.entity.TbBaseRolePermis;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/**
 * 角色模块关联
 * 
 * @Title: BaseRolePermisDAO.java
 * @Description: <br>
 *               <br>
 * @Company: etonetech
 * @Created Mar 23, 2009 11:39:07 AM
 * @author ranhualin
 * @version 1.0
 * @since 1.0
 */
// Spring DAO Bean的标识
@Repository
public class BaseRolePermisDAO extends HibernateDao<TbBaseRolePermis, String> {


	private VBaseRolePermis copy(VBaseRolePermis view, TbBaseRolePermis bean) {
		try {
			BeanUtils.copyProperties(view, bean);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		return view;
	}

	/**
	 * 检查用户是否有该权限
	 * 
	 * @param symbol
	 *            :权限描述符
	 * @param userId
	 *            ：用户编号
	 */
	public TbBaseRolePermis checkRolePermis(String symbol, String userId) {
		TbBaseRolePermis baseRolePermis = null;
		List list = this.find(SqlrConstants.TbBaseRolePermis_0001,
				new Object[] { symbol, userId });
		if (list != null && !list.isEmpty()) {
			baseRolePermis = (TbBaseRolePermis) list.get(0);
		}

		return baseRolePermis;
	}

	/**
	 * 通过角色ID和模块ID查询
	 * 
	 * @param nmRolesId
	 * @param nmPermId
	 * @return
	 */
	public TbBaseRolePermis getRolePermisByRP(String rolesId, String vcPermId) {
		TbBaseRolePermis baseRolePermis = null;

		String sql = "from TbBaseRolePermis where vcPermId='" + vcPermId
				+ "' and vcRoleId='" + rolesId + "'";
		List<TbBaseRolePermis> list = this.createQuery(sql).list();
		if (list != null && !list.isEmpty()) {
			baseRolePermis = (TbBaseRolePermis) list.get(0);
		}
		return baseRolePermis;
	}

	/**
	 * 根据用户编号查找该用户具有的全部权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getRolePermisByUserId(String userNo) {
		List<String> permisList = null;
		List list = this.find(SqlrConstants.TbBaseRolePermis_0002,
				new Object[] { userNo });
		if (list != null && !list.isEmpty()) {
			permisList = new ArrayList();
			for (Iterator it = list.iterator(); it.hasNext();) {
				TbBaseRolePermis rolePermis = (TbBaseRolePermis) it.next();
				permisList.add(rolePermis.getVcPermId());
			}
		}

		return permisList;
	}
	public List<TbBaseRolePermis> getRolePermisByUserNo(String userNo) {
		List<TbBaseRolePermis> permisList = null;
		List list = this.find(SqlrConstants.TbBaseRolePermis_0002,
				new Object[] { userNo });
		if (list != null && !list.isEmpty()) {
			permisList = new ArrayList();
			for (Iterator it = list.iterator(); it.hasNext();) {
				TbBaseRolePermis rolePermis = (TbBaseRolePermis) it.next();
				permisList.add(rolePermis);
			}
		}

		return permisList;
	}
	/**
	 * 获取角色对应的菜单集合对象
	 * @param role_list
	 * @return 参数设定:() 功能描述: 逻辑判断: 返回值:
	 * @author:wendy
	 */
	public List getRolePermis(String role_list) {
		List permisList = null;
		List list = this.find(SqlrConstants.TbBaseRolePermis_0003,new Object[] { role_list });
		if (list != null && !list.isEmpty()) {
			permisList = new ArrayList();
			for (Iterator it = list.iterator(); it.hasNext();) {
				TbBaseRolePermis rolePermis = (TbBaseRolePermis) it.next();
				permisList.add(rolePermis);
			}
		}
		return permisList;
	}

	public boolean setRolePermis(List list, String role_no) {
		String src_op = "";
		
		if (list.size() > 0) {
			src_op = " and vcPermId not in("
					+ list.toString().replaceAll("\\[|\\]", "\'").replace(",", "\',\'") + ")";
		}
		boolean falg = true;
		String hsql = " delete TbBaseRolePermis ";
		hsql = hsql + " where vcRoleId = '"+role_no+"'";
		hsql = hsql + src_op;

		super.createQuery(hsql).executeUpdate();
		// 更新列表操作权限
		if (falg) {
			String permis_no = null;
			for (int i = 0; i < list.size(); i++) {
				permis_no = (String) list.get(i);
					TbBaseRolePermis baseRolePermis = this.getRolePermisByRP(
							role_no, permis_no);
					// 更新操作
					try {
						if (baseRolePermis != null) {
						} else {
							baseRolePermis = new TbBaseRolePermis();
							baseRolePermis.setVcPermId(permis_no);
							baseRolePermis.setVcRoleId(role_no);
							
							this.save(baseRolePermis);
						}
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
			}
		}
		return falg;
	}

	public Map<String, TbBaseRolePermis> findByRoleNo(String roleId) {
		String usql = "from TbBaseRolePermis where vcRoleId='" + roleId + "'";
		List<TbBaseRolePermis> list = super.createQuery(usql).list();
		Map<String, TbBaseRolePermis> map = new HashMap<String, TbBaseRolePermis>();
		for (TbBaseRolePermis tbBaseRolePermis : list) {
			map.put(tbBaseRolePermis.getVcPermId(), tbBaseRolePermis);
		}
		return map;
	}

}
