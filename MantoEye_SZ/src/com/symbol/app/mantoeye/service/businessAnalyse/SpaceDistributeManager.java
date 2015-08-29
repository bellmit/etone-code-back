package com.symbol.app.mantoeye.service.businessAnalyse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessAnalyse.SpaceDistributeDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class SpaceDistributeManager {

	@Autowired
	private SpaceDistributeDAO spaceDistributeDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int areaType,
			int timeLevel, String time) {
		return spaceDistributeDAO.query(page, isTD, areaType, timeLevel, time);
	}

	public List<CommonFlush> listData(int isTD, int areaType, int timeLevel,
			String time) {
		return spaceDistributeDAO.listData(isTD, areaType, timeLevel, time);
	}

}
