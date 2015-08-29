package com.symbol.app.mantoeye.service.businessAnalyse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessAnalyse.SpaceDistributeBusinessDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class SpaceDistributeBusinessManager {

	@Autowired
	private SpaceDistributeBusinessDAO spaceDistributeBusinessDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int areaType,
			String areaName, int timeLevel, String time) {
		return spaceDistributeBusinessDAO.query(page, isTD, areaType, areaName,
				timeLevel, time);
	}

	public List<CommonFlush> listData(int isTD, int areaType, String areaName,
			int timeLevel, String time) {
		return spaceDistributeBusinessDAO.listData(isTD, areaType, areaName,
				timeLevel, time);
	}

}