package com.symbol.app.mantoeye.dao.groupBisness;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

@Repository
public class GroupBisAreaDistributeDAO extends HibernateDao {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param isTD
	 * @param areaType
	 *            BSC:1 SGSN:2 街道办:3 营销片区:4 分公司:5
	 * @param timeLevel
	 *            小时:1 天:2 周:3 月:4
	 * @param year
	 * @param month
	 * @param week
	 * @param day
	 * @param hour
	 * @return
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int areaType,
			int timeLevel, String time, String apnName) {
		String dateStr = this.changeDate(time, timeLevel);
		String[] dateValue = dateStr.split(";");
		int year = Common.StringToInt(dateValue[0] + "");
		int month = Common.StringToInt(dateValue[1] + "");
		int week = Common.StringToInt(dateValue[2] + "");
		int day = Common.StringToInt(dateValue[3] + "");
		int hour = Common.StringToInt(dateValue[4] + "");

		String sql = this.buildSql(page, isTD, areaType, timeLevel, year,
				month, week, day, hour, apnName);
		// SQLQuery query = this.getSession().createSQLQuery(sql);
		// List pageList = query.list();
		// query.setFirstResult(page.getFirst());
		// query.setMaxResults(page.getPageSize());
		String sqls = sql.split("order by")[0];
		String totalSql = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj = this.getSession().createSQLQuery(totalSql)
				.uniqueResult();
		Integer total = Integer.parseInt(null == totalObj.toString()
				|| "".equals(totalObj.toString()) ? "0" : totalObj.toString());
		List<Object[]> list = this.getSession().createSQLQuery(sql)
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);
		if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
			time = CommonUtils.getDayInnerWeek(time);
		}
		newPage.setResult(buildBeanList(list, time));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<CommonFlush> listData(final Page page, int isTD, int areaType,
			int timeLevel, String time, String apnName) {
		String dateStr = this.changeDate(time, timeLevel);
		String[] str = dateStr.split(";");
		int year = Common.StringToInt(str[0]);
		int month = Common.StringToInt(str[1]);
		int week = Common.StringToInt(str[2]);
		int day = Common.StringToInt(str[3]);
		int hour = Common.StringToInt(str[4]);
		String sql = this.buildSql(page, isTD, areaType, timeLevel, year,
				month, week, day, hour, apnName);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
			time = CommonUtils.getDayInnerWeek(time);
		}

		return buildBeanList(list, time);
	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonFlush> buildBeanList(List list, String time) {
		List<CommonFlush> commonFlushList = commonFlushList = new ArrayList<CommonFlush>();
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setBusiness(objs[0] + "");// 业务
				commonFlush.setBusinessName(objs[1] + "");// 业务名称
				commonFlush.setIntUpFlush(Common.StringToLong(objs[2] + ""));// 上流量
				commonFlush.setIntDownFlush(Common.StringToLong(objs[3] + ""));// 下流量
				commonFlush.setTotalFlush(Common.StringToLong(objs[4] + ""));// 总流量
				commonFlush.setIntImsis(Common.StringToLong(objs[5] + ""));// 用户数
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[6] + ""));

				commonFlush.setFullDate(time);
				commonFlush.calculateData();
				// commonFlush.numberFormatData();
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
	}

	/**
	 * 组装SQL语句
	 * 
	 * @param isTD
	 * @param areaType
	 * @param timeLevel
	 * @param year
	 * @param month
	 * @param week
	 * @param day
	 * @param hour
	 * @return
	 */
	public String buildSql(final Page page, int isTD, int areaType,
			int timeLevel, int year, int month, int week, int day, int hour,
			String apnName) {
		apnName = apnName.toUpperCase();
		String sql = "";
		String fields = "";
		String table = "";
		// 维度类型
		switch (areaType) {
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_BSC:
			fields = " intBscId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnBsc"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnBsc"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnBsc"
									: "vStatMonthApnBsc";
			break;
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SGSN:
			fields = " intSgsnId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnSgsn"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnSgsn"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnSgsn"
									: "vStatMonthApnSgsn";
			break;
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_STREET:
			fields = " intStreetId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnStreet"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnStreet"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnStreet"
									: "vStatMonthApnStreet";
			break;
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SALEAREA:
			fields = " intSaleAreaId,vcSaleAreaName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnSaleArea"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnSaleArea"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnSaleArea"
									: "vStatMonthApnSaleArea";
			break;
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_BRANCH:
			fields = " intBranchId,vcBranchName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnCompany"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnCompany"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnCompany"
									: "vStatMonthApnCompany";
			break;
		}
		sql = " select " + fields;

		// 时间粒度
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intMonth,intDay,intHour"
					+ " from " + table + " where 1=1 " + " and intRaitype="
					+ isTD + " and intYear=" + year + " and intMonth=" + month
					+ " and intDay=" + day + " and intHour=" + hour
					+ " and vcApnDomain='" + apnName + "'";
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intMonth,intDay"
					+ " from " + table + " where 1=1 " + " and intRaitype="
					+ isTD + " and intYear=" + year + " and intMonth=" + month
					+ " and intDay=" + day + " and vcApnDomain='" + apnName
					+ "'";
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intYear,intWeek"
					+ " from " + table + " where 1=1 " + " and intRaitype="
					+ isTD + " and intYear=" + year
					// + " and intMonth=" + month
					+ " and intWeek=" + week + " and vcApnDomain='" + apnName
					+ "'";
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intMonth"
					+ " from " + table + " where 1=1 " + " and intRaitype="
					+ isTD + " and intYear=" + year + " and intMonth=" + month
					+ " and vcApnDomain='" + apnName + "'";
			break;
		}

		sql = sql + " order by " + page.getOrderBy() + " " + page.getOrder();
		return sql;
	}

	public String changeDate(String date, int timeLevel) {
		Date d = null;
		if (timeLevel == 1) {

		} else if (timeLevel == 2) {
			date = date + " 01:01";
		} else if (timeLevel == 3) {
			date = date + " 01:01";
		} else if (timeLevel == 4) {
			date = date + "-01" + " 01:01";
		}
		StringBuffer sql = new StringBuffer();
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		String dateStr = year + ";" + month + ";" + week + ";" + day + ";"
				+ hour;
		return dateStr;

	}

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> queryTrend(final Page page, int isTD,
			int areaType, int timeLevel, String stime, String etime,
			String apnName, String areaname) {
		String sql = buildTrendSql(isTD, areaType, timeLevel, stime, etime,
				apnName, areaname);
		sql = sql.substring(0, sql.indexOf("order")) + " order by "
				+ buildOrder(page, timeLevel);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setFirstResult(page.getFirst());
		query.setMaxResults(page.getPageSize());
		List<CommonFlush> list = query.list();
		Page<CommonFlush> newPage = new Page<CommonFlush>();
		newPage.setResult(buildTrendBeanList(list, timeLevel));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	private String buildOrder(Page<CommonFlush> page, int timeLevel) {
		String sql = "";
		String deforder = "asc";
		String orderBy = page.getOrderBy();
		String order = page.getOrder();
		if (Common.judgeString(orderBy) && !orderBy.equals("fullDate")) {
			sql = " " + orderBy + " " + order;
		} else {
			if (Common.judgeString(order) && order.toLowerCase().equals("desc")) {
				deforder = "desc";
			}
			if (timeLevel == 3) {
				sql += "intYear " + deforder + ",intWeek " + deforder;
			} else {
				sql += " ddate " + deforder;
			}
		}
		return sql;
	}

	/**
	 * 
	 */
	public List<CommonFlush> queryAllTrend(int isTD, int areaType,
			int timeLevel, String stime, String etime, String apnName,
			String areaname) {
		String sql = buildTrendSql(isTD, areaType, timeLevel, stime, etime,
				apnName, areaname);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildTrendBeanList(list, timeLevel);
	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonFlush> buildTrendBeanList(List list, int timeLevel) {
		List<CommonFlush> commonFlushList = commonFlushList = new ArrayList<CommonFlush>();
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setIntUpFlush(Common.StringToLong(objs[0] + ""));// 上流量
				commonFlush.setIntDownFlush(Common.StringToLong(objs[1] + ""));// 下流量
				commonFlush.setTotalFlush(Common.StringToLong(objs[2] + ""));// 总流量
				commonFlush.setIntImsis(Common.StringToLong(objs[3] + ""));// 用户数
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[4] + ""));
				if (timeLevel == 1) {
					commonFlush.setFullDate((objs[5] + "").substring(0, 16));
				} else if (timeLevel == 2) {
					commonFlush.setFullDate((objs[5] + "").split(" ")[0]);
				} else if (timeLevel == 4) {
					commonFlush.setFullDate((objs[5] + "").substring(0, 7));
				} else {
					commonFlush.setIntYear(Common.StringToInt(objs[5] + ""));
					commonFlush.setIntWeek(Common.StringToInt(objs[6] + ""));
					commonFlush.setSpanDate(timeLevel);
				}

				commonFlush.calculateData();
				// commonFlush.numberFormatData();
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
	}

	public String buildTrendSql(int isTD, int areaType, int timeLevel,
			String sTime, String eTime, String apnName, String areaName) {
		apnName = apnName.toUpperCase();
		String sql = "";
		String confields = "";
		String table = "";
		String ordersql = "";
		// 维度类型
		switch (areaType) {
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_BSC:
			confields = " vcName ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnBsc"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnBsc"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnBsc"
									: "vStatMonthApnBsc";
			break;
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SGSN:
			confields = " vcName ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnSgsn"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnSgsn"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnSgsn"
									: "vStatMonthApnSgsn";
			break;
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_STREET:
			confields = " vcName ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnStreet"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnStreet"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnStreet"
									: "vStatMonthApnStreet";
			break;
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SALEAREA:
			confields = " vcSaleAreaName ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnSaleArea"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnSaleArea"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnSaleArea"
									: "vStatMonthApnSaleArea";
			break;
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_BRANCH:
			confields = " vcBranchName ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnCompany"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnCompany"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnCompany"
									: "vStatMonthApnCompany";
			break;
		}
		sql = " select ";

		// 时间粒度
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,"
					+ " convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00')) as ddate "
					+ " from " + table + " where 1=1 " + " and intRaitype="
					+ isTD + " and  ddate >= "
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  ddate <= "
					+ MantoEyeUtils.formatData(eTime, timeLevel)
					+ " and vcApnDomain='" + apnName + "'" + " and "
					+ confields + " = '" + areaName + "' ";
			ordersql = " order by ddate asc";
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,"
					+ " convert(datetime,string(intYear,'-',intMonth,'-',intDay)) as ddate "
					+ " from " + table + " where 1=1 " + " and intRaitype="
					+ isTD + " and  ddate>="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  ddate<="
					+ MantoEyeUtils.formatData(eTime, timeLevel)
					+ " and vcApnDomain='" + apnName + "'" + " and "
					+ confields + " = '" + areaName + "' ";
			ordersql = " order by ddate asc";
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			Date sDate = CommonUtils.getDate(sTime);
			int sYear = CommonUtils.getYear(sDate);
			int sMonth = CommonUtils.getMonth(sDate);
			int sWeek = CommonUtils.getWeek(sDate);

			Date eDate = CommonUtils.getDate(eTime);
			int eYear = CommonUtils.getYear(eDate);
			int eMonth = CommonUtils.getMonth(eDate);
			int eWeek = CommonUtils.getWeek(eDate);

			if (sMonth == 12 && sWeek == 1) {
				sYear = sYear + 1;
			}
			if (eMonth == 12 && eWeek == 1) {
				eYear = eYear + 1;
			}
			String bSql = "";
			for (int i = 1; i < eYear - sYear; i++) {
				bSql = bSql + " or (intYear = " + (sYear + i) + ") ";
			}
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,intYear,intWeek"
					+ " from " + table + " where 1=1 " + " and intRaitype="
					+ isTD;
			if (sYear != eYear) {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek < 100 )" + " or ( intYear = "
						+ eYear + " and intWeek>0 and intWeek <= " + eWeek
						+ " ))  " + bSql;
			} else {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek <= " + eWeek + " ))  " + bSql;
			}
			sql = sql + " and vcApnDomain='" + apnName + "'" + " and "
					+ confields + " = '" + areaName + "' ";
			ordersql = " order by intYear asc,intWeek asc";
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmAveFlush,"
					+ " convert(datetime,string(intYear,'-',intMonth)) as ddate "
					+ " from " + table + " where 1=1 " + " and intRaitype="
					+ isTD + " and  ddate >="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  ddate <="
					+ MantoEyeUtils.formatData(eTime, timeLevel)
					+ " and vcApnDomain='" + apnName + "'" + " and "
					+ confields + " = '" + areaName + "' ";
			ordersql = " order by ddate asc";
			break;
		}

		sql = sql + ordersql;
		return sql;
	}
}
