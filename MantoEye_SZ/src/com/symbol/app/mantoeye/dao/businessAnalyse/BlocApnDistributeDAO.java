package com.symbol.app.mantoeye.dao.businessAnalyse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;
import com.symbol.wp.modules.orm.hibernate.HibernateDao;

/*
 * 集团Apn分布
 */
//Spring DAO Bean的标识
@Repository
public class BlocApnDistributeDAO extends HibernateDao {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param isTD
	 *            TD标识
	 * @param apnType
	 *            APN类型 (集团APN 其他...)
	 * @param timeLevel
	 *            时间粒度
	 * @param time
	 *            查询时间
	 * @return
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int apnType,
			int timeLevel, String time) {

		Date s = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(s);
		int month = CommonUtils.getMonth(s);
		int week = CommonUtils.getWeek(s);
		int day = CommonUtils.getDay(s);
		int hour = CommonUtils.getHour(s);

		String sql = this.buildSql(isTD, apnType, timeLevel, year, month, week,
				day, hour, page);
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
		newPage.setResult(buildBeanList(list, timeLevel, time, isTD));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	// 查询该时间的集团业务总流量和总用户数
	private Double[] findSumTotalData(int timeLevel, String date, int dataType) {
		String totalSql = MantoEyeUtils.getGroupFlushAndImsiSql(dataType,
				timeLevel, date);
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
	 * 查询所有
	 * 
	 * @param page
	 * @param isTD
	 *            TD标识
	 * @param apnType
	 *            APN类型 (集团APN 其他...)
	 * @param timeLevel
	 *            时间粒度
	 * @param time
	 *            查询时间
	 * @return
	 */
	public List<CommonFlush> listData(int isTD, int apnType, int timeLevel,
			String time) {
		Date s = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(s);
		int month = CommonUtils.getMonth(s);
		int week = CommonUtils.getWeek(s);
		int day = CommonUtils.getDay(s);
		int hour = CommonUtils.getHour(s);
		String sql = this.buildSql(isTD, apnType, timeLevel, year, month, week,
				day, hour, null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list, timeLevel, time, isTD);
	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonFlush> buildBeanList(List list, int timeLevel,
			String time, int idTD) {
		// 计算占比
		Double[] dbs = findSumTotalData(timeLevel, time, idTD);
		List<CommonFlush> commonFlushList = null;
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			commonFlushList = new ArrayList<CommonFlush>();
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setBusinessName(objs[0] + "");// 集团APN名称
				commonFlush.setIntUpFlush(Common.StringToLong(objs[1] + ""));// 上流量
				commonFlush.setIntDownFlush(Common.StringToLong(objs[2] + ""));// 下流量
				commonFlush.setTotalFlush(Common.StringToLong(objs[3] + ""));// 总流量
				commonFlush.setIntImsis(Common.StringToLong(objs[4] + ""));// 用户数
				commonFlush.setActiveCount(Common.StringToLong(objs[5] + ""));// 激活次数
				commonFlush.setCompany(objs[6] + "");// 集团APN中文名
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[7] + ""));
				commonFlush.setLineName(objs[8] + "");
				if (timeLevel == 3) {
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(time));
				} else {
					commonFlush.setFullDate(time);// 时间
				}
				commonFlush.calculateData();
				// commonFlush.numberFormatData();
				commonFlush.calculateFlushRate(dbs[0], dbs[1]);
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
	}

	/**
	 * 组装查询语句
	 * 
	 */
	public String buildSql(int isTD, int apnType, int timeLevel, int year,
			int month, int week, int day, int hour, Page page) {
		String sql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmFlush ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		switch (timeLevel) {
		case 1:
			sql = "select  vcApnDomain,nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,vcApnName,nmAveFlush,vcLine,intYear,intMonth,intDay,intHour"
					+ " from vStatHourApn  "
					+ " where 1=1 "
					+ " and intYear="
					+ year
					+ " and intMonth="
					+ month
					+ " and intDay="
					+ day
					+ " and intHour=" + hour;
			sortSql = sortSql + " order by intYear,intMonth,intDay,intHour, "
					+ sortColumn + sortType;
			break;
		case 2:
			sql = "select vcApnDomain,nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,vcApnName,nmAveFlush,vcLine,intYear,intMonth,intDay"
					+ " from vStatDayApn  "
					+ " where 1=1 "
					+ " and intYear="
					+ year + " and intMonth=" + month + " and intDay=" + day;
			sortSql = sortSql + " order by intYear,intMonth,intDay, "
					+ sortColumn + sortType;
			break;
		case 3:
			sql = "select vcApnDomain,nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,vcApnName,nmAveFlush,vcLine,intYear,intYear,intWeek"
					+ " from vStatWeekApn  "
					+ " where 1=1 "
					+ " and intYear="
					+ year
					// + " and intMonth=" + month
					+ " and intWeek=" + week;
			sortSql = sortSql + " order by intYear,intWeek, " + sortColumn
					+ sortType;
			break;
		case 4:
			sql = "select vcApnDomain,nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,vcApnName,nmAveFlush,vcLine,intYear,intMonth"
					+ " from vStatMonthApn  "
					+ " where 1=1 "
					+ " and intYear="
					+ year + " and intMonth=" + month;
			sortSql = sortSql + " order by intYear,intMonth, " + sortColumn
					+ sortType;
			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		sql = sql + " and intApnTypeId =3 ";
		sql = sql + sortSql;
		return sql;
	}

	/**
	 * 分页查询
	 */
	public Page<CommonFlush> queryTrend(final Page page, int isTD,
			int timeLevel, String stime, String etime, String apnName) {
		String sql = buildTrendSql(isTD, timeLevel, stime, etime, apnName);
		sql = sql.substring(0, sql.indexOf("order")) + " order by "
				+ buildOrder(page, timeLevel);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setFirstResult(page.getFirst());
		query.setMaxResults(page.getPageSize());
		List list = query.list();
		Page newPage = new Page();
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
	 * 分页查询
	 */
	public List<CommonFlush> queryAllTrend(int isTD, int timeLevel,
			String stime, String etime, String apnName) {
		String sql = buildTrendSql(isTD, timeLevel, stime, etime, apnName);
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
				commonFlush.setActiveCount(Common.StringToLong(objs[4] + ""));// 激活次数
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[5] + ""));
				if (timeLevel == 1) {
					commonFlush.setFullDate((objs[6] + "").substring(0, 16));
					commonFlush.setCompany(objs[7] + "");// 集团APN中文名
				} else if (timeLevel == 2) {
					commonFlush.setFullDate((objs[6] + "").split(" ")[0]);
					commonFlush.setCompany(objs[7] + "");// 集团APN中文名
				} else if (timeLevel == 4) {
					commonFlush.setFullDate((objs[6] + "").substring(0, 7));
					commonFlush.setCompany(objs[7] + "");// 集团APN中文名
				} else {
					commonFlush.setIntYear(Common.StringToInt(objs[6] + ""));
					commonFlush.setIntWeek(Common.StringToInt(objs[7] + ""));
					commonFlush.setCompany(objs[8] + "");// 集团APN中文名
					commonFlush.setSpanDate(timeLevel);
				}

				commonFlush.calculateData();
				// commonFlush.numberFormatData();
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
	}

	public String buildTrendSql(int isTD, int timeLevel, String sTime,
			String eTime, String apnName) {
		String sql = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmFlush ";

		switch (timeLevel) {
		case 1:
			sql = "select  nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,nmAveFlush,"
					+ " convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00')) as ddate,vcApnName "
					+ " from vStatHourApn  "
					+ " where 1=1 "
					+ " and  ddate >= "
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  ddate <= "
					+ MantoEyeUtils.formatData(eTime, timeLevel)
					+ " and vcApnDomain = '" + apnName + "'";
			sortSql = sortSql + " order by ddate asc";
			break;
		case 2:
			sql = "select nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,nmAveFlush,"
					+ " convert(datetime,string(intYear,'-',intMonth,'-',intDay)) as ddate,vcApnName "
					+ " from vStatDayApn  "
					+ " where 1=1 "
					+ " and  ddate >= "
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  ddate <= "
					+ MantoEyeUtils.formatData(eTime, timeLevel)
					+ " and vcApnDomain = '" + apnName + "'";
			sortSql = sortSql + " order by ddate asc";
			break;
		case 3:
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
			sql = "select nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,nmAveFlush,intYear,intWeek,vcApnName"
					+ " from vStatWeekApn  " + " where 1=1 ";
			if (sYear != eYear) {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek < 100 )" + " or ( intYear = "
						+ eYear + " and intWeek>0 and intWeek <= " + eWeek
						+ " ))  " + bSql;
			} else {
				sql = sql + " and  (( intYear = " + sYear + " and intWeek>="
						+ sWeek + " and intWeek <= " + eWeek + " ))  " + bSql;
			}
			sql = sql + " and vcApnDomain = '" + apnName + "'";
			sortSql = sortSql + " order by intYear asc,intWeek asc";
			break;
		case 4:
			sql = "select nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,nmAveFlush,"
					+ " convert(datetime,string(intYear,'-',intMonth)) as ddate,vcApnName "
					+ " from vStatMonthApn  "
					+ " where 1=1 "
					+ " and  ddate >= "
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  ddate <= "
					+ MantoEyeUtils.formatData(eTime, timeLevel)
					+ " and vcApnDomain = '" + apnName + "'";
			sortSql = sortSql + " order by ddate asc";
			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		sql = sql + " and intApnTypeId =3 ";
		sql = sql + sortSql;
		return sql;
	}
}
