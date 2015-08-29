package com.symbol.app.mantoeye.service.terminalmanager;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.terminal.FlushLevelAnalyseDAO;
import com.symbol.app.mantoeye.dao.terminal.FlushLevelAreaAnalyseDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class FlushLevelAreaAnalyseManager {
	@Autowired
	private FlushLevelAreaAnalyseDAO flushLevelAreaAnalyseDAO;

	public Page<CommonSport> query(final Page page,Long nmStatFlushLayeringId,String time_search,int timeLevel_search,int areaType) {
		return flushLevelAreaAnalyseDAO.query(page, nmStatFlushLayeringId,time_search,timeLevel_search,areaType);
	}

	public List<CommonSport> listData(Long nmStatFlushLayeringId,String time_search,int timeLevel_search,int areaType) {
		return flushLevelAreaAnalyseDAO.listData(nmStatFlushLayeringId,time_search,timeLevel_search,areaType);
	}


}
