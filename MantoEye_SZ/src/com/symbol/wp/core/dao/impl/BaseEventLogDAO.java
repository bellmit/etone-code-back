package com.symbol.wp.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.symbol.wp.core.entity.TbBaseEventLog;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;
/**
 * 系统事件日志
 * @author rankin
 *
 */
//Spring DAO Bean的标识
@Repository
public class BaseEventLogDAO extends HibernateDao<TbBaseEventLog, String>{

}
