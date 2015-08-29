package com.symbol.app.mantoeye.service.mms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.mms.SpMmsTitleAreaDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class SpMmsTitleAreaManager {

	@Autowired
	private SpMmsTitleAreaDAO spMmsTitleAreaDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int areaType,
			int timeLevel, String time, String mms_title_search) {
		return spMmsTitleAreaDAO.query(page, isTD, areaType, timeLevel, time,
				mms_title_search);
	}

	public List<CommonFlush> listData(int isTD, int areaType, int timeLevel,
			String time, String mms_title_search) {
		return spMmsTitleAreaDAO.listData(isTD, areaType, timeLevel, time,
				mms_title_search);
	}

	/**
	 * 获取SP彩信主题集合
	 */
	public List<String> listMmsTitle(int isTD, int areaType, int timeLevel,
			String time, String like_mms_title_search) {
		return spMmsTitleAreaDAO.listMmsTitle(isTD, areaType, timeLevel, time,
				like_mms_title_search);
	}
}
