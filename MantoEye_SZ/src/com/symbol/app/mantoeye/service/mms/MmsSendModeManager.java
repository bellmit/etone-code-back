package com.symbol.app.mantoeye.service.mms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.mms.MmsSendModeDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;

@Service
@Transactional
public class MmsSendModeManager {

	@Autowired
	private MmsSendModeDAO mmsSendModeDAO;

	// 按彩信发送方式统计
	public List<CommonFlush> listData(int isTD, int timeLevel,
			String time) {
		return mmsSendModeDAO.listData(isTD, timeLevel, time);
	}

}
