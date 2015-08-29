package com.symbol.wp.core.dao.impl;

/* 所有BO的父类 */

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.transaction.JDBCTransaction;
import org.springframework.stereotype.Repository;

import com.symbol.wp.core.entity.TbBaseRoles;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.PropertyFilter.MatchType;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;
//Spring DAO Bean的标识
@Repository
public class BaseRolesDAO extends HibernateDao<TbBaseRoles, String>{


	public List getBeanList(TbBaseRoles searchBean) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("vcRolesName",searchBean.getVcRolesName(),MatchType.EQ));

		return super.find(filters);
	}
	/**
	 * 通过登录名查询该用户拥有的角色
	 * @param loginName
	 * @return
	 */
	public List<TbBaseRoles> getRolesByLoginName(String loginName){
		String sql = "from TbBaseRoles a where a.id in" +
				"(select b.vcRoleId from TbBaseUserRole b where b.vcUserId " +
				"in(select c.id from TbBaseUserInfo c where c.vcLoginName = ?))";
		List<TbBaseRoles> list= super.find(sql, new String[]{loginName});
		return list;
	}
	/**
	 * 通过，模块id查询拥有该模块的角色
	 * @param premid
	 * @return
	 */
	public List<TbBaseRoles> getRolesByPremId(String premid){
		String sql = "from TbBaseRoles a where a.id in" +
				"(select b.vcRoleId from TbBaseRolePermis b where b.vcPermId = ? )";
		List<TbBaseRoles> list= super.find(sql, new String[]{premid});
		return list;
	}
	
	public void deleteByNo(String roleGroupId){
		String usql="delete from TbBaseRoles where vcRoleGroupId=?";
		this.getSession().createQuery(usql).setParameter(0, roleGroupId).executeUpdate();
	}
	
}
