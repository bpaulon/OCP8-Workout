package ocp.time;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import static org.junit.Assert.*;

public class DateTimeFormatterTests {

	@Test
	public void test01() {
		DateTimeFormatter frmt = DateTimeFormatter.ofPattern("d/M/uuuuuu");
		String s = frmt.format(LocalDate.of(2017, Month.NOVEMBER, 30));
		assertEquals("30/11/002017", s);
		
		s = frmt.format(LocalDate.of(-10, 10, 21));
		assertEquals("21/10/-000010", s);
	}
}
