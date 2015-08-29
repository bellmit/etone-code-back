package com.symbol.app.mantoeye.service.bigflushuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.bigflushuser.BlackUserDAO;
import com.symbol.app.mantoeye.dto.flush.BigFlushUser;
import com.symbol.app.mantoeye.entity.business.BlackUser;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.EntityManager;


//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class BlackUserManager extends EntityManager<BlackUser, Long>{

	@Autowired
	private BlackUserDAO blackUserDAO;
	
	/**
	 * ，把View的list写进去
	 */
	@Transactional(readOnly = true)
	public Page<BlackUser> query(final Page<BlackUser> page,String msisdn) {
		
		Page<BlackUser> apage = blackUserDAO.query(page, msisdn);
		/*List<BlackUser> dateList = apage.getResult();
		apage.setVresult(dateList);*/
		return apage;
	}
	
	public List<BlackUser> queryAllBlackUser(final Page page,String msisdn){
		return blackUserDAO.queryAllBlackUser(page,msisdn);
	}
	
	@Transactional(readOnly = true)
	public BlackUser queryEntiryById(Long id){
		return blackUserDAO.queryEntiryById(id);
	}
	
	@Transactional
	public int deleteUser(String id){
		return blackUserDAO.deleteUser(id);
	}
	
	@Transactional
	public void insertBlackUser(List<BigFlushUser> list,String descrite){
		blackUserDAO.insertBlackUser(list,descrite);
	}
	
	
	
	
}
