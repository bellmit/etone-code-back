package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.terminal.TerminalSystemBusinessDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class TerminalSystemBusinessManager {
	@Autowired
	private TerminalSystemBusinessDAO systemBusinessDAO;

	public Page<CommonSport> query(final Page page,int terminalId,int areaType,
			int timeLevel, String startTime,long nmAreaId) {
		return systemBusinessDAO.query(page,terminalId  ,areaType,
				timeLevel, startTime,nmAreaId);
	}

	public List<CommonSport> listData(int terminalId,int areaType,int timeLevel, String startTime,long nmAreaId) {
		return systemBusinessDAO.listData( terminalId, areaType, timeLevel,  startTime,nmAreaId);
	}


}
