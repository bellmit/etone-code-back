package com.symbol.app.mantoeye.dao;

import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.entity.FtbImeiRecord;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class ImeiCatchDAO extends HibernateDao<FtbImeiRecord,Long>{
	
}
