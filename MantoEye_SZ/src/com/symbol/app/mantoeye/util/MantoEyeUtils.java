package com.symbol.app.mantoeye.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.symbol.app.mantoeye.constants.CommonConstants;
import com.symbol.app.mantoeye.dto.flush.TerminalBusinessEntity;

/**
 * MantoEye系统相关的公共类
 * 
 * @author rankin
 * 
 */
public class MantoEyeUtils {

	private final static Logger logger = LoggerFactory
			.getLogger(MantoEyeUtils.class);

	/**
	 * 组装查询时间格式
	 * 
	 */
	public static String formatData(String time, int timeLevel) {
		Date s = CommonUtils.getDate(time);
		String y = CommonUtils.getYear(s) + "";
		String m = CommonUtils.getMonth(s) + "";
		String w = CommonUtils.getWeek(s) + "";
		String d = CommonUtils.getDay(s) + "";
		String h = CommonUtils.getHour(s) + "";
		String str = "\'";
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			str = str + y + "-" + m + "-" + d + " " + h + ":00:00";
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			str = str + y + "-" + m + "-" + d;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			str = str + y + "-" + w;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			str = str + y + "-" + m;
			break;
		}
		str = str + "\'";
		return str;
	}

	/**
	 * 通过名称规则，设置相应的对应数据库的排序字段，如果字段没有规律或者不包含在下面，请具体的写
	 */
	public static String getSortField(String sort) {
		sort = sort.toLowerCase();
		if (sort.indexOf("fulldate") != -1) {
			return "fullDate";// 按时间顺序
		} else if (sort.indexOf("upflush") != -1
				|| sort.indexOf("uprate") != -1) {
			return "nmAppUpFlush";
		} else if (sort.indexOf("downflush") != -1
				|| sort.indexOf("downrate") != -1) {
			return "nmAppDownFlush";
		} else if (sort.indexOf("totalflush") != -1
				|| sort.indexOf("flushrate") != -1) {
			return "nmFlush";
		} else if (sort.indexOf("imsi") != -1) {
			return "nmUsers";
		} else if (sort.indexOf("aveflush") != -1) {
			return "nmAveFlush";
		} else if (sort.indexOf("visit") != -1) {
			return "nmVisitCounts";
		} else if (sort.indexOf("activecount") != -1) {
			return "nmActiveCounts";
		} else if (sort.indexOf("mmsupsend") != -1) {
			return "nmUpCounts";// 彩信上发送量
		} else if (sort.indexOf("mmsdownsend") != -1) {
			return "nmDownCounts";// 彩信下发送量
		} else if (sort.indexOf("mmstotalsend") != -1) {
			return "nmTotalCounts";// 彩信总发送量
		} else {
			return sort;
		}
	}

	/**
	 * 通过名称规则，设置相应的对应数据库的排序字段，如果字段没有规律或者不包含在下面，请具体的写
	 */
	public static String getMmsSortField(String sort) {
		sort = sort.toLowerCase();
		if (sort.indexOf("fulldate") != -1) {
			return "fullDate";// 按时间顺序
		} else if (sort.indexOf("upflush") != -1) {
			return "nmAppUpFlush";
		} else if (sort.indexOf("downflush") != -1) {
			return "nmAppDownFlush";
		} else if (sort.indexOf("totalflush") != -1) {
			return "nmFlush";
		} else if (sort.indexOf("imsi") != -1) {
			return "nmUsers";
		} else if (sort.indexOf("visit") != -1) {
			return "nmVisitCounts";
		} else if (sort.indexOf("activecount") != -1) {
			return "nmActiveCounts";
		} else if (sort.indexOf("mmsupsend") != -1
				|| sort.indexOf("uprate") != -1
				|| sort.indexOf("upcount") != -1) {
			return "nmUpCounts";// 彩信上发送量
		} else if (sort.indexOf("mmsdownsend") != -1
				|| sort.indexOf("downrate") != -1
				|| sort.indexOf("downcount") != -1) {
			return "nmDownCounts";// 彩信下发送量
		} else if (sort.indexOf("mmstotalsend") != -1
				|| sort.indexOf("flushrate") != -1
				|| sort.indexOf("totalcount") != -1) {
			return "nmTotalCounts";// 彩信总发送量
		} else if (sort.indexOf("upsendflush") != -1) {
			return "nmUpCounts";// 彩信上发送量
		} else if (sort.indexOf("downsendflush") != -1) {
			return "nmDownCounts";// 彩信下发送量
		} else if (sort.indexOf("totalsendflush") != -1) {
			return "nmTotalCounts";// 彩信总发送量
		} else {
			return sort;
		}
	}

	/**
	 * 通过名称规则，设置相应的对应数据库的排序字段，如果字段没有规律或者不包含在下面，请具体的写 只为大流量用户
	 */
	public static String getSortFieldForBigFlushUser(String sort) {
		if (sort.indexOf("upFlushMB") != -1 || sort.indexOf("UpFlushMB") != -1) {
			return "nmAppUpFlush";
		} else if (sort.indexOf("downFlushMB") != -1
				|| sort.indexOf("DownFlushMB") != -1) {
			return "nmAppDownFlush";
		} else if (sort.indexOf("totalFlushMB") != -1
				|| sort.indexOf("TotalFlushMB") != -1) {
			return "nmFlush";
		} else if (sort.indexOf("lastFlushMB") != -1
				|| sort.indexOf("LastFlushMB") != -1) {
			return "intLastFlush";
		} else if (sort.indexOf("preMonthMB") != -1
				|| sort.indexOf("PreMonthMB") != -1) {
			return "intLastMonthFlush";
		} else if (sort.indexOf("strLastTime") != -1
				|| sort.indexOf("StrLastTime") != -1) {
			return "dtLastTime";
		} else if (sort.indexOf("strFirstTime") != -1
				|| sort.indexOf("StrFirstTime") != -1) {
			return "dtFirstTime";
		} else if (sort.indexOf("overDay") != -1
				|| sort.indexOf("OverDay") != -1) {
			return "intOverDate";
		} else if (sort.indexOf("msisdn") != -1 || sort.indexOf("Msisdn") != -1) {
			return "nmMsisdn";
		} else {
			return sort;
		}
	}

	/**
	 * SP彩信排序字段
	 * 
	 * @param sort
	 * @return
	 */
	public static String getSpMmsSortField(String sort) {
		sort = sort.toLowerCase();
		if (sort.indexOf("port") != -1) {
			return "nmSpPort";// SP彩信端口
		} else if (sort.indexOf("totalsend") != -1
				|| sort.indexOf("flushrate") != -1) {
			return "nmCounts";// SP彩信总发送量
		} else if (sort.indexOf("title") != -1) {
			return "vcTitle";// SP彩信总发送量
		} else {
			return sort;
		}
	}

	/**
	 * 通过名称规则，设置相应的对应数据库的排序字段，如果字段没有规律或者不包含在下面，请具体的写
	 */
	public static String getSortFieldForBB(String sort) {
		if (sort.indexOf("StrUpFlush") != -1
				|| sort.indexOf("strUpFlush") != -1) {
			return "nmUpFlush";
		} else if (sort.indexOf("StrDownFlush") != -1
				|| sort.indexOf("strDownFlush") != -1) {
			return "nmDownFlush";
		} else if (sort.indexOf("TotalFlush") != -1
				|| sort.indexOf("totalFlush") != -1) {
			return "nmTotalFlush";
		} else if (sort.indexOf("Imsi") != -1 || sort.indexOf("imsi") != -1) {
			return "nmUsers";
		} else if (sort.indexOf("strnmAveFlushKB") != -1
				|| sort.indexOf("StrnmAveFlushKB") != -1) {
			return "nmAveFlush";
		} else {
			return sort;
		}
	}

	/**
	 * 排序list方法
	 * 
	 * @param list
	 * @param order
	 * @param orderBy
	 * @return
	 */

	public static List<TerminalBusinessEntity> orderList(
			List<TerminalBusinessEntity> list, String order, String orderBy) {
		int flag = 1;// 1:表示数字类型排序,2:表示字符类型排序
		if (orderBy.equals("terminalName")) {
			flag = 2;
		}

		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.size() - i - 1; j++) {
				TerminalBusinessEntity tb1 = list.get(j);
				TerminalBusinessEntity tb2 = list.get(j + 1);
				Class c1 = tb1.getClass();
				Class c2 = tb2.getClass();
				String methodName = "get"
						+ orderBy.substring(0, 1).toUpperCase()
						+ orderBy.substring(1, orderBy.length());
				Method m1 = null;
				Method m2 = null;
				try {
					m1 = c1.getMethod(methodName);
					m2 = c1.getMethod(methodName);
					TerminalBusinessEntity term = null;
					Object args1 = m1.invoke(tb1);
					Object args2 = m2.invoke(tb2);
					if (flag == 1) {
						Double args11 = Double.parseDouble(args1.toString());
						Double args12 = Double.parseDouble(args2.toString());
						if (order.toUpperCase().equals("DESC")) {
							if (args11 < args12) {
								term = tb1;
								list.remove(j);
								list.add(j, tb2);
								list.remove(j + 1);
								list.add(j + 1, term);
							}
						} else {
							if (args11 > args12) {
								term = tb1;
								list.remove(j);
								list.add(j, tb2);
								list.remove(j + 1);
								list.add(j + 1, term);
							}
						}
					} else {
						if (order.toUpperCase().equals("DESC")) {
							if (args1.toString().compareToIgnoreCase(
									args2.toString()) < 0) {
								term = tb1;
								list.remove(j);
								list.add(j, tb2);
								list.remove(j + 1);
								list.add(j + 1, term);
							}
						} else {
							if (args1.toString().compareToIgnoreCase(
									args2.toString()) > 0) {
								term = tb1;
								list.remove(j);
								list.add(j, tb2);
								list.remove(j + 1);
								list.add(j + 1, term);
							}
						}
					}
				} catch (SecurityException e) {
					logger.error(e.getMessage());
				} catch (NoSuchMethodException e) {
					logger.error(e.getMessage());
				} catch (IllegalArgumentException e) {
					logger.error(e.getMessage());
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage());
				} catch (InvocationTargetException e) {
					logger.error(e.getMessage());
				}
			}
		}

		return list;
	}

	/**
	 * 获取查询指定时间内的总流量和用户数的SQL语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param time
	 * @return
	 */
	public static String getTotalFlushAndImsiSql(int isTD, int timeLevel,
			String time) {
		StringBuilder sql = new StringBuilder();
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);

		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			sql.append(" select nmFlush,nmUsers ");	//V3查全网数据
			sql.append(" from VStatHourGprsTotalFlush where ");
			sql.append(" intYear=" + year + " and intMonth=" + month);
			sql.append(" and intDay=" + day + " and intHour=" + hour);
			sql.append(" and (intRaitype = " + isTD + " or intRaitype is null)");
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			sql.append(" select nmFlush,nmUsers ");
			sql.append(" from VStatDayGprsTotalFlush where ");
			sql.append(" intYear=" + year + " and intMonth=" + month);
			sql.append(" and intDay=" + day);
			sql.append(" and (intRaitype = " + isTD + " or intRaitype is null)");
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			sql.append(" select nmFlush,nmUsers ");
			sql.append(" from VStatWeekGprsTotalFlush where ");
			sql.append(" intYear=" + year + " and intWeek=" + week);
			sql.append(" and (intRaitype = " + isTD + " or intRaitype is null)");
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql.append(" select nmFlush,nmUsers ");
			sql.append(" from VStatMonthGprsTotalFlush where ");
			sql.append(" intYear=" + year + " and intMonth=" + month);
			sql.append(" and (intRaitype = " + isTD + " or intRaitype is null)");
			break;
		}
		return sql.toString();
	}

	/**
	 * 获取查询指定时间内的集团业务总流量和用户数的SQL语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param time
	 * @return
	 */
	public static String getGroupFlushAndImsiSql(int isTD, int timeLevel,
			String time) {
		String sql = "";
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);

		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			sql = sql
					+ " select nmFlush,nmUsers "
					+ " from vStatHourApnType where vcApnTypeName = '集团APN' and intRaitype = "
					+ isTD + " and intYear=" + year + " and intMonth=" + month
					+ " and intDay=" + day + " and intHour=" + hour;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			sql = sql
					+ " select nmFlush,nmUsers "
					+ " from vStatDayApnType where vcApnTypeName = '集团APN' and intRaitype = "
					+ isTD + " and intYear=" + year + " and intMonth=" + month
					+ " and intDay=" + day;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			sql = sql
					+ " select nmFlush,nmUsers "
					+ " from vStatWeekApnType where vcApnTypeName = '集团APN' and intRaitype = "
					+ isTD + " and intYear=" + year + " and intWeek=" + week;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql = sql
					+ " select nmFlush,nmUsers "
					+ " from vStatMonthApnType where vcApnTypeName = '集团APN' and intRaitype = "
					+ isTD + " and intYear=" + year + " and intMonth=" + month;
			break;
		}
		return sql;
	}

	/**
	 * 获取查询指定时间内的某集团业务总流量和用户数的SQL语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param time
	 * @return
	 */
	public static String getGroupBelongFlushAndImsiSql(int isTD, int timeLevel,
			String time, String apnName) {
		String sql = "";
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);

		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			sql = sql + " select sum(nmFlush),sum(nmUsers) "
					+ " from vStatHourApnUserBelong where vcApnDomain = '"
					+ apnName + "' and intRaitype = " + isTD + " and intYear="
					+ year + " and intMonth=" + month + " and intDay=" + day
					+ " and intHour=" + hour;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			sql = sql + " select sum(nmFlush),sum(nmUsers) "
					+ " from vStatDayApnUserBelong where vcApnDomain = '"
					+ apnName + "' and intRaitype = " + isTD + " and intYear="
					+ year + " and intMonth=" + month + " and intDay=" + day;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			sql = sql + " select sum(nmFlush),sum(nmUsers) "
					+ " from vStatWeekApnUserBelong where vcApnDomain = '"
					+ apnName + "' and intRaitype = " + isTD + " and intYear="
					+ year + " and intWeek=" + week;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql = sql + " select sum(nmFlush),sum(nmUsers) "
					+ " from vStatMonthApnUserBelong where vcApnDomain = '"
					+ apnName + "' and intRaitype = " + isTD + " and intYear="
					+ year + " and intMonth=" + month;
			break;
		}
		return sql;
	}

	/**
	 * 获取查询指定时间内的黑莓业务总流量和用户数的SQL语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param time
	 * @return
	 */
	public static String getBBFlushAndImsiSql(int timeLevel, String time) {
		String sql = "";
		Date date = CommonUtils.getDate(time);
		int year = CommonUtils.getYear(date);
		int month = CommonUtils.getMonth(date);
		int week = CommonUtils.getWeek(date);
		int day = CommonUtils.getDay(date);
		int hour = CommonUtils.getHour(date);

		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			sql = sql + " select sum(nmTotalFlush),sum(nmUsers) "
					+ " from vStatHourCountryBbFlush where   intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day
					+ " and intHour=" + hour;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			sql = sql + " select sum(nmTotalFlush),sum(nmUsers) "
					+ " from vStatDayCountryBbFlush where  intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			sql = sql + " select sum(nmTotalFlush),sum(nmUsers) "
					+ " from vStatWeekCountryBbFlush where  intYear=" + year
					+ " and intWeek=" + week;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql = sql + " select sum(nmTotalFlush),sum(nmUsers) "
					+ " from vStatMonthCountryBbFlush where  intYear=" + year
					+ " and intMonth=" + month;
			break;
		}
		return sql;
	}

	/**
	 * 获取查询指定时间内的黑莓业务总流量和用户数的SQL语句
	 * 
	 * @param isTD
	 * @param timeLevel
	 * @param time
	 * @return
	 */
	public static String getBBFlushAndImsiSql(int timeLevel, int year,
			int month, int day, int hour, int week) {
		String sql = "";
		switch (timeLevel) {
		case CommonConstants.MANTOEYE_TIME_LEVEL_HOUR:
			sql = sql + " select sum(nmTotalFlush),sum(nmUsers) "
					+ " from vStatHourCountryBbFlush where   intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day
					+ " and intHour=" + hour;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_DAY:
			sql = sql + " select sum(nmTotalFlush),sum(nmUsers) "
					+ " from vStatDayCountryBbFlush where  intYear=" + year
					+ " and intMonth=" + month + " and intDay=" + day;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_WEEK:
			sql = sql + " select sum(nmTotalFlush),sum(nmUsers) "
					+ " from vStatWeekCountryBbFlush where  intYear=" + year
					+ " and intWeek=" + week;
			break;
		case CommonConstants.MANTOEYE_TIME_LEVEL_MONTH:
			sql = sql + " select sum(nmTotalFlush),sum(nmUsers) "
					+ " from vStatMonthCountryBbFlush where  intYear=" + year
					+ " and intMonth=" + month;
			break;
		}
		return sql;
	}
}
