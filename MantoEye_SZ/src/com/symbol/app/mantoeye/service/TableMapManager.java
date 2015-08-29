package com.symbol.app.mantoeye.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.taskCatch.TableMapDAO;
import com.symbol.app.mantoeye.entity.FtbTableMap;
import com.symbol.app.mantoeye.entity.TbFeedback;
import com.symbol.app.mantoeye.entity.business.DtbBusinessCompany;
import com.symbol.app.mantoeye.entity.business.DtbBusinessSite;
import com.symbol.app.mantoeye.entity.business.DtbBusinessType;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;
/**
 * 统计表管理（即席查询和数据提取用）
 * @author rankin
 *
 */
@Service
@Transactional
public class TableMapManager extends EntityManager<FtbTableMap, Short>{
	@Autowired
	private TableMapDAO tableMapDAO;
	
	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected TableMapDAO getEntityDao() {
		return tableMapDAO;
	}
	
	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<FtbTableMap> search(final Page<FtbTableMap> page,
			final List<PropertyFilter> filters) {
		Page<FtbTableMap> apage = getEntityDao().find(page, filters);
		List<FtbTableMap> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}
	@Transactional
	public void saveEntity(FtbTableMap entity){
	
		tableMapDAO.saveEntity(entity);	
	}
}
