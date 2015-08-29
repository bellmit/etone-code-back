package com.symbol.app.mantoeye.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 常用的公共方法
 * 
 * @author rankin
 * 
 */
public class CommonUtils {

	private static final Log logger = LogFactory.getLog(CommonUtils.class);

	/**
	 * 格式化指定日期的字符串表示
	 * 
	 * @param date
	 *            格式:yyyy-MM-dd || yyyy-MM
	 * @return String
	 */
	@SuppressWarnings("finally")
	public static String formatDate(Date date, boolean flag) {
		String formatString = flag ? "yyyy-MM-dd" : "yyyy-MM";
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		String dateString = "";
		try {
			dateString = sdf.format(date);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			return dateString;
		}
	}

	/**
	 * 返回当前时间的字符串 格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 格式为yyyy-MM-dd HH:mm:ss
	 */
	public static String formatFullDate(Date date) {
		if (date == null) {
			return "";
		}
		String formatString = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		String dateString = "";
		try {
			dateString = sdf.format(date);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return dateString;
	}

	/**
	 * 格式化当前日期的字符串表示
	 * 
	 * @param date
	 *            格式:"yyyy-MM-dd HH:mm:ss"
	 * @return String
	 */

	public static String formatDate(Object date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = null;
		try {
			dateString = sdf.format(date);

			// dateString = sdf.format(new Date());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			return dateString;
		}
	}

	/**
	 * 获取当前日期
	 * 
	 * @param date
	 *            格式:"yyyy-MM-dd HH:mm:ss"
	 * @return String
	 */
	@SuppressWarnings("finally")
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = "";
		try {
			date = sdf.format(new Date());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			return date;
		}
	}

