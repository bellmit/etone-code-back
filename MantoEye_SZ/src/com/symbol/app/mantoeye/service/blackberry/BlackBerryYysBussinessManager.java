package com.symbol.app.mantoeye.service.blackberry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.blackberry.BlackBerryYysDAO;
import com.symbol.app.mantoeye.dto.blackberry.LdcUsersBean;
import com.symbol.wp.modules.orm.Page;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class BlackBerryYysBussinessManager {
	
	@Autowired
	private BlackBerryYysDAO blackBerryYysDAO;
	
	/**
	 * 分页查询
	 * @param page
	 * @param timeLevel
	 * @param date
	 * @return
	 */
	public Page<LdcUsersBean> query(final Page page,int timeLevel,String date){
		 return blackBerryYysDAO.query(page, timeLevel, date);
	}
	
	/**
	 * 查询所有
	 * @param timeLevel
	 * @param date
	 * @return
	 */
	public List<LdcUsersBean> listData(final Page page, int timeLevel,String date){
		return blackBerryYysDAO.listData(page,timeLevel, date);
	}
}
