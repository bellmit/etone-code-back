package com.symbol.app.mantoeye.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.ImeiCatchDAO;
import com.symbol.app.mantoeye.entity.FtbImeiRecord;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class ImeiCatchManager extends EntityManager<FtbImeiRecord,Long>{

	@Autowired
	private ImeiCatchDAO imeiCatchDAO;
	
	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected ImeiCatchDAO getEntityDao() {
		return imeiCatchDAO;
	}
	
	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<FtbImeiRecord> search(final Page<FtbImeiRecord> page,
			final List<PropertyFilter> filters) {
		Page<FtbImeiRecord> apage = getEntityDao().find(page, filters);
		List<FtbImeiRecord> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}
}
