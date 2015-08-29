package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.terminal.TerminalSystemDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class TerminalSystemManager {
	@Autowired
	private TerminalSystemDAO terminalSystemDAO;

	public Page<CommonSport> query(final Page page, int timeLevel, String startTime,int areaType) {
		return terminalSystemDAO.query(page, timeLevel,  startTime, areaType);
	}

	public List<CommonSport> listData(int timeLevel, String startTime,int areaType) {
		return terminalSystemDAO.listData(timeLevel,  startTime, areaType);
	}


}
