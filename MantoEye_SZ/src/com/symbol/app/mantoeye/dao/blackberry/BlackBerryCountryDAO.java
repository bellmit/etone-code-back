package com.symbol.app.mantoeye.dao.blackberry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.common.CommonQueryDAO;
import com.symbol.app.mantoeye.dto.blackberry.BlackBerryCountryBean;
import com.symbol.app.mantoeye.dto.blackberry.CountryUserTopBean;
import com.symbol.app.mantoeye.dto.blackberry.LdcUsersBean;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

/**
 * 用户归属地
 * 
 * @author rankin
 * 
 */
@Repository
public class BlackBerryCountryDAO extends CommonQueryDAO {

	// 查询该时间的全网总流量和总用户数
	private Double[] findSumTotalData(int timeLevel, int year, int month,
			int day, int hour, int week) {
		String totalSql = MantoEyeUtils.getBBFlushAndImsiSql(timeLevel, year,
				month, day, hour, week);
		Double[] dbs = new Double[] { -1.0, -1.0 };
		List l = this.getSession().createSQLQuery(totalSql).list();
		if (l != null && l.size() > 0) {
			Object[] objs = (Object[]) l.get(0);
			dbs[0] = Common.StringTodouble(objs[0] + "");
			dbs[1] = Common.StringTodouble(objs[1] + "");
		}
		dbs[0] = dbs[0] == 0 ? -1.0 : dbs[0];
		dbs[1] = dbs[1] == 0 ? -1.0 : dbs[1];
		return dbs;
	}

