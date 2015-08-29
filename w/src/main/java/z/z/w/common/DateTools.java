/**
 * z.z.w.common.DateTools.java
 */
package z.z.w.common;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Date tools
 * 
 * @author Wu Zhenzhen
 * @version Nov 21, 2012 3:14:33 PM
 */
public final class DateTools {
	public static final String INTERVAL_HOUR = "H";
	public static final String INTERVAL_MIN = "M";
	public static final String INTERVAL_SEC = "S";

	/**
	 * 
	 * <br>
	 * Created on: Apr 23, 2013 3:14:02 PM
	 * 
	 * @param dataTime
	 * @param min
	 * @param sec
	 * @param millsec
	 * @return
	 */
	public static Long getCalendarMillSec(Calendar calendar, long dataTime,
			int min, int sec, int millsec) {
		calendar.setTimeInMillis(dataTime);

		calendar.set(Calendar.MINUTE, min);
		calendar.set(Calendar.SECOND, sec);
		calendar.set(Calendar.MILLISECOND, millsec);

		return calendar.getTimeInMillis();
	}

	/**
	 * calendar.set(Calendar.MINUTE, 0); <br>
	 * calendar.set(Calendar.SECOND, 0); <br>
	 * calendar.set(Calendar.MILLISECOND, 0);
	 * 
	 * <br>
	 * Created on: Apr 23, 2013 3:14:40 PM
	 * 
	 * @param dataTime
	 * @return
	 */
	public static Long getCalendarMillSec(Calendar calendar, long dataTime) {
		return getCalendarMillSec(calendar, dataTime, 0, 0, 0);
	}

	/**
	 * 
	 * <br>
	 * Created on: Apr 23, 2013 3:13:58 PM
	 * 
	 * @param dataTime
	 * @param date
	 * @return
	 */
	public static Long getCalendarMillSec(Calendar calendar, long dataTime,
			Date date) {
		return getCalendarMillSec(calendar, dataTime, DateTools.getMin(date),
				DateTools.getSec(date), DateTools.getMillSec(date));
	}

	/**
	 * private constructor <br>
	 * Created on: Nov 21, 2012 3:14:41 PM
	 */
	private DateTools() {
	}

	/**
	 * default time parse yyyy-MM-dd HH:mm:ss
	 */
	public static final String DEFAUL_PARSE = "yyyy-MM-dd HH:mm:ss";

	/**
	 * default time parse yyyy-MM-dd HH:00:00
	 */
	public static final String YYYYMMDD_HHmmss = "yyyy-MM-dd HH:00:00";

	/**
	 * default time parse yyyyMMddHHmmss
	 */
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * default time parse yyyy-MM-dd
	 */
	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	/**
	 * default time parse yyyyMMdd
	 */
	public static final String YYYYMMDD = "yyyyMMdd";

	/**
	 * default time parse HH:mm:ss
	 */
	public static final String HH_MM_SS = "HH:mm:ss";

	/**
	 * default time parse HHmmss
	 */
	public static final String HHMMSS = "HHmmss";

	/**
	 * default time parse yyyyMMdd_HHmmss
	 */
	public static final String YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";

	/**
	 * default time parse yyyyMMdd_HHmm
	 */
	public static final String YYYYMMDD_HHMM = "yyyyMMdd_HHmm";

	/**
	 * default time parse yyyyMMddHH
	 */
	public static final String YYYYMMDDHH = "yyyyMMddHH";

	/**
	 * default time parse yyyyMMddHHmm
	 */
	public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";

	/**
	 * default time parse yyyyMMddHH0000
	 */
	// public static final String YYYYMMDDHHDZ = "yyyyMMddHH0000";

	/**
	 * Get date by year-month-day hour:min:sec <br>
	 * Created on: Nov 21, 2012 3:30:02 PM
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param min
	 * @param sec
	 * @return
	 */
	public static Date getDate(int year, int month, int day, int hour, int min,
			int sec) {
		Calendar calendar = new GregorianCalendar();

		calendar.set(year, (month - 1), day, hour, min, sec);

		return calendar.getTime();
	}

