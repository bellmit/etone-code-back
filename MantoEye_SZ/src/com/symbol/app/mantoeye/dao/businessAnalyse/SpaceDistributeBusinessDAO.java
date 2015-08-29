package com.symbol.app.mantoeye.dao.businessAnalyse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dao.common.HibernateQueryDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.wp.core.util.Common;
import com.symbol.wp.modules.orm.Page;

@Repository
public class SpaceDistributeBusinessDAO extends HibernateQueryDAO {

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
			String areaName, int timeLevel, String time) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, areaName, timeLevel, year,
				month, week, day, hour, page);
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
		newPage.setResult(buildBeanList(list, time, timeLevel));
		newPage.setPageNo(page.getPageNo());
		return newPage;
	}

	public List<CommonFlush> listData(int isTD, int areaType, String areaName,
			int timeLevel, String time) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, areaName, timeLevel, year,
				month, week, day, hour, null);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		return buildBeanList(list, time, timeLevel);
	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonFlush> buildBeanList(List list, String time, int timeLevel) {
		List<CommonFlush> commonFlushList = null;
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			commonFlushList = new ArrayList<CommonFlush>();
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setArea(objs[0] + "");// 区域
				commonFlush.setBusinessName(objs[1] + "");// 业务名称
				commonFlush.setIntUpFlush(Common.StringToLong(objs[2] + ""));// 上流量
				commonFlush.setIntDownFlush(Common.StringToLong(objs[3] + ""));// 下流量
				commonFlush.setTotalFlush(Common.StringToLong(objs[4] + ""));// 总流量
				commonFlush.setIntImsis(Common.StringToLong(objs[5] + ""));// 用户数
				commonFlush.setVisit(Common.StringToLong(objs[6] + ""));// 访问次数
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[7] + ""));
				if (timeLevel == 3) {
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(time));
				} else {
					commonFlush.setFullDate(time);
				}
//				switch (timeLevel) {
//				case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
//					commonFlush.setLocaltotalFlush(Common.StringToLong(objs[12] + ""));
//					commonFlush.setLocalintImsis(Common.StringToLong(objs[13] + ""));
//					break;
//				case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
//					commonFlush.setLocaltotalFlush(Common.StringToLong(objs[11] + ""));
//					commonFlush.setLocalintImsis(Common.StringToLong(objs[12] + ""));
//					break;
//				case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
//					commonFlush.setLocaltotalFlush(Common.StringToLong(objs[10] + ""));
//					commonFlush.setLocalintImsis(Common.StringToLong(objs[11] + ""));
//					break;
//				case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
//					commonFlush.setLocaltotalFlush(Common.StringToLong(objs[10] + ""));
//					commonFlush.setLocalintImsis(Common.StringToLong(objs[11] + ""));
//					break;
//				}
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
	 */
	public String buildSql(int isTD, int areaType, String areaName,
			int timeLevel, int year, int month, int week, int day, int hour,
			Page page) {
		String sql = "";
		String fields = "";
		String table = "";
		String name = "";
		String sortSql = "";
		String sortType = " desc ";
		String sortColumn = " nmFlush ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		// 维度类型
		switch (areaType) {
		case 1:
			fields = " vcName,vcBussinessName, ";
			table = (timeLevel == 1) ? "vStatHourBscBussType"
					: (timeLevel == 2) ? "vStatDayBscBussType"
							: (timeLevel == 3) ? "vStatWeekBscBussType"
									: "vStatMonthBscBussType";
			name = "vcName";
			break;
		case 2:
			fields = " vcName,vcBussinessName, ";
			table = (timeLevel == 1) ? "vStatHourSgsnBussType"
					: (timeLevel == 2) ? "vStatDaySgsnBussType"
							: (timeLevel == 3) ? "vStatWeekSgsnBussType"
									: "vStatMonthSgsnBussType";
			name = "vcName";
			break;
		case 3:
			fields = " vcName,vcBussinessName, ";
			table = (timeLevel == 1) ? "vStatHourStreetBussType"
					: (timeLevel == 2) ? "vStatDayStreetBussType"
							: (timeLevel == 3) ? "vStatWeekStreetBussType"
									: "vStatMonthStreetBussType";
			name = "vcName";
			break;
		case 4:
			fields = " vcSaleAreaName,vcBussinessName, ";
			table = (timeLevel == 1) ? "vStatHourSaleAreaBussType"
					: (timeLevel == 2) ? "vStatDaySaleAreaBussType"
							: (timeLevel == 3) ? "vStatWeekSaleAreaBussType"
									: "vStatMonthSaleAreaBussType";
			name = "vcSaleAreaName";
			break;
		case 5:
			fields = " vcBranchName,vcBussinessName, ";
			table = (timeLevel == 1) ? "vStatHourCompanyBussType"
					: (timeLevel == 2) ? "vStatDayCompanyBussType"
							: (timeLevel == 3) ? "vStatWeekCompanyBussType"
									: "vStatMonthCompanyBussType";
			name = "vcBranchName";
			break;
		}

		sql = " select " + fields;

		// 时间粒度
		switch (timeLevel) {
		case 1:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intMonth,intDay,intHour"
//					+ ",nmLocalFlush,nmLocalUsers"	//V3
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day
					+ " and intHour=" + hour;
			break;
		case 2:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intMonth,intDay"
//					+ ",nmLocalFlush,nmLocalUsers"	//V3
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day;
			break;
		case 3:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intWeek"
//					+ ",nmLocalFlush,nmLocalUsers"	//V3
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					// + " and intMonth=" + month

					+ " and intWeek=" + week;
			break;
		case 4:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intMonth"
//					+ ",nmLocalFlush,nmLocalUsers"	//V3
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month;
			break;
		}

		sql = sql + " and intRaitype = " + isTD;
		sql = sql + " and " + name + " = " + "'" + areaName + "'";
		sortSql = sortSql + "  order by " + sortColumn + sortType;
		sql = sql + sortSql;
		return sql;
	}

}
