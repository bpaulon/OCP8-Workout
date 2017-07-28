package ocp.lambda.operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import static org.junit.Assert.*;

@SuppressWarnings("unused")
public class OperatorTests {
	
	@Test
	public void test01() {

		BinaryOperator<String> op2 = String::concat;
		op2 = (s1, s2) -> s1.concat(s2);
		op2 = new BinaryOperator<String>() {
			@Override
			public String apply(String firstArg, String secondArg) {
				return firstArg.concat(secondArg);
			}
		};

		UnaryOperator<String> op3 = String::trim;
		op3 = s -> s.trim();

		BiFunction<String, String, String> f1 = String::concat;
		f1 = (s1, s2) -> { return s1.concat(s2);};

		Function<String, String> f2 = String::trim;
		f2 = new Function<String, String>() {
			@Override
			public String apply(String t) {
				return t.trim();
			}
		};
		
		Function<String, Integer> f3 = String::length;
		f3 = s -> s.length();
		
	}

	@Test
	public void postIncrementShouldFail() {
		IntUnaryOperator iuo1 = i -> i++;
		// equivalent to
		IntUnaryOperator iuo = new IntUnaryOperator() {
			@Override
			public int applyAsInt(int i) {
				return i++;
			}
		};

		List<Integer> l1 = IntStream.iterate(0, iuo1)
				.limit(5)
				.boxed()
				.collect(Collectors.toCollection(ArrayList::new));
		assertEquals(Arrays.asList(0, 0, 0, 0, 0), l1);
	}

	
}
