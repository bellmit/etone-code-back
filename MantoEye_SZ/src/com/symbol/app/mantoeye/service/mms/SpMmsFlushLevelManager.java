package com.symbol.app.mantoeye.service.mms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.mms.SpMmsFlushLevelDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;

@Service
@Transactional
public class SpMmsFlushLevelManager {
	@Autowired
	private SpMmsFlushLevelDAO spMmsFlushLevelDAO;

	public List<CommonFlush> listData(int isTD, String time) {
		return spMmsFlushLevelDAO.listData(isTD, time);
	}
}