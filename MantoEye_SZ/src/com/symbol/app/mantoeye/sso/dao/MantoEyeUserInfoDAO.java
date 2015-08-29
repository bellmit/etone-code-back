package com.symbol.app.mantoeye.sso.dao;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.wp.core.entity.TbBaseUserInfo;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

//Spring DAO Bean的标识
@Repository
public class MantoEyeUserInfoDAO extends HibernateDao<TbBaseUserInfo, String> {

	/**
	 * 添加统一网管平台用户信息
	 * 
	 * @param userId
	 * @param loginID
	 * @param fullName
	 * @param ouID
	 * @param email
	 * @param mobile
	 * @param telephoneNumber
	 */
	public void saveUnmpUser(String userId, String loginID, String fullName,
			String ouID, String email, String mobile, String telephoneNumber) {
		String sql = "insert into tbBaseUserInfo(id,vcLoginName,vcDeptId,vcUserName,vcMobel,vcEmail,vcTelephone,tiUserType)"
				+ " values(?,?,?,?,?,?,?,?)  ";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, userId);// 用户id
		query.setParameter(1, loginID);// 登录名
		query.setParameter(2, ouID);// 组织单元id
		query.setParameter(3, fullName);// 用户全名
		query.setParameter(4, mobile);// 电话
		query.setParameter(5, email);// email
		query.setParameter(6, telephoneNumber);// 电话号码
		query.setParameter(7, 1);// 标识此用户为统一网管平台用户
		query.executeUpdate();
	}

	/**
	 * 删除所有的统一网管平台用户
	 */
	public void deleteAllUnmpUser() {
		String sql = "delete tbBaseUserInfo where tiUserType = 1 ";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	/**
	 * 删除没有关联用户的用户角色关联信息
	 * 
	 * @param userIds
	 */
	public void deleteUserRoleMap(String[] userIds) {

		String sql = " delete tbBaseUserRole where  vcUserId not in (select id from tbBaseUserInfo where 1=1 ) ";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
}
