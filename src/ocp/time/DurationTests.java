package ocp.time;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class DurationTests {

	
	/**
	 * The ofXXX() method calls (ofDays, ofMillis, etc) are static and they create a new 
	 * Duration each time. 
	 * 
	 */
	@Test
	public void test01(){
		
		Duration d = Duration.ofDays(1).ofHours(25).ofMillis(70);
		List<TemporalUnit> units = d.getUnits();
		assertEquals("PT0.07S", d.toString());
		assertEquals(Arrays.asList(ChronoUnit.SECONDS, ChronoUnit.NANOS), units);
		
		
		Duration d1 = Duration.ofDays(3);
		Duration d2 = d1.ofHours(25);
		assertEquals("PT25H", d2.toString());
	}
}
