package ocp.time;

import java.time.Period;

import org.junit.Test;
import static org.junit.Assert.*;

public class PeriodTests {

	@Test
	public void test01() {
		Period p = Period.of(1, 24, 32);
		assertEquals("P1Y24M32D", p.toString());
		
		// a normalized period adjusts the number of months but not the number of days for obvious reasons. 
		// We know we have 12 months in a year 
		p = p.normalized();
		assertEquals("P3Y32D", p.toString());
	}
}

