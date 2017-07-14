package ocp.generics;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

public class DiamondNotationTests {

	@SuppressWarnings("unused")
	@Test
	public void test01() {

		// '<>' CANNOT be used with anonymous classes like
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
		
		// For a generic class no type specified means the type is Object.
		// Because an overriding method can return a covariant the following Supplier
		// definition is valid. Instead for the BinaryOperator we are forced to use
		// the Object type for the apply method
		Supplier<String> s = new Supplier() {
			@Override
			public String get() {
				return "";
			}
		};
		
		BinaryOperator<String> bo = new BinaryOperator() {
			@Override
			public String apply (Object first, Object second) {
				return "";
			}
		};
		BinaryOperator bo2 = new BinaryOperator<String>() {
			@Override
			public String apply(String first, String second){
				return null;
			}
		};
		
	}
}
