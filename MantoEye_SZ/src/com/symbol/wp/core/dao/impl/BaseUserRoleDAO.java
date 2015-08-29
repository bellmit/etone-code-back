package com.symbol.wp.core.dao.impl;

/* 所有BO的父类 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.symbol.wp.core.constants.SqlrConstants;
import com.symbol.wp.core.entity.TbBaseUserRole;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;
//Spring DAO Bean的标识
@Repository
public class BaseUserRoleDAO extends HibernateDao<TbBaseUserRole, String>{


	/**
	 * @param userId
	 * @return 参数设定:(String userId) 功能描述:根据用户编号获取该用户的角色编号 逻辑判断: 返回值:
	 * @author:wendy
	 */
	public List getRoles(String vcUserNo) {
		List rolesList = null;

		List list = this.find(SqlrConstants.TbBaseUserRole_0001,
				new String[] { vcUserNo });

		if (list != null && !list.isEmpty()) {
			rolesList = new ArrayList();
			for (Iterator it = list.iterator(); it.hasNext();) {
				TbBaseUserRole baseUserRole = (TbBaseUserRole) it.next();
				rolesList.add(baseUserRole.getVcRoleId());
			}
		}
		return rolesList;
	}

	public String[] getRolesArray(String userId) {
		String[] roles = null;
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("vcUserId",userId,MatchType.EQ));
		List list = super.find(filters);
		TbBaseUserRole baseUserRole = null;

		if (list != null && !list.isEmpty()) {
			roles = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				baseUserRole = (TbBaseUserRole) list.get(i);
				roles[i] = baseUserRole.getVcRoleId();
			}
		}

		return roles;
	}

	public void deleteByUserId(String keys[]) {
		String sql = "delete from TbBaseUserRole where vcUserId in ? ";
		super.createQuery(sql, new String[][]{keys});		
	}
	
	public void deleteByUserId(String userId) {
		String sql = "delete from TbBaseUserRole where vcUserId in ('"+userId+"')";
		Session session=getSession();
		Query query = session.createQuery(sql);
		query.executeUpdate();
		session.flush();
		
	}
}
