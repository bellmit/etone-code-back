package com.symbol.app.mantoeye.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.SrcDataResultDAO;
import com.symbol.app.mantoeye.entity.business.FtbSrcDataResult;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class SrcDataResultManager extends EntityManager<FtbSrcDataResult, Long>{
	@Autowired
	private SrcDataResultDAO srcDataResultDAO;
	
	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected SrcDataResultDAO getEntityDao() {
		return srcDataResultDAO;
	}
}
