package com.symbol.app.mantoeye.service.gruopBisness;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.groupBisness.GroupBisAreaDistributeDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class GroupBisnessAreaDistriburteManager {

	@Autowired
	private GroupBisAreaDistributeDAO groupBisAreaDistributeDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int areaType,
			int timeLevel, String time, String apnName) {
		return groupBisAreaDistributeDAO.query(page, isTD, areaType, timeLevel,
				time, apnName);
	}

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> queryTrend(final Page<CommonFlush>  page, int isTD,
			int areaType, int timeLevel, String stime, String etime,
			String apnName, String areaname) {
		return groupBisAreaDistributeDAO.queryTrend(page, isTD, areaType,
				timeLevel, stime, etime, apnName, areaname);
	}
	public List<CommonFlush> queryAllTrend( int isTD,
			int areaType, int timeLevel, String stime, String etime,
			String apnName, String areaname) {
		return groupBisAreaDistributeDAO.queryAllTrend(isTD, areaType,
				timeLevel, stime, etime, apnName, areaname);
	}


	public List<CommonFlush> listData(final Page<CommonFlush>  page, int isTD, int areaType,
			int timeLevel, String time, String apnName) {
		return groupBisAreaDistributeDAO.listData(page, isTD, areaType,
				timeLevel, time, apnName);
	}

}
