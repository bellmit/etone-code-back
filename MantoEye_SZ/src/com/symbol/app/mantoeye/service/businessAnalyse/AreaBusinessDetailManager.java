package com.symbol.app.mantoeye.service.businessAnalyse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessAnalyse.AreaBusinessDetailDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class AreaBusinessDetailManager {

	@Autowired
	private AreaBusinessDetailDAO areaBusinessDetailDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, 
			String areaName, int timeLevel, String time) {
		return areaBusinessDetailDAO.query(page, isTD, areaName,
				timeLevel, time);
	}

	public List<CommonFlush> listData(int isTD, String areaName,
			int timeLevel, String time) {
		return areaBusinessDetailDAO.listData(isTD, areaName,
				timeLevel, time);
	}

}
