package com.symbol.app.mantoeye.service.blackberry;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.blackberry.BlackBerryCountryDAO;
import com.symbol.app.mantoeye.dto.blackberry.BlackBerryCountryBean;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
/**
 *  用户归属地 统计
 * @author rankin
 *
 */
@Service
@Transactional
public class BlackBerryCountryManager {
	@Autowired
	private BlackBerryCountryDAO blackBerryCountryDAO;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 用户归属地 分布
	 * @param spaceLevel
	 * @param timeLevel
	 * @param countryId
	 * @param date
	 * @return
	 */
	public List<BlackBerryCountryBean> findCountryDistribute(String timeLevel,String date){
		
		Date d = CommonUtils.getDate(date) ;
		int year = CommonUtils.getYear(d);
		int month = CommonUtils.getMonth(d);
		int day = CommonUtils.getDay(d);
		int hour = CommonUtils.getHour(d);
		int week = CommonUtils.getWeek(d);
		
		if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_MONTH+"")){
			return blackBerryCountryDAO.findMonthCountry(year, month);
		}else if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_WEEK+"")){
			return blackBerryCountryDAO.findWeekCountry(year, week);
		}else if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_DAY+"")){
			return blackBerryCountryDAO.findDayCountry(year, month, day);
		}else if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_HOUR+"")){
			return blackBerryCountryDAO.findHourCountry(year, month, day, hour);
		}else{
			logger.info("类型参数错误！");
			return null;
		}
		
	}
	
	
	/**
	 * 用户归属地 空间分布
	 * @param spaceLevel
	 * @param timeLevel
	 * @param countryId
	 * @param date
	 * @return
	 */
	public List<BlackBerryCountryBean> findCountrySpace(int spaceLevel,String timeLevel,Long countryId,String date){
		
		Date d = CommonUtils.getDate(date) ;
		int year = CommonUtils.getYear(d);
		int month = CommonUtils.getMonth(d);
		int day = CommonUtils.getDay(d);
		int hour = CommonUtils.getHour(d);
		int week = CommonUtils.getWeek(d);
		
		if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_MONTH+"")){
			return blackBerryCountryDAO.findMonthCountrySpace(spaceLevel, countryId, year, month);
		}else if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_WEEK+"")){
			return blackBerryCountryDAO.findWeekCountrySpace(spaceLevel, countryId, year, week);
		}else if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_DAY+"")){
			return blackBerryCountryDAO.findDayCountrySpace(spaceLevel, countryId, year, month, day);
		}else if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_HOUR+"")){
			return blackBerryCountryDAO.findHourCountrySpace(spaceLevel, countryId, year, month, day, hour);
		}else{
			logger.info("类型参数错误！");
			return null;
		}
		
	}
	
	/**
	 * Top100小区用户流量
	 * @return
	 */
	public List<CommonFlush> findCountryTopUser(Long countryId,String date,int stattype){
		return blackBerryCountryDAO.findCountryTopUser(countryId, date,stattype);
	}
}
