package com.symbol.app.mantoeye.service.businessAnalyse;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.dao.businessAnalyse.ProtocolDistributeDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.SymbolUtils;
import com.symbol.wp.modules.orm.Page;

@Service
@Transactional
public class ProtocolDistributeManager {
	@Autowired
	private ProtocolDistributeDAO protocolDistributeDAO;

	public List<CommonFlush> listData(int protocolType, int isTD,
			int timeLevel, String time) {
		return protocolDistributeDAO.listData(protocolType, isTD, timeLevel,
				time);
	}
	/**
	 * 分页查询
	 */
	public Page<CommonFlush> queryTrend(int protocolType,final Page<CommonFlush>  page, int isTD,
			int timeLevel, String stime, String etime,
			String tcpName) {
		return protocolDistributeDAO.queryTrend(protocolType,page, isTD,
				timeLevel, stime, etime, tcpName);
	}
	public List<CommonFlush> queryAllTrend( int protocolType,int isTD,
			 int timeLevel, String stime, String etime,
			String tcpName) {
		return protocolDistributeDAO.queryAllTrend(protocolType,isTD, 
				timeLevel, stime, etime, tcpName);
	}
}
