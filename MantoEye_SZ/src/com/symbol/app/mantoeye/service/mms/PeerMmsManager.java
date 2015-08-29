package com.symbol.app.mantoeye.service.mms;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.mms.PeerMmsDAO;
import com.symbol.app.mantoeye.dto.mms.PeerMmsBean;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class PeerMmsManager {
	
	@Autowired
	private PeerMmsDAO peerMmsDAO;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 点对点号段统计
	 * @param raitype
	 * @param timeLevel
	 * @param date
	 * @return
	 */
	public List<PeerMmsBean> findPeerMmsSeg(int raitype,String timeLevel,String date){
		Date d = CommonUtils.getDate(date) ;
		int year = CommonUtils.getYear(d);
		int month = CommonUtils.getMonth(d);
		int day = CommonUtils.getDay(d);
		int week = CommonUtils.getWeek(d);
		
		if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_MONTH+"")){
			return peerMmsDAO.findMonthPeerMmsSeg(raitype, year, month);
		}else if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_WEEK+"")){
			return peerMmsDAO.findWeekPeerMmsSeg(raitype, year, week);
		}else if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_DAY+"")){
			return peerMmsDAO.findDayPeerMmsSeg(raitype, year, month, day);
		}else{
			logger.info("类型参数错误！");
			return null;
		}
	}
	/**
	 * 点对点时段分布统计
	 * @param raitype
	 * @param timeLevel
	 * @param date
	 * @return
	 */
	public List<PeerMmsBean> findHourPeerMms(int raitype,String startdate,String enddata){
		
		return peerMmsDAO.findHourPeerMms(raitype,startdate,enddata);
		
	}
	/**
	 * 点对点时段分布统计
	 * @param raitype
	 * @param timeLevel
	 * @param date
	 * @return
	 */
	public Page<PeerMmsBean> findHourPeerMmsPage(Page<PeerMmsBean> page,int raitype,String startdate,String enddata){
		
		return peerMmsDAO.findHourPeerMmsPage(page,raitype,startdate,enddata);
		
	}
	
	/**
	 * 点对点空间分布统计
	 * @param raitype
	 * @param spaceLevel
	 * @param timeLevel
	 * @param date
	 * @return
	 */
	public List<PeerMmsBean> findPeerMmsSpace(int raitype,int spaceLevel,String timeLevel,String date){
		
		Date d = CommonUtils.getDate(date) ;
		int year = CommonUtils.getYear(d);
		int month = CommonUtils.getMonth(d);
		int day = CommonUtils.getDay(d);
		int hour = CommonUtils.getHour(d);
		int week = CommonUtils.getWeek(d);
		
		if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_MONTH+"")){
			return peerMmsDAO.findMonthPeerMmsSpace(spaceLevel,raitype, year, month);
		}else if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_WEEK+"")){
			return peerMmsDAO.findWeekPeerMmsSpace(spaceLevel,raitype, year, week);
		}else if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_DAY+"")){
			return peerMmsDAO.findDayPeerMmsSpace(spaceLevel,raitype, year, month, day);
		}else if(timeLevel.equals(CommonConstants.MANTOEYE_TIME_LEVEL_HOUR+"")){
			return peerMmsDAO.findHourPeerMmsSpace(spaceLevel,raitype, year, month, day,hour);
		}else{
			logger.info("类型参数错误！");
			return null;
		}
		
	}

}
