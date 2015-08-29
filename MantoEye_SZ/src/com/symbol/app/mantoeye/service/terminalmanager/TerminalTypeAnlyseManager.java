package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.terminal.TerminalAreaAnlyseDAO;
import com.symbol.app.mantoeye.dao.terminal.TerminalTypeAnlyseDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class TerminalTypeAnlyseManager {

	@Autowired
	private TerminalTypeAnlyseDAO terminalTypeAnlyseDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonSport> query(final Page page, int isTD,
			int timeLevel, String time_search,int areaType) {
		return terminalTypeAnlyseDAO.query(page, isTD, timeLevel, time_search,areaType);
	}

	public List<CommonSport> listData(int isTD,int timeLevel, String time_search,int areaType) {
		return terminalTypeAnlyseDAO.listData(isTD, timeLevel, time_search,areaType);
	}

}
