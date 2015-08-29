package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.terminal.FlushLevelDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class FlushLevelManager {
	@Autowired
	private FlushLevelDAO flushLevelDAO;

	public Page<CommonSport> query(final Page page, String time_search,int timeLevel_search) {
		return flushLevelDAO.query(page,time_search,timeLevel_search);
	}

	public List<CommonSport> listData(String time_search,int timeLevel_search) {
		return flushLevelDAO.listData(time_search,timeLevel_search);
	}


}
