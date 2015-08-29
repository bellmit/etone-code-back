package com.symbol.app.mantoeye.dao;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.entity.TbFeedback;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class FeedbackDAO extends HibernateDao<TbFeedback, String>{
	
}
