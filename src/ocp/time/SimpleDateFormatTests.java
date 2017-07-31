package ocp.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class SimpleDateFormatTests {

	@Test
	public void testMultipleMonthLetters() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMMMMMM", Locale.US);
		System.out.println(sdf.format(new Date()));
	}
}