	/**
	 * 用户归属地分布(月)
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public List<BlackBerryCountryBean> findMonthCountry(int year, int month) {
		// 计算占比
		Double[] dbs = findSumTotalData(4, year, month, 0, 0, 0);

		List<BlackBerryCountryBean> result = new ArrayList<BlackBerryCountryBean>();
		BlackBerryCountryBean bean = null;
		Object[] objs = null;

		String sql = "Select nmUpFlush,nmDownFlush,nmTotalFlush,nmAveFlush,nmUsers,intCountryId,vcName from vStatMonthCountryBbFlush where intYear = ? and intMonth = ? order by nmTotalFlush desc";

		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, year);
		sq.setInteger(1, month);
		List rs = sq.list();
		logger.info("Size:" + rs.size());

		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new BlackBerryCountryBean();
				bean.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_MONTH
						+ "");
				bean.setYear(year);
				bean.setMonth(month);
				objs = (Object[]) rs.get(i);

				bean.setUpFlush(Common.StringToLong(objs[0] + ""));
				bean.setDownFlush(Common.StringToLong(objs[1] + ""));
				bean.setTotalFlush(Common.StringToLong(objs[2] + ""));
				bean.setAverageFlush(Common.StringTodouble(objs[3] + ""));
				bean.setImsis(Common.StringToLong(objs[4] + ""));

				bean.setDimensionId(Common.StringToLong(objs[5] + ""));
				bean.setDimensionName(objs[6] + "");
				bean.calculateData();
				bean.calculateFlushRate(dbs[0], dbs[1]);
				result.add(bean);
			}
		}

		return result;
	}

	/**
	 * 用户归属地分布(周)
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public List<BlackBerryCountryBean> findWeekCountry(int year, int week) {
		// 计算占比
		Double[] dbs = findSumTotalData(3, year, 0, 0, 0, week);

		List<BlackBerryCountryBean> result = new ArrayList<BlackBerryCountryBean>();
		BlackBerryCountryBean bean = null;
		Object[] objs = null;

		String sql = "Select nmUpFlush,nmDownFlush,nmTotalFlush,nmAveFlush,nmUsers,intCountryId,vcName from vStatWeekCountryBbFlush where intYear = ? and intWeek = ? order by nmTotalFlush desc";

		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, year);
		sq.setInteger(1, week);
		List rs = sq.list();
		logger.info("Size:" + rs.size());

		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new BlackBerryCountryBean();
				bean
						.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_WEEK
								+ "");
				bean.setYear(year);
				bean.setWeek(week);
				objs = (Object[]) rs.get(i);

				bean.setUpFlush(Common.StringToLong(objs[0] + ""));
				bean.setDownFlush(Common.StringToLong(objs[1] + ""));
				bean.setTotalFlush(Common.StringToLong(objs[2] + ""));
				bean.setAverageFlush(Common.StringTodouble(objs[3] + ""));
				bean.setImsis(Common.StringToLong(objs[4] + ""));

				bean.setDimensionId(Common.StringToLong(objs[5] + ""));
				bean.setDimensionName(objs[6] + "");
				bean.calculateData();
				bean.calculateFlushRate(dbs[0], dbs[1]);
				result.add(bean);
			}
		}

		return result;
	}

	/**
	 * 用户归属地分布(天)
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public List<BlackBerryCountryBean> findDayCountry(int year, int month,
			int day) {
		// 计算占比
		Double[] dbs = findSumTotalData(2, year, month, day, 0, 0);
		List<BlackBerryCountryBean> result = new ArrayList<BlackBerryCountryBean>();

		BlackBerryCountryBean bean = null;
		Object[] objs = null;

		String sql = "Select nmUpFlush,nmDownFlush,nmTotalFlush,nmAveFlush,nmUsers,intCountryId,vcName from vStatDayCountryBbFlush where intYear = ? and intMonth = ? and intDay = ? order by nmTotalFlush desc";

		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, year);
		sq.setInteger(1, month);
		sq.setInteger(2, day);
		List rs = sq.list();
		logger.info("Size:" + rs.size());

		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new BlackBerryCountryBean();
				bean.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_DAY + "");
				bean.setYear(year);
				bean.setMonth(month);
				bean.setDay(day);
				objs = (Object[]) rs.get(i);

				bean.setUpFlush(Common.StringToLong(objs[0] + ""));
				bean.setDownFlush(Common.StringToLong(objs[1] + ""));
				bean.setTotalFlush(Common.StringToLong(objs[2] + ""));
				bean.setAverageFlush(Common.StringTodouble(objs[3] + ""));
				bean.setImsis(Common.StringToLong(objs[4] + ""));

				bean.setDimensionId(Common.StringToLong(objs[5] + ""));
				bean.setDimensionName(objs[6] + "");
				bean.calculateData();
				bean.calculateFlushRate(dbs[0], dbs[1]);
				result.add(bean);
			}
		}

		return result;
	}

	/**
	 * 用户归属地分布(时)
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public List<BlackBerryCountryBean> findHourCountry(int year, int month,
			int day, int hour) {
		// 计算占比
		Double[] dbs = findSumTotalData(1, year, month, day, hour, 0);
		List<BlackBerryCountryBean> result = new ArrayList<BlackBerryCountryBean>();
		BlackBerryCountryBean bean = null;
		Object[] objs = null;

		String sql = "Select nmUpFlush,nmDownFlush,nmTotalFlush,nmAveFlush,nmUsers,intCountryId,vcName from vStatHourCountryBbFlush where intYear = ? and intMonth = ? and intDay = ? and intHour = ? order by nmTotalFlush desc";

		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, year);
		sq.setInteger(1, month);
		sq.setInteger(2, day);
		sq.setInteger(3, hour);
		List rs = sq.list();
		logger.info("Size:" + rs.size());

		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new BlackBerryCountryBean();
				bean
						.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_HOUR
								+ "");
				bean.setYear(year);
				bean.setMonth(month);
				bean.setDay(day);
				bean.setHour(hour);
				objs = (Object[]) rs.get(i);

				bean.setUpFlush(Common.StringToLong(objs[0] + ""));
				bean.setDownFlush(Common.StringToLong(objs[1] + ""));
				bean.setTotalFlush(Common.StringToLong(objs[2] + ""));
				bean.setAverageFlush(Common.StringTodouble(objs[3] + ""));
				bean.setImsis(Common.StringToLong(objs[4] + ""));

				bean.setDimensionId(Common.StringToLong(objs[5] + ""));
				bean.setDimensionName(objs[6] + "");
				bean.calculateData();
				bean.calculateFlushRate(dbs[0], dbs[1]);
				result.add(bean);
			}
		}

		return result;
	}

	/**
	 * 用户归属地 空间分布(月)
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public List<BlackBerryCountryBean> findMonthCountrySpace(int spaceLevel,
			Long countryId, int year, int month) {

		List<BlackBerryCountryBean> result = new ArrayList<BlackBerryCountryBean>();
		BlackBerryCountryBean bean = null;
		Object[] objs = null;

		String viewName = "";
		String dimId = "";
		String dimName = "";
		if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_BSC) {
			viewName = "vStatMonthBscUserBelongBbFlush";
			dimId = "intBscId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SGSN) {
			viewName = "vStatMonthSgsnCountryBbFlush";
			dimId = "intSgsnId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_STREET) {
			viewName = "vStatMonthStreetCountryBbFlush";
			dimId = "intStreetId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA) {
			viewName = "vStatMonthSaleAreaCountryBbFlush";
			dimId = "intSaleAreaId";
			dimName = "vcSaleAreaName";
		} else {
			viewName = "vStatMonthBranchCountryBbFlush";
			dimId = "intBranchId";
			dimName = "vcBranchName";
		}

		String sql = "Select nmUpFlush,nmDownFlush,nmTotalFlush,nmAveFlush,nmUsers,"
				+ dimId
				+ ","
				+ dimName
				+ ",vcCountryName from "
				+ viewName
				+ " where  intYear = ? and intMonth = ? ";
		if(countryId>0){
			sql = sql +" and intCountryId = "+countryId;
		}
		sql = sql +" order by  nmTotalFlush desc ";

		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setLong(0, countryId);
		sq.setInteger(1, year);
		sq.setInteger(2, month);
		List rs = sq.list();
		logger.info("Size:" + rs.size());

		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new BlackBerryCountryBean();
				bean.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_MONTH
						+ "");
				bean.setYear(year);
				bean.setMonth(month);
				bean.setCountryId(countryId);
				objs = (Object[]) rs.get(i);

				bean.setUpFlush(Common.StringToLong(objs[0] + ""));
				bean.setDownFlush(Common.StringToLong(objs[1] + ""));
				bean.setTotalFlush(Common.StringToLong(objs[2] + ""));
				bean.setAverageFlush(Common.StringTodouble(objs[3] + ""));
				bean.setImsis(Common.StringToLong(objs[4] + ""));

				bean.setDimensionId(Common.StringToLong(objs[5] + ""));
				bean.setDimensionName(objs[6] + "");
				bean.setCountryName(objs[7] + "");
				bean.calculateData();
				result.add(bean);
			}
		}

		return result;
	}

	/**
	 * 用户归属地 空间分布(周)
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public List<BlackBerryCountryBean> findWeekCountrySpace(int spaceLevel,
			Long countryId, int year, int week) {

		List<BlackBerryCountryBean> result = new ArrayList<BlackBerryCountryBean>();
		BlackBerryCountryBean bean = null;
		Object[] objs = null;

		String viewName = "";
		String dimId = "";
		String dimName = "";
		if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_BSC) {
			viewName = "vStatWeekBscUserBelongBbFlush";
			dimId = "intBscId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SGSN) {
			viewName = "vStatWeekSgsnCountryBbFlush";
			dimId = "intSgsnId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_STREET) {
			viewName = "vStatWeekStreetCountryBbFlush";
			dimId = "intStreetId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA) {
			viewName = "vStatWeekSaleAreaCountryBbFlush";
			dimId = "intSaleAreaId";
			dimName = "vcSaleAreaName";
		} else {
			viewName = "vStatWeekBranchCountryBbFlush";
			dimId = "intBranchId";
			dimName = "vcBranchName";
		}

		String sql = "Select nmUpFlush,nmDownFlush,nmTotalFlush,nmAveFlush,nmUsers,"
				+ dimId
				+ ","
				+ dimName
				+ ",vcCountryName from "
				+ viewName
				+ " where intYear = ? and intWeek = ? ";
		if(countryId>0){
			sql = sql +" and intCountryId = "+countryId;
		}
		sql = sql +" order by  nmTotalFlush desc ";
		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, year);
		sq.setInteger(1, week);
		List rs = sq.list();
		logger.info("Size:" + rs.size());

		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new BlackBerryCountryBean();
				bean
						.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_WEEK
								+ "");
				bean.setYear(year);
				bean.setWeek(week);
				bean.setCountryId(countryId);
				objs = (Object[]) rs.get(i);

				bean.setUpFlush(Common.StringToLong(objs[0] + ""));
				bean.setDownFlush(Common.StringToLong(objs[1] + ""));
				bean.setTotalFlush(Common.StringToLong(objs[2] + ""));
				bean.setAverageFlush(Common.StringTodouble(objs[3] + ""));
				bean.setImsis(Common.StringToLong(objs[4] + ""));

				bean.setDimensionId(Common.StringToLong(objs[5] + ""));
				bean.setDimensionName(objs[6] + "");
				bean.setCountryName(objs[7] + "");
				bean.calculateData();
				result.add(bean);
			}
		}

		return result;
	}

	/**
	 * 用户归属地 空间分布(天)
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public List<BlackBerryCountryBean> findDayCountrySpace(int spaceLevel,
			Long countryId, int year, int month, int day) {

		List<BlackBerryCountryBean> result = new ArrayList<BlackBerryCountryBean>();
		BlackBerryCountryBean bean = null;
		Object[] objs = null;

		String viewName = "";
		String dimId = "";
		String dimName = "";
		if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_BSC) {
			viewName = "vStatDayBscUserBelongBbFlush";
			dimId = "intBscId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SGSN) {
			viewName = "vStatDaySgsnCountryBbFlush";
			dimId = "intSgsnId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_STREET) {
			viewName = "vStatDayStreetCountryBbFlush";
			dimId = "intStreetId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA) {
			viewName = "vStatDaySaleAreaCountryBbFlush";
			dimId = "intSaleAreaId";
			dimName = "vcSaleAreaName";
		} else {
			viewName = "vStatDayBranchCountryBbFlush";
			dimId = "intBranchId";
			dimName = "vcBranchName";
		}

		String sql = "Select nmUpFlush,nmDownFlush,nmTotalFlush,nmAveFlush,nmUsers,"
				+ dimId
				+ ","
				+ dimName
				+ ",vcCountryName from "
				+ viewName
				+ " where intYear = ? and intMonth = ? and intDay = ?  ";
		if(countryId>0){
			sql = sql +" and intCountryId = "+countryId;
		}
		sql = sql +" order by  nmTotalFlush desc ";

		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, year);
		sq.setInteger(1, month);
		sq.setInteger(2, day);
		List rs = sq.list();
		logger.info("Size:" + rs.size());

		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new BlackBerryCountryBean();
				bean.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_DAY + "");
				bean.setYear(year);
				bean.setMonth(month);
				bean.setDay(day);
				bean.setCountryId(countryId);
				objs = (Object[]) rs.get(i);

				bean.setUpFlush(Common.StringToLong(objs[0] + ""));
				bean.setDownFlush(Common.StringToLong(objs[1] + ""));
				bean.setTotalFlush(Common.StringToLong(objs[2] + ""));
				bean.setAverageFlush(Common.StringTodouble(objs[3] + ""));
				bean.setImsis(Common.StringToLong(objs[4] + ""));

				bean.setDimensionId(Common.StringToLong(objs[5] + ""));
				bean.setDimensionName(objs[6] + "");
				bean.setCountryName(objs[7] + "");
				bean.calculateData();
				result.add(bean);
			}
		}

		return result;
	}

	/**
	 * 用户归属地 空间分布(小时)
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public List<BlackBerryCountryBean> findHourCountrySpace(int spaceLevel,
			Long countryId, int year, int month, int day, int hour) {

		List<BlackBerryCountryBean> result = new ArrayList<BlackBerryCountryBean>();
		BlackBerryCountryBean bean = null;
		Object[] objs = null;

		String viewName = "";
		String dimId = "";
		String dimName = "";
		if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_BSC) {
			viewName = "vStatHourBscUserBelongBbFlush";
			dimId = "intBscId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SGSN) {
			viewName = "vStatHourSgsnCountryBbFlush";
			dimId = "intSgsnId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_STREET) {
			viewName = "vStatHourStreetCountryBbFlush";
			dimId = "intStreetId";
			dimName = "vcName";
		} else if (spaceLevel == CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA) {
			viewName = "vStatHourSaleAreaCountryBbFlush";
			dimId = "intSaleAreaId";
			dimName = "vcSaleAreaName";
		} else {
			viewName = "vStatHourBranchCountryBbFlush";
			dimId = "intBranchId";
			dimName = "vcBranchName";
		}

		String sql = "Select nmUpFlush,nmDownFlush,nmTotalFlush,nmAveFlush,nmUsers,"
				+ dimId
				+ ","
				+ dimName
				+ ",vcCountryName from "
				+ viewName
				+ " where intYear = ? and intMonth = ? and intDay = ? and intHour=? " ;
		if(countryId>0){
			sql = sql +" and intCountryId = "+countryId;
		}
		sql = sql +" order by  nmTotalFlush desc ";

		SQLQuery sq = this.getSession().createSQLQuery(sql);
		sq.setInteger(0, year);
		sq.setInteger(1, month);
		sq.setInteger(2, day);
		sq.setInteger(3, hour);

		List rs = sq.list();
		logger.info("Size:" + rs.size());

		if (rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				bean = new BlackBerryCountryBean();
				bean
						.setTimeLevel(CommonConstants.MANTOEYE_TIME_LEVEL_HOUR
								+ "");
				bean.setYear(year);
				bean.setMonth(month);
				bean.setDay(day);
				bean.setHour(hour);
				bean.setCountryId(countryId);
				objs = (Object[]) rs.get(i);

				bean.setUpFlush(Common.StringToLong(objs[0] + ""));
				bean.setDownFlush(Common.StringToLong(objs[1] + ""));
				bean.setTotalFlush(Common.StringToLong(objs[2] + ""));
				bean.setAverageFlush(Common.StringTodouble(objs[3] + ""));
				bean.setImsis(Common.StringToLong(objs[4] + ""));

				bean.setDimensionId(Common.StringToLong(objs[5] + ""));
				bean.setDimensionName(objs[6] + "");
				bean.setCountryName(objs[7] + "");
				bean.calculateData();
				result.add(bean);
			}
		}

		return result;
	}

	/**
	 * Top100小区用户流量
	 * 
	 * @return
	 */
	public List<CommonFlush> findCountryTopUser(Long countryId, String date,
			int stattype) {
		Date d = CommonUtils.getDate(date);
		int year = CommonUtils.getYear(d);
		int month = CommonUtils.getMonth(d);
		int week = CommonUtils.getWeek(d);
		int day = CommonUtils.getDay(d);
		String sql = "";
		if (stattype == 2) {
			sql = buildBaseSql("vStatDayBbCountryUserFlush");
			if (countryId > 0) {
				sql = sql + " and  a.intCountryId = " + countryId;
			}
			sql = sql + " and  a.intYear = " + year + " and a.intMonth = "
					+ month + " and a.intDay=" + day
					+ "   order by a.nmUsers DESC";
		} else if (stattype == 3) {
			sql = buildBaseSql("vStatWeekBbCountryUserFlush");
			if (countryId > 0) {
				sql = sql + " and  a.intCountryId = " + countryId;
			}

			sql = sql + " and  a.intYear = " + year + " and a.intWeek=" + week
					+ "   order by a.nmUsers DESC";

		} else if (stattype == 4) {
			sql = buildBaseSql("vStatMonthBbCountryUserFlush");
			if (countryId > 0) {
				sql = sql + " and  a.intCountryId = " + countryId;
			}
				sql = sql + " and  a.intYear =" + year + " and a.intMonth = "
					+ month + "  order by a.nmUsers DESC";
		}

		return buildBeanList(this.getSession().createSQLQuery(sql).list(),
				date, stattype);
	}

