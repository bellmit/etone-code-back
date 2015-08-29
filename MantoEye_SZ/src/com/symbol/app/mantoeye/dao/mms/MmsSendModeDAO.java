package com.symbol.app.mantoeye.dao.mms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.symbol.app.mantoeye.dao.common.CommonQueryDAO;
import com.symbol.app.mantoeye.dto.flush.CommonFlush;
import com.symbol.app.mantoeye.util.CommonUtils;
import com.symbol.app.mantoeye.util.MantoEyeUtils;
import com.symbol.wp.core.util.Common;

@Repository
public class MmsSendModeDAO extends CommonQueryDAO {
	/**
	 * 查询所有
	 */
	public List<CommonFlush> listData(int isTD, int timeLevel, String time) {
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);
		// 查询总发送量
		String totalSql = this.buildSql(true, isTD, timeLevel, year, month,
				week, day, hour);
		SQLQuery totalQuery = this.getSession().createSQLQuery(totalSql);
		List totalList = totalQuery.list();
		if (totalList.get(0) != null) {
			// 查询数据
			String sql = this.buildSql(false, isTD, timeLevel, year, month,
					week, day, hour);
			SQLQuery query = this.getSession().createSQLQuery(sql);
			List list = query.list();
			return buildBeanList(totalQuery.list().get(0), list, time,
					timeLevel);
		} else {
			return null;
		}
	}

	/**
	 * 组装BEAN实体
	 * 
	 * @param list
	 * @param time
	 * @return
	 */
	public List<CommonFlush> buildBeanList(Object totalObj, List list,
			String time, int timeLevel) {
		List<CommonFlush> commonFlushList = null;
		Double totalCounts = Double.parseDouble(totalObj.toString());
		CommonFlush commonFlush = null;
		if (list != null && !list.isEmpty()) {
			commonFlushList = new ArrayList<CommonFlush>();
			for (int i = 0; i < list.size(); i++) {
				commonFlush = new CommonFlush();
				Object[] objs = (Object[]) list.get(i);
				int id = Common.StringToInt(objs[0]+"");
				String businessName = (id == 1) ? "sp彩信" : (id == 2 ? "点对点彩信"
						: "其它");
				commonFlush.setBusinessName(businessName);// 发送方式
				commonFlush.setUpSendFlush(Common.StringToLong(objs[1]+""));// 上行发送量
				commonFlush
						.setDownSendFlush(Common.StringToLong(objs[2]+""));// 下行发送量
				commonFlush.setTotalSendFlush(Long
						.parseLong(objs[3]+""));// 总发送量
				commonFlush.setFlushRate(CommonUtils.formatPercent(Double
						.parseDouble(objs[3]+""), totalCounts));// 发送量占比
				if (timeLevel == 3) {
					commonFlush.setFullDate(CommonUtils.getDayInnerWeek(time));
				} else {
					commonFlush.setFullDate(time);
				}
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
	 * @param time
	 * @return
	 */
	public String buildSql(boolean flag, int isTD, int timeLevel, int year,
			int month, int week, int day, int hour) {
		String sql = "select sum(nmTotalCounts) ";
		switch (timeLevel) {
		case 1:
			if (!flag) {
				sql = "select intMmsFromNoId, nmUpCounts,nmDownCounts,nmTotalCounts,intYear,intMonth,intDay,intHour";
			}
			sql = sql + " from  vStatHourWapMode where 1=1 " + " and intYear="
					+ year + " and intMonth=" + month + " and intDay=" + day
					+ " and intHour=" + hour;
			break;
		case 2:
			if (!flag) {
				sql = "select intMmsFromNoId, nmUpCounts,nmDownCounts,nmTotalCounts,intYear,intMonth,intDay";
			}
			sql = sql + " from vStatDayWapMode where 1=1 " + " and intYear="
					+ year + " and intMonth=" + month + " and intDay=" + day;
			break;
		case 3:
			if (!flag) {
				sql = "select intMmsFromNoId, nmUpCounts,nmDownCounts,nmTotalCounts,intYear,intYear,intWeek";
			}
			sql = sql + " from vStatWeekWapMode where 1=1 " + " and intYear="
					+ year 
//					+ " and intMonth=" + month 
					+ " and intWeek=" + week;
			break;
		case 4:
			if (!flag) {
				sql = "select intMmsFromNoId, nmUpCounts,nmDownCounts,nmTotalCounts,intYear,intMonth";
			}
			sql = sql + " from vStatMonthWapMode where 1=1 " + " and intYear="
					+ year + " and intMonth=" + month;
			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		if (!flag) {
		sql = sql + " order by nmTotalCounts desc ";
		}
		return sql;
	}

	/**
	 * 构建查询全网流量SQL语句
	 * 
	 * @param flag
	 *            是否跨度标识
	 * @param isTD
	 * @param timeLevel
	 *            时间粒度
	 * @param sTime
	 * @param eTime
	 * @return
	 */
	public String buildTotalSql(boolean flag, int isTD, int timeLevel,
			String sTime, String eTime) {
		// 开始时间
		Date date = CommonUtils.getDate(sTime);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);

		String sql = "select sum(nmTotalCounts) ";
		switch (timeLevel) {
		case 1:
			if (flag) {
				sql = sql
						+ " from  vStatHourWapMode where 1=1 "
						+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00'))>="
						+ MantoEyeUtils.formatData(sTime, timeLevel)
						+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay,' ',inthour,':00:00'))<="
						+ MantoEyeUtils.formatData(eTime, timeLevel);
			} else {
				sql = sql + " from  vStatHourWapMode where 1=1 "
						+ " and intYear=" + year + " and intMonth=" + month
						+ " and intDay=" + day + " and intHour=" + hour;
			}

			break;
		case 2:
			if (flag) {
				sql = sql
						+ " from vStatDayWapMode where 1=1 "
						+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay))>="
						+ MantoEyeUtils.formatData(sTime, timeLevel)
						+ " and  convert(datetime,string(intYear,'-',intMonth,'-',intDay))<="
						+ MantoEyeUtils.formatData(eTime, timeLevel);
			} else {
				sql = sql + " from vStatDayWapMode where 1=1 "
						+ " and intYear=" + year + " and intMonth=" + month
						+ " and intDay=" + day;
			}

			break;
		case 3:
			if (flag) {
				sql = sql
						+ " from vStatWeekWapMode where 1=1 "
						+ " and  convert(varchar(20),string(intYear,'-',intWeek))>="
						+ MantoEyeUtils.formatData(sTime, timeLevel)
						+ " and  convert(varchar(20),string(intYear,'-',intWeek))<="
						+ MantoEyeUtils.formatData(eTime, timeLevel);
			} else {
				sql = sql + " from vStatWeekWapMode where 1=1 "
						+ " and intYear=" + year 
						+ " and intWeek=" + week;
			}

			break;
		case 4:
			if (flag) {
				sql = sql
						+ " from vStatMonthWapMode where 1=1 "
						+ " and  convert(datetime,string(intYear,'-',intMonth))>="
						+ MantoEyeUtils.formatData(sTime, timeLevel)
						+ " and  convert(datetime,string(intYear,'-',intMonth))<="
						+ MantoEyeUtils.formatData(eTime, timeLevel);
			} else {
				sql = sql + " from vStatMonthWapMode where 1=1 "
						+ " and intYear=" + year + " and intMonth=" + month;
			}

			break;
		}
		sql = sql + " and intRaitype = " + isTD;
		return sql;
	}
}
