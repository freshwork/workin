package org.workin.test.utils;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.workin.fortest.BaseTestCase;
import org.workin.util.DateUtils;

/**
 * 
 * @author <a href="mailto:goingmm@gmail.com">G.Lee</a>
 *
 */
@SuppressWarnings("deprecation")
public class DateUtilsTest extends BaseTestCase {
	
	
	@Test public void isToday() {
		assertTrue(DateUtils.isToday(DateUtils.getNow()));
		assertFalse(DateUtils.isToday(new Date("2000/1/1")));
	}
	
	@Test public void getDayOfWeekStr() {
		assertEquals("Tuesday", DateUtils.getDayOfWeekStr(3));
	}
	
	
	@Test public void daysDiff() {
		assertEquals(2, DateUtils.daysDiff(DateUtils.getNow(), DateUtils.getDaysAfterOrBefore(DateUtils.getNow(), 2)));
		assertEquals(100, DateUtils.daysDiff(DateUtils.getNow(), DateUtils.getDaysAfterOrBefore(DateUtils.getNow(), -100)));
	}
	
	@Test public void getWeekdayInWeek() {
		Date appointedDate = new Date("2009/11/11");
		
		assertEquals(new Date("2009/11/08"), DateUtils.getWeekdayInWeek(appointedDate, Calendar.SUNDAY));
		assertEquals(new Date("2009/11/09"), DateUtils.getWeekdayInWeek(appointedDate, Calendar.MONDAY));
		assertEquals(new Date("2009/11/10"), DateUtils.getWeekdayInWeek(appointedDate, Calendar.TUESDAY));
		assertEquals(new Date("2009/11/11"), DateUtils.getWeekdayInWeek(appointedDate, Calendar.WEDNESDAY));
		assertEquals(new Date("2009/11/12"), DateUtils.getWeekdayInWeek(appointedDate, Calendar.THURSDAY));
		assertEquals(new Date("2009/11/13"), DateUtils.getWeekdayInWeek(appointedDate, Calendar.FRIDAY));
		assertEquals(new Date("2009/11/14"), DateUtils.getWeekdayInWeek(appointedDate, Calendar.SATURDAY));
	}
	
	@Test public void getPlainDateTest() {
		Date appointedDate = new Date("2009/11/11 11:11:11");
		String date2str = DateUtils.dateToString(DateUtils.getPlainDate(appointedDate), DateUtils.PATTEN_DATE_FORMAT_DATETIME_PLUS);
		assertEquals("2009-11-11 00:00:00:000", date2str);
	}
	
	@Test public void getPlainTimeTest() {
		Date appointedDate = new Date("2009/11/11 07:08:09");
		String date2str = DateUtils.dateToString(DateUtils.getPlainTime(appointedDate), DateUtils.PATTEN_DATE_FORMAT_DATETIME_PLUS);
		assertEquals("1970-01-01 07:08:09:000", date2str);
	}
	
//	@Test public void monthsDiff() {
//		assertEquals(1, DateUtils.monthsDiff(DateUtils.getNow(), DateUtils.getDaysAfterOrBefore(DateUtils.getNow(), 10)));
//	}
}
