package com.symbol.app.mantoeye.service.businessAnalyse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessAnalyse.BlocApnDistributeDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class BlocApnDistributeManager {

	@Autowired
	private BlocApnDistributeDAO blocApnDistributeDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int apnType,
			int timeLevel, String time) {
		return blocApnDistributeDAO.query(page, isTD, apnType, timeLevel, time);
	}

	public List<CommonFlush> listData(int isTD, int timeLevel, int apnType,
			String time) {
		return blocApnDistributeDAO.listData(isTD, timeLevel, apnType, time);
	}
	/**
	 * 分页查询
	 */
	public Page<CommonFlush> queryTrend(final Page<CommonFlush>  page, int isTD,
			int timeLevel, String stime, String etime,
			String apnName) {
		return blocApnDistributeDAO.queryTrend(page, isTD,
				timeLevel, stime, etime, apnName);
	}
	public List<CommonFlush> queryAllTrend( int isTD,
			 int timeLevel, String stime, String etime,
			String apnName) {
		return blocApnDistributeDAO.queryAllTrend(isTD, 
				timeLevel, stime, etime, apnName);
	}

}
