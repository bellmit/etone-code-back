package com.symbol.app.mantoeye.service.businessRule;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.business.TopFlushAndImsiDAO;
import com.symbol.app.mantoeye.dao.common.DimensionDAO;
import com.symbol.app.mantoeye.dto.flush.DimensionBeans;
import com.symbol.app.mantoeye.dto.flush.TopStat;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;

/**
 * 流量用户top100小区
 * 
 * @author rankin
 * 
 */
// Spring Service Bean的标识.
@Service
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class TopFlushAndImsiManager {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DimensionBeans dimensionBeans;

	@Autowired
	private TopFlushAndImsiDAO topFlushAndImsiDAO;

	@Autowired
	private DimensionDAO dimensionDAO;

	// 流量top100
	@Transactional(readOnly = true)
	public List<TopStat> findTopStatFlush(int raittype, String stattype,
			String date) {
		// logger.info("dimensionBeans:"+dimensionBeans.getDimensions());
		if (dimensionBeans.getDimensions() == null) {
			initDimensions();
		}
		Date d = CommonUtils.getDate(date);
		int year = CommonUtils.getYear(d);
		int month = CommonUtils.getMonth(d);
		int day = CommonUtils.getDay(d);
		int hour = CommonUtils.getHour(d);
		int week = CommonUtils.getWeek(d);
		List<TopStat> list = null;
		int istattype = Common.StringToInt2(stattype);
		if (istattype > 0 && istattype < 5) {
			list = topFlushAndImsiDAO.findTopStatData(true, istattype,
					raittype, year, month, day, hour, week);
		} else {
			list = null;
		}
		return list;
	}

	// 用户数top100
	@Transactional(readOnly = true)
	public List<TopStat> findTopStatImsi(int raittype, String stattype,
			String date) {
		// logger.info("dimensionBeans:"+dimensionBeans.getDimensions());
		if (dimensionBeans.getDimensions() == null) {
			initDimensions();
		}
		Date d = CommonUtils.getDate(date);
		int year = CommonUtils.getYear(d);
		int month = CommonUtils.getMonth(d);
		int day = CommonUtils.getDay(d);
		int hour = CommonUtils.getHour(d);
		int week = CommonUtils.getWeek(d);
		List<TopStat> list = null;
		int istattype = Common.StringToInt2(stattype);
		if (istattype > 0 && istattype < 5) {
			list = topFlushAndImsiDAO.findTopStatData(false, istattype,
					raittype, year, month, day, hour, week);
		} else {
			list = null;
		}
		return list;
	}

	/**
	 * 初始化维表数据
	 */
	@Transactional(readOnly = true)
	public void initDimensions() {
		logger.info("**开始更新维表数据！**");
		try {
			logger.info(new Date() + "");
			dimensionDAO.initBsc();
			dimensionDAO.initCity();
			dimensionDAO.initCountry();
			dimensionDAO.initGsn();
			dimensionDAO.initProvince();
			dimensionDAO.initSaleArea();
			dimensionDAO.initStreet();
			dimensionDAO.initSubsidiaryCompany();
			logger.info("**更新维表数据成功！**");
		} catch (Exception e) {
			logger.warn("**更新维表数据失败！**");
			logger.error(e.getMessage());
		}
	}
}
