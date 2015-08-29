package com.symbol.app.mantoeye.dao.blackberry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class BlackBerryAreaYysDAO extends HibernateQueryDAO {

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
			int timeLevel, String time, String yysName) {
		String dateStr = this.changeDate(time, timeLevel);
		String[] dateValue = dateStr.split(";");
		int year = Common.StringToInt(dateValue[0] + "");
		int month = Common.StringToInt(dateValue[1] + "");
		int week = Common.StringToInt(dateValue[2] + "");
		int day = Common.StringToInt(dateValue[3] + "");
		int hour = Common.StringToInt(dateValue[4] + "");

		String sql = this.buildSql(page, isTD, areaType, timeLevel, year,
				month, week, day, hour, yysName);
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
			int timeLevel, String time, String yysName) {
		String dateStr = this.changeDate(time, timeLevel);
		String[] str = dateStr.split(";");
		int year = Common.StringToInt(str[0]);
		int month = Common.StringToInt(str[1]);
		int week = Common.StringToInt(str[2]);
		int day = Common.StringToInt(str[3]);
		int hour = Common.StringToInt(str[4]);
		String sql = this.buildSql(page, isTD, areaType, timeLevel, year,
				month, week, day, hour, yysName);
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
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[6] + ""));//
				// 访问次数
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
			String yysName) {
		String sql = "";
		String fields = "";
		String table = "";
		// 维度类型
		switch (areaType) {
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_BSC:
			fields = " intBscId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourBscLdcBbFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayBscLdcBbFlush"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekBscLdcBbFlush"
									: "vStatMonthBscLdcBbFlush";
			break;
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SGSN:
			fields = " intSgsnId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourSgsnLdcBbFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySgsnLdcBbFlush"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSgsnLdcBbFlush"
									: "vStatMonthSgsnLdcBbFlush";
			break;
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_STREET:
			fields = " intStreetId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourStreetLdcBbFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayStreetLdcBbFlush"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekStreetLdcBbFlush"
									: "vStatMonthStreetLdcBbFlush";
			break;
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_SALEAREA:
			fields = " intSaleAreaId,vcSaleAreaName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourSaleAreaLdcBbFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDaySaleAreaLdcBbFlush"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekSaleAreaLdcBbFlush"
									: "vStatMonthSaleAreaLdcBbFlush";
			break;
		case CommonConstants.MANTOEYE_APN_SPACE_LEVEL_BRANCH:
			fields = " intBranchId,vcBranchName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourBranchLdcBbFlush"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayBranchLdcBbFlush"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekBranchLdcBbFlush"
									: "vStatMonthBranchLdcBbFlush";
			break;
		}
		sql = " select " + fields;

		// 时间粒度
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			sql = sql
					+ " nmUpFlush,nmDownFlush,nmTotalFlush,nmUsers,nmAveFlush"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day
					+ " and intHour=" + hour;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			sql = sql
					+ " nmUpFlush,nmDownFlush,nmTotalFlush,nmUsers,nmAveFlush"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			sql = sql
					+ " nmUpFlush,nmDownFlush,nmTotalFlush,nmUsers,nmAveFlush"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					// + " and intMonth=" + month
					+ " and intWeek=" + week

			;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql = sql
					+ " nmUpFlush,nmDownFlush,nmTotalFlush,nmUsers,nmAveFlush"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month;
			break;
		}

		sql = sql + " and vcIdc='" + yysName + "'";
		String orderBy = " nmTotalFlush ";
		String order = " desc ";
		if (page.getOrderBy() != null) {
			orderBy = page.getOrderBy();
			order = page.getOrder();
		}
		sql = sql + " order by " + orderBy + " " + order;
		return sql;
	}

	public String changeDate(String date, int timeLevel) {
		Date d = null;
		if (timeLevel == 1) {

		} else if (timeLevel == 2) {
			date = date + " 01:00";
		} else if (timeLevel == 3) {
			date = date + " 01:00";
		} else if (timeLevel == 4) {
			date = date + "-01" + " 00:00";
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
	 * 查询所有营运商名称
	 * 
	 * @return
	 */
	public String getAllYysName() {
		String sql = "select vcIdc from dtbIdc order by vcIdc desc";
		List list = this.getSession().createSQLQuery(sql).list();
		String allYysName = "";
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				if (i == list.size() - 1) {
					allYysName = allYysName + list.get(i);
				} else {
					allYysName = allYysName + list.get(i) + ":";
				}
			}
		}
		return allYysName;
	}
}
