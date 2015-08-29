package com.symbol.wp.core.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.symbol.wp.core.entity.TbBaseRoleGroup;
import com.symbol.wp.core.entity.TbBaseRoles;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

//Spring DAO Bean的标识
@Repository
public class BaseRoleDAO extends HibernateDao<TbBaseRoles,Long> {
	private static final Log log = LogFactory.getLog(TbBaseRoleGroup.class);
	
	public void findRoleId(){
		
	}
}
