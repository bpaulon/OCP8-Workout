package ocp.lambda;

import static org.junit.Assert.assertEquals;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
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
}
