// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   common.java

package com.symbol.wp.core.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 定义了一些公用方法
 * 
 * @Title: Common.java
 * @Description: <br>
 * <br>
 * @Company: etonetech
 * @Created Mar 24, 2009 10:14:19 AM
 * @author ranhualin
 * @version 1.0
 * @since 1.0
 */
/*
 * 方法比较多，请通过搜索后面目录关键字来查找方法。添加方法时，请把方法添加在相应的分类里面。 目录： 1.日期时间类方法 2.字符串操作类方法
 * 3.数字操作类方法 4.类型转换类方法 5.SQL相关方法 6.JAVASCRIPT类相关方法 7.HTML相关方法 8.验证类方法 9.计量单位转换方法
 * 10.其他方法
 */
public class Common {
	private static final Log logger = LogFactory.getLog(Common.class);

	/*
	 * ----------------------------------日期时间类方法----------------------------------
	 * ----
	 */
	// /////////////////////////////////////Start////////////////////////////////////////////////////
	/**
	 * 日期相减
	 * 
	 * @param s
	 * @param s1
	 * @return
	 */
	public static int DateAfter(String s, String s1) {
		String s2 = s;
		String s3 = s1;
		if (s2.length() < 11)
			s2 = s2 + " 01:01:01";
		if (s3.length() < 11)
			s3 = s3 + " 01:01:01";
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		Date date = null;
		Date date1 = null;
		try {
			date = simpledateformat.parse(s2);
			date1 = simpledateformat.parse(s3);
		} catch (Exception _ex) {
			logger.error("unparseable using " + simpledateformat);
		}
		long l = date1.getTime() - date.getTime();
		int i = Integer.parseInt(String.valueOf(l / 0x5265c00L));
		return i;
	}

	/**
	 * 对日期进行月的加减法计算 offSet为正表示加,offSet为负表示减
	 * 
	 * @param chkDate
	 * @param offSet
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date getOffsetDate(java.util.Date chkDate,
			java.lang.Integer offSet) throws Exception {
		int month = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(chkDate);
		calendar.add(Calendar.MONTH, offSet);
		return calendar.getTime();
	}

	/**
	 * 对日期进行小时的加减法计算 offSet为正表示加,offSet为负表示减
	 * 
	 * @param chkDate
	 * @param offSet
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date getOffsetHour(java.util.Date chkDate,
			java.lang.Integer offSet) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTime(chkDate);
		c.add(Calendar.HOUR_OF_DAY, offSet);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String now = sdf.format(c.getTime());
		return c.getTime();
	}

	/**
	 * 时间加上offSet分钟 offSet为正表示加,offSet为负表示减
	 * 
	 * @param chkDate
	 * @param offSet
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date getOffsetMinute(java.util.Date chkDate,
			java.lang.Integer offSet) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(chkDate);
		calendar.add(Calendar.MINUTE, offSet);
		return calendar.getTime();
	}

	/**
	 * 获取结束时间和开始时间的间隔 返回值 格式为:day+"天"+hour+"小时"+minute+"分钟"+second+"秒"
	 * 
	 * @param start
	 * @param end
	 * @return 格式为:day+"天"+hour+"小时"+minute+"分钟"+second+"秒"
	 */
	public static String getTimeDistance(String start, String end) {
		String TimeDistance = "";
		Date startDate = null;
		Date endDate = null;
		if (end.length() < 11)
			end = end + " 00:00:00";
		if (start.length() < 11)
			start = start + " 00:00:00";
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		try {
			startDate = simpledateformat.parse(start);
			endDate = simpledateformat.parse(end);
		} catch (Exception _ex) {
			logger.error("unparseable using " + simpledateformat);
		}
		long millisecond = endDate.getTime() - startDate.getTime(); // 两个时间的相隔毫秒数
		millisecond = millisecond / 1000;
		long day = (long) millisecond / (60 * 60 * 24);// 天数
		long hour = (millisecond - 60 * 60 * 24 * day) / (60 * 60);// 小时
		long minute = (millisecond - 60 * 60 * 24 * day - 60 * 60 * hour) / 60;// 分钟
		long second = millisecond - 60 * 60 * 24 * day - 60 * 60 * hour - 60
				* minute;// 秒
		TimeDistance = day + "天" + hour + "小时" + minute + "分钟" + second + "秒";
		return TimeDistance;
	}

	/**
	 * 获取日期 逻辑判断: 返回值:格式****-**-**
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateNum(String date) {
		String array[] = null;
		if (date != null && !date.equals("") && !date.equalsIgnoreCase("null")) {
			array = date.split(" ");
			return array[0];
		} else {
			return "";
		}
	}

	/**
	 * 获取小时
	 * 
	 * @param date
	 *            格式为****-**-** **:**:**
	 * @return
	 */
	public static int getHourNum(String date) {
		String array[] = null;
		String hour = "0";
		if (date != null && !date.equals("") && !date.equalsIgnoreCase("null")) {
			array = date.split(" ");
			if (array != null && array.length > 1 && !array[1].equals("")) {
				hour = array[1].split(":")[0];
			}
		}
		return Integer.parseInt(hour);
	}

	/**
	 * 获取分钟
	 * 
	 * @param date
	 *            格式为****-**-** **:**:**
	 * @return
	 */
	public static int getMinuteNum(String date) {
		String array[] = null;
		String minute = "0";
		if (date != null && !date.equals("") && !date.equalsIgnoreCase("null")) {
			array = date.split(" ");
			if (array != null && array.length > 1 && !array[1].equals("")) {
				minute = array[1].split(":")[1];
			}
		}
		return Integer.parseInt(minute);
	}

	/**
	 * 获取秒
	 * 
	 * @param date
	 *            格式为****-**-** **:**:**
	 * @return
	 */
	public static int getSecondNum(String date) {
		String array[] = null;
		String second = "0";
		if (date != null && !date.equals("") && !date.equalsIgnoreCase("null")) {
			array = date.split(" ");
			if (array != null && array.length > 1 && !array[1].equals("")) {
				second = array[1].split(":")[2];
			}
		}
		return Integer.parseInt(second);
	}

	/**
	 * 字符串转换为日期时间 格式为yyyy-MM-dd HH:mm:ss 转换失败返回null
	 * 
	 * @param s
	 * @return 格式为yyyy-MM-dd HH:mm:ss
	 */
	public static Date getDate(String s) {
		// 2007-1-26 23:59:12
		if (s == null)
			return null;
		if (s.length() < 11)
			s = s + " 01:01:01";
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
	 * 字符串转换为日期时间 格式为yyyy-MM-dd HH:mm:ss 转换失败返回null
	 * 
	 * @param s
	 * @return 格式为yyyy-MM-dd HH:mm:ss
	 */
	public static Date getDate2(String s) {
		if (s.length() < 11)
			s = s + " 01:01:01";
		StringBuffer stringbuffer = new StringBuffer(s);
		if (stringbuffer.charAt(7) != '-')
			stringbuffer.insert(5, '0');
		if (stringbuffer.charAt(10) != ' ')
			stringbuffer.insert(8, '0');
		if (stringbuffer.charAt(13) != ':')
			stringbuffer.insert(11, '0');
		if (stringbuffer.charAt(16) != ':')
			stringbuffer.insert(14, '0');
		if (stringbuffer.length() == 18)
			stringbuffer.insert(17, '0');
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			Date date = simpledateformat.parse(new String(stringbuffer));
			return date;
		} catch (Exception _ex) {
			return null;
		}
	}

	/**
	 * 获取今天的日期 格式 yyyy-MM-dd
	 * 
	 * @return 格式 yyyy-MM-dd
	 */
	public static String getToday() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 得到时间具体的数字值
	 * 
	 * @param date
	 *            时间
	 * @param timeType
	 *            时间类型 : year ：年,month ： 月,day ： 日
	 * @return year:yyyy, month :m ,day:d
	 */
	public static Integer getTimeNum(String date, String timeType) {

		if ("year".equals(timeType)) {
			return getYear_(date);
		} else if ("month".equals(timeType)) {
			return getMonth_(date);
		} else if ("day".equals(timeType)) {
			return getDay(date);
		}

		return null;
	}

	private static Integer getYear_(String date) {
		if (null != date && !"".equals(date.trim())) {
			String temp = date.substring(0, 4);
			return Integer.parseInt(temp);
		}

		return null;
	}

	private static Integer getMonth_(String date) {
		if (null != date && !"".equals(date.trim())) {
			String temp = date.substring(5, 7);
			String t = temp.substring(0, 1);
			if ("0".equals(temp)) {
				return Integer.parseInt(temp.substring(1));
			} else {
				return Integer.parseInt(temp);
			}
		}

		return null;
	}

	public static Integer getDay(String date) {
		if (null != date && !"".equals(date.trim())) {
			String temp = date.substring(8);
			String t = temp.substring(0, 1);
			if ("0".equals(temp)) {
				return Integer.parseInt(temp.substring(1));
			} else {
				return Integer.parseInt(temp);
			}
		}

		return null;
	}

	/**
	 * 获取传入日期时间字符串的 年
	 * 
	 * @param date
	 *            格式yyyy-MM-dd HH:mm:ss
	 * @return 格式yyyy
	 */
	public static String getYear(String date) {
		if (date != null && !date.equals("")) {
			String temp = new String(date);
			temp = temp.substring(0, 4);
			return temp;
		} else {
			return null;
		}
	}

	/**
	 * 获取传入日期时间字符串的 月
	 * 
	 * @param date
	 *            格式yyyy-MM-dd HH:mm:ss
	 * @return 格式MM
	 */
	public static String getMonth(String date) {
		if (date != null && !date.equals("")) {
			String temp = new String(date);
			temp = temp.substring(5, 7);
			return temp;
		} else {
			return null;
		}
	}

	/**
	 * 获取传入日期时间字符串的 年和月
	 * 
	 * @param date
	 *            格式yyyy-MM-dd HH:mm:ss
	 * @return 格式yyyy-MM
	 */
	public static String getYearAndMonth(String date) {
		String yearAndMonth = null;
		if (date != null && !date.equals("")) {
			yearAndMonth = getYear(date) + "-" + getMonth(date);
		}
		return yearAndMonth;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		if (date == null)
			return null;
		Calendar clr = Calendar.getInstance();
		clr.setTime(date);
		clr.add(Calendar.DAY_OF_MONTH, -1);
		clr.set(Calendar.HOUR, 23);
		clr.set(Calendar.MINUTE, 59);
		clr.set(Calendar.SECOND, 59);
		return clr.getTime();
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		if (date == null)
			return null;
		Calendar clr = Calendar.getInstance();
		clr.setTime(date);
		clr.set(Calendar.HOUR, 23);
		clr.set(Calendar.MINUTE, 59);
		clr.set(Calendar.SECOND, 59);
		return clr.getTime();
	}

