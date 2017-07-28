package ocp.time;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ocp.lambda.ExceptionLoggingRule;

public class DateTimeTests {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Rule
	public ExceptionLoggingRule exLogger = new ExceptionLoggingRule();
	
	@Test
	public void test01() {
		/*
		 * On March 12, 2017, Daylight Saving Time starts in Los Angeles (US Pacific timezone). This happens at 2:00 AM and time goes 
		 * forward one hour, becoming 3:00 am.
		 * The hour 2:29 does not exist so it's automatically changed to 3:29
		 */
		ZonedDateTime timeBeforeChange = ZonedDateTime.of(LocalDateTime.of(2017,  Month.MARCH,  12, 2, 29), ZoneId.of("US/Pacific"));
		assertEquals("2017-03-12T03:29-07:00[US/Pacific]", timeBeforeChange.toString());
	}
	
	@Test
	public void test02() {
		// only the seconds get trimmed from the date if zero
		LocalDateTime ldt = LocalDateTime.parse("2017-03-12T02:29:00");
		assertEquals("2017-03-12T02:29", ldt.toString());
		
		LocalDateTime ldt02 = LocalDateTime.parse("2017-03-12T02:00:00");
		assertEquals("2017-03-12T02:00", ldt02.toString());
	}
	
	@Test
	public void test03() {
		LocalDate ld = LocalDate.of(2015, 2, 27);
		ld = ld.plusDays(3);
		assertEquals(Month.MARCH, ld.getMonth());
		assertEquals("MARCH", Month.MARCH.toString());
	}
	
	@Test
	public void usingInvalidTemporalFieldShouldThrowException() {
		LocalDate ld = LocalDate.of(2017,  Month.AUGUST, 8);
		// valid assignment of day-of-week
		assertEquals(LocalDate.of(2017, Month.AUGUST, 13), ld.with(ChronoField.DAY_OF_WEEK, 7));
		
		// cannot set hours to a LocalDate -> throws Exception
		thrown.expect(UnsupportedTemporalTypeException.class);
		thrown.expectMessage("Unsupported field: HourOfDay");
		ld.with(ChronoField.HOUR_OF_DAY, 1);
	}
	
	
}
