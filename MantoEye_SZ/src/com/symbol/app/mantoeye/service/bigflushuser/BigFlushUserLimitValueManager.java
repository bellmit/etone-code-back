package com.symbol.app.mantoeye.service.bigflushuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.bigflushuser.BigFlushUserLimitValueDAO;
import com.symbol.app.mantoeye.entity.business.BigFlushUserLimitValue;


//Spring Service Bean的标识.
@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class BigFlushUserLimitValueManager {
	
	@Autowired
	private BigFlushUserLimitValueDAO bigFlushUserLimitValueDAO;
	
	
	public List<BigFlushUserLimitValue> query(){
		return bigFlushUserLimitValueDAO.query();
	}
	@Transactional
	public boolean save(String id,String value){
		return bigFlushUserLimitValueDAO.save(id, value);
	}
}
