package ocp.formatting;

import java.text.NumberFormat;
import java.util.Locale;

import org.junit.Test;

public class NumberFormattingTests {

	@Test
	public void test01() {
		int testNr = 2_100_100;
		NumberFormat de = NumberFormat.getCurrencyInstance(Locale.GERMANY);
		System.out.println(de.format(testNr));
		
		de = NumberFormat.getCurrencyInstance(Locale.GERMAN);
		System.out.println(de.format(testNr));
		
	}
}
