package com.symbol.app.mantoeye.service.businessAnalyse;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessAnalyse.CompetitorBusinessDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class CompetitorBusinessManager {

	@Autowired
	private CompetitorBusinessDAO competitorBusinessDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonSport> query(final Page page, int isTD,
			int timeLevel, String time_search,int spaceLevel_search,String businessName,String vcName,String bussinessId) {
		return competitorBusinessDAO.query(page, isTD, timeLevel, time_search,spaceLevel_search,businessName,vcName,bussinessId);
	}

	public List<CommonSport> listData(int isTD,int timeLevel, String time_search,int spaceLevelSearch,String businessName,String vcName,String bussinessId) {
		return competitorBusinessDAO.listData(isTD, timeLevel, time_search,spaceLevelSearch,businessName,vcName,bussinessId);
	}
	
	public List queryCompetitor(Long bussinessId){
		return competitorBusinessDAO.queryCompetitor(bussinessId);
	}

}
