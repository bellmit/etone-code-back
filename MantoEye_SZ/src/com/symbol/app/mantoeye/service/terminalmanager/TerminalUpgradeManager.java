package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.terminal.TerminalUpgradeDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class TerminalUpgradeManager {
	@Autowired
	private TerminalUpgradeDAO terminalUpgradeDAO;

	public Page<CommonSport> query(final Page page, int area,String sTime,String eTime,int changUsers_search,int changUsers_end,int timeLevel) {
		return terminalUpgradeDAO.query( page,  area, sTime, eTime,changUsers_search,changUsers_end,timeLevel);
	}

	public List<CommonSport> listData(int area,String sTime,String eTime,int changUsers_search,int changUsers_end,int timeLevel) {
	return terminalUpgradeDAO.listData( area, sTime, eTime,changUsers_search,changUsers_end,timeLevel);
	}


}
