package ocp.generics;

import java.util.function.BiConsumer;

import org.junit.Test;

public class DiamondNotationTests {

	@SuppressWarnings("unused")
	@Test
	public void test01() {

		// '<>' cannot be used with anonymous classes like
		// BiConsumer<String, Integer> bic = new BiConsumder<> {...} - DOES NOT COMPILE
		BiConsumer<String, Integer> bic = new BiConsumer<String, Integer>() {
			public void accept(String s, Integer i) {
				// consume both
			}
		};

		// but types are inferred for the lambda
		bic = (s, v) -> {
			s.length();
			v.intValue();
		};
	}
}
