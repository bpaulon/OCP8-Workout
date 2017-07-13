package ocp.time;
import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

public class DateTimeTests {

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
}
