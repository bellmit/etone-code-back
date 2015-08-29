package com.symbol.app.mantoeye.dao.businessAnalyse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
public class ApnDistributeDAO extends HibernateDao {
	Map<Integer, String> mapApn = new HashMap<Integer, String>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public void initMap() {
		mapApn.put(4, "vStatMonthApnType");
		mapApn.put(3, "vStatWeekApnType");
		mapApn.put(2, "vStatDayApnType");
		mapApn.put(1, "vStatHourApnType");
	}

	// 查询该时间的全网总流量和总用户数
	private Double[] findSumTotalData(int timeLevel, String date, int dataType) {
		String totalSql = MantoEyeUtils.getTotalFlushAndImsiSql(dataType,
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

	public List<CommonFlush> queryApnDistribute(int timeLevel, String date,
			int dataType) {
		// 计算占比
		Double[] dbs = findSumTotalData(timeLevel, date, dataType);

		String strDate = CommonUtils.changeDate(date, timeLevel);
		StringBuffer sql = new StringBuffer();
		String[] str = strDate.split(";");
		int year = Common.StringToInt(str[0]);
		int month = Common.StringToInt(str[1]);
		int week = Common.StringToInt(str[2]);
		int day = Common.StringToInt(str[3]);
		int hour = Common.StringToInt(str[4]);
		sql
				.append("select vcApnTypeName,nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,nmAveFlush from ");
		sql.append(mapApn.get(timeLevel));
		sql.append(" where 1=1 ");
		sql.append(" and intRaitype=");
		sql.append(dataType);
		if (timeLevel == 1) {
			sql.append(" and intYear=");
			sql.append(year);
			sql.append(" and intMonth=");
			sql.append(month);
			sql.append(" and intDay=");
			sql.append(day);
			sql.append(" and intHour=");
			sql.append(hour);
		} else if (timeLevel == 2) {
			sql.append(" and intYear=");
			sql.append(year);
			sql.append(" and intMonth=");
			sql.append(month);
			sql.append(" and intDay=");
			sql.append(day);
		} else if (timeLevel == 3) {
			sql.append(" and intYear=");
			sql.append(year);
			// sql.append(" and intMonth=");
			// sql.append(month);
			sql.append(" and intWeek=");
			sql.append(week);
		} else if (timeLevel == 4) {
			sql.append(" and intYear=");
			sql.append(year);
			sql.append(" and intMonth=");
			sql.append(month);
		}
		sql.append(" order by (nmAppUpFlush+nmAppDownFlush) desc ");
		CommonFlush commonFlush = null;
		List<CommonFlush> list = new ArrayList<CommonFlush>();

		Iterator it = this.getSession().createSQLQuery(sql.toString()).list()
				.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			commonFlush = new CommonFlush();
			commonFlush.setApnName(obj[0] + "");
			commonFlush.setIntUpFlush(Common.StringToLong(obj[1] + ""));
			commonFlush.setIntDownFlush(Common.StringToLong(obj[2] + ""));
			commonFlush.setTotalFlush(Common.StringToLong(obj[3] + ""));
			commonFlush.setIntImsis(Common.StringToLong(obj[4] + ""));
			commonFlush.setActiveCount(Common.StringToLong(obj[5] + ""));
			commonFlush.setNmAveFlush(Common.StringTodouble(obj[6] + ""));
			commonFlush.calculateData();
			commonFlush.calculateFlushRate(dbs[0], dbs[1]);
			// commonFlush.numberFormatData();
			if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
				commonFlush.setFullDate(CommonUtils.getDayInnerWeek(date));

			} else {
				commonFlush.setFullDate(date);
			}
			list.add(commonFlush);
		}
		return list;
	}

