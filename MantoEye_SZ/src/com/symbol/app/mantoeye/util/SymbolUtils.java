package com.symbol.app.mantoeye.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 常用的公共方法
 * 
 * @author rankin
 * 
 */
public class SymbolUtils {

	private final static Logger logger = LoggerFactory
			.getLogger(SymbolUtils.class);

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
	 * 格式化时间
	 * 
	 * @param sd
	 *            格式yyyy-mm-dd hh:MM:ss
	 * @return
	 */
	public static Date getDate(String sd) {
		if (sd.split(" ").length == 1) {
			sd = sd + " 00:00:00";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			return sdf.parse(sd);
		} catch (ParseException e) {
			logger.error(e.getMessage());
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
	 * 通过年月日构造yyyy-MM-dd hh:mm:ss格式的字符串
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
	 * 通过年月日小时构造yyyy-MM-dd hh:mm:ss格式的字符串
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
	 * 获取当前月第一天
	 * 
	 * @return
	 */
	public static String getCurrentMonthFirstDay() {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(new Date());
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return df.format(gc.getTime());

	}

	/**
	 * 获取当前日期 格式 :yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrentDay() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(new Date());
	}

	/**
	 *因为 传入页面弹出框的数据之间要拥戴‘:’‘,’‘@’作为风格符， 因此必须把名称里面的这些符号去掉，不然页面对应关系会出错
	 * 
	 * @param str
	 * @return
	 */
	public static String getSaftDialogStr(String str) {
		if (str != null && str.trim() != "") {
			return str.replaceAll(",", "，").replaceAll(":", "：").replaceAll(
					"@", "at");
		} else {
			return str;
		}
	}
}
