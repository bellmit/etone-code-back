package com.symbol.app.mantoeye.service.mms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.mms.SpMmsPortDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class SpMmsPortManager {

	@Autowired
	private SpMmsPortDAO spMmsPortDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int timeLevel,
			String time, String port , String company) {
		return spMmsPortDAO.query(page, isTD, timeLevel, time , port , company);
	}

	public List<CommonFlush> listData(int isTD, int timeLevel, String time,String port,String company) {
		return spMmsPortDAO.listData(isTD, timeLevel, time,port,company);
	}

}
