package com.symbol.app.mantoeye.dao.common;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;

import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/**
 * 查询数据库的总父类
 * @author rankin
 *
 * @param <T>
 * @param <PK>
 */
public class HibernateQueryDAO<T, PK extends Serializable> extends HibernateDao<T, PK> {
		
	//覆盖父类的方法，连接数据库的查询节点
	@Override
	@Resource(name="sessionFactoryQuery1")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}	
}