	private String buildBaseSql(String tableName) {
		String baseSql = "select top 100 a.nmFlush,a.nmUsers,a.vcCGI,a.vcName,c.vcCgiChName,e.vcName as bscname,f.vcName as streetname,g.vcSaleAreaName,h.vcName as sgsnname,i.vcBranchName,a.nmAveFlush  "
				+ " from "
				+ tableName
				+ " a,ftbCgi c,dtbBsc e,dtbGsn  f,dtbSaleArea g,dtbStreet h,dtbSubsidiaryCompany i "
				+ " where a.vcCGI=c.vcCGI and c.intBscId = e.intBscId "
				+ " and c.intSgsnId = f.intSgsnId and c.intSaleAreaId = g.intSaleAreaId and c.intStreetId = h.intStreetId "
				+ " and c.intBranchId = i.intBranchId";
		return baseSql;
	}

	public List<CommonFlush> buildBeanList(List list, String time, int timeLevel) {

		List<CommonFlush> commonFlushList = new ArrayList<CommonFlush>();
		;
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {

			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);

				commonFlush.setTotalFlush(Common.StringToLong(objs[0] + ""));
				commonFlush.setIntImsis(Common.StringToLong(objs[1] + ""));
				commonFlush.setCgi(objs[2] + "");
				commonFlush.setTop(i + 1);
				commonFlush
						.setUserBelong(objs[3] != null ? objs[3] + "" : "--");
				commonFlush.setCgiName(objs[4] != null ? objs[4] + "" : "--");
				commonFlush.setBsc(objs[5] != null ? objs[5] + "" : "--");
				commonFlush.setStreet(objs[6] != null ? objs[6] + "" : "--");
				commonFlush.setSaleArea(objs[7] != null ? objs[7] + "" : "--");
				commonFlush.setSgsn(objs[8] != null ? objs[8] + "" : "--");
				commonFlush.setSubsidiaryCompany(objs[9] != null ? objs[9] + ""
						: "--");
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[10] + ""));
				// commonFlush.setArea(objs[4]!=null?objs[4]+"":"--");
				// logger.info(objs[5]+"--"+objs[6]+objs[8]);
				// 访问次数
				if (timeLevel == 3) {
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(time));
				} else {
					commonFlush.setFullDate(time);
				}
				commonFlush.calculateData();
				// commonFlush.numberFormatData();
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
	}
}
