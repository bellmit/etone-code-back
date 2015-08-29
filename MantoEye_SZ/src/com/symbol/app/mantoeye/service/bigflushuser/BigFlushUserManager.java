package com.symbol.app.mantoeye.service.bigflushuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.bigflushuser.BigFlushUserDAO;
import com.symbol.app.mantoeye.dto.flush.BigFlushUser;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class BigFlushUserManager {
	
	@Autowired
	private BigFlushUserDAO bigFlushUserDAO;
	
	public Page<BigFlushUser> query(String sdate,String edate,final Page page,int isBlackUser,String s_msisdn){
		return bigFlushUserDAO.query(page, sdate,edate,isBlackUser,s_msisdn);
	}
	
	public List<BigFlushUser> queryAll(String sdate,String edate,int isBlackUser,String s_msisdn){
		return bigFlushUserDAO.queryAll(sdate,edate, isBlackUser,s_msisdn);
	}
	
	
	//查询大流量用户分布
	public List<BigFlushUser> queryDistinationByBigUser(String date,Long msisdn,String flag){
		return bigFlushUserDAO.queryDestinationByBigUser(date, msisdn, flag);
	}
	
	//根据ID查询所以大流量用户
	@Transactional(readOnly = true)
	public List<BigFlushUser> queryEntiryByKeys(String keys){
		return bigFlushUserDAO.queryEntityByKeys(keys);
	}
	public List<String> findBigFlushMsisdn(String searchDateStart,String searchDateEnd,String like_msisdn_search){
		return bigFlushUserDAO.findBigFlushMsisdn(searchDateStart,searchDateEnd,
				like_msisdn_search);
	}
	/**
	 * 分页查询
	 */
	public Page<BigFlushUser> queryTrend(final Page<BigFlushUser>  page,String stime, String etime,
			String apnName) {
		return bigFlushUserDAO.queryTrend(page, stime, etime, apnName);
	}
	public List<BigFlushUser> queryAllTrend(  String stime, String etime,
			String apnName) {
		return bigFlushUserDAO.queryAllTrend( stime, etime, apnName);
	}
}
