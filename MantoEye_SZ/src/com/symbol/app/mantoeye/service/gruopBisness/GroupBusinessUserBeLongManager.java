package com.symbol.app.mantoeye.service.gruopBisness;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.groupBisness.GroupBisUserBelongDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class GroupBusinessUserBeLongManager {

	@Autowired
	private GroupBisUserBelongDAO groupBisUserBelongDAO;
	
	public List<CommonFlush> queryUserAttachDistribute(int timeLevel,String date,int dataType,String apnName ){
		groupBisUserBelongDAO.initMap();
		return groupBisUserBelongDAO.queryUserAttachDistributeDAO(timeLevel, date, dataType,apnName);
	}
	
	public String getAllApnName(){
		return groupBisUserBelongDAO.getAllApnName();
	}
	public List<CommonFlush> queryAllTrend( int isTD,
			int timeLevel, String stime, String etime,
			String apnName, String belongId) {
		return groupBisUserBelongDAO.queryAllTrend(isTD,
				timeLevel, stime, etime, apnName, belongId);
	}
	public Page<CommonFlush> queryTrend(final Page<CommonFlush>  page,  int isTD,
			int timeLevel, String stime, String etime,
			String apnName, String belongId) {
		return groupBisUserBelongDAO.queryTrend(page,isTD,
				timeLevel, stime, etime, apnName, belongId);
	}
}
