/**
 * z.z.w.common.DateToolsTest.java
 */
package z.z.w.common;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

/**
 * @author Wu Zhenzhen
 * @version Dec 9, 2012 7:48:06 PM
 */
public class DateToolsTest {
	/**
	 * Test method for
	 * {@link z.z.w.common.DateTools#getDate(int, int, int, int, int, int)}.
	 */
	@Test
	public void testGetDate() {
		System.out.println(DateTools.getDate(2014, 192, 22, 8, 7, 7));
	}

	/**
	 * Test method for {@link z.z.w.common.DateTools#getMin(java.util.Date)}.
	 */
	@Test
	public void testGetMin() {
		System.out.println(DateTools.getMin(new Date()));
	}

	/**
	 * Test method for {@link z.z.w.common.DateTools#getSec(java.util.Date)}.
	 */
	@Test
	public void testGetSec() {
		System.out.println(DateTools.getSec(new Date()));
	}

	/**
	 * Test method for {@link z.z.w.common.DateTools#getMonth(java.util.Date)}.
	 */
	@Test
	public void testGetMonth() {
		System.out.println(DateTools.getMonth(new Date()));
	}

	/**
	 * Test method for {@link z.z.w.common.DateTools#getDay(java.util.Date)}.
	 */
	@Test
	public void testGetDay() {
		System.out.println(DateTools.getDay(new Date()));
	}

	/**
	 * Test method for {@link z.z.w.common.DateTools#getHour(java.util.Date)}.
	 */
	@Test
	public void testGetHour() {
		System.out.println(DateTools.getHour(new Date()));
	}

	/**
	 * Test method for {@link z.z.w.common.DateTools#getWeek(java.util.Date)}.
	 */
	@Test
	public void testGetWeek() {
		System.out.println(DateTools.getWeek(new Date()));
	}

	/**
	 * Test method for {@link z.z.w.common.DateTools#getDateByLongTime(long)}.
	 */
	@Test
	public void testGetDateByLongTime() {
		System.out.println(DateTools.getDateByLongTime(3243543989l));
	}

	/**
	 * Test method for {@link z.z.w.common.DateTools#getCurrentDateToLong()}.
	 */
	@Test
	public void testGetCurrentDateToLong() {
		System.out.println(DateTools.getCurrentDateToLong());
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DateTools#getDateToLong(java.util.Date)}.
	 */
	@Test
	public void testGetDateToLong() {
		System.out.println(DateTools.getDateToLong(new Date()));
	}

	/**
	 * Test method for {@link z.z.w.common.DateTools#getYear(java.util.Date)}.
	 */
	@Test
	public void testGetYear() {
		System.out.println(DateTools.getYear(new Date()));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DateTools#getCalendar(java.util.Date)}.
	 */
	@Test
	public void testGetCalendar() {
		System.out.println(DateTools.getCalendar(new Date()));
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DateTools#getParseDateToStr(java.util.Date)}.
	 */
	@Test
	public void testGetParseDateToStrDate() {
		System.out.println(DateTools.getParseDateToStr(new Date()));
	}

	/**
	 * Test method for {@link z.z.w.common.DateTools#getCurrentDateStr()}.
	 */
	@Test
	public void testGetCurrentDateStr() {
		System.out.println(DateTools.getCurrentDateStr());
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DateTools#getParseDateToStr(java.util.Date, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetParseDateToStrDateString() {
		System.out.println(DateTools.getParseDateToStr(new Date(),
				DateTools.YYYYMMDD_HHMMSS));
	}

	/**
	 * Test method for {@link z.z.w.common.DateTools#getCurrentDate()}.
	 */
	@Test
	public void testGetCurrentDate() {
		System.out.println(DateTools.getCurrentDate());
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DateTools#getDate(int, int, int, int, int, int)}.
	 */
	@Test
	public void testGetDateIntIntIntIntIntInt() {
		// fail("Not yet implemented");
	}

	/**
	 * Test method for {@link z.z.w.common.DateTools#getDate(java.lang.String)}.
	 */
	@Test
	public void testGetDateString() {
		try {
			System.out.println(DateTools.getDate("2012-12-09 19:49:00"));
		} catch (ParseException e) {
			fail("Error : " + e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link z.z.w.common.DateTools#getDate(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testGetDateStringString() {
		try {
			System.out.println(DateTools
					.getDate("20130411130000", DateTools.YYYYMMDDHHMMSS));
		} catch (ParseException e) {
			fail("Error : " + e.getMessage());
		}
	}

}
