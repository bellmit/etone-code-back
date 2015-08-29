package com.symbol.app.mantoeye.service.sports;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.sports.WirelessBusinessDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class WirelessBusinessrManager {
	@Autowired
	private WirelessBusinessDAO  wirelessBusinessDAO;

	public Page<CommonSport> query(final Page page, int timeLevel,
			String startTime, String endTime,Integer intType,String vcCGI) {

		return wirelessBusinessDAO.query(page, timeLevel, startTime, endTime,
				intType,vcCGI);
	}

	public List<CommonSport> listData(int timeLevel,
			String startTime, String endTime, Integer intType,String vcCGI) {
		return wirelessBusinessDAO.listData(timeLevel, startTime, endTime,intType,vcCGI);
	}

}
