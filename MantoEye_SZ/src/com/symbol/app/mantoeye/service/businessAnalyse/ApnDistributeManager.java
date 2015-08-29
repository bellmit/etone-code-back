package com.symbol.app.mantoeye.service.businessAnalyse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessAnalyse.ApnDistributeDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.EntityManager;

@Service
//默认将类中的所有函数纳入事务管理.
@Transactional
public class ApnDistributeManager extends EntityManager<CommonFlush, String>{
	@Autowired
	private ApnDistributeDAO apnDistributeDAO;
	
	public List<CommonFlush> queryApnDistribute(int timeLevel,String date,int dataType){
		apnDistributeDAO.initMap();
		return apnDistributeDAO.queryApnDistribute(timeLevel, date, dataType);
	}
	
	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int areaType,
			int timeLevel, String time,String anpName) {
		return apnDistributeDAO.query(page, isTD, areaType, timeLevel, time,anpName);
	}
	
	public List<CommonFlush> listData(final Page page,int isTD, int areaType, int timeLevel,
			String time,String anpName) {
		return apnDistributeDAO.listData(page,isTD, areaType, timeLevel, time,anpName);
	}
	
}
