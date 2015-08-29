package com.symbol.app.mantoeye.service.blackberry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.blackberry.BlackBerryAreaDistinationDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.wp.modules.orm.Page;

//Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class BlackBerryAreaDistinationManager {

	@Autowired
	private BlackBerryAreaDistinationDAO blackBerryAreaDistinationDAO;

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int areaType,
			int timeLevel, String time) {
		return blackBerryAreaDistinationDAO.query(page, isTD, areaType,
				timeLevel, time);
	}
	
	public List<CommonFlush> listData(final Page page,int isTD, int areaType, int timeLevel,
			String time) {
		return blackBerryAreaDistinationDAO.listData(page,isTD, areaType, timeLevel, time);
	}
}
