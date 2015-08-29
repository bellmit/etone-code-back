package com.symbol.app.mantoeye.dao.mms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class MmsTimeLevelDAO extends HibernateQueryDAO {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param isTD
	 *            TD标识
	 * @param timeLevel
	 *            时间粒度 1:小时 2:天 3:周 4:月
	 * @return
	 */
	public Page<CommonFlush> query(final Page page, int isTD, int timeLevel,
			String sTime, String eTime) {
		String sql = this.buildSql(false, isTD, timeLevel, sTime, eTime, page);
		SQLQuery query = this.getSession().createSQLQuery(sql);
//		List pageList = query.list();
//		query.setFirstResult(page.getFirst());
//		query.setMaxResults(page.getPageSize());
		String sqls = sql.split("order by")[0];
		String totalSql_ = "select count(1) from ( " + sqls + " ) as totalCount";
		Object totalObj_ = this.getSession().createSQLQuery(totalSql_)
				.uniqueResult();
		Integer total = Integer.parseInt(totalObj_.toString());
		List<Object[]> list = this.getSession().createSQLQuery(sql)
				.setFirstResult(page.getFirst()).setMaxResults(
						page.getPageSize()).list();
		Page newPage = new Page();
		newPage.setPageSize(page.getPageSize());
		newPage.setTotalCount(total);

		// 查询总发送量
		String totalSql = this.buildSql(true, isTD, timeLevel, sTime, eTime,
				null);
		SQLQuery totalQuery = this.getSession().createSQLQuery(totalSql);
		List totalList = totalQuery.list();
		if (totalList.get(0) != null) {
			Object totalObj = totalList.get(0);
			newPage.setResult(buildBeanList(true, totalObj, list, timeLevel,
					sTime, eTime));
		} else {
			newPage.setResult(null);
		}
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	/**
	 * 查询所有
	 */
	public List<CommonFlush> listData(int isTD, int timeLevel, String sTime,
			String eTime) {
		String sql = this.buildSql(false, isTD, timeLevel, sTime, eTime, null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();

		// 查询总发送量
		String totalSql = this.buildSql(true, isTD, timeLevel, sTime, eTime,
				null);
		SQLQuery totalQuery = this.getSession().createSQLQuery(totalSql);
		List totalList = totalQuery.list();
		if (totalList.get(0) != null) {
			Object totalObj = totalList.get(0);
			return buildBeanList(true, totalObj, list, timeLevel, sTime, eTime);
		} else {
			return new ArrayList<CommonFlush>();
		}

	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonFlush> buildBeanList(boolean flag, Object totalObj,
			List list, int timeLevel, String startTime, String endTime) {
		List<CommonFlush> commonFlushList = null;
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			commonFlushList = new ArrayList<CommonFlush>();
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setUpSendFlush(Common.StringToLong(objs[0]+""));// 上行发送量
				commonFlush
						.setDownSendFlush(Common.StringToLong(objs[1]+""));// 下行发送量
				commonFlush.setTotalSendFlush(Long
						.parseLong(objs[2]+""));// 总发送量
				if (flag) {// 需要设置占比情况
					commonFlush.setUpRate(CommonUtils.formatPercent(Long
							.parseLong(objs[0]+""), Double
							.parseDouble(totalObj + "")));// 上发送量占比
					commonFlush.setDownRate(CommonUtils.formatPercent(Long
							.parseLong(objs[1]+""), Double
							.parseDouble(totalObj + "")));// 下发送量占比
					commonFlush.setFlushRate(CommonUtils.formatPercent(Long
							.parseLong(objs[2]+""), Double
							.parseDouble(totalObj + "")));// 总占比
				}

				commonFlush.setIntYear(Common.StringToInt(objs[3]+""));// 年
				commonFlush.setIntMonth(Common.StringToInt(objs[4]+""));// 月
				switch (timeLevel) {
				case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
					commonFlush.setIntDay(Common.StringToInt(objs[5]+""));// 日
					commonFlush
							.setIntHour(Common.StringToInt(objs[6]+""));// 小时
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
					commonFlush.setIntDay(Common.StringToInt(objs[5]+""));// 日
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
					commonFlush
							.setIntWeek(Common.StringToInt(objs[5]+""));// 周
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
					break;
				}
				commonFlush.setSpanDate(timeLevel);
				//commonFlush.formatMmsData();
				commonFlushList.add(commonFlush);
			}
		}
		return commonFlushList;
	}

	/**
	 * 组装查询语句
	 * 
	 * @param flag
	 *            是否统计总发送量
	 * @param isTD
	 * @param timeLevel
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String buildSql(boolean flag, int isTD, int timeLevel, String sTime,
			String eTime, Page page) {
		String sql = "select sum(nmTotalCounts) ";
		String sortSql = "";
		String defaultSortSql = "";
		String defaultSortType = " asc ";
		String sortType = " desc ";
		String sortColumn = " nmTotalCounts ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		switch (timeLevel) {
		case 1:
			if (!flag) {
				sql = "select nmUpCounts,nmDownCounts,nmTotalCounts,intYear,intMonth,intDay,intHour,string(intYear,intMonth,intDay,inthour) as times";
			}
			sql = sql
					+ " from vStatHourWapFlush  "
					+ " where 1=1 "
					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00'))>="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00'))<="
					+ MantoEyeUtils.formatData(eTime, timeLevel);
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth " + defaultSortType + ",intDay"
					+ defaultSortType + ",intHour " + defaultSortType;
			break;
		case 2:
			if (!flag) {
				sql = "select nmUpCounts,nmDownCounts,nmTotalCounts,intYear,intMonth,intDay";
			}
			sql = sql
					+ " from vStatDayWapFlush  "
					+ " where 1=1 "
					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay))>="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay))<="
					+ MantoEyeUtils.formatData(eTime, timeLevel);
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth " + defaultSortType + ",intDay "
					+ defaultSortType;
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
			if (!flag) {
				sql = "select nmUpCounts,nmDownCounts,nmTotalCounts,intYear,intYear,intWeek";
			}
			if (sYear != eYear) {
				sql = sql + " from vStatWeekWapFlush  " + " where 1=1 "
				+ " and  (( intYear = " + sYear + " and intWeek>=" + sWeek+" )"
						 + " or ( intYear = " + eYear
				+ " and intWeek <= " + eWeek + " ))  " + bSql;				
			} else {
				sql = sql + " from vStatWeekWapFlush  " + " where 1=1 "
				+ " and  ( intYear = " + sYear + " and intWeek>=" + sWeek+" )"
						 + " and ( intYear = " + eYear
				+ " and intWeek <= " + eWeek + " )  " + bSql;
			}
			
			defaultSortSql = " order by intYear " + defaultSortType
					+",intWeek  "
					+ defaultSortType;
			break;
		case 4:
			if (!flag) {
				sql = "select nmUpCounts,nmDownCounts,nmTotalCounts,intYear,intMonth";
			}
			sql = sql + " from vStatMonthWapFlush  " + " where 1=1 "
					+ " and  convert(datetime,string(intYear,'-',intMonth))>="
					+ MantoEyeUtils.formatData(sTime, timeLevel)
					+ " and  convert(datetime,string(intYear,'-',intMonth))<="
					+ MantoEyeUtils.formatData(eTime, timeLevel);
			defaultSortSql = " order by intYear " + defaultSortType
					+ ",intMonth  " + defaultSortType;
			break;
		}
		sql = sql + " and intRaitype= " + isTD;
		if (!flag) {
			sortSql = sortSql + " order by  " + sortColumn + sortType;
		}
		if (!flag) {
			if (page != null) {
				if ("intYear".equals(page.getOrderBy())) {
					sql = sql + defaultSortSql;
				} else if ("fullDate".equals(page.getOrderBy())) {// 按时间顺序查询
					defaultSortSql = defaultSortSql.replaceAll(defaultSortType,
							sortType);
					sql = sql + defaultSortSql;
				} else {
					sql = sql + sortSql;
				}
			} else {
				sql = sql + defaultSortSql;
			}
		}
		return sql;
	}
}
