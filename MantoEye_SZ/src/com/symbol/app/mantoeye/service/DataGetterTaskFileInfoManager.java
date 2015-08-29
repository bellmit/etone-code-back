package com.symbol.app.mantoeye.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.DataGetterTaskFileInfoDAO;
import com.symbol.app.mantoeye.entity.FtbDataGetterTaskFileInfo;
import com.symbol.wp.modules.orm.hibernate.EntityManager;
import com.symbol.wp.modules.orm.hibernate.EntityManagerOld;

/**
 * 原始信令数据提取生成文件
 * @author rankin
 *
 */
//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class DataGetterTaskFileInfoManager extends EntityManagerOld<FtbDataGetterTaskFileInfo, Long>{
	@Autowired
	private DataGetterTaskFileInfoDAO dataGetterTaskFileInfoDAO;

	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected DataGetterTaskFileInfoDAO getEntityDao() {
		return dataGetterTaskFileInfoDAO;
	}

}
