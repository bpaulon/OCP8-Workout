package ocp.lambda;

import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;
import static org.junit.Assert.*;

public class ReduceTests {

	@Test
	public void identityShouldBePrependedOnce() {
		Supplier<Stream<String>> supplier = () -> Stream.of("Abc", "Def"); 
		BinaryOperator<String> operator = (s1, s2)->s1.concat(s2.toUpperCase());
		
		Optional<String> optResult = supplier.get().reduce(operator);
		assertEquals("AbcDEF", optResult.get());
		
		// when we pass in an identity to the reduce operation the result is not an Optional 
		// but the type of the stream (in this case String)
		String result = supplier.get().reduce("-", operator);
		assertEquals("-ABCDEF", result);
		
		
	}
}
