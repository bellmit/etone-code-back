package com.symbol.app.mantoeye.service.businessAnalyse;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.symbol.app.mantoeye.dao.businessAnalyse.OwnBusinessTerminalDAO;
import com.symbol.app.mantoeye.dto.sports.CommonSport;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class OwnBusinessTerminalManager {

	@Autowired
	private OwnBusinessTerminalDAO ownBusinessTerminalDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonSport> query(final Page page, int isTD,
			int timeLevel, String startTime,String endTime) {
		return ownBusinessTerminalDAO.query(page, isTD, timeLevel, startTime,endTime);
	}

	public List<CommonSport> listData(int isTD,int timeLevel, String startTime,String endTime) {
		return ownBusinessTerminalDAO.listData(isTD, timeLevel, startTime,endTime);
	}

}
