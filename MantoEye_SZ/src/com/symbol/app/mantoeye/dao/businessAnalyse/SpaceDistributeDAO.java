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
public class SpaceDistributeDAO extends HibernateQueryDAO {

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
			int timeLevel, String time) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, timeLevel, year, month,
				week, day, hour, page);
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

	public List<CommonFlush> listData(int isTD, int areaType, int timeLevel,
			String time) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		String sql = this.buildSql(isTD, areaType, timeLevel, year, month,
				week, day, hour, null);
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
		List<CommonFlush> commonFlushList = new ArrayList<CommonFlush>();
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			commonFlushList = new ArrayList<CommonFlush>();
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				commonFlush.setBusiness(objs[0] + "");// 业务
				commonFlush.setBusinessName(objs[1] + "");// 业务名称
				commonFlush.setIntUpFlush(Common.StringToLong(objs[2] + ""));// 上流量
				commonFlush.setIntDownFlush(Common.StringToLong(objs[3] + ""));// 下流量
				commonFlush.setTotalFlush(Common.StringToLong(objs[4] + ""));// 总流量
				commonFlush.setIntImsis(Common.StringToLong(objs[5] + ""));// 用户数
				// 访问次数
				if (objs[6] == null) {
					commonFlush.setVisit(0L);
				} else {
					commonFlush.setVisit(Common.StringToLong(objs[6] + ""));
				}
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[7] + ""));
				if (timeLevel == 3) {
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(time));
				} else {
					commonFlush.setFullDate(time);
				}
				switch (timeLevel) {
				case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
					commonFlush.setLocaltotalFlush(Common.StringToLong(objs[12] + ""));
					commonFlush.setLocalintImsis(Common.StringToLong(objs[13] + ""));
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
					commonFlush.setLocaltotalFlush(Common.StringToLong(objs[11] + ""));
					commonFlush.setLocalintImsis(Common.StringToLong(objs[12] + ""));
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
					commonFlush.setLocaltotalFlush(Common.StringToLong(objs[11] + ""));
					commonFlush.setLocalintImsis(Common.StringToLong(objs[12] + ""));
					break;
				case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
					commonFlush.setLocaltotalFlush(Common.StringToLong(objs[10] + ""));
					commonFlush.setLocalintImsis(Common.StringToLong(objs[11] + ""));
					break;
				}
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
	public String buildSql(int isTD, int areaType, int timeLevel, int year,
			int month, int week, int day, int hour, Page page) {
		String sql = "";
		String sortSql = "";
		String fields = "";
		String table = "";
		String sortType = " desc ";
		String sortColumn = " nmFlush ";
		if (page != null) {
			sortType = " " + page.getOrder() + " ";
			sortColumn = " " + page.getOrderBy() + " ";
		}
		// 维度类型
		switch (areaType) {
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BSC:
			fields = " intBscId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourBscGprsSpace"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayBscGprsSpace"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekBscGprsSpace"
									: "vStatMonthBscGprsSpace";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN:
			fields = " intSgsnId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourSgsnGprsSpace"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySgsnGprsSpace"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSgsnGprsSpace"
									: "vStatMonthSgsnGprsSpace";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_STREET:
			fields = " intStreetId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourStreetGprsSpace"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayStreetGprsSpace"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekStreetGprsSpace"
									: "vStatMonthStreetGprsSpace";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA:
			fields = " intSaleAreaId,vcSaleAreaName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourSaleAreaGprsSpace"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySaleAreaGprsSpace"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSaleAreaGprsSpace"
									: "vStatMonthSaleAreaGprsSpace";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BRANCH:
			fields = " intBranchId,vcBranchName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourCompanyGprsSpace"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayCompanyGprsSpace"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekCompanyGprsSpace"
									: "vStatMonthCompanyGprsSpace";
			break;
		}
		sql = " select " + fields;

		// 时间粒度
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intMonth,intDay,intHour"
					+ ",nmLocalFlush,nmLocalUsers"	//V3
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day
					+ " and intHour=" + hour;
			sortSql = sortSql + " order by intYear,intMonth,intDay,intHour, "
					+ sortColumn + sortType;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intMonth,intDay"
					+ ",nmLocalFlush,nmLocalUsers"	//V3
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day;
			sortSql = sortSql + " order by intYear,intMonth,intDay, "
					+ sortColumn + sortType;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intYear,intWeek"
					+ ",nmLocalFlush,nmLocalUsers"	//V3
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					// + " and intMonth=" + month
					+ " and intWeek=" + week;
			sortSql = sortSql + " order by intYear,intYear,intWeek, "
					+ sortColumn + sortType;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmVisitCounts,nmAveFlush,intYear,intMonth"
					+ ",nmLocalFlush,nmLocalUsers"	//V3
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month;
			sortSql = sortSql + " order by intYear,intMonth, " + sortColumn
					+ sortType;
			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		sql = sql + sortSql;
		return sql;
	}

}
