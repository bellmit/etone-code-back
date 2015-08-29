package com.symbol.app.mantoeye.service.blackberry;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.blackberry.BlackBerryActiveUserDAO;
import com.symbol.app.mantoeye.dto.blackberry.ActiveUserBean;
import com.symbol.app.mantoeye.dto.blackberry.ActiveUserSpaceBean;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class BlackBerryActiveUserManager {
	@Autowired
	private BlackBerryActiveUserDAO blackBerryActiveUserDAO;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public List<ActiveUserBean> findAllActiveUser(String date,String conditionType,Long countryId,Long ldcId,String condition,int conditionVal){
		Date d = CommonUtils.getDate(date) ;
		int year = CommonUtils.getYear(d);
		int month = CommonUtils.getMonth(d);
		
		return blackBerryActiveUserDAO.findAllActiveUser(year, month,conditionType,countryId,ldcId,condition,conditionVal);
	}
	/**
	 * 分页显示数据
	 */
	public List<ActiveUserBean> findActiveUserPage(String date,Page page,String conditionType,Long countryId,Long ldcId,String condition,int conditionVal){
		Date d = CommonUtils.getDate(date) ;
		int year = CommonUtils.getYear(d);
		int month = CommonUtils.getMonth(d);
		
		return blackBerryActiveUserDAO.findActiveUserPage(year, month,page,conditionType,countryId,ldcId,condition,conditionVal);
	}
	/**
	 * 获取记录条数
	 * @param date
	 * @return
	 */
	public int findActiveUserCount(String date,String conditionType,Long countryId,Long ldcId,String condition,int conditionVal){
		Date d = CommonUtils.getDate(date) ;
		int year = CommonUtils.getYear(d);
		int month = CommonUtils.getMonth(d);
		
		return blackBerryActiveUserDAO.findActiveUserCount(year, month,conditionType,countryId,ldcId,condition,conditionVal);
	}
	
	public List<ActiveUserSpaceBean> findActiveUserSpace(int spaceLevel,String date,String conditionType,Long countryId,Long ldcId,String condition,int conditionVal){
		
		Date d = CommonUtils.getDate(date) ;
		int year = CommonUtils.getYear(d);
		int month = CommonUtils.getMonth(d);
		
		return blackBerryActiveUserDAO.findActiveUserSpace(spaceLevel, year, month,conditionType,countryId,ldcId,condition,conditionVal);
		
	}
}