	/**
	 * 
	 * Get minute of date <br>
	 * Created on: Nov 21, 2012 3:23:23 PM
	 * 
	 * @param date
	 * @return
	 */
	public static int getMin(Date date) {
		return getCalendar(date).get(Calendar.MINUTE);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-5 上午10:33:33
	 * 
	 * @return current min
	 */
	public static int getMin() {
		return getCalendar(getCurrentDate()).get(Calendar.MINUTE);
	}

	/**
	 * Get second of date <br>
	 * Created on: Nov 21, 2012 3:31:19 PM
	 * 
	 * @param date
	 * @return
	 */
	public static int getSec(Date date) {
		return getCalendar(date).get(Calendar.SECOND);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-5 上午10:33:52
	 * 
	 * @return current sec
	 */
	public static int getSec() {
		return getCalendar(getCurrentDate()).get(Calendar.SECOND);
	}

	/**
	 * 
	 * <br>
	 * Created on: Apr 23, 2013 11:15:36 AM
	 * 
	 * @param date
	 * @return
	 */
	public static int getMillSec(Date date) {
		return getCalendar(date).get(Calendar.MILLISECOND);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-5 上午10:34:07
	 * 
	 * @return current millsec
	 */
	public static int getMillSec() {
		return getCalendar(getCurrentDate()).get(Calendar.MILLISECOND);
	}

	/**
	 * Get month of date <br>
	 * Created on: Nov 21, 2012 3:26:47 PM
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		return (getCalendar(date).get(Calendar.MONTH) + 1);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-5 上午10:34:24
	 * 
	 * @return current month
	 */
	public static int getMonth() {
		return (getCalendar(getCurrentDate()).get(Calendar.MONTH) + 1);
	}

	/**
	 * Get day of month by date <br>
	 * Created on: Nov 21, 2012 3:26:37 PM
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		return getCalendar(date).get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-5 上午10:35:36
	 * 
	 * @return current day
	 */
	public static int getDay() {
		return getCalendar(getCurrentDate()).get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Get hour of day by date <br>
	 * Created on: Nov 21, 2012 3:26:24 PM
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		return getCalendar(date).get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-5 上午10:35:22
	 * 
	 * @return current hour
	 */
	public static int getHour() {
		return getCalendar(getCurrentDate()).get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Get week of year by date <br>
	 * Created on: Nov 21, 2012 3:26:06 PM
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		return getCalendar(date).get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-5 上午10:35:09
	 * 
	 * @return current week
	 */
	public static int getWeek() {
		return getCalendar(getCurrentDate()).get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Construct date by give long times <br>
	 * Created on: Nov 21, 2012 3:25:46 PM
	 * 
	 * @param time
	 * @return
	 */
	public static Date getDateByLongTime(long time) {
		return (new Date(time));
	}

	/**
	 * Get long times of current date <br>
	 * Created on: Nov 21, 2012 3:25:33 PM
	 * 
	 * @return
	 */
	public static long getCurrentDateToLong() {
		return System.currentTimeMillis();
	}

	/**
	 * Get long times of date <br>
	 * Created on: Nov 21, 2012 3:25:18 PM
	 * 
	 * @param date
	 * @return
	 */
	public static long getDateToLong(Date date) {
		return (date.getTime());
	}

	/**
	 * Get year of date <br>
	 * Created on: Nov 21, 2012 3:22:56 PM
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		return getCalendar(date).get(Calendar.YEAR);
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-5 上午10:34:57
	 * 
	 * @return current year
	 */
	public static int getYear() {
		return getCalendar(getCurrentDate()).get(Calendar.YEAR);
	}

	/**
	 * Get calendar instance of date <br>
	 * Created on: Nov 21, 2012 3:22:24 PM
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(Date date) {
		return org.apache.commons.lang3.time.DateUtils.toCalendar(date);
	}

	/**
	 * parse date for yyyy-MM-dd HH:mm:ss string <br>
	 * Created on: Nov 21, 2012 3:21:00 PM
	 * 
	 * @param date
	 * @return
	 */
	public static String getParseDateToStr(Date date) {
		return org.apache.commons.lang3.time.DateFormatUtils.format(date,
				DEFAUL_PARSE);
	}

	/**
	 * current date by yyyy-MM-dd HH:mm:ss string <br>
	 * Created on: Nov 21, 2012 3:19:24 PM
	 * 
	 * @return
	 */
	public static String getCurrentDateStr() {
		return getParseDateToStr(getCurrentDate(), DEFAUL_PARSE);
	}

	/**
	 * parse date by pattern <br>
	 * Created on: Nov 21, 2012 3:18:52 PM
	 * 
	 * @param date
	 * @param parsePattern
	 * @return
	 */
	public static String getParseDateToStr(Date date, String parsePattern) {
		return org.apache.commons.lang3.time.DateFormatUtils.format(date,
				parsePattern);
	}

	/**
	 * current date <br>
	 * Created on: Nov 21, 2012 3:18:13 PM
	 * 
	 * @return date
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * Get string format Date <br>
	 * Created on: Dec 9, 2012 7:42:24 PM
	 * 
	 * @param date
	 * @throws ParseException
	 */
	public static Date getDate(String dateStr) throws ParseException {
		return getDate(dateStr, DateTools.DEFAUL_PARSE);
	}

	/**
	 * Get string format Date <br>
	 * Created on: Dec 9, 2012 7:44:01 PM
	 * 
	 * @param date
	 * @param parsePattern
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String dateStr, String parsePattern)
			throws ParseException {
		Date date = DateUtils.parseDate(dateStr, parsePattern);
		return date;
	}

	/**
	 * 
	 * <br>
	 * Created on: 2013-9-6 下午04:48:24
	 * 
	 * @param intervalStr
	 * @return
	 */
	public static long getTimeLongByString(String intervalStr) {

		try {
			long interval = NumberUtils.toInt(intervalStr.substring(0,
					(intervalStr.length() - 1)));

			if (intervalStr.toUpperCase().contains(INTERVAL_HOUR)) {
				interval = interval * 60 * 60 * 1000;
			} else if (intervalStr.toUpperCase().contains(INTERVAL_MIN)) {
				interval = interval * 60 * 1000;
			} else if (intervalStr.toUpperCase().contains(INTERVAL_SEC)) {
				interval = interval * 1000;
			} else {
				interval = 2 * 1000 * 60;
			}

			return interval;
		} catch (Exception e) {
			throw new StringIndexOutOfBoundsException(
					"Time interval substring time error, use defalut value!");
		}
	}
}