	/**
	 * 分页查询APN空间分布
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
			int timeLevel, String time, String business) {
		String strDate = this.changeDate(time, timeLevel);
		String[] str = strDate.split(";");
		int year = Common.StringToInt(str[0]);
		int month = Common.StringToInt(str[1]);
		int week = Common.StringToInt(str[2]);
		int day = Common.StringToInt(str[3]);
		int hour = Common.StringToInt(str[4]);

		String sql = this.buildSql(page, isTD, areaType, timeLevel, year,
				month, week, day, hour, business);
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
		newPage.setPageNo(page.getPageNo());
		if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
			time = CommonUtils.getDayInnerWeek(time);
		}
		newPage.setResult(buildBeanList(list, time));

		return newPage;
	}

	public List<CommonFlush> listData(final Page page, int isTD, int areaType,
			int timeLevel, String time, String business) {
		String strDate = this.changeDate(time, timeLevel);
		String[] str = strDate.split(";");
		int year = Common.StringToInt(str[0]);
		int month = Common.StringToInt(str[1]);
		int week = Common.StringToInt(str[2]);
		int day = Common.StringToInt(str[3]);
		int hour = Common.StringToInt(str[4]);
		String sql = this.buildSql(page, isTD, areaType, timeLevel, year,
				month, week, day, hour, business);
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		if (timeLevel == 3) {// 如果时间粒度是周 获取该周内的时间
			time = CommonUtils.getDayInnerWeek(time);
		}
		return buildBeanList(list, time);
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
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param timeLevel
	 * @return
	 */
	public List<CommonFlush> buildBeanList(List list, String time) {
		List<CommonFlush> commonFlushList = null;
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
				commonFlush.setVisit(Common.StringToLong(objs[6] + ""));//
				commonFlush.setNmAveFlush(Common.StringTodouble(objs[7] + ""));//
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
	public String buildSql(Page page, int isTD, int areaType, int timeLevel,
			int year, int month, int week, int day, int hour, String anpName) {
		String sql = "";
		String fields = "";
		String table = "";
		// 维度类型
		switch (areaType) {
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BSC:
			fields = " intBscId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnTypeBsc"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnTypeBsc"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnTypeBsc"
									: "vStatMonthApnTypeBsc";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SGSN:
			fields = " intSgsnId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnTypeSgsn"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnTypeSgsn"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnTypeSgsn"
									: "vStatMonthApnTypeSgsn";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_STREET:
			fields = " intStreetId,vcName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnTypeStreet"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnTypeStreet"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnTypeStreet"
									: "vStatMonthApnTypeStreet";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_SALEAREA:
			fields = " intSaleAreaId,vcSaleAreaName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnTypeSaleArea"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnTypeSaleArea"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnTypeSaleArea"
									: "vStatMonthApnTypeSaleArea";
			break;
		case CommonConstants.MANTOEYE_SPACE_LEVEL_BRANCH:
			fields = " intBranchId,vcBranchName, ";
			table = (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_HOUR) ? "vStatHourApnTypeCompany"
					: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_DAY) ? "vStatDayApnTypeCompany"
							: (timeLevel == CommonConstants.MANTOEYE_TIME_LEVEL_WEEK) ? "vStatWeekApnTypeCompany"
									: "vStatMonthApnTypeCompany";
			break;
		}
		sql = " select " + fields;

		// 时间粒度
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,nmAveFlush,intYear,intMonth,intDay,intHour"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day
					+ " and intHour=" + hour;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,nmAveFlush,intYear,intMonth,intDay"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,nmAveFlush,intYear,intWeek"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intWeek=" + week;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql = sql
					+ " nmAppUpFlush,nmAppDownFlush,nmFlush,nmUsers,nmActiveCounts,nmAveFlush,intYear,intMonth"
					+ " from " + table + " where 1=1 " + " and intYear=" + year
					+ " and intMonth=" + month;
			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		sql = sql + " and vcApnTypeName = '" + anpName + "'";
		sql = sql + " order by " + page.getOrderBy() + " " + page.getOrder();
		return sql;
	}
}