	/**
	 * 字符串转换为日期 格式为yyyy-MM-dd 转换失败返回null
	 * 
	 * @param s
	 * @return 格式为yyyy-MM-dd
	 */
	public static Date getYMDDate(String s) {
		// 2007-1-26
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = simpledateformat.parse(s);
			return date;
		} catch (Exception _ex) {
			logger.error("时间格式有误:请按此时间格式:yyyy-MM-dd");
			return null;
		}
	}

	/**
	 * 返回当前时间的字符串 格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return 格式为yyyy-MM-dd HH:mm:ss
	 */
	public static String getNow() {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String s = simpledateformat.format(date);
		return s;
	}

	/**
	 * 返回当点时间的 输入格式的字符串
	 * 
	 * @param s
	 *            日期时间格式
	 * @return 格式为s
	 */
	public static String getNow(String s) {
		// /一种时间格式类
		SimpleDateFormat simpledateformat = new SimpleDateFormat(s);
		Date date = new Date();
		String s1 = simpledateformat.format(date);
		return s1;
	}

	/**
	 * 返回当前时间的字符串 格式为yyyyMMdd
	 * 
	 * @return 格式为yyyyMMdd
	 */
	public static String getNoLineToday() {
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
		Date c = new Date();
		String d = s.format(c);
		return d;
	}

	/**
	 * 获取参数时间日期的日期字符串 格式yyyy-MM-dd
	 * 
	 * @param d
	 * @return 格式yyyy-MM-dd
	 */
	public static String getDateFormat(java.util.Date d) {
		return getDateTimeFormatBy(d, "yyyy-MM-dd");
	}

	/**
	 * 获取参数时间日期的小时分钟字符串 格式HH:mm
	 * 
	 * @param d
	 * @return 格式HH:mm
	 */
	public static String getTimeHMFormat(java.util.Date d) {
		return getDateTimeFormatBy(d, "HH:mm");
	}

	/**
	 * 获取参数时间日期的小时分钟秒字符串 格式HH:mm:ss
	 * 
	 * @param d
	 * @return 格式HH:mm:ss
	 */
	public static String getTimeHMSFormat(java.util.Date d) {
		return getDateTimeFormatBy(d, "HH:mm:ss");
	}

	/**
	 * 获取参数时间日期的时间日期字符串 格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param d
	 * @return 格式yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTimeFormat(java.util.Date d) {
		return getDateTimeFormatBy(d, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取参数时间日期的时间日期字符串 格式yyyy-MM-dd HH:mm
	 * 
	 * @param d
	 * @return 格式yyyy-MM-dd HH:mm
	 */
	public static String getShortDTF(java.util.Date d) {
		return getDateTimeFormatBy(d, "yyyy-MM-dd HH:mm");
	}

	/**
	 * 获取参数时间日期的时间日期字符串 格式yyyy-MM-dd HH
	 * 
	 * @param d
	 * @return 格式yyyy-MM-dd HH
	 */
	public static String getShortYMDH(java.util.Date d) {
		return getDateTimeFormatBy(d, "yyyy-MM-dd HH");
	}

	/**
	 * 以时间日期格式p格式化输入时间
	 * 
	 * @param d
	 * @return 格式p
	 */
	public static String getDateTimeFormatBy(java.util.Date d, String p) {
		if (d == null)
			return "-";
		SimpleDateFormat sdf = new SimpleDateFormat(p);

		return sdf.format(d);
	}

	/**
	 * 返回当天是上旬、中旬、还是下旬
	 * 
	 * @param var
	 *            日期的天
	 * @return 上旬 中旬 下旬
	 */
	public static String getTenDayString(int var) {
		if (var > 0 && var <= 10)
			return "上旬";
		if (var > 10 && var <= 20)
			return "中旬";
		if (var > 20 && var <= 31)
			return "下旬";
		return "";
	}

	/**
	 * 
	 * @param var
	 * @return
	 */
	public static int getTenDayEnd(int var) {
		if (var == 20 || var == 10)
			return var - 9;
		if (var == 31)
			return var - 10;
		if (var <= 0)
			return 1;
		return -1;
	}

	/**
	 * 
	 * @param var
	 * @return
	 */
	public static int getTenDay(int var) {
		if (var > 0 && var <= 10)
			return 10;
		if (var > 10 && var <= 20)
			return 20;
		if (var > 20 && var <= 31)
			return 31;
		if (var <= 0)
			return 31;
		return -1;
	}

	/**
	 * 把时间段按 ****-**-**形式封装到list里面去 包含开始时间到结束时间的相隔天数为dayNum 的天
	 * 
	 * @param startTime
	 *            开始日期
	 * @param endTime
	 *            结束日期
	 * @param dayNum
	 *            间隔的天数
	 * @return
	 */
	public static List getYMDList(String startTime, String endTime, int dayNum) {
		List list = null;
		String startArry[] = startTime.split("-");
		String endArry[] = endTime.split("-");
		int startYear = Common.StringToInt(startArry[0]);// 开始时间的年份
		int endYear = Common.StringToInt(endArry[0]);// 结束时间的年份
		int startMonth = Common.StringToInt(startArry[1]);// 开始时间的月份
		int endMonth = Common.StringToInt(endArry[1]);// 结束时间的月份
		int startDay = Common.StringToInt(startArry[2]);// 开始时间的日
		int endDay = Common.StringToInt(endArry[2]);// 结束时间的日
		int yearInt = endYear - startYear;
		if (yearInt == 0) {// 相同年份时
			list = new ArrayList();
			if (startMonth >= endMonth) {
				if (startMonth == endMonth && startDay <= endDay) {// 相同月份
					for (int day = startDay; day <= endDay; day += dayNum) {
						logger.info("  :" + startYear + "-"
								+ Common.fillZero(String.valueOf(startMonth))
								+ "-" + Common.fillZero(String.valueOf(day)));
						list.add(startYear + "-"
								+ Common.fillZero(String.valueOf(startMonth))
								+ "-" + Common.fillZero(String.valueOf(day)));
					}
				}
			} else {
				int gap_month = endMonth - startMonth;
				list = new ArrayList();
				int start = startDay;// 第一次进入
				int end = 31;
				for (int i = 0; i <= gap_month; i++) {
					for (int j = start; j <= end; j += dayNum) {
						int day = (j % 31 == 0 ? 31 : j % 31);
						int month = startMonth + i;
						list.add(startYear + "-"
								+ Common.fillZero(String.valueOf(month)) + "-"
								+ Common.fillZero(String.valueOf(day)));
						if (day == 31) {
							start = 1;
							if (i == gap_month - 1) {// 最后一个
								end = endDay;
							}
							break;
						}
					}
				}
			}
		} else if (yearInt > 0) {
			list = new ArrayList();
			int start = startMonth;// 第一次进入
			int end = 12;
			int gap_month = endMonth - startMonth;
			for (int i = 0; i <= yearInt; i++) {
				for (int j = start; j <= end; j++) {
					int month = (j % 12 == 0 ? 12 : j % 12);
					int year = startYear + i;
					int start_day = startDay;
					int end_day = 31;
					for (int k = start_day; k < end_day; k++) {
						int day = (k % 31 == 0 ? 31 : k % 31);
						list.add(year + "-"
								+ Common.fillZero(String.valueOf(month)) + "-"
								+ Common.fillZero(String.valueOf(day)));
						if (day == 31) {
							start_day = 1;
							if (k == gap_month - 1) {// 最后一个
								end_day = endDay;
							}
							break;
						}
					}
					if (month == 12) {
						start = 1;
						if (i == yearInt - 1) {// 最后一个
							end = endMonth;
						}
						break;
					}
				}
			}
		}
		return list;
	}

	/**
	 * 把结束时间和开始时间 之间的时间段封装到list 时间类型：9999-12 包含开始时间到结束时间的每一月
	 * 
	 * @author wendy
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public static List getYMList(String startTime, String endTime) {
		List list = null;
		String startArry[] = startTime.split("-");
		String endArry[] = endTime.split("-");
		int startYear = Common.StringToInt(startArry[0]);// 开始时间的年份
		int endYear = Common.StringToInt(endArry[0]);// 结束时间的年份
		int startMonth = Common.StringToInt(startArry[1]);// 开始时间的月份
		int endMonth = Common.StringToInt(endArry[1]);// 结束时间的月份
		int yearInt = endYear - startYear;
		if (yearInt == 0 && endMonth >= startMonth) {// 相同年份时
			list = new ArrayList();
			for (int i = startMonth; i <= endMonth; i++) {
				list.add(startYear + "-" + Common.fillZero(String.valueOf(i)));
			}
		} else if (yearInt > 0) {
			list = new ArrayList();
			int start = startMonth;// 第一次进入
			int end = 12;
			for (int i = 0; i <= yearInt; i++) {
				for (int j = start; j <= end; j++) {
					int month = (j % 12 == 0 ? 12 : j % 12);
					int year = startYear + i;
					list.add(year + "-"
							+ Common.fillZero(String.valueOf(month)));
					if (month == 12) {
						start = 1;
						if (i == yearInt - 1) {// 最后一个
							end = endMonth;
						}
						break;
					}
				}
			}
		}
		return list;
	}

	/**
	 * 把结束时间和开始时间 之间的时间段封装到list 时间类型：9999 第一季度 包含开始时间到结束时间中的每一季度
	 * 
	 * @param startTime
	 * @param endTime
	 * @return 格式：2001年第3季度
	 */
	public static List getYSList(String startTime, String endTime) {
		List list = null;
		String startArry[] = startTime.split("-");
		String endArry[] = endTime.split("-");
		int startYear = Common.StringToInt(startArry[0]);// 开始时间的年份
		int endYear = Common.StringToInt(endArry[0]);// 结束时间的年份
		int startSeason = Common.StringToInt(startArry[1]);// 开始时间的季度
		int endSeason = Common.StringToInt(endArry[1]);// 结束时间的季度
		int yearInt = endYear - startYear;
		if (yearInt == 0 && endSeason >= startSeason) {// 相同年份时
			list = new ArrayList();
			for (int i = startSeason; i <= endSeason; i++) {
				list.add(startYear + "年" + "第" + i + "季度");
			}
		} else if (yearInt > 0) {
			list = new ArrayList();
			int start = startSeason;// 第一次进入
			int end = 4;
			for (int i = 0; i <= yearInt; i++) {
				for (int j = start; j <= end; j++) {
					int season = (j % 4 == 0 ? 4 : j % 4);
					int year = startYear + i;
					list.add(year + "年" + "第" + season + "季度");
					if (season == 12) {
						start = 1;
						if (i == yearInt - 1) {// 最后一个
							end = season;
						}
						break;
					}
				}
			}
		}
		return list;
	}

	/**
	 * 把****-** 几旬放到list里面去 包含开始时间到结束时间的所有月中的旬
	 * 
	 * @author wendy
	 * @param startTime
	 * @param endTime
	 * @return 格式：2001-03 下旬
	 */
	public static List getYTenList(String startTime, String endTime) {
		List list = new ArrayList();
		String startArry[] = startTime.split("-");
		String endArry[] = endTime.split("-");
		int startYear = Common.StringToInt(startArry[0]);// 开始时间的年份
		int endYear = Common.StringToInt(endArry[0]);// 结束时间的年份
		int startMonth = Common.StringToInt(startArry[1]);// 开始时间的月份
		int endMonth = Common.StringToInt(endArry[1]);// 结束时间的月份
		int start_period = Common.StringToInt(startArry[2]);// 开始时间的旬期
		int end_period = Common.StringToInt(endArry[2]);// 结束时间的旬期
		int yearInt = endYear - startYear;
		if (yearInt == 0) {// 相同的年份
			if (startMonth == endMonth && end_period >= start_period) {
				for (int i = start_period; i < end_period; i++) {
					if (i == 1) {
						list.add(startYear + "-"
								+ Common.fillZero(String.valueOf(startMonth))
								+ " " + "上旬");
					} else if (i == 2) {
						list.add(startYear + "-"
								+ Common.fillZero(String.valueOf(startMonth))
								+ " " + "中旬");
					} else if (i == 3) {
						list.add(startYear + "-"
								+ Common.fillZero(String.valueOf(startMonth))
								+ " " + "下旬");
					}
				}
			} else if (endMonth > startMonth) {
				int monthNum = endMonth - startMonth;
				int start = start_period;// 第一次进入
				int end = 3;
				for (int i = 0; i <= monthNum; i++) {
					for (int j = start; j <= end; j++) {
						int period = (j % 3 == 0 ? 3 : j % 3);
						int month = startMonth + i;
						if (j == 1) {
							list.add(startYear + "-"
									+ Common.fillZero(String.valueOf(month))
									+ " " + "上旬");
						} else if (j == 2) {
							list.add(startYear + "-"
									+ Common.fillZero(String.valueOf(month))
									+ " " + "中旬");
						} else if (j == 3) {
							list.add(startYear + "-"
									+ Common.fillZero(String.valueOf(month))
									+ " " + "下旬");
						}
						if (period == 3) {
							start = 1;
							if (i == monthNum - 1) {// 最后一个
								end = end_period;
							}
							break;
						}
					}
				}
			}
		} else if (yearInt > 0) {
			int start_month = startMonth;// 第一次进入
			int end_month = 12;
			int gap_month = endMonth - startMonth;
			for (int i = 0; i <= yearInt; i++) {
				for (int j = start_month; j <= end_month; j++) {
					int month = (j % 12 == 0 ? 12 : j % 12);
					int year = startYear + i;
					int start = start_period;
					int end = 3;
					for (int k = start; k < end; k++) {
						int period = (k % 3 == 0 ? 3 : k % 3);
						if (k == 1) {
							list.add(year + "-"
									+ Common.fillZero(String.valueOf(month))
									+ " " + "上旬");
						} else if (k == 2) {
							list.add(year + "-"
									+ Common.fillZero(String.valueOf(month))
									+ " " + "中旬");
						} else if (k == 3) {
							list.add(year + "-"
									+ Common.fillZero(String.valueOf(month))
									+ " " + "下旬");
						}
						if (period == 3) {
							start = 1;
							if (k == gap_month - 1) {// 最后一个
								end = end_period;
							}
							break;
						}
					}
					if (month == 12) {
						start_month = 1;
						if (i == yearInt - 1) {// 最后一个
							end_month = endMonth;
						}
						break;
					}
				}
			}
		}
		return list;
	}

	/**
	 * 获取年列表 ****年 包含开始日期到结束日期的每一年
	 * 
	 * @param startTime
	 *            格式 2001
	 * @param endTime
	 *            格式 2008
	 * @return 格式：2007
	 */
	public static List getYList(String startTime, String endTime) {
		List list = new ArrayList();
		int startYear = Common.StringToInt(startTime);// 开始时间的年份
		int endYear = Common.StringToInt(endTime);// 结束时间的年份
		for (int i = startYear; i <= endYear; i++) {
			list.add(String.valueOf(i));
		}
		return list;
	}

	/**
	 * 获取某一季度的第一个月
	 * 
	 * @author season
	 * @param start
	 * @return
	 */
	public static String getSeasonStartTime(int start) {
		String month = "";
		String[] s1 = { "01", "02", "03" };// 第一季度
		String[] s2 = { "04", "05", "06" };// 第二季度
		String[] s3 = { "07", "08", "09" };// 第三季度
		String[] s4 = { "10", "11", "12" };// 第四季度
		switch (start) {
		case 1: {
			month = s1[0];
			break;
		}
		case 2: {
			month = s2[0];
			break;
		}
		case 3: {
			month = s3[0];
			break;
		}
		case 4: {
			month = s4[0];
			break;
		}
		}
		return month;
	}

	/**
	 * 获取某一季度的最后一个月
	 * 
	 * @author season
	 * @param start
	 * @return
	 */
	public static String getSeasonEndTime(int end) {
		String month = "";
		String[] s1 = { "01", "02", "03" };// 第一季度
		String[] s2 = { "04", "05", "06" };// 第二季度
		String[] s3 = { "07", "08", "09" };// 第三季度
		String[] s4 = { "10", "11", "12" };// 第四季度
		switch (end) {
		case 1: {
			month = s1[2];
			break;
		}
		case 2: {
			month = s2[2];
			break;
		}
		case 3: {
			month = s3[2];
			break;
		}
		case 4: {
			month = s4[2];
			break;
		}
		}
		return month;
	}

	/**
	 * 在日期为****-**后加-31
	 * 
	 * @author wendy
	 * @param outputTime
	 * @return
	 */
	public static String formateMonthEndTime(String outputTime) {

		return outputTime + "-31";
	}

	/**
	 * ****-** 上旬 输入格式为：yyyy-MM 上旬 以该旬第一天替换该旬并返回
	 * 
	 * @param time
	 *            格式 yyyy-MM 上旬
	 * @return yyyy-MM-dd
	 */
	public static String formateStartTenPeriod(String outputTime) {
		String time = outputTime.substring(0, 7);
		String period = outputTime.substring(8, outputTime.length());// 旬期
		if (period.equals("上旬")) {
			time = time + "-01";
		} else if (period.equals("中旬")) {
			time = time + "-11";
		} else if (period.equals("下旬")) {
			time = time + "-21";
		}
		return time;
	}

	/**
	 * 
	 * ****-** 上旬 输入格式为：yyyy-MM 上旬 以该旬最后一天替换该旬并返回
	 * 
	 * @param time
	 *            格式 yyyy-MM 上旬
	 * @return yyyy-MM-dd
	 */
	public static String formateEndTenPeriod(String outputTime) {
		String time = outputTime.substring(0, 7);
		String period = outputTime.substring(8, outputTime.length());// 旬期
		logger.error("period:" + period);
		if (period.equals("上旬")) {
			time = time + "-10";
		} else if (period.equals("中旬")) {
			time = time + "-20";
		} else if (period.equals("下旬")) {
			time = time + "-31";
		}
		return time;
	}

	/**
	 * ****-** 上旬 输入格式为：yyyy-MM 1 (1-上旬，2－中旬，3下旬)以该旬第一天替换该旬并返回
	 * 
	 * @param outputTime
	 *            格式 yyyy-MM 1
	 * @return yyyy-MM-dd
	 */
	public static String formateStartTenPeriod1(String outputTime) {
		String time = outputTime.substring(0, 7);
		String period = outputTime.substring(8, outputTime.length());// 旬期
		if (period.equals("1")) {
			time = time + "-01";
		} else if (period.equals("2")) {
			time = time + "-11";
		} else if (period.equals("3")) {
			time = time + "-21";
		}
		return time;
	}

	/**
	 * ****-** 上旬 输入格式为：yyyy-MM 1 (1-上旬，2－中旬，3下旬)以该旬最后一天替换该旬并返回
	 * 
	 * @param outputTime
	 *            格式 yyyy-MM 1
	 * @return yyyy-MM-dd
	 */
	public static String formateEndTenPeriod1(String outputTime) {
		String time = outputTime.substring(0, 7);
		String period = outputTime.substring(8, outputTime.length());// 旬期
		if (period.equals("1")) {
			time = time + "-10";
		} else if (period.equals("2")) {
			time = time + "-20";
		} else if (period.equals("3")) {
			time = time + "-31";
		}
		return time;
	}

	/**
	 * 把时间为 (XXXX 第几季度)获取该季度的第一个月
	 * 
	 * @param outputTime
	 *            格式：2001 2
	 * @return 格式：2001-4
	 */
	public static String formateSeasonStartTime(String outputTime) {
		String time = "";
		if (outputTime != null) {
			String year = outputTime.substring(0, 4);
			int i = Common.StringToInt(outputTime.substring(6, 7));// 季度数
			String month = Common.getSeasonStartTime(i);
			time = year + "-" + month;
		}
		return time;
	}

	/**
	 * 把时间为 (XXXX 第几季度)获取该季度的最后一个月最后一天
	 * 
	 * @param outputTime
	 *            格式：2001 2
	 * @return 格式：2001-6-31
	 */
	public static String formateSeasonEndTime(String outputTime) {

		String time = "";
		if (outputTime != null) {
			String year = outputTime.substring(0, 4);
			int i = Common.StringToInt(outputTime.substring(6, 7));// 季度数
			String month = Common.getSeasonEndTime(i);
			time = year + "-" + month + "-31";
		}
		return time;
	}

	/**
	 * 把时间为 (XXXX 第几季度)获取该季度的最后一个月最后一天
	 * 
	 * @param outputTime
	 *            格式：2001 2
	 * @return 格式：2001-6
	 */
	public static String formateSeasonEndTime2(String outputTime) {

		String time = "";
		if (outputTime != null) {
			String year = outputTime.substring(0, 4);
			int i = Common.StringToInt(outputTime.substring(6, 7));// 季度数

			String month = Common.getSeasonEndTime(i);
			time = year + "-" + month;
		}
		return time;
	}

	/**
	 * 为年份加上 "-12-31"
	 * 
	 * @param outputTime
	 *            为****
	 * @return
	 */
	public static String formateYearEndTime(String outputTime) {

		String time = outputTime + "-12" + "-31";
		return time;
	}

	/**
	 * 为年份加上 "-12"
	 * 
	 * @param outputTime
	 *            为****
	 * @return
	 */
	public static String formateYearEndTime2(String outputTime) {

		String time = outputTime + "-12";
		return time;
	}

	/**
	 * 获取两个时间相隔的天数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long calendarDayPlus(Date d1, Date d2) {
		Calendar cd1 = Calendar.getInstance();
		Calendar cd2 = Calendar.getInstance();
		cd1.setTime(d1);
		cd2.setTime(d2);
		long milis = calendarMilisPlus(cd1, cd2);
		milis = milis / 1000 / 60 / 60 / 24;
		return milis;
	}

	/**
	 * 获取两个时间相隔的小时数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long calendarHourPlus(Date d1, Date d2) {
		Calendar cd1 = Calendar.getInstance();
		Calendar cd2 = Calendar.getInstance();
		cd1.setTime(d1);
		cd2.setTime(d2);
		long milis = calendarMilisPlus(cd1, cd2);
		milis = milis / 1000 / 60 / 60;
		return milis;
	}

	/**
	 * 获取两个时间相隔的分钟数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long calendarMinutePlus(Date d1, Date d2) {
		Calendar cd1 = Calendar.getInstance();
		Calendar cd2 = Calendar.getInstance();
		cd1.setTime(d1);
		cd2.setTime(d2);
		long milis = calendarMilisPlus(cd1, cd2);
		milis = milis / 1000 / 60;
		return milis;
	}

	/**
	 * 获取两个时间相隔的分钟数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long calendarSecondPlus(Date d1, Date d2) {
		Calendar cd1 = Calendar.getInstance();
		Calendar cd2 = Calendar.getInstance();
		cd1.setTime(d1);
		cd2.setTime(d2);
		long milis = calendarMilisPlus(cd1, cd2);
		milis = milis / 1000;
		return milis;
	}

	/**
	 * 获取两个时间相隔的毫数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long calendarMilisPlus(Calendar d1, Calendar d2) {
		return d2.getTime().getTime() - d1.getTime().getTime();
	}

	/**
	 * 得到该天的下一天 日期格式为（yyyyMMdd）
	 * 
	 * @param sdf
	 * @param dateString
	 * @param calendarFlag
	 * @return
	 */
	public static String getDateNext(String sdf, String dateString,
			int calendarFlag) {
		Calendar now_Time = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat(sdf);
		try {
			now_Time.setTime(df.parse(dateString));
		} catch (Exception e) {
			logger.error("日期转换出错！");
		}
		now_Time.add(calendarFlag, 1);
		SimpleDateFormat sdNowDate = new SimpleDateFormat(sdf);
		return sdNowDate.format(now_Time.getTime());
	}

	public static String getVSdate(String sdate) {
		String vsdate = Common.getNow().substring(0, 10);
		if (sdate.length() == 8) {
			vsdate = sdate.substring(0, 4) + "-" + sdate.substring(4, 6) + "-"
					+ sdate.substring(6);
		}
		if (sdate.length() == 10) {
			vsdate = sdate.substring(0, 4) + "-" + sdate.substring(4, 6) + "-"
					+ sdate.substring(6, 8) + " " + sdate.substring(8) + ":00";
		}
		return vsdate;
	}

	/**
	 * 获取查询约束时间 当输入字符串包含的时间hh:mm:ss为空时 如果是开始时间将添加00:00:00 如果是结束时间 则添加23:59:59
	 * 
	 * @param sd
	 *            日期字符串 yyyy-MM-dd
	 * @param st
	 *            时间字符串 hh:mm:ss
	 * @param flag
	 *            标识 s-开始时间 如果st参数为空，则默认添加时间为 00:00:00 e-结束时间 如果st参数为空，则默认添加时间为
	 *            23:59:59
	 * @return
	 */
	public static String getSEDate(String sd, String st, String flag) {
		String now = getDateTimeFormatBy(new java.util.Date(), "yyyy-MM-dd");
		String s = " 00";
		String s1 = ":00:00";
		String str = now + s;
		if (flag.equals("e")) {
			s = " 23";
			s1 = ":59:59";
		}
		if (sd != null && sd != "") {
			if (st != null && st != "") {
				str = sd + " " + st;
			} else {
				str = sd + s;
			}
		} else {
			if (st != null && st != "") {
				str = now + " " + st;
			}
		}
		if (str.length() == 13) {
			str = str + s1;
		}
		return str;
	}

	/*
	 * 在此处添加新的日期时间类方法
	 */

	// /////////////////////////////////////END////////////////////////////////////////////////////
	/*
	 * ----------------------------------字符串操作类方法--------------------------------
	 * ------
	 */
	// /////////////////////////////////////Start////////////////////////////////////////////////////
	/**
	 * 补零，当输入字符串位数小于2时前面补0
	 * 
	 * @param s
	 * @return 参数设定:() 功能描述: 逻辑判断: 返回值:
	 */
	public static String fillZero(String s) {
		return s.length() == 1 ? s = "0" + s : s;
	}

	/**
	 * 截取一段内容在后面+....
	 * 
	 * @param s
	 * @param i
	 * @return <p>
	 *         参数设定:() 功能描述:逻辑判断: 返回值:
	 *         </P>
	 */
	public static String DisplayShort(String s, int i) {
		String s1 = "";
		int j = 0;
		// ********************当String s 为null时发生异常
		if (s != null) {
			j = s.length();
		}
		// ********************
		if (j > i) {
			s1 = s.substring(0, i);
			s1 = s1 + "...";
		} else {
			if (s != null) {
				s1 = s;
			}
		}
		return s1;
	}

	/**
	 * 截取一段内容在后面+....
	 * 
	 * @param s
	 * @param i
	 * @return <p>
	 *         参数设定:() 功能描述:逻辑判断: 返回值:
	 *         </P>
	 */
	public static String long2short(String str, int len) {
		if (str == null)
			return "";
		if (str.length() > len) {
			str = str.substring(0, len - 1) + "...";
			return str;
		} else
			return str;
	}

	/**
	 * 截取指定字符个数的内容
	 * 
	 * @param s
	 * @param i
	 * @return
	 */
	public static String DisplayShortContent(String s, int i) {
		String s1 = null;
		int j = 0;
		j = s.length();
		if (j > i)
			s1 = s.substring(0, i);
		else
			s1 = s;
		return s1;
	}

	/**
	 * @param s
	 * @return <p>
	 *         参数设定:() 功能描述:null字符转为空字符 逻辑判断: 返回值:
	 * @author:wendy </P>
	 */
	public static String KillNull(String s) {
		if (s != null) {
			if (s.equals("null"))
				return " ";
			else
				return s;
		} else {
			return " ";
		}
	}

	/**
	 * @function 判断字符串是否为null,空,""
	 * @author wendy
	 * @param str
	 * @return
	 */
	public static boolean judgeString(String str) {
		boolean flag = false;
		if (str != null && !(str.trim()).equals("")
				&& !str.equalsIgnoreCase("null")) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 转换编码 iso-8859-1 转换为GBK
	 * 
	 * @param s
	 * @return
	 */
	public static String convertToGB(String s) {
		if (s == null) {
			logger
					.error("cannot convert a null String!\nthis msg is from icprm.share.Covert.class");
			return s;
		}
		try {
			byte abyte0[] = s.getBytes("iso-8859-1");
			return new String(abyte0, "GBK");
		} catch (Exception exception) {
			logger.error(exception);
		}
		return s;
	}

	/**
	 * 转换编码 iso-8859-1 转换为utf-8
	 * 
	 * @param s
	 * @return
	 */
	public static String convertToUTF8(String s) {
		if (s == null) {
			logger
					.error("cannot convert a null String!\nthis msg is from icprm.share.Covert.class");
			return s;
		}
		try {
			byte abyte0[] = s.getBytes("iso-8859-1");
			return new String(abyte0, "utf-8");
		} catch (Exception exception) {
			logger.error(exception);
		}
		return s;
	}

	/**
	 * 转换编码 GBK 转换为iso-8859-1
	 * 
	 * @param s
	 * @return
	 */
	public static String convertGBKToISO(String s) {
		if (s == null)
			return s;
		try {
			byte abyte0[] = s.getBytes("GBK");
			return new String(abyte0, "iso-8859-1");
		} catch (Exception exception) {
			logger.error(exception);
		}
		return s;
	}

	/**
	 * 转换编码 utf-8 转换为iso-8859-1
	 * 
	 * @param s
	 * @return
	 */
	public static String convertUtf8ToISO(String s) {
		if (s == null)
			return s;
		try {
			byte abyte0[] = s.getBytes("utf-8");
			return new String(abyte0, "iso-8859-1");
		} catch (Exception exception) {
			logger.error(exception);
		}
		return s;
	}

	// /////////////////////////////////////END////////////////////////////////////////////////////
	/*
	 * ----------------------------------数字操作类方法----------------------------------
	 * ----
	 */
	// /////////////////////////////////////Start////////////////////////////////////////////////////
	/**
	 * 判断Double是否为空
	 * 
	 * @param d
	 * @return
	 */
	public static Double OutputToDouble(Double d) {
		if (d != null) {
			return d;
		} else {
			return new Double(1);
		}
	}

	/**
	 * 大Double转为小double
	 * 
	 * @param d
	 * @return
	 */
	public static double doubleValue(Double d) {
		double dd = 0;
		if (d != null)
			dd = d.doubleValue();
		return dd;
	}

	/**
	 * 返回Double的相反数
	 * 
	 * @param d
	 * @return
	 */
	public static Double DoubleToNegative(Double d) {
		Double dd = d;
		if (d != null) {
			dd = new Double(-d.doubleValue());
		}
		return dd;
	}

	/**
	 * 两个double相乘
	 * 
	 * @param d1
	 * @param d2
	 * @return Double
	 */
	public static Double DoubleMultToDouble(double d1, double d2) {
		return Double.valueOf(String.valueOf(d1 * d2));
	}

	/**
	 * 两个double相除
	 * 
	 * @param d1
	 * @param d2
	 * @return Double
	 */
	public static Double DoubleDivToDouble(double d1, double d2) {
		if (d2 != 0)
			return new Double(d1 / d2);
		else
			return new Double(0);
	}

	/**
	 * 两个Double相乘 ,返回double
	 * 
	 * @param d1
	 * @param d2
	 * @return double
	 */
	public static double DoubleMultToDouble(Double d1, Double d2) {

		if (d1 != null && d2 != null) {
			return d1.doubleValue() / d2.doubleValue();
		}

		return 0;
	}

	/**
	 * 两个double相减
	 * 
	 * @param d1
	 * @param d2
	 * @return Double
	 */
	public static Double DoubleSubtractToDouble(double d1, double d2) {
		return new Double(d1 - d2);
	}

	/**
	 * 两个Double相减
	 * 
	 * @param d1
	 * @param d2
	 * @return Double
	 */
	public static Double DoubleSubtractToDouble(Double d1, Double d2) {
		Double dd = null;
		if (d1 != null && d2 != null) {
			dd = new Double(d1.doubleValue() - d2.doubleValue());
		}
		return dd;
	}

	/**
	 * 两个double相加
	 * 
	 * @param d1
	 * @param d2
	 * @return Double
	 */
	public static Double DoubleAddToDouble(double d1, double d2) {
		return new Double(d1 + d2);
	}

	/**
	 * 两个Double相加
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double DoubleAddToDouble(Double d1, Double d2) {
		Double d = d1;
		if (d1 != null && d2 != null) {
			d = new Double(d1.doubleValue() + d2.doubleValue());
		}
		return d;
	}

	/**
	 * 把一个Integer类型加1
	 * 
	 * @param it
	 * @return
	 */
	public static Integer IntegerAddOne(Integer it) {
		int total = 0;
		if (it != null) {
			total = it.intValue() + 1;
		}
		return new Integer(total);
	}

	/**
	 * 数字小于0时前面加0 返回字符串
	 * 
	 * @param num
	 * @return
	 */
	public static String numberConvert(int num) {
		String s = String.valueOf(num);
		if (num < 10)
			s = "0" + s;
		return s;
	}

	/**
	 * 获取最大的数字
	 * 
	 * @param num
	 * @return
	 */
	public static int maxNum(int num[]) {
		int max = num[0];
		for (int i = 1; i < num.length; i++) {
			if (max < num[i]) {
				max = num[i];
			}
		}
		return max;
	}

	/**
	 * 比较两个数的大小
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int compareMax(int s1, int s2) {
		return s1 > s2 ? s1 : s2;
	}

	/**
	 * 当输入float小于0时返回0，否则返回本身
	 * 
	 * @param flo_Input
	 * @return
	 */
	public static float OutConvert(float flo_Input) {
		float flo_Output = 0.0f;
		if (flo_Input > 0.0f)
			flo_Output = flo_Input;
		return flo_Output;
	}

	/**
	 * 当输入字符串为null时返回0 否则返回本省
	 * 
	 * @param str_Input
	 * @return
	 */
	public static String OutConvertString2Int(String str_Input) {
		String str_Output = "0";
		if (str_Input != null)
			str_Output = str_Input;
		return str_Output;
	}

	// /////////////////////////////////////END////////////////////////////////////////////////////
	/*
	 * ----------------------------------类型转换类方法----------------------------------
	 * ----
	 */
	// /////////////////////////////////////Start////////////////////////////////////////////////////
	/**
	 * @param d
	 * @return <p>
	 *         参数设定:() 功能描述:Integer 类型转化为字符串 逻辑判断: 返回值:字符串，如果Integer为空，则&nbsp;
	 * @author:wendy </P>
	 */
	public static String OutConvert(Integer d) {
		String str = "";
		if (d != null) {
			str = String.valueOf(d);
		} else {
			str = "&nbsp;";
		}
		return str;
	}

	/**
	 * @param d
	 * @return <p>
	 *         参数设定:() 功能描述:把对象转化为字符串 逻辑判断: 返回值:字符串，如果对象为null，返回为：“--”
	 * @author:wendy </P>
	 */
	public static String OutConvert(Object d) {
		String str = "";
		if (d != null) {
			str = String.valueOf(d);
		} else {
			str = "--";
		}
		return str;
	}

	/**
	 * Integer数组转换为String数组
	 * 
	 * @param d
	 * @return
	 */
	public static String[] outConvert(Integer d[]) {
		String str = "";
		if (d != null) {
			for (int i = 0; i < d.length; i++) {
				str += (String.valueOf(d[i]) + ",");
			}
		}
		return str.split(",");
	}

	/**
	 * 格式化int 逻辑判断: 返回值: int类型，如果i为-1则返回0
	 * 
	 * @param i
	 * @return <p>
	 *         参数设定:() 功能描述:
	 * @author:wendy </P>
	 */
	public static int OutConvert(int i) {
		int j = i;
		if (j == -1)
			j = 0;
		return j;
	}

	/**
	 * 格式化String 如果为null这返回""，否则去除开始结束的空格
	 * 
	 * @param s
	 * @return
	 */
	public static String OutConvert(String s) {
		String s1 = s;
		if (s1 == null || s1.equalsIgnoreCase("null")) {
			s1 = "";
		} else {
			s1 = s.trim();
		}
		return s1;
	}

	/**
	 * 把Double转换成字符
	 * 
	 * @param d
	 * @return
	 */
	public static String OutConvert(Double d) {
		String str = "";
		if (d != null) {
			str = String.valueOf(d);
		} else {
			str = "&nbsp;";
		}
		return str;
	}

	/**
	 * String转换为int 转换失败返回0
	 * 
	 * @param s
	 * @return
	 */
	public static int StringToInt(String s) {
		int i = 0;
		if (s == null)
			i = 0;
		else
			try {
				i = Integer.parseInt(s);
			} catch (Exception _ex) {
			}
		return i;
	}

	/**
	 * String转换为int 转换失败返回-1
	 * 
	 * @param s
	 * @return
	 */
	public static int StringToInt2(String s) {
		int i = -1;
		if (s != null && !s.equals("")) {
			try {
				i = Integer.parseInt(s);
			} catch (Exception _ex) {
			}
		}
		return i;
	}

	/**
	 * String转换为Integer 转换失败返回0
	 * 
	 * @param s
	 * @return
	 */
	public static Integer StringToInteger(String s) {
		int i = 0;
		Integer It;
		if (s != null && !s.equals("") && !s.equalsIgnoreCase("null")) {
			try {
				i = Integer.parseInt(s);
			} catch (Exception _ex) {
			}
			It = new Integer(i);
		} else {
			It = new Integer(0);
		}
		return It;
	}

	/**
	 * Integer转换为int
	 * 
	 * @param i
	 * @return
	 */
	public static int IntegerToint(Integer i) {
		int j = Integer.parseInt(String.valueOf(i));
		return j;
	}

	/**
	 * String转换为Long 转换失败返回0
	 * 
	 * @param s
	 * @return
	 */
	public static Long StringToLong(String s) {
		Long i = new Long(0);
		if (s == null)
			i = new Long(0);
		else
			try {
				i = new Long(s);
			} catch (Exception _ex) {
			}
		return i;
	}

	/**
	 * String转换为Long 转换失败返回-1
	 * 
	 * @param s
	 * @return
	 */
	public static Long StringToLong2(String s) {
		Long i = new Long(-1);
		if (s == null)
			i = new Long(-1);
		else
			try {
				i = new Long(s);
			} catch (Exception _ex) {
			}
		return i;
	}

	/**
	 * String转换为Byte 转换失败返回0
	 * 
	 * @param s
	 * @return
	 */
	public static Byte StringToByte(String s) {
		Byte i = Byte.parseByte("0");
		if (s == null)
			i = Byte.parseByte("0");
		else
			try {
				i = Byte.parseByte(s);
			} catch (Exception _ex) {
			}
		return i;
	}

	/**
	 * String转换为float 转换失败返回0
	 * 
	 * @param s
	 * @return
	 */
	public static float StringToFloat(String s) {
		float i = 0;
		if (s == null)
			i = 0;
		else
			try {
				i = Float.parseFloat(s);
			} catch (Exception _ex) {
			}
		return i;
	}

	/**
	 * String转换为Short 转换失败返回0
	 * 
	 * @param s
	 * @return
	 */
	public static Short StringToShort(String s) {
		Short i = 0;
		if (s == null)
			i = 0;
		else
			try {
				i = Short.parseShort(s);
			} catch (Exception _ex) {
			}
		return i;
	}

	/**
	 * String转换为Short 转换失败返回-1
	 * 
	 * @param s
	 * @return
	 */
	public static Short StringToShort2(String s) {
		Short i = -1;
		if (s == null)
			i = -1;
		else
			try {
				i = Short.parseShort(s);
			} catch (Exception _ex) {
			}
		return i;
	}

	/**
	 * String转换为double 转换失败返回0
	 * 
	 * @param s
	 * @return
	 */
	public static double StringTodouble(String s) {
		double i = 0;
		if (s == null || s.matches("\\D") || s.equals(""))
			i = 0;
		else
			try {
				i = Double.parseDouble(s);
			} catch (Exception _ex) {
			}
		return i;
	}

	/**
	 * Double转换为double 转换失败返回0
	 * 
	 * @param s
	 * @return
	 */
	public static double DoubleTodouble(Double s) {
		double i = 0;
		if (s == null) {
			i = 0;
		} else {
			try {
				i = Common.StringTodouble(String.valueOf(s));
			} catch (Exception e) {

			}
		}
		return i;
	}

	/**
	 * 如果Double对象是空,返回0 如果Double对象不为空,返回Double对象的double类型的值
	 * 
	 * @author Jim
	 * @param doubleObj
	 * @return
	 */
	public static double DoubleValue(Double doubleObj) {
		double d = 0;
		if (doubleObj == null) {
			return d;
		} else {
			d = doubleObj.doubleValue();
		}
		return d;
	}

	/**
	 * String类型数组转换为String 中间加入分隔符,
	 * 
	 * @param as
	 * @return
	 */
	public static String StringArrayToSqlString(String as[]) {
		String s = "";
		if (as != null) {
			int i = as.length;
			for (int j = 0; j < i; j++)
				if (j == 0)
					s = s + "'" + as[j];
				else if (j == (i - 1))
					s = s + "','" + as[j] + "'";
				else
					s = s + "','" + as[j];
		}
		return s;
	}

	/**
	 * String类型数组转换为String 中间加入分隔符,
	 * 
	 * @param as
	 * @return
	 */
	public static String StringArrayToString(String as[]) {
		String s = "";
		if (as != null) {
			int i = as.length;
			for (int j = 0; j < i; j++)
				if (j == 0)
					s = s + as[j];
				else
					s = s + "," + as[j];
		}
		return s;
	}

	/**
	 * String类型数组转换为String 中间加入分隔符,
	 * 
	 * @param as
	 * @return
	 */
	public static String IntegerArrayToString(Integer as[]) {
		String s = "";
		if (as != null) {
			int i = as.length;
			for (int j = 0; j < i; j++)
				if (j == 0)
					s = s + as[j];
				else
					s = s + "," + as[j];
		}
		return s;
	}

	/**
	 * Double转换为Decimal
	 * 
	 * @param val
	 * @return
	 */
	public static BigDecimal DoubleToBigDecimal(Double val) {
		return new BigDecimal(val.doubleValue()).setScale(1,
				BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * Double转换为Decimal
	 * 
	 * @param val
	 * @return
	 */
	public static BigDecimal DoubleToBigDecimal2(Double val) {
		if (val != null) {
			return new BigDecimal(val.doubleValue()).setScale(1,
					BigDecimal.ROUND_HALF_UP);
		}
		return new BigDecimal(new Double(0).doubleValue())
				.setScale(BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * Double转换为Decimal
	 * 
	 * @param val
	 *            值
	 * @param len
	 *            小数位
	 * @return
	 */
	public static BigDecimal DoubleToBigDecimal(Double val, int len) {
		if (val != null) {
			return new BigDecimal(val.doubleValue()).setScale(len,
					BigDecimal.ROUND_HALF_UP);
		}
		return new BigDecimal(0);
	}

	/**
	 * 拆分以,分隔的字符串并把结果转换为Long类型放入List
	 * 
	 * @param s
	 * @return
	 */
	public static List<Long> stringToList(String s) {
		ArrayList<Long> slist = new ArrayList<Long>();
		String[] a = null;
		if (s != null && !s.equals("")) {
			a = s.split(",");
			for (int i = 0; i < a.length; i++) {
				slist.add(Common.StringToLong(a[i].trim()));
			}
		}
		return slist;
	}

	/**
	 * @param strs
	 *            字符串数组
	 * @param flag
	 *            标识符
	 * @return 若flag 为"1", 返回格式为 '1', '2', '3'; 否则返回格式 1, 2, 3
	 */
	public static String ArrayToString(String[] strs, String flag) {
		String str = "";
		String sp = "";
		StringBuffer strb = new StringBuffer();
		if (judgeString(flag) && flag.equals("1")) {
			sp = "'";
		}
		if (strs != null && strs.length > 0) {
			for (int i = 0; i < strs.length; i++) {
				strb.append(sp);
				strb.append(strs[i]);
				strb.append(sp);
				strb.append(",");
			}
		}
		str = strb.toString();
		if (Common.judgeString(str)) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * 若flag 为"1", 返回格式为 '1', '2', '3'; 否则返回格式 1, 2, 3
	 * 
	 * @param strs
	 *            1, 2, 3 格式的字符串
	 * @param flag
	 * @return
	 */
	public static String FormatString(String strs, String flag) {
		String str = "";
		String sp = "";
		StringBuffer strb = new StringBuffer();
		if (judgeString(flag) && flag.equals("1")) {
			strs = strs.trim();
			sp = "'";
			strb.append(sp);
			strb.append(strs.replaceAll(",", "','"));
			strb.append(sp);
			str = strb.toString();
		} else {
			str = strs;
		}
		return str;
	}

	/**
	 * @param list
	 *            若list保存的为对象，则抛出异常
	 * @param flag
	 *            标识符
	 * @return 若flag 为"1", 返回格式为 '1', '2', '3'; 否则返回格式 1, 2, 3
	 */
	public static String ListToString(List list, String flag) {
		try {
			String str = list.toString();
			if (judgeString(flag) && flag.equals("1")) {
				str = str.replaceAll("(\\[|\\])", "'");
				return str.replace(", ", "', '");
			} else {
				return str.substring(1, str.length() - 1);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "";
	}

	/**
	 * StringArrayToLongArray
	 * 
	 * @param s
	 * @return
	 */
	public static Long[] StringArrayToLongArray(String[] s) {
		Long[] l = new Long[s.length];
		for (int i = 0; i < s.length; i++) {
			try {
				l[i] = new Long(s[i]);
			} catch (Exception _ex) {
			}
		}
		return l;
	}

	/**
	 * StringArrayToIntegerArray
	 * 
	 * @param s
	 * @return
	 */
	public static Integer[] StringArrayToIntegerArray(String[] s) {
		Integer[] l = new Integer[s.length];
		for (int i = 0; i < s.length; i++) {
			try {
				l[i] = new Integer(s[i]);
			} catch (Exception _ex) {
			}
		}
		return l;
	}

	/**
	 * StringArrayToIntegerArray
	 * 
	 * @param s
	 * @return
	 */
	public static Short[] StringArrayToShortArray(String[] s) {
		Short[] l = new Short[s.length];
		for (int i = 0; i < s.length; i++) {
			try {
				l[i] = new Short(s[i]);
			} catch (Exception _ex) {
			}
		}
		return l;
	}

	/**
	 * ObjectArrayToLongArray
	 * 
	 * @param s
	 * @return
	 */
	public static Long[] ObjectArrayToLongArray(Object[] o) {
		Long[] l = new Long[o.length];
		for (int i = 0; i < o.length; i++) {
			try {
				l[i] = new Long(o[i].toString());
			} catch (Exception _ex) {
			}
		}
		return l;
	}

	// /////////////////////////////////////END////////////////////////////////////////////////////
	/*
	 * ----------------------------------SQL相关方法----------------------------------
	 * ----
	 */
	// /////////////////////////////////////Start////////////////////////////////////////////////////
	/**
	 * 组装插入的sql语句
	 * 
	 * @param s
	 * @param s1
	 * @param s2
	 * @param flag
	 * @return
	 * 
	 */
	public static String GetSqlInsert(String s, String s1, String s2,
			boolean flag) {
		String s3 = new String(")values(");
		String s4 = new String(") values (");
		String as[] = new String[2];
		if (s.equals(""))
			return "";
		try {
			if (s2.equals("")) {
				s2 = null;
				return s;
			}
			s2 = s2.replace('\'', '"');
		} catch (NullPointerException _ex) {
			return s;
		}
		s = replaceString(s, s3, s4);
		String s6 = new String("\\) values \\(");
		as = s.split(s6);
		if (as[1].equals(")")) {
			as[0] = as[0] + s1;
			if (flag)
				as[1] = "'" + s2 + "'" + ")";
			else
				as[1] = s2 + ")";
		} else {
			as[0] = as[0] + "," + s1;
			int i = as[1].length();
			as[1] = as[1].substring(0, i - 1) + ",";
			if (flag)
				as[1] = as[1] + "'" + s2 + "'" + ")";
			else
				as[1] = as[1] + s2 + ")";
		}
		String s7 = as[0] + ") values (" + as[1];
		return s7;
	}

	/**
	 * @param s
	 * @param s1
	 * @param s2
	 * @param s3
	 * @param s4
	 * @param flag
	 * @return <p>
	 *         参数设定:() 功能描述:组装查询sql语句 逻辑判断: 返回值:
	 * @author:wendy </P>
	 */
	public static String GetSqlSelect(String s, String s1, String s2,
			String s3, String s4, boolean flag) {
		if (s2.equals(""))
			return "";
		if (s.equals("")) {
			s = s + " where " + s1 + s3;
			if (flag)
				s = s + "'" + s2 + "'";
			else
				s = s + s2;
			return s;
		}
		s = s + " " + s4 + " " + s1 + s3;
		if (flag)
			s = s + "'" + s2 + "'";
		else
			s = s + s2;
		return s;
	}

	/**
	 * 组装查询sql语句
	 * 
	 * @param s
	 * @param s1
	 * @param s2
	 * @param flag
	 * @return
	 */
	public static String GetSqlSelect(String s, String s1, String s2,
			boolean flag) {
		if (s2.equals(""))
			return s;
		if (s.equals("")) {
			s = s + " where " + s1 + "=";
			if (flag)
				s = s + "'" + s2 + "'";
			else
				s = s + s2;
			return s;
		}
		s = s + " " + s1 + "=";
		if (flag)
			s = s + "'" + s2 + "' and";
		else
			s = s + s2;
		return s;
	}

	/**
	 * 组装更新sql语句
	 * 
	 * @param s
	 * @param s1
	 * @param s2
	 * @param flag
	 * @return
	 */
	public static String GetSqlUpdate(String s, String s1, String s2,
			boolean flag) {
		char c = '\'';
		char c1 = '"';
		boolean flag1 = false;
		if (s2 == null)
			s2 = "";
		if (s2.equals(""))
			if (flag)
				s2 = "";
			else
				s2 = "null";
		if (flag)
			s2 = s2.replace(c, c1);
		if (s == null)
			s = "";
		if (s.equals(""))
			s = s + s1 + "=";
		else
			s = s + "," + s1 + "=";
		if (flag)
			s = s + "'" + s2 + "'";
		else
			s = s + s2;
		return s;
	}

	/**
	 * 返回安全查询字符串 替换'为''
	 * 
	 * @param Content
	 * @return
	 */
	public String addSingleQuotes(String Content) {
		return Content.replaceAll("'", "''");
	}

	/**
	 * 添加null
	 * 
	 * @param str
	 * @return
	 */
	public static String clearNull(String str) {
		if (str == null)
			return null;
		str = replaceStr(str, "'null'", "null");
		str = replaceStr(str, "''", "null");
		str = replaceStr(str, ",,", ",null,");
		str = replaceStr(str, ",,", ",null,");
		str = replaceStr(str, ",)", ",null)");
		str = replaceStr(str, "(,", "(null,");
		return str;
	}

	/**
	 * 将sql语句中的？号转为相应的参数 ，其中参数为字符串型的
	 * 
	 * @author jim
	 * @date 2007-4-12
	 * @param sql
	 * @param str
	 * @return String
	 */
	public static String editSqlCode(String sql, String str) {

		try {
			// 截取从0到第一个问号的字符串加参数str，然后在加上第一问号以后的字符串
			sql = sql.substring(0, sql.indexOf("?")) + str
					+ sql.substring(sql.indexOf("?") + 1);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return sql;
	}

	/**
	 * 将sql语句中的？号转为相应的参数 ，其中参数为长整型的
	 * 
	 * @author jim
	 * @date 2007-4-12
	 * @param sql
	 * @param str
	 * @return String
	 */
	public static String editSqlCode(String sql, Integer str) {

		try {
			sql = sql.substring(0, sql.indexOf("?")) + str
					+ sql.substring(sql.indexOf("?") + 1);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return sql;
	}

	/**
	 * 将sql语句中的？号转为相应的参数 ，其中参数为整型的
	 * 
	 * @author jim
	 * @date 2007-4-12
	 * @param sql
	 * @param str
	 * @return String
	 */
	public static String editSqlCode(String sql, int str) { // 提取SQL语句，并把(int
		// str)传出来的值来代替？

		try {
			sql = sql.substring(0, sql.indexOf("?")) + str
					+ sql.substring(sql.indexOf("?") + 1);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return sql;
	}

	/**
	 * 将sql语句中的？号转为相应的参数 ，其中参数为整型的
	 * 
	 * @author jim
	 * @date 2007-4-12
	 * @param sql
	 * @param str
	 * @return String
	 */
	public static String editSqlCode(String sql, Float str) { // 提取SQL语句，并把(Float
		// str)传出来的值来代替？

		try {
			sql = sql.substring(0, sql.indexOf("?")) + str
					+ sql.substring(sql.indexOf("?") + 1);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return sql;
	}

	// /////////////////////////////////////END////////////////////////////////////////////////////
	/*
	 * ----------------------------------JAVASCRIPT类方法----------------------------
	 * ----------
	 */
	// /////////////////////////////////////Start////////////////////////////////////////////////////
	/**
	 * 关闭弹出窗口
	 */
	public static String closeWindow() {
		String s = new String("");
		s = s + "<script language='javascript'>" + "\n";
		s = s + "window.close()";
		s = s + "</script>";
		return s;
	}

	/**
	 * javascript alert输入字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String errorPrint(String s) {
		String s1 = new String("");
		s1 = s1 + "<script language='javascript'>" + "\n";
		s1 = s1 + "alert('" + s + "');" + "\n";
		s1 = s1 + "</script>";
		return s1;
	}

	/**
	 * 在javascript 内执行输入字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String executeJavaScript(String s) {
		String s1 = new String("");
		s1 = s1 + "<script language='javascript'>" + "\n";
		s1 = s1 + s + "\n";
		s1 = s1 + "</script>";
		return s1;
	}

	/**
	 * 在script 内执行输入字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String executeScript(String s) {
		String s1 = new String("");
		s1 = s1 + "<script>" + "\n";
		s1 = s1 + s + "\n";
		s1 = s1 + "</script>";
		return s1;
	}

	/**
	 * javascript 内 window.location.href= 在当前页面打开url链接
	 * 
	 * @param s
	 * @return
	 */
	public static String location(String s) {
		String s1 = new String("");
		s1 = s1 + "<script language='javascript'>" + "\n";
		s1 = s1 + "window.location.href='" + s + "';" + "\n";
		s1 = s1 + "</script>";
		return s1;
	}

	/**
	 * javascript 内 parent.location.href= 在父页面打开url链接
	 * 
	 * @param s
	 * @return
	 */
	public static String parentlocation(String s) {
		String s1 = new String("");
		s1 = s1 + "<script language='javascript'>" + "\n";
		s1 = s1 + "parent.location.href='" + s + "';" + "\n";
		s1 = s1 + "</script>";
		return s1;
	}

	/**
	 * javascript 内 window.open 在弹出窗口打开链接
	 * 
	 * @param s
	 * @return
	 */
	public static String newWindow(String s, String s1, String s2) {
		String s3 = new String("");
		s3 = s3 + "<script language='javascript'>" + "\n";
		s3 = s3 + "window.open('" + s + "'" + ",'" + s1 + "'" + ",'" + s2
				+ "');" + "\n";
		s3 = s3 + "</script>";
		return s3;
	}

	// /////////////////////////////////////END////////////////////////////////////////////////////
	/*
	 * ----------------------------------HTML相关方法--------------------------------
	 * ------
	 */
	// /////////////////////////////////////Start////////////////////////////////////////////////////
	/**
	 * 返回字符串的网页表现形式 替换"\n"为"<br>
	 * " " "为"&nbsp;"
	 */
	public static String addBr(String Content) {
		int aaa = 0;
		String makeContent = new String();
		Content = Content.replaceAll("\n", "<br>");
		aaa = 0;
		makeContent = new String();
		makeContent = Content.replaceAll(" ", "&nbsp;");
		return makeContent;
	}

	/**
	 * 用于保存页面刷新时未持久化的值
	 * 
	 * @param requestValue
	 * @param persistenceValue
	 * @return
	 */
	public static String getTextInputValue(String requestValue,
			String persistenceValue) {
		String textInputValue = "";
		if (requestValue == null) {
			if (persistenceValue != null)
				textInputValue = persistenceValue;
		} else if (requestValue != null)
			textInputValue = requestValue;
		return textInputValue;
	}

	/**
	 * 用于保存页面刷新时未持久化的值
	 * 
	 * @param requestValue
	 * @param persistenceValue
	 * @return
	 */
	public static String getSelectInputString(String requestValue,
			String persistenceValue) {
		String selectInputString = "0";
		if (requestValue == null) {
			if (persistenceValue != null)
				selectInputString = persistenceValue;
		} else if (requestValue != null)
			selectInputString = requestValue;
		return selectInputString;
	}

	/**
	 * 用于保存页面刷新时未持久化的值
	 * 
	 * @param requestValue
	 * @param persistenceValue
	 * @return
	 */
	public static int getSelectInputInt(String requestValue,
			String persistenceValue) {
		int selectInputInt = 0;
		if (requestValue == null) {
			if (persistenceValue != null)
				selectInputInt = Integer.parseInt(persistenceValue);
		} else if (requestValue != null)
			selectInputInt = Integer.parseInt(requestValue);
		return selectInputInt;
	}

	/**
	 * 用于保存页面刷新时未持久化的值
	 * 
	 * @param requestValue
	 * @param persistenceValue
	 * @return
	 */
	public static int getSetSelect(String requestValue, String persistenceValue) {
		if (requestValue == null) {
			if (Integer.parseInt(persistenceValue) > 0) {
				return Integer.parseInt(persistenceValue);
			} else
				return -1;
		} else if (requestValue != null && !requestValue.equals("")) {
			return Integer.parseInt(requestValue);
		}
		return -1;
	}

	// /////////////////////////////////////END////////////////////////////////////////////////////
	/*
	 * ----------------------------------验证类方法------------------------------------
	 * --
	 */
	// /////////////////////////////////////Start////////////////////////////////////////////////////
	/**
	 * 判断Email是否合法
	 */
	public boolean checkEmail(String s) {
		String s1 = new String("");
		int i = s.indexOf(46) + 1;
		int j = s.length() - s.indexOf(46);
		return s.equals("") || s.indexOf(64) == -1 || s.indexOf(46) == -1 ? false
				: false;
	}

	/**
	 * 判断字符串是否为null 或 ""
	 * 
	 * @param s
	 * @return
	 */
	public static boolean IsNullOrEmpty(String s) {
		return (s == null || s.trim().length() == 0);
	}

	// /////////////////////////////////////END////////////////////////////////////////////////////
	/*
	 * ----------------------------------计量单位转换方法--------------------------------
	 * ------
	 */
	// /////////////////////////////////////Start////////////////////////////////////////////////////
	/**
	 * MoneyFormat
	 * 
	 * @param money
	 * @return
	 */
	public static String MoneyFormat(String money) {
		String moneyformat = "";
		NumberFormat numformat = NumberFormat.getCurrencyInstance(new Locale(
				"zh", "CN"));
		if (money != null && !money.equals("")) {
			moneyformat = numformat.format(Double.parseDouble(money));
		} else {
			return moneyformat;
		}
		return moneyformat;
	}

	/**
	 * MoneyFormat
	 * 
	 * @param money
	 * @return
	 */
	public static String MoneyFormat(double money) {
		String moneyformat = "";
		NumberFormat numformat = NumberFormat.getCurrencyInstance(new Locale(
				"zh", "CN"));

		moneyformat = numformat.format(money);
		return moneyformat;
	}

	/**
	 * MoneyFormat
	 * 
	 * @param money
	 * @return
	 */
	public static String MoneyFormat(float money) {
		String moneyformat = "";
		NumberFormat numformat = NumberFormat.getCurrencyInstance(new Locale(
				"zh", "CN"));

		moneyformat = numformat.format(money);
		return moneyformat;
	}

	/**
	 * s:表示自身的单位 m:表示转化的目标单位 重量单位转换
	 * 
	 * @author wendy
	 * @param var
	 * @param s
	 * @param m
	 * @return
	 */
	public static double changUnit(double var, String s, String m) {
		if (s.equalsIgnoreCase("T") || s.equals("吨")) {
			if (m.equalsIgnoreCase("kg") || m.equals("千克")) {
				var = var * 1000;
			}
			if (m.equalsIgnoreCase("g") || m.equals("克")) {
				var = var * 1000000;
			}
		}
		if (s.equalsIgnoreCase("kg") || s.equals("千克")) {
			if (m.equalsIgnoreCase("T") || m.equals("吨")) {
				var = var / 1000;
			}
			if (m.equalsIgnoreCase("g") || m.equals("克")) {
				var = var * 1000;
			}
		}
		if (s.equalsIgnoreCase("g") || s.equals("克")) {
			if (m.equalsIgnoreCase("T") || m.equals("吨")) {
				var = var / 1000000;
			}
			if (m.equalsIgnoreCase("kg") || m.equals("千克")) {
				var = var / 1000;
			}
		}
		return var;
	}

	/**
	 * 重量单位转换
	 * 
	 * @param var
	 * @param type
	 *            t||吨||g||克
	 * @return 单位：千克
	 */
	public static double unitToKG(double var, String type) {
		if (type.equalsIgnoreCase("t") || type.equals("吨"))
			return var * 1000;
		if (type.equalsIgnoreCase("g") || type.equals("克"))
			return var / 1000;
		return var;
	}

	/**
	 * 重量单位转换
	 * 
	 * @param var
	 * @param type
	 *            kg||千克||公斤||g||克
	 * @return 单位：吨
	 */
	public static double UnitToT(double var, String type) {
		if (type.equalsIgnoreCase("kg") || type.equals("千克")
				|| type.equals("公斤"))
			return var / 1000;
		if (type.equalsIgnoreCase("g") || type.equals("克"))
			return var / 1000000;
		return var;
	}

	// /////////////////////////////////////END////////////////////////////////////////////////////
	/*
	 * ----------------------------------其他方法------------------------------------
	 * --
	 */
	// /////////////////////////////////////Start////////////////////////////////////////////////////
	public static String PagePart(int i, int j, int k, String s) {
		int l = chkPage(i, j, k);
		if (i != 0 && j * k - i >= k)
			j--;
		if (l == 1) {
			s = s + " limit " + (j - 1) * k + "," + k;
			return s;
		}
		if (l != 1 && l != 1000 && l != 1001) {
			s = s + " limit " + (j - 1) * k + "," + l;
			return s;
		} else {
			return "";
		}
	}

	public static int chkPage(int i, int j, int k) {
		if (j * k <= 0)
			return 1000;
		if ((j - 1) * k > i)
			return 1001;
		if (j * k > i && j * k - i < k) {
			int l = k - (j * k - i);
			return l;
		} else {
			return 1;
		}
	}

	/**
	 * 获取颜色
	 * 
	 * @param i
	 * @param s
	 * @param s1
	 * @return
	 */
	public static String getColor(int i, String s, String s1) {
		if (i % 2 == 0)
			return s;
		else
			return s1;
	}

	/**
	 * 返回随机int类型数字
	 * 
	 * @return
	 */
	public int getRandom() {
		Random random = new Random();
		int i = random.nextInt();
		return i;
	}

	/**
	 * 获取随机字符串
	 * 
	 * @param size
	 * @return
	 */
	public static String getRandomString(int size) {// 随机字符串
		char[] c = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'q',
				'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
				'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
		Random random = new Random(); // 初始化随机数产生器
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			sb.append(c[Math.abs(random.nextInt()) % c.length]);
		}
		return sb.toString();
	}

	/**
	 * 用法未知...
	 * 
	 * @param s
	 * @param org
	 * @param ob
	 * @return
	 */

	public static String replaceStr(String s, String org, String ob) {
		String newString = "";
		int first = 0;
		while (s.indexOf(org) != -1) {
			first = s.indexOf(org);
			if (first != s.length()) {
				newString = newString + s.substring(0, first) + ob;
				s = s.substring(first + org.length(), s.length());
			}
		}

		newString = newString + s;
		return newString;
	}

	/**
	 * 用法未知...
	 * 
	 * @param s
	 * @param as
	 * @return
	 */
	public static String getResultName(String s, String as[]) {
		String s1 = "";
		if (s != null) {
			int i = 0;
			int j;
			for (j = s.indexOf(","); j != -1;)
				if (!s.equals("")) {
					String s2 = s.substring(i, j);
					int k = Integer.parseInt(s2);
					String s5 = as[k];
					if (i != 0)
						s1 = s1 + "," + s5;
					else
						s1 = s1 + s5;
					i = j + 1;
					j = s.indexOf(",", j + 1);
				}

			if (j == -1)
				if (i != 0) {
					String s3 = s.substring(i);
					if (!s3.equals("")) {
						int l = Integer.parseInt(s3);
						String s6 = as[l];
						s1 = s1 + "," + s6;
					}
				} else {
					String s4 = s.substring(i);
					if (!s4.equals("")) {
						int i1 = Integer.parseInt(s4);
						String s7 = as[i1];
						s1 = s1 + s7;
					}
				}
		}
		return s1;
	}

	/**
	 * 用法未知...
	 * 
	 * @param s
	 * @param s1
	 * @return
	 */
	public ArrayList getSeperate(String s, String s1) {
		ArrayList arraylist = new ArrayList();
		int i = 0;
		int k = s.indexOf(s1);
		boolean flag2;
		for (flag2 = false; k != -1; flag2 = true) {
			int j = k;
			String s2 = s.substring(i, j);
			arraylist.add(s2);
			i = k + 1;
			k = s.indexOf(s1, k + 1);
		}

		if (flag2 && k == -1) {
			String s3 = s.substring(i);
			arraylist.add(s3);
		}
		return arraylist;
	}

	/**
	 * 用法未知...
	 * 
	 * @param s
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static String replaceString(String s, String s1, String s2) {
		StringBuffer stringbuffer = new StringBuffer(s);
		int i = 0;
		int j = 0;
		int k = s1.length();
		if (k > 0) {
			int l = k - s2.length();
			while ((i = s.indexOf(s1, i)) != -1) {
				stringbuffer.replace(i - j, (i - j) + k, s2);
				i += k;
				j += l;
			}
		}
		return stringbuffer.toString();
	}

	public static String getFormateString(String str) {
		String str2[] = Common.OutConvert(str).split(",");
		String s = "";
		if (str2 != null && str2.length >= 1) {
			for (int i = 0; i < str2.length; i++) {
				s += "'" + str2[i] + "',";
			}
		}
		return s.equals("") ? "" : s.substring(0, s.length() - 1);
	}
	
	public static String getFormateHour(String str) {
		String s = OutConvert(str);
		return s.equals("") ? "" : s.substring(0, 14) + "00:00";
	}

	public static String getFormateNum(String str) {
		String str2[] = Common.OutConvert(str).split(",");
		String s = "";
		if (str2 != null && str2.length >= 1) {
			for (int i = 0; i < str2.length; i++) {
				s += str2[i] + ",";
			}
		}
		return s.equals("") ? "0" : s.substring(0, s.length() - 1);
	}

	/**
	 * 
	 * @param queryDateStart
	 * @param queryDateEnd
	 * @param timeLevel
	 * @param isKuaDu
	 * @return
	 */
	public static String buildDateTimeCompareSql(String queryDateStart,
			String queryDateEnd, int timeLevel, String... asName) {
		StringBuffer sql = new StringBuffer(150);
		String[] dataArrStart = null;
		String yearStart = "";
		int monthStart = 0;
		int dayStart = 0;
		int hourStart = 0;
		String weekStart = "";

		String[] dataArrEnd = null;
		String yearEnd = "";
		int monthEnd = 0;
		int dayEnd = 0;
		int hourEnd = 0;
		String weekEnd = "";

		boolean asNameIsNull = getIsNull(asName);

		if (asNameIsNull) {
			switch (timeLevel) {
			case 1:
				String[] timeArrStart = queryDateStart.split(" ");
				dataArrStart = timeArrStart[0].split("-");
				yearStart = dataArrStart[0];
				monthStart = getIntFormatStr(dataArrStart[1]);
				dayStart = getIntFormatStr(dataArrStart[2]);
				hourStart = getIntFormatStr(timeArrStart[1].split(":")[0]);

				String[] timeArrEnd = queryDateEnd.split(" ");
				dataArrEnd = timeArrEnd[0].split("-");
				yearEnd = dataArrEnd[0];
				monthEnd = getIntFormatStr(dataArrEnd[1]);
				dayEnd = getIntFormatStr(dataArrEnd[2]);
				hourEnd = getIntFormatStr(timeArrEnd[1].split(":")[0]);

				if (yearStart.equals(yearEnd)) {
					sql.append(" and ").append(asName[0]).append(".intYear=")
							.append(yearStart);
					if (monthStart == monthEnd) {
						sql.append(" and ").append(asName[0]).append(
								".intMonth=").append(monthStart);
						if (dayStart == dayEnd) {
							sql.append(" and ").append(asName[0]).append(
									".intDay=").append(dayStart);
							sql.append(" and ").append(asName[0]).append(
									".intHour>=").append(hourStart);

							sql.append(" and ").append(asName[0]).append(
									".intHour<=").append(hourEnd);
						} else {
							sql.append(" and (( ").append(asName[0]).append(
									".intDay>").append(dayStart);
							sql.append(" and ").append(asName[0]).append(
									".intHour>=").append(hourStart);
							sql.append(" ) or (").append(asName[0]).append(
									".intDay>").append(dayStart);
							sql.append(" and ").append(asName[0]).append(
									".intDay<").append(dayEnd).append(")");
							sql.append(" or (").append(asName[0]).append(
									".intHour<=").append(hourEnd);
							sql.append(" and ").append(asName[0]).append(
									".intDay=").append(dayEnd).append("))");
						}
					} else {
						sql.append(" and ((").append(asName[0]).append(
								".intMonth=").append(monthStart);
						sql.append(" and ").append(asName[0])
								.append(".intDay=").append(dayStart);
						sql.append(" and ").append(asName[0]).append(
								".intHour>=").append(hourStart);
						sql.append(" ) or (").append(asName[0]).append(
								".intMonth>").append(monthStart);
						sql.append(" and ").append(asName[0]).append(
								".intMonth<").append(monthEnd);
						sql.append(" ) or (").append(asName[0]).append(
								".intMonth=").append(monthStart);
						sql.append(" and ").append(asName[0])
								.append(".intDay>").append(dayStart);
						sql.append(" ) or (").append(asName[0]).append(
								".intMonth=").append(monthEnd);
						sql.append(" and ").append(asName[0])
								.append(".intDay<").append(dayEnd);
						sql.append(" ) or (").append(asName[0]).append(
								".intMonth=").append(monthEnd);
						sql.append(" and ").append(asName[0])
								.append(".intDay=").append(dayEnd);
						sql.append(" or ").append(asName[0]).append(
								".intHour>=").append(hourEnd).append("))");
					}
				} else {
					sql.append(" and ").append(asName[0]).append(".intYear=")
							.append(yearStart);
					sql.append(" and (").append(asName[0]).append(".intMonth=")
							.append(monthStart);
					sql.append(" and ").append(asName[0]).append(".intDay=")
							.append(dayStart);
					sql.append(" and ").append(asName[0]).append(".intHour>=")
							.append(hourStart);
					sql.append(" or (").append(asName[0]).append(".intMonth=")
							.append(monthStart);
					sql.append(" and ").append(asName[0]).append(".intDay>")
							.append(dayStart).append(")");
					sql.append(" or (").append(asName[0]).append(".intMonth>")
							.append(monthStart).append("))");

					sql.append(" or (").append(asName[0]).append(".intYear>")
							.append(yearStart);
					sql.append(" and ").append(asName[0]).append(".intYear<")
							.append(yearEnd);

					sql.append(" ) or (").append(asName[0]).append(".intYear=")
							.append(yearEnd);
					sql.append(" and (").append(asName[0]).append(".intMonth<")
							.append(monthEnd);
					sql.append(" or (").append(asName[0]).append(".intMonth=")
							.append(monthEnd);
					sql.append(" and ").append(asName[0]).append(".intDay=")
							.append(dayEnd);
					sql.append(" and ").append(asName[0]).append(".intHour<=")
							.append(hourEnd).append(")");
					sql.append(" or (").append(asName[0]).append(".intMonth<")
							.append(monthEnd);
					sql.append(" and ").append(asName[0]).append(".intDay<")
							.append(dayEnd).append(")))");
				}

				break;
			case 2:
				dataArrStart = queryDateStart.split("-");
				yearStart = dataArrStart[0];
				monthStart = getIntFormatStr(dataArrStart[1]);
				dayStart = getIntFormatStr(dataArrStart[2]);

				dataArrEnd = queryDateEnd.split("-");
				yearEnd = dataArrEnd[0];
				monthEnd = getIntFormatStr(dataArrEnd[1]);
				dayEnd = getIntFormatStr(dataArrEnd[2]);

				if (yearStart.equals(yearEnd)) {
					sql.append(" and ").append(asName[0]).append(".intYear=")
							.append(yearStart);
					if (monthStart == monthEnd) {
						sql.append(" and ").append(asName[0]).append(
								".intMonth=").append(monthStart);
						sql.append(" and ").append(asName[0]).append(
								".intDay>=").append(dayStart);

						sql.append(" and ").append(asName[0]).append(
								".intDay<=").append(dayEnd);
					} else {
						sql.append(" and ((").append(asName[0]).append(
								".intMonth=").append(monthStart);
						sql.append(" and ").append(asName[0]).append(
								".intDay>=").append(dayStart);
						sql.append(" ) or (").append(asName[0]).append(
								".intMonth>").append(monthStart);
						sql.append(" and ").append(asName[0]).append(
								".intMonth<").append(monthEnd);
						sql.append(" ) or (").append(asName[0]).append(
								".intMonth=").append(monthEnd);
						sql.append(" and ").append(asName[0]).append(
								".intDay<=").append(dayEnd).append("))");
					}
				} else {
					sql.append(" and ").append(asName[0]).append(".intYear=")
							.append(yearStart);
					sql.append(" and ((").append(asName[0])
							.append(".intMonth=").append(monthStart);
					sql.append(" and ").append(asName[0]).append(".intDay>=")
							.append(dayStart).append(")");
					sql.append(" or (").append(asName[0]).append(".intMonth>")
							.append(monthStart).append("))");
					sql.append(" or (").append(asName[0]).append(".intYear>")
							.append(yearStart);
					sql.append(" and ").append(asName[0]).append(".intYear<")
							.append(yearEnd);
					sql.append(" ) or (").append(asName[0]).append(".intYear=")
							.append(yearEnd);
					sql.append(" and (").append(asName[0]).append(".intMonth<")
							.append(monthEnd);
					sql.append(" or (").append(asName[0]).append(".intMonth=")
							.append(monthEnd);
					sql.append(" and ").append(asName[0]).append(".intDay<=")
							.append(dayEnd).append(")))");
				}
				break;
			case 3:
				dataArrStart = queryDateStart.split("-");
				yearStart = dataArrStart[0];
				weekStart = dataArrStart[1];

				dataArrEnd = queryDateEnd.split("-");
				yearEnd = dataArrEnd[0];
				weekEnd = dataArrEnd[1];

				if (yearStart.equals(yearEnd)) {
					sql.append(" and ").append(asName[0]).append(".intYear=")
							.append(yearStart);
					sql.append(" and ").append(asName[0]).append(".intWeek>=")
							.append(weekStart);
					sql.append(" and ").append(asName[0]).append(".intWeek<=")
							.append(weekEnd);
				} else {

					sql.append(" and (").append(asName[0]).append(".intYear=")
							.append(yearStart);
					sql.append(" and ").append(asName[0]).append(".intWeek>=")
							.append(weekStart);
					sql.append(" ) or ( ").append(asName[0]).append(
							".intWeek<=").append(weekEnd);
					sql.append(" and ").append(asName[0]).append(".intYear=")
							.append(yearEnd);
					sql.append(" ) or ( ").append(asName[0])
							.append(".intYear>").append(yearStart);
					sql.append(" and ").append(asName[0]).append(".intYear<")
							.append(yearEnd).append(")");
				}

				break;
			case 4:
				dataArrStart = queryDateStart.split("-");
				yearStart = dataArrStart[0];
				monthStart = getIntFormatStr(dataArrStart[1]);

				dataArrEnd = queryDateEnd.split("-");
				yearEnd = dataArrEnd[0];
				monthEnd = getIntFormatStr(dataArrEnd[1]);

				if (yearStart.equals(yearEnd)) {
					sql.append(" and ").append(asName[0]).append(".intYear=")
							.append(yearStart);
					sql.append(" and ").append(asName[0]).append(".intMonth>=")
							.append(monthStart);

					sql.append(" and ").append(asName[0]).append(".intMonth<=")
							.append(monthEnd);
				} else {
					sql.append(" and ").append(asName[0]).append(".intYear=")
							.append(yearStart);
					sql.append(" and ").append(asName[0]).append(".intMonth>=")
							.append(monthStart);
					sql.append(" or (").append(asName[0]).append(".intYear>")
							.append(yearStart);
					sql.append(" and ").append(asName[0]).append(".intYear<")
							.append(yearEnd);
					sql.append(" ) or (").append(asName[0]).append(".intYear=")
							.append(yearEnd);
					sql.append(" and ").append(asName[0]).append(".intMonth<=")
							.append(monthEnd).append(")");
				}
				break;
			}
			// ///////////////
		} else {
			switch (timeLevel) {
			case 1:
				String[] timeArrStart = queryDateStart.split(" ");
				dataArrStart = timeArrStart[0].split("-");
				yearStart = dataArrStart[0];
				monthStart = getIntFormatStr(dataArrStart[1]);
				dayStart = getIntFormatStr(dataArrStart[2]);
				hourStart = getIntFormatStr(timeArrStart[1].split(":")[0]);

				String[] timeArrEnd = queryDateEnd.split(" ");
				dataArrEnd = timeArrEnd[0].split("-");
				yearEnd = dataArrEnd[0];
				monthEnd = getIntFormatStr(dataArrEnd[1]);
				dayEnd = getIntFormatStr(dataArrEnd[2]);
				hourEnd = getIntFormatStr(timeArrEnd[1].split(":")[0]);

				if (yearStart.equals(yearEnd)) {
					sql.append(" and intYear=").append(yearStart);
					if (monthStart == monthEnd) {
						sql.append(" and intMonth=").append(monthStart);
						if (dayStart == dayEnd) {
							sql.append(" and intDay=").append(dayStart);
							sql.append(" and intHour>=").append(hourStart);

							sql.append(" and intHour<=").append(hourEnd);
						} else {
							sql.append(" and (( intDay>").append(dayStart);
							sql.append(" and intHour>=").append(hourStart);
							sql.append(" ) or (intDay>").append(dayStart);
							sql.append(" and intDay<").append(dayEnd).append(
									")");
							sql.append(" or (intHour<=").append(hourEnd);
							sql.append(" and intDay=").append(dayEnd).append(
									"))");
						}
					} else {
						sql.append(" and ((intMonth=").append(monthStart);
						sql.append(" and intDay=").append(dayStart);
						sql.append(" and intHour>=").append(hourStart);
						sql.append(" ) or (intMonth>").append(monthStart);
						sql.append(" and intMonth<").append(monthEnd);
						sql.append(" ) or (intMonth=").append(monthStart);
						sql.append(" and intDay>").append(dayStart);
						sql.append(" ) or (intMonth=").append(monthEnd);
						sql.append(" and intDay<").append(dayEnd);
						sql.append(" ) or (intMonth=").append(monthEnd);
						sql.append(" and intDay=").append(dayEnd);
						sql.append(" or intHour>=").append(hourEnd)
								.append("))");
					}
				} else {
					sql.append(" and intYear=").append(yearStart);
					sql.append(" and (intMonth=").append(monthStart);
					sql.append(" and intDay=").append(dayStart);
					sql.append(" and intHour>=").append(hourStart);
					sql.append(" or (intMonth=").append(monthStart);
					sql.append(" and intDay>").append(dayStart).append(")");
					sql.append(" or (intMonth>").append(monthStart)
							.append("))");

					sql.append(" or (intYear>").append(yearStart);
					sql.append(" and intYear<").append(yearEnd);

					sql.append(" ) or (intYear=").append(yearEnd);
					sql.append(" and (intMonth<").append(monthEnd);
					sql.append(" or (intMonth=").append(monthEnd);
					sql.append(" and intDay=").append(dayEnd);
					sql.append(" and intHour<=").append(hourEnd).append(")");
					sql.append(" or (intMonth<").append(monthEnd);
					sql.append(" and intDay<").append(dayEnd).append(")))");
				}

				break;
			case 2:
				dataArrStart = queryDateStart.split("-");
				yearStart = dataArrStart[0];
				monthStart = getIntFormatStr(dataArrStart[1]);
				dayStart = getIntFormatStr(dataArrStart[2]);

				dataArrEnd = queryDateEnd.split("-");
				yearEnd = dataArrEnd[0];
				monthEnd = getIntFormatStr(dataArrEnd[1]);
				dayEnd = getIntFormatStr(dataArrEnd[2]);

				if (yearStart.equals(yearEnd)) {
					sql.append(" and intYear=").append(yearStart);
					if (monthStart == monthEnd) {
						sql.append(" and intMonth=").append(monthStart);
						sql.append(" and intDay>=").append(dayStart);

						sql.append(" and intDay<=").append(dayEnd);
					} else {
						sql.append(" and ((intMonth=").append(monthStart);
						sql.append(" and intDay>=").append(dayStart);
						sql.append(" ) or (intMonth>").append(monthStart);
						sql.append(" and intMonth<").append(monthEnd);
						sql.append(" ) or (intMonth=").append(monthEnd);
						sql.append(" and intDay<=").append(dayEnd).append("))");
					}
				} else {
					sql.append(" and intYear=").append(yearStart);
					sql.append(" and ((intMonth=").append(monthStart);
					sql.append(" and intDay>=").append(dayStart).append(")");
					sql.append(" or (intMonth>").append(monthStart)
							.append("))");
					sql.append(" or (intYear>").append(yearStart);
					sql.append(" and intYear<").append(yearEnd);

					sql.append(" ) or (intYear=").append(yearEnd);
					sql.append(" and (intMonth<").append(monthEnd);
					sql.append(" or (intMonth=").append(monthEnd);
					sql.append(" and intDay<=").append(dayEnd).append(")))");
				}
				break;
			case 3:
				dataArrStart = queryDateStart.split("-");
				yearStart = dataArrStart[0];
				weekStart = dataArrStart[1];

				dataArrEnd = queryDateEnd.split("-");
				yearEnd = dataArrEnd[0];
				weekEnd = dataArrEnd[1];

				if (yearStart.equals(yearEnd)) {
					sql.append(" and intYear=").append(yearStart);
					sql.append(" and intWeek>=").append(weekStart);
					sql.append(" and intWeek<=").append(weekEnd);
				} else {

					sql.append(" and (intYear=").append(yearStart);
					sql.append(" and intWeek>=").append(weekStart);
					sql.append(" ) or ( intWeek<=").append(weekEnd);
					sql.append(" and intYear=").append(yearEnd);
					sql.append(" ) or ( intYear>").append(yearStart);
					sql.append(" and intYear<").append(yearEnd);
				}

				break;
			case 4:
				dataArrStart = queryDateStart.split("-");
				yearStart = dataArrStart[0];
				monthStart = getIntFormatStr(dataArrStart[1]);

				dataArrEnd = queryDateEnd.split("-");
				yearEnd = dataArrEnd[0];
				monthEnd = getIntFormatStr(dataArrEnd[1]);

				if (yearStart.equals(yearEnd)) {
					sql.append(" and intYear=").append(yearStart);
					sql.append(" and intMonth>=").append(monthStart);

					sql.append(" and intMonth<=").append(monthEnd);
				} else {
					sql.append(" and intYear=").append(yearStart);
					sql.append(" and intMonth>=").append(monthStart);
					sql.append(" or (intYear>").append(yearStart);
					sql.append(" and intYear<").append(yearEnd);

					sql.append(" ) or (intYear=").append(yearEnd);
					sql.append(" and intMonth<=").append(monthEnd).append(")");
				}
				break;
			}
		}

		return sql.toString();
	}

	private static boolean getIsNull(String[] asName) {

		if (null != asName && asName.length != 0 && !"".equals(asName[0])) {
			return true;
		}

		return false;
	}

	// /**
	// *
	// * @param timeLevel
	// * 时间粒度
	// * @param dateTimeKeyValue
	// * 比较字段和值(只限于intYear,intMonth,intWeek,intDay,intHour这几个字段的比较)
	// * @param isCompare
	// * 是否是跨度时间(true:跨度，false：单个值)
	// * @param asName
	// * 表或视图的别名
	// * @return
	// */
	// public static String buildDateTimeCompareSql(int timeLevel,
	// Map<String, String> dateTimeKeyValue, boolean isCompare,
	// String asName) {
	//
	// StringBuffer sql = new StringBuffer(200);
	//
	// String yearStart = "";
	// int monthStart = 0;
	// int dayStart = 0;
	// int hourStart = 0;
	// String weekStart = "";
	//
	// String yearEnd = "";
	// int monthEnd = 0;
	// int dayEnd = 0;
	// int hourEnd = 0;
	// String weekEnd = "";
	//
	// if (isCompare) {
	// switch (timeLevel) {
	// case 1:
	// yearStart = dateTimeKeyValue.get("queryYearStart");
	// monthStart = Integer.valueOf(dateTimeKeyValue
	// .get("queryMonthStart"));
	// dayStart = Integer.valueOf(dateTimeKeyValue
	// .get("queryDayStart"));
	// hourStart = Integer.valueOf(dateTimeKeyValue
	// .get("queryHourStart"));
	//
	// yearEnd = dateTimeKeyValue.get("queryYearEnd");
	// monthEnd = Integer.valueOf(dateTimeKeyValue
	// .get("queryMonthEnd"));
	// dayEnd = Integer.valueOf(dateTimeKeyValue.get("queryDayEnd"));
	// hourEnd = Integer.valueOf(dateTimeKeyValue.get("queryHourEnd"));
	//
	// if (yearStart.equals(yearEnd)) {
	// sql.append(" and ").append(asName).append(".intYear=")
	// .append(yearStart);
	// if (monthStart == monthEnd) {
	// sql.append(" and ").append(asName).append(".intMonth=")
	// .append(monthStart);
	// if (dayStart == dayEnd) {
	// sql.append(" and ").append(asName).append(
	// ".intDay=").append(dayStart);
	// sql.append(" and ").append(asName).append(
	// ".intHour>=").append(hourStart);
	//
	// sql.append(" and ").append(asName).append(
	// ".intHour<=").append(hourEnd);
	// } else {
	// sql.append(" and (( ").append(asName).append(
	// ".intDay>").append(dayStart);
	// sql.append(" and ").append(asName).append(
	// ".intHour>=").append(hourStart);
	// sql.append(" ) or (").append(asName).append(
	// ".intDay>").append(dayStart);
	// sql.append(" and ").append(asName).append(
	// ".intDay<").append(dayEnd).append(")");
	// sql.append(" or (").append(asName).append(
	// ".intHour<=").append(hourEnd);
	// sql.append(" and ").append(asName).append(
	// ".intDay=").append(dayEnd).append("))");
	// }
	// } else {
	// sql.append(" and ((").append(asName).append(
	// ".intMonth=").append(monthStart);
	// sql.append(" and ").append(asName).append(".intDay=")
	// .append(dayStart);
	// sql.append(" and ").append(asName).append(".intHour>=")
	// .append(hourStart);
	// sql.append(" ) or (").append(asName).append(
	// ".intMonth>").append(monthStart);
	// sql.append(" and ").append(asName).append(".intMonth<")
	// .append(monthEnd);
	// sql.append(" ) or (").append(asName).append(
	// ".intMonth=").append(monthStart);
	// sql.append(" and ").append(asName).append(".intDay>")
	// .append(dayStart);
	// sql.append(" ) or (").append(asName).append(
	// ".intMonth=").append(monthEnd);
	// sql.append(" and ").append(asName).append(".intDay<")
	// .append(dayEnd);
	// sql.append(" ) or (").append(asName).append(
	// ".intMonth=").append(monthEnd);
	// sql.append(" and ").append(asName).append(".intDay=")
	// .append(dayEnd);
	// sql.append(" or ").append(asName).append(".intHour>=")
	// .append(hourEnd).append("))");
	// }
	// } else {
	// sql.append(" and ").append(asName).append(".intYear=")
	// .append(yearStart);
	// sql.append(" and (").append(asName).append(".intMonth=")
	// .append(monthStart);
	// sql.append(" and ").append(asName).append(".intDay=")
	// .append(dayStart);
	// sql.append(" and ").append(asName).append(".intHour>=")
	// .append(hourStart);
	// sql.append(" or (").append(asName).append(".intMonth=")
	// .append(monthStart);
	// sql.append(" and ").append(asName).append(".intDay>")
	// .append(dayStart).append(")");
	// sql.append(" or (").append(asName).append(".intMonth>")
	// .append(monthStart).append("))");
	//
	// sql.append(" or (").append(asName).append(".intYear>")
	// .append(yearStart);
	// sql.append(" and ").append(asName).append(".intYear<")
	// .append(yearEnd);
	//
	// sql.append(" ) or (").append(asName).append(".intYear=")
	// .append(yearEnd);
	// sql.append(" and (").append(asName).append(".intMonth<")
	// .append(monthEnd);
	// sql.append(" or (").append(asName).append(".intMonth=")
	// .append(monthEnd);
	// sql.append(" and ").append(asName).append(".intDay=")
	// .append(dayEnd);
	// sql.append(" and ").append(asName).append(".intHour<=")
	// .append(hourEnd).append(")");
	// sql.append(" or (").append(asName).append(".intMonth<")
	// .append(monthEnd);
	// sql.append(" and ").append(asName).append(".intDay<")
	// .append(dayEnd).append(")))");
	// }
	//
	// break;
	// case 2:
	// yearStart = dateTimeKeyValue.get("queryYearStart");
	// monthStart = Integer.valueOf(dateTimeKeyValue
	// .get("queryMonthStart"));
	// dayStart = Integer.valueOf(dateTimeKeyValue
	// .get("queryDayStart"));
	//
	// yearEnd = dateTimeKeyValue.get("queryYearEnd");
	// monthEnd = Integer.valueOf(dateTimeKeyValue
	// .get("queryMonthEnd"));
	// dayEnd = Integer.valueOf(dateTimeKeyValue.get("queryDayEnd"));
	//
	// if (yearStart.equals(yearEnd)) {
	// sql.append(" and ").append(asName).append(".intYear=")
	// .append(yearStart);
	// if (monthStart == monthEnd) {
	// sql.append(" and ").append(asName).append(".intMonth=")
	// .append(monthStart);
	// sql.append(" and ").append(asName).append(".intDay>=")
	// .append(dayStart);
	//
	// sql.append(" and ").append(asName).append(".intDay<=")
	// .append(dayEnd);
	// } else {
	// sql.append(" and ((").append(asName).append(
	// ".intMonth=").append(monthStart);
	// sql.append(" and ").append(asName).append(".intDay>=")
	// .append(dayStart);
	// sql.append(" ) or (").append(asName).append(
	// ".intMonth>").append(monthStart);
	// sql.append(" and ").append(asName).append(".intMonth<")
	// .append(monthEnd);
	// sql.append(" ) or (").append(asName).append(
	// ".intMonth=").append(monthEnd);
	// sql.append(" and ").append(asName).append(".intDay<=")
	// .append(dayEnd).append("))");
	// }
	// } else {
	// sql.append(" and ").append(asName).append(".intYear=")
	// .append(yearStart);
	// sql.append(" and ((").append(asName).append(".intMonth=")
	// .append(monthStart);
	// sql.append(" and ").append(asName).append(".intDay>=")
	// .append(dayStart).append(")");
	// sql.append(" or (").append(asName).append(".intMonth>")
	// .append(monthStart).append("))");
	// sql.append(" or (").append(asName).append(".intYear>")
	// .append(yearStart);
	// sql.append(" and ").append(asName).append(".intYear<")
	// .append(yearEnd);
	// sql.append(" ) or (").append(asName).append(".intYear=")
	// .append(yearEnd);
	// sql.append(" and (").append(asName).append(".intMonth<")
	// .append(monthEnd);
	// sql.append(" or (").append(asName).append(".intMonth=")
	// .append(monthEnd);
	// sql.append(" and ").append(asName).append(".intDay<=")
	// .append(dayEnd).append(")))");
	// }
	// break;
	// case 3:
	// yearStart = dateTimeKeyValue.get("queryYearStart");
	// weekStart = dateTimeKeyValue.get("queryWeekStart");
	//
	// yearEnd = dateTimeKeyValue.get("queryYearEnd");
	// weekEnd = dateTimeKeyValue.get("queryWeekEnd");
	//
	// if (yearStart.equals(yearEnd)) {
	// sql.append(" and ").append(asName).append(".intYear=")
	// .append(yearStart);
	// sql.append(" and ").append(asName).append(".intWeek>=")
	// .append(weekStart);
	// sql.append(" and ").append(asName).append(".intWeek<=")
	// .append(weekEnd);
	// } else {
	//
	// sql.append(" and (").append(asName).append(".intYear=")
	// .append(yearStart);
	// sql.append(" and ").append(asName).append(".intWeek>=")
	// .append(weekStart);
	// sql.append(" ) or ( ").append(asName).append(".intWeek<=")
	// .append(weekEnd);
	// sql.append(" and ").append(asName).append(".intYear=")
	// .append(yearEnd);
	// sql.append(" ) or ( ").append(asName).append(".intYear>")
	// .append(yearStart);
	// sql.append(" and ").append(asName).append(".intYear<")
	// .append(yearEnd).append(")");
	// }
	//
	// break;
	// case 4:
	// yearStart = dateTimeKeyValue.get("queryYearStart");
	// monthStart = Integer.valueOf(dateTimeKeyValue
	// .get("queryMonthStart"));
	//
	// yearEnd = dateTimeKeyValue.get("queryYearEnd");
	// monthEnd = Integer.valueOf(dateTimeKeyValue
	// .get("queryMonthEnd"));
	//
	// if (yearStart.equals(yearEnd)) {
	// sql.append(" and ").append(asName).append(".intYear=")
	// .append(yearStart);
	// sql.append(" and ").append(asName).append(".intMonth>=")
	// .append(monthStart);
	//
	// sql.append(" and ").append(asName).append(".intMonth<=")
	// .append(monthEnd);
	// } else {
	// sql.append(" and ").append(asName).append(".intYear=")
	// .append(yearStart);
	// sql.append(" and ").append(asName).append(".intMonth>=")
	// .append(monthStart);
	// sql.append(" or (").append(asName).append(".intYear>")
	// .append(yearStart);
	// sql.append(" and ").append(asName).append(".intYear<")
	// .append(yearEnd);
	// sql.append(" ) or (").append(asName).append(".intYear=")
	// .append(yearEnd);
	// sql.append(" and ").append(asName).append(".intMonth<=")
	// .append(monthEnd).append(")");
	// }
	// break;
	// }
	// // /////////////
	// } else {
	//
	// switch (timeLevel) {
	// case 1:
	// sql.append(" and ").append(asName).append(".intYear=").append(
	// dateTimeKeyValue.get("queryYear"));
	// sql.append(" and ").append(asName).append(".intMonth=").append(
	// dateTimeKeyValue.get("queryMonth"));
	// sql.append(" and ").append(asName).append(".intDay=").append(
	// dateTimeKeyValue.get("queryDay"));
	// sql.append(" and ").append(asName).append(".intHour=").append(
	// dateTimeKeyValue.get("queryHour"));
	// break;
	// case 2:
	// sql.append(" and ").append(asName).append(".intYear=").append(
	// dateTimeKeyValue.get("queryYear"));
	// sql.append(" and ").append(asName).append(".intMonth=").append(
	// dateTimeKeyValue.get("queryMonth"));
	// sql.append(" and ").append(asName).append(".intDay=").append(
	// dateTimeKeyValue.get("queryDay"));
	// break;
	// case 3:
	// sql.append(" and ").append(asName).append(".intYear=").append(
	// dateTimeKeyValue.get("queryYear"));
	// sql.append(" and ").append(asName).append(".intWeek=").append(
	// dateTimeKeyValue.get("queryWeek"));
	// break;
	// case 4:
	// sql.append(" and ").append(asName).append(".intYear=").append(
	// dateTimeKeyValue.get("queryYear"));
	// sql.append(" and ").append(asName).append(".intMonth=").append(
	// dateTimeKeyValue.get("queryMonth"));
	// break;
	// }
	//
	// }
	//
	// return sql.toString();
	//
	// }
	//
	// public static String buildDateTimeCompareSql(String queryDate,
	// int timeLevel, String... asName) {
	// StringBuffer sql = new StringBuffer(150);
	// String[] dataArrStart = null;
	// String yearStart = "";
	// int monthStart = 0;
	// int dayStart = 0;
	// int hourStart = 0;
	// String weekStart = "";
	//
	// boolean asNameIsNull = getIsNull(asName);
	//
	// if (asNameIsNull) {
	// switch (timeLevel) {
	// case 1:
	// String[] timeArrStart = queryDate.split(" ");
	// dataArrStart = timeArrStart[0].split("-");
	// yearStart = dataArrStart[0];
	// monthStart = getIntFormatStr(dataArrStart[1]);
	// dayStart = getIntFormatStr(dataArrStart[2]);
	// hourStart = getIntFormatStr(timeArrStart[1].split(":")[0]);
	// sql.append(" and ").append(asName[0]).append(".intYear=")
	// .append(yearStart);
	// sql.append(" and ").append(asName[0]).append(".intMonth=")
	// .append(monthStart);
	// sql.append(" and ").append(asName[0]).append(".intDay=")
	// .append(dayStart);
	// sql.append(" and ").append(asName[0]).append(".intHour=")
	// .append(hourStart);
	// break;
	// case 2:
	// dataArrStart = queryDate.split("-");
	// yearStart = dataArrStart[0];
	// monthStart = getIntFormatStr(dataArrStart[1]);
	// dayStart = getIntFormatStr(dataArrStart[2]);
	//
	// sql.append(" and ").append(asName[0]).append(".intYear=")
	// .append(yearStart);
	// sql.append(" and ").append(asName[0]).append(".intMonth=")
	// .append(monthStart);
	// sql.append(" and ").append(asName[0]).append(".intDay=")
	// .append(dayStart);
	// break;
	// case 3:
	// dataArrStart = queryDate.split("-");
	// yearStart = dataArrStart[0];
	// weekStart = dataArrStart[1];
	//
	// sql.append(" and ").append(asName[0]).append(".intYear=")
	// .append(yearStart);
	// sql.append(" and ").append(asName[0]).append(".intWeek=")
	// .append(weekStart);
	//
	// break;
	// case 4:
	// dataArrStart = queryDate.split("-");
	// yearStart = dataArrStart[0];
	// monthStart = getIntFormatStr(dataArrStart[1]);
	//
	// sql.append(" and ").append(asName[0]).append(".intYear=")
	// .append(yearStart);
	// sql.append(" and ").append(asName[0]).append(".intMonth=")
	// .append(monthStart);
	// break;
	// }
	// } else {
	// switch (timeLevel) {
	// case 1:
	// String[] timeArrStart = queryDate.split(" ");
	// dataArrStart = timeArrStart[0].split("-");
	// yearStart = dataArrStart[0];
	// monthStart = getIntFormatStr(dataArrStart[1]);
	// dayStart = getIntFormatStr(dataArrStart[2]);
	// hourStart = getIntFormatStr(timeArrStart[1].split(":")[0]);
	// sql.append(" and intYear=").append(yearStart);
	// sql.append(" and intMonth=").append(monthStart);
	// sql.append(" and intDay=").append(dayStart);
	// sql.append(" and intHour=").append(hourStart);
	// break;
	// case 2:
	// dataArrStart = queryDate.split("-");
	// yearStart = dataArrStart[0];
	// monthStart = getIntFormatStr(dataArrStart[1]);
	// dayStart = getIntFormatStr(dataArrStart[2]);
	//
	// sql.append(" and intYear=").append(yearStart);
	// sql.append(" and intMonth=").append(monthStart);
	// sql.append(" and intDay=").append(dayStart);
	// break;
	// case 3:
	// dataArrStart = queryDate.split("-");
	// yearStart = dataArrStart[0];
	// weekStart = dataArrStart[1];
	//
	// sql.append(" and intYear=").append(yearStart);
	// sql.append(" and intWeek=").append(weekStart);
	//
	// break;
	// case 4:
	// dataArrStart = queryDate.split("-");
	// yearStart = dataArrStart[0];
	// monthStart = getIntFormatStr(dataArrStart[1]);
	//
	// sql.append(" and intYear=").append(yearStart);
	// sql.append(" and intMonth=").append(monthStart);
	// break;
	// }
	//
	// }
	//
	// return sql.toString();
	// }

	private static int getIntFormatStr(String timeStr) {
		String intFormat = "";

		boolean isLTen = getNumIsLTen(timeStr);

		if (isLTen) {
			intFormat = timeStr.substring(1);
		} else {
			intFormat = timeStr;
		}

		return Integer.parseInt(intFormat);
	}

	private static boolean getNumIsLTen(String timeStr) {
		return ("01".equals(timeStr) || "02".equals(timeStr)
				|| "03".equals(timeStr) || "04".equals(timeStr)
				|| "07".equals(timeStr) || "08".equals(timeStr)
				|| "05".equals(timeStr) || "06".equals(timeStr) || "09"
				.equals(timeStr));
	}

	// //////////////////////////////////END////////////////////////////////////////////////////

	public static void main(String arg[]) {

	}

	public static String getTimeLevelCH(String timeLevelSearch) {
		if ("1".equals(timeLevelSearch)) {
			return "   时粒度";
		} else if ("2".equals(timeLevelSearch)) {
			return "   天粒度";
		} else if ("3".equals(timeLevelSearch)) {
			return "   周粒度";
		} else {
			return "   月粒度";
		}
	}
}
