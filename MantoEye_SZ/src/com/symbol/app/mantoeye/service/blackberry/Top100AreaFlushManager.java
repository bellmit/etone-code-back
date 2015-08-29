package com.symbol.app.mantoeye.service.blackberry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.blackberry.Top100AreaFlushDAO;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class Top100AreaFlushManager extends HibernateQueryDAO{
	@Autowired
	private Top100AreaFlushDAO top100AreaFlushDAO;
	
	public List<CommonFlush> query(String time,int time_level){
		return top100AreaFlushDAO.query(time,time_level);
	}
}
