package com.symbol.app.mantoeye.service.businessAnalyse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessAnalyse.AreasBusinessDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class AreasBusinessManager {

	@Autowired
	private AreasBusinessDAO areasBusinessDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int areaType,
			int timeLevel, String time,String areaValue) {
		return areasBusinessDAO.query(page, isTD, areaType, timeLevel, time,areaValue);
	}

	public List<CommonFlush> listData(int isTD, int areaType, int timeLevel,
			String time,String areaValue) {
		return areasBusinessDAO.listData(isTD, areaType, timeLevel, time,areaValue);
	}

}
