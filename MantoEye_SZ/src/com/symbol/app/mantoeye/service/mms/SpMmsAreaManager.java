package com.symbol.app.mantoeye.service.mms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.mms.SpMmsAreaDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class SpMmsAreaManager {

	@Autowired
	private SpMmsAreaDAO spMmsAreaDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int areaType,
			int timeLevel, String time) {
		return spMmsAreaDAO.query(page, isTD, areaType, timeLevel, time);
	}

	public List<CommonFlush> listData(int isTD, int areaType, int timeLevel,
			String time) {
		return spMmsAreaDAO.listData(isTD, areaType, timeLevel, time);
	}

}
