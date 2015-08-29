package com.symbol.wp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.wp.core.dao.impl.BaseEventLogDAO;
import com.symbol.wp.core.dao.impl.BaseOpLogDAO;
import com.symbol.wp.core.dto.VBaseOpLog;
import com.symbol.wp.core.entity.TbBaseEventLog;
import com.symbol.wp.core.entity.TbBaseOpLog;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.PropertyFilter;
import com.symbol.wp.modules.orm.hibernate.EntityManager;
/**
 * 系统事件日志
 * @author rankin
 *
 */
//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class BaseEventLogManager extends EntityManager<TbBaseEventLog, String> {
	@Autowired
	private BaseEventLogDAO baseEventLogDAO;
	/**
	 * 实现回调函数,为EntityManager基类的CRUD操作提供DAO.
	 */
	@Override
	protected BaseEventLogDAO getEntityDao() {
		return baseEventLogDAO;
	}
	@Override
	@Transactional(readOnly = true)
	public Page<TbBaseEventLog> search(final Page<TbBaseEventLog> page,
			final List<PropertyFilter> filters) {
		Page<TbBaseEventLog> age=baseEventLogDAO.find(page, filters);
		
		return age;
	}

}
