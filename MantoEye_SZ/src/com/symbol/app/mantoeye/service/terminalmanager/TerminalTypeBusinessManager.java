package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.terminal.TerminalTypeBusinessDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class TerminalTypeBusinessManager {

	@Autowired
	private TerminalTypeBusinessDAO terminalTypeBusinessDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonSport> query(final Page page, int isTD,
			int timeLevel, String time_search,int areaType,Long nmAreaId,String vcTerminalNetType) {
		return terminalTypeBusinessDAO.query(page, isTD, timeLevel, time_search,areaType,nmAreaId,vcTerminalNetType);
	}

	public List<CommonSport> listData(int isTD,int timeLevel, String time_search,int areaType,Long nmAreaId,String vcTerminalNetType) {
		return terminalTypeBusinessDAO.listData(isTD, timeLevel, time_search,areaType,nmAreaId,vcTerminalNetType);
	}

}
