package com.symbol.app.mantoeye.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.taskCatch.TableColumnMapDAO;
import com.symbol.app.mantoeye.entity.FtbTableColumnMap;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;
@Service
@Transactional
public class TableColumnMapManager extends EntityManager<FtbTableColumnMap, Integer>{
	@Autowired
	private TableColumnMapDAO tableColumnMapDAO;
	
	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected TableColumnMapDAO getEntityDao() {
		return tableColumnMapDAO;
	}
	
	/**
	 * 重写父类的方法，把View的list写进去
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<FtbTableColumnMap> search(final Page<FtbTableColumnMap> page,
			final List<PropertyFilter> filters) {
		Page<FtbTableColumnMap> apage = getEntityDao().find(page, filters);
		List<FtbTableColumnMap> dateList = apage.getResult();
		apage.setVresult(dateList);
		return apage;
	}
	@Transactional
	public void saveEntity(FtbTableColumnMap entity,Short tableId){
	
		tableColumnMapDAO.saveEntity(entity,tableId);	
	}
}
