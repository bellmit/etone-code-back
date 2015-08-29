package com.symbol.wp.core.dao.impl;

/* 所有BO的父类 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

// Spring DAO Bean的标识
@Repository
public class BaseUserInfoDAO extends HibernateDao<TbBaseUserInfo, String> {

	protected boolean checkEditSymbol(TbBaseUserInfo entity, String key) {
		if (key.equals(entity.getId())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 重置密码
	 * 
	 * @param userids
	 * @param pwd
	 */
	public void updatePwdReset(String userids, String pwd) {
		String hql = "update TbBaseUserInfo set vcPassword = ? where id in ("
				+ userids + ")";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, pwd);
		query.executeUpdate();
		session.flush();
	}

	/**
	 * 根据登录名获取用户名字
	 * 
	 * @param vcLoginName
	 * @return
	 */
	public String getVcUserName(String vcLoginName) {
		String sql = "select vcUserName from TbBaseUserInfo where vcLoginName =?";
		Session session = getSession();
		Query query = session.createQuery(sql);
		query.setParameter(0, vcLoginName);
		List list = query.list();
		session.flush();
		if (list != null && !list.isEmpty()) {
			return (String) list.get(0);
		}
		return "";
	}

	/**
	 * 根据角色标识获取用户标识符
	 * 
	 * @param vcRoleNo
	 * @return
	 */
	public String getUserIdByRoleId(String vcRoleId) {
		String sql = "select vcUserId from TbBaseUserRole  where vcRoleId in ( ? )";
		Session session = getSession();
		Query query = session.createQuery(sql);
		query.setParameter(0, vcRoleId);
		List list = query.list();
		StringBuffer sb = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				sb.append(it.next() + ",");
			}
		}
		session.flush();
		if (sb.length() > 0) {
			int i = sb.lastIndexOf(",");
			if (i > 0) {
				return sb.substring(0, i);
			}
			return "";
		} else {
			return "";
		}
	}

	/**
	 * 根据用户名获取用户登陆名
	 * 
	 * @param vcRoleNo
	 * @return
	 */
	public String getVcLoginUser(String userName) {
		// 模糊查询
		String sql = "select vcLoginName from TbBaseUserInfo  where  vcUserName like ?";
		Session session = getSession();
		Query query = session.createQuery(sql);
		query.setParameter(0, "%" + userName + "%");
		List list = query.list();
		StringBuffer sb = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			for (Iterator it = list.iterator(); it.hasNext();) {
				sb.append(it.next() + ",");
			}
		}
		session.flush();
		if (sb.length() > 0) {
			int i = sb.lastIndexOf(",");
			if (i > 0) {
				return sb.substring(0, i);
			}
			return "";
		} else {
			return "";
		}
	}
	/**
	 * 根据用户登陆名获取用户名
	 * 
	 * @param vcRoleNo
	 * @return
	 */
	public Map<String,String> getUserNames(String  loginnames) {
		Map<String,String> map = new HashMap<String,String>();
		// 模糊查询
		String sql = "select vcLoginName,vcUserName from tbBaseUserInfo  where  vcLoginName in ("+loginnames+") ";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, loginnames);
		List list = query.list();
		if (list != null && !list.isEmpty()) {
			for(int i=0;i<list.size();i++){
				Object[] objs = (Object[])list.get(i);
				map.put(objs[0]+"", objs[1]+"");
			}
		}
		return map;
	}
}