	/**
	 * 获取当前日期
	 * 
	 * @param date
	 *            格式:"yyyy-MM-dd"
	 * @return String
	 */
	public static String getCurrentDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = "";
		try {
			date = sdf.format(new Date());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			return date;
		}
	}

	/**
	 * 
	 * 获取昨天日期
	 * 
	 * @param todayDataStr
	 * @return
	 */
	public static String getYestodayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		Date date;
		String yestodayDate = "";
		try {
			date = sdf.parse(sdf.format(new Date()));
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			yestodayDate = sdf.format(cal.getTime());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return yestodayDate;
	}

	/**
	 * 
	 * 获取上一个月
	 * 
	 * @param todayDataStr
	 * @return
	 */
	public static String getLastMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		GregorianCalendar cal = new GregorianCalendar();
		Date date;
		String yestodayDate = "";
		try {
			date = sdf.parse(sdf.format(new Date()));
			cal.setTime(date);
			cal.add(Calendar.MONTH, -1);
			yestodayDate = sdf.format(cal.getTime());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return yestodayDate;
	}

	public static String getYYestodayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		Date date;
		String yestodayDate = "";
		try {
			date = sdf.parse(sdf.format(new Date()));
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			yestodayDate = sdf.format(cal.getTime());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return yestodayDate;
	}

	/**
	 * 
	 * 获取昨天日期
	 * 
	 * @param todayDataStr
	 * @return
	 */
	public static String getYestoday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GregorianCalendar cal = new GregorianCalendar();
		Date date;
		String yestodayDate = "";
		try {
			date = sdf.parse(sdf.format(new Date()));
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			yestodayDate = sdf.format(cal.getTime());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return yestodayDate;
	}

	/**
	 * 
	 * 获取前30天日期
	 * 
	 * @param todayDataStr
	 * @return
	 */
	public static String getPerThityDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		Date date;
		String yestodayDate = "";
		try {
			date = sdf.parse(sdf.format(new Date()));
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, -30);
			yestodayDate = sdf.format(cal.getTime());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return yestodayDate;
	}

	/**
	 * 
	 * 获取昨天的日期
	 * 
	 * @param todayDataStr
	 * @return
	 */
	public static String getSYestoday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar cal = new GregorianCalendar();
		Date date;
		String yestodayDate = "";
		try {
			date = sdf.parse(sdf.format(new Date()));
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			yestodayDate = sdf.format(cal.getTime());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return yestodayDate;
	}

	/**
	 * 
	 * 获取两个小时前的时间
	 * 
	 * @param todayDataStr
	 * @return
	 */
	public static String getPerTwoHour() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GregorianCalendar cal = new GregorianCalendar();
		Date date;
		String yestodayDate = "";
		try {
			date = sdf.parse(sdf.format(new Date()));
			cal.setTime(date);
			cal.add(Calendar.HOUR_OF_DAY, -2);
			yestodayDate = sdf.format(cal.getTime());
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return yestodayDate;
	}

	/**
	 * 格式化时间 字符串转换为日期时间 格式为yyyy-MM-dd HH:mm:ss 转换失败返回null
	 * 
	 * @param s
	 * @return 格式为yyyy-MM-dd HH:mm:ss
	 */
	public static Date getDate(String s) {
		if (s == null)
			return null;
		if (s.split("-").length == 2) {// 当时间格式为yyyy-MM格式时
			s = s + "-01";
		}
		String[] ds = s.split(" ");
		if (ds.length == 1) {
			s = s + " 00:00:00";
		} else if (ds.length == 2) {
			String[] ts = ds[1].split(":");
			if (ts.length == 1) {
				s = s + ":00:00";
			} else if (ts.length == 2) {
				s = s + ":00";
			}
		} else {
			logger.error("时间格式有误:请按此时间格式:yyyy-MM-dd HH:mm:ss");
			return null;
		}
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			Date date = simpledateformat.parse(s);
			return date;
		} catch (Exception _ex) {
			logger.error("时间格式有误:请按此时间格式:yyyy-MM-dd HH:mm:ss");
			return null;
		}
	}

	/**
	 * 获取当前的年
	 * 
	 * @param date
	 *            格式yyyy-mm-dd hh:MM:ss
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取当前的月
	 * 
	 * @param date
	 *            格式yyyy-mm-dd hh:MM:ss
	 * @return 1-12
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取当前的日
	 * 
	 * @param date
	 *            格式yyyy-mm-dd hh:MM:ss
	 * @return 1-31
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);

	}

	/**
	 * 获取当前的小时
	 * 
	 * @param date
	 *            格式yyyy-mm-dd hh:MM:ss
	 * @return 1-24
	 */
	public static int getHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前的周
	 * 
	 * @param date
	 *            格式yyyy-mm-dd hh:MM:ss
	 * @return 1-24
	 */
	public static int getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 根据某一天获取周的范围
	 * 
	 * 
	 * public static String getWeekRange(Date date) { String start = ""; String
	 * end = ""; Calendar cal = Calendar.getInstance(); cal.setTime(date); int
	 * index = cal.get(cal.DAY_OF_WEEK); // 开始时间 date.setTime(date.getTime() -
	 * (index - 1) * 24 * 60 * 60 * 1000); start = formatDate(date, true); //
	 * 结束时间 date.setTime(date.getTime() + 6 * 24 * 60 * 60 * 1000); end =
	 * formatDate(date, true); return start + "~" + end; }
	 */

	/**
	 * 通过年月日构造yyyy-MM-dd HH:mm:ss格式的字符串
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String buildFullDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return sdf.format(c.getTime()) + " 00:00:00";
	}

	/**
	 * 通过年月日构造yyyy-MM-dd格式的字符串
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String buildShortDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		return sdf.format(c.getTime());
	}

	/**
	 * 通过年月日小时构造yyyy-MM-dd HH:mm:ss格式的字符串
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static String buildFullDate(int year, int month, int day, int hour) {
		Calendar c = Calendar.getInstance();
		c.set(year, month - 1, day, hour - 1, 0, 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(c.getTime());
	}

	/**
	 * 对日期进行天的加减法计算 offSet为正表示加,offSet为负表示减
	 * 
	 * @param chkDate
	 * @param offSet
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date getOffsetDay(java.util.Date chkDate,
			java.lang.Integer offSet) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(chkDate);
		calendar.add(Calendar.DATE, offSet);
		return calendar.getTime();
	}

	/**
	 * 格式化页面显示数据
	 * 
	 * @param obj
	 * @return
	 */
	public static String killNull(Object obj) {
		String rs = "--";
		if (obj == null) {
			rs = "--";
		} else if ("".equals(obj.toString().trim())) {
			rs = "--";
		} else if (obj.toString().equals("null")) {
			rs = "--";
		} else {
			rs = obj.toString();
		}
		return rs;
	}

	/**
	 * 转换单位 B-->KB 保留2为小数
	 * 
	 * @param obj
	 * @return
	 */
	public static Double formatBToKb(Long b) {
		if (b <= 0) {
			return 0.0;
		}
		return (Math.floor((b * 100.0) / 1024 + 0.5)) / 100.0;
	}

	/**
	 * 转换单位 B-->KB 保留2为小数
	 * 
	 * @param obj
	 * @return
	 */
	public static Double formatBToMb(Long b) {
		if (b <= 0) {
			return 0.0;
		}
		return (Math.floor((b * 100.0) / (1024 * 1024) + 0.5)) / 100.0;
	}

	/**
	 * 转换单位 B-->KB 保留2为小数
	 * 
	 * @param obj
	 * @return
	 */
	public static Double formatBToGb(Long b) {
		if (b <= 0) {
			return 0.0;
		}
		return (Math.floor((b * 100.0) / (1024 * 1024 * 1024) + 0.5)) / 100.0;
	}

	/**
	 * 转换单位 B-->KB 保留2为小数
	 * 
	 * @param obj
	 * @return
	 */
	public static Double formatBToTb(Long b) {
		if (b <= 0) {
			return 0.0;
		}
		return (Math.floor((b * 100.0) / (1024.0 * 1024 * 1024 * 1024) + 0.5)) / 100.0;
	}

	/**
	 * 转换单位 KB-->MB 保留2为小数
	 * 
	 * @param obj
	 * @return
	 */
	public static Double formatKbToMb(int kb) {
		if (kb <= 0) {
			return 0.0;
		}
		return (Math.floor((kb * 100.0) / (1024) + 0.5)) / 100.0;
	}

	/**
	 * 转换单位 KB-->GB 保留2为小数
	 * 
	 * @param obj
	 * @return
	 */
	public static Double formatKbToGb(int kb) {
		if (kb <= 0) {
			return 0.0;
		}
		return (Math.floor((kb * 100.0) / (1024 * 1024) + 0.5)) / 100.0;
	}

	/**
	 * 转换单位 B-->KB 保留2为小数
	 * 
	 * @param obj
	 * @return
	 */
	public static Double formatBToKb(Double b) {
		if (b <= 0) {
			return 0.0;
		}
		return (Math.floor((b * 100.0) / (1024) + 0.5)) / 100.0;
	}

	/**
	 * 转换单位 B-->KB 保留2为小数
	 * 
	 * @param obj
	 * @return
	 */
	public static Double formatBToMb(Double b) {
		if (b <= 0) {
			return 0.0;
		}
		return (Math.floor((b * 100.0) / (1024 * 1024) + 0.5)) / 100.0;
	}

	/**
	 * 转换单位 -->万 保留2为小数
	 * 
	 * @param obj
	 * @return
	 */
	public static Double formatToWan(Double b) {
		if (b <= 0) {
			return 0.0;
		}
		return (Math.floor((b * 100.0) / (10000) + 0.5)) / 100.0;
	}

	/**
	 * 转换单位 -->亿 保留2为小数
	 * 
	 * @param obj
	 * @return
	 */
	public static Double formatToYi(Double b) {
		if (b <= 0) {
			return 0.0;
		}
		return (Math.floor((b * 100.0) / (100000000L) + 0.5)) / 100.0;
	}

	public static String getDayInnerWeek(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			calendar.add(Calendar.DATE, -(day - 1));
			Date startDate = calendar.getTime();
			calendar.add(Calendar.DATE, 6);
			Date endDate = calendar.getTime();
			String sdate = sdf.format(startDate);
			String edate = sdf.format(endDate);
			return sdate + "~" + edate;
		} catch (ParseException e) {
			logger.error(e.getMessage());
			return dateStr;
		}
	}

	/**
	 * 根据周获取周的日期范围
	 * 
	 * @param year
	 * @param month
	 * @param week
	 * @return
	 */
	public static String getWeekRange(int year, int month, int week) {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.YEAR, year);
		if (month != 0) {
			cal.set(cal.MONTH, month);
		}
		cal.set(cal.WEEK_OF_YEAR, week);
		return getDayInnerWeek(formatDate(cal.getTime(), true));
	}

	public static String changeDate(String date, int timeLevel) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
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
	 * 设置数据百分比
	 */
	public static String formatPercent(double p1, double p2) {
		String percent = "";
		double p3 = p1 / p2;
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(4);// 最小4位
		percent = nf.format(p3);
		if (percent.indexOf("%") != -1) {// 去掉百分号
			percent = percent.substring(0, percent.lastIndexOf("%"));
		}
		return percent;
	}
}
