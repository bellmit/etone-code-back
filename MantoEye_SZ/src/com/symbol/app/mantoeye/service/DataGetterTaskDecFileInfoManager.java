package com.symbol.app.mantoeye.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.entity.FtbDataGetterDecTask;
import com.symbol.wp.modules.orm.hibernate.EntityManagerOld;

import com.symbol.app.mantoeye.dao.DataGetterTaskDecFileInfoDAO;

/**
 * 解析数据提取生成文件
 * 
 * 
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class DataGetterTaskDecFileInfoManager extends
     EntityManagerOld<FtbDataGetterDecTask, Long> {
	@Autowired
	private DataGetterTaskDecFileInfoDAO dataGetterTaskDecFileInfoDAO;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected DataGetterTaskDecFileInfoDAO getEntityDao() {
		return dataGetterTaskDecFileInfoDAO;
	}

}
