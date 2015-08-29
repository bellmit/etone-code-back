package com.symbol.app.mantoeye.service.mms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.mms.MmsTimeLevelDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class MmsTimeLevelManager {

	@Autowired
	private MmsTimeLevelDAO mmsTimeLevelDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int timeLevel,
			String startTime, String endTime) {
		return mmsTimeLevelDAO.query(page, isTD, timeLevel, startTime, endTime);
	}

	public List<CommonFlush> listData(int isTD, int timeLevel,
			String startTime, String endTime) {
		return mmsTimeLevelDAO.listData(isTD, timeLevel, startTime, endTime);
	}
}
