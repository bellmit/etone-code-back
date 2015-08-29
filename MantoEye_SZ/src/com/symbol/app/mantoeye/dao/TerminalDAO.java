package com.symbol.app.mantoeye.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.entity.TerminalEntity;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class TerminalDAO extends HibernateDao<TerminalEntity, String>{

//	@Override
//	@Resource(name="sessionFactory")
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}
	
}
