package ocp.lambda;

import static org.junit.Assert.assertEquals;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class ReduceTests {

	Supplier<Stream<String>> supplier = () -> Stream.of("Abc", "Def");

	@Test
	public void identityShouldBePrependedOnce() {

		BinaryOperator<String> operator = (s1, s2) -> s1.concat(s2.toUpperCase());
		Optional<String> optResult = supplier.get()
				.reduce(operator);
		assertEquals("AbcDEF", optResult.get());

		// when we pass in an identity to the reduce operation the result is not an Optional
		// but the type of the stream (in this case String)
		String result = supplier.get()
				.reduce("-", operator);
		assertEquals("-ABCDEF", result);
	}

	@Test
	public void getLastElement() {
		String last = supplier.get()
				.reduce((a, b) -> b)
				.orElseThrow(() -> new RuntimeException("empty"));
		assertEquals("Def", last);
	}

	@Test
	public void testReduceOfPrimitiveStreams() {
		Supplier<Stream<Integer>> supplier = () -> Stream.of(1, 2, 3, 4, 5);

		assertEquals(15, supplier.get()
				.mapToInt(i -> i.intValue())
				.sum());

		assertEquals(3, supplier.get()
				.mapToDouble(i -> i.intValue())
				.average()
				.getAsDouble(), 0.0);
	}

	// example of reduce(Integer identity, BiFunction accumulator, BiOperator combiner)
	/**
	 * To do parallel reduction to a different result type, we need two functions: one that accumulates T elements to
	 * intermediate U values, and a second that combines the intermediate U values into a single U result. If we aren't
	 * switching types, it turns out that the accumulator function is the same as the combiner function. That's why
	 * reduction to the same type has only the accumulator function and reduction to a different type requires separate
	 * accumulator and combiner functions.
	 * 
	 * Note: In the current implementation, the combiner is never called when evaluating a sequential pipeline.
	 */
	@Test
	public void testReduceWithAccumulatorNoChangeResultType() {
		Supplier<Stream<Integer>> sup = () -> IntStream.rangeClosed(1, 3)
				.boxed();
		int sum = sup.get()
				.reduce(0, (i1, i2) -> i1 + i2 * 10, (i1, i2) -> 0);
		assertEquals(60, sum);

		sum = sup.get()
				.collect(Collectors.summingInt(i -> i.intValue()));
		assertEquals(21, sum);
	}

	/**
	 * U reduce(I, (U, T) -> U, (U, U) -> U) (Identity value I is of type U)
	 */
	@Test
	public void testReduceWithAccumulatorFromStringToInteger() {
		Supplier<Stream<String>> sup1 = () -> Stream.of("a", "bb", "ccc", "dddd", "eeeee", "ffffff");
		
		BiFunction<Integer, String, Integer> bif = (identity, s2) -> {
			System.out.println(identity + ":" + s2);
			return identity + s2.length();
		};
		
		BinaryOperator<Integer> uop = (int1, int2) -> {
				int res = int1 *2 + int2;
				System.out.println("Combiner " + int1 + ":" + int2 + "-> " + res);
				return res;
			};
		Integer s = sup1.get().parallel()
				.reduce(0, bif,  uop);
		System.out.println(s);
		
		/* Output:
		 *  0:ffffff
		 *	0:a
		 *	0:bb
		 *	0:dddd
		 *	0:ccc
		 *	0:eeeee
		 *	Combiner 2:3-> 7
		 *	Combiner 1:7-> 9
		 *	Combiner 5:6-> 16
		 *	Combiner 4:16-> 24
		 *	Combiner 9:24-> 42
		 *	42
		 */
		
		s = sup1.get().reduce(0, bif,  uop);
		
		/*	Output:
		 
		  	0:a
		  	1:bb
		  	3:ccc
			6:dddd
			10:eeeee
			15:ffffff
			21
		 */
		System.out.println(s);
	}
}
