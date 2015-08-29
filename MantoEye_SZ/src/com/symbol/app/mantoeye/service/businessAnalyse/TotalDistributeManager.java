package com.symbol.app.mantoeye.service.businessAnalyse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessAnalyse.TotalDistributeDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class TotalDistributeManager {

	@Autowired
	private TotalDistributeDAO totalDistributeDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int timeLevel,
			String startTime, String endTime) {
		return totalDistributeDAO.query(page, isTD, timeLevel, startTime,
				endTime);
	}

	public List<CommonFlush> listData(int isTD, int timeLevel,
			String startTime, String endTime) {
		return totalDistributeDAO.listData(isTD, timeLevel, startTime, endTime);
	}
}
