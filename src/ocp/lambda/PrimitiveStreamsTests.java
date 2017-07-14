package ocp.lambda;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.junit.Test;

public class PrimitiveStreamsTests {

	@Test
	public void sumShouldReturnTypeOfStream() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

		double doubleSum = list.stream()
				.mapToDouble(x -> x)
				.sum();
		assertEquals(15.0, doubleSum, 0.0);

		long longSum = list.stream()
				.mapToLong(x -> x)
				.sum();
		assertEquals(15L, longSum);
	}

	@Test
	public void sumOfMappedDoubleValuesShouldReturnCorrectValue() {
		List<String> list = Arrays.asList("1", "2", "3");

		double sum = list.stream()
				.mapToDouble(x -> Double.valueOf(x))
				.sum();
		assertEquals(6.0, sum, 0.0);
	}

	@Test
	public void sumOfMappedIntValuesShouldReturnCorrectValue() {
		List<Integer> list = Arrays.asList(1, 2, 3);
		int sum = list.stream().mapToInt(i -> i).sum();
		assertEquals(6, sum);
	}
	
	@Test
	public void sumOfIntValuesShouldReturnCorrectvalue() {
		Supplier<IntStream> s = () -> IntStream.of(1, 2, 3);
		
		int sum = s.get().sum();
		assertEquals(6, sum);
		
		// overloaded reduce method with identity returns an Integer and not an
		// Optional
		sum = s.get().boxed().reduce(0, (i,j) -> i += j);
		assertEquals(6, sum);
		
		// provide a supplier that returns an Integer for the orElseGet
		sum = s.get().boxed().reduce((i,j) -> i += j).orElseGet(() -> 0);
		assertEquals(6, sum);
		
		// provide a default value for orElse
		sum = s.get().boxed().reduce((i,j) -> i += j).orElse(0);
		assertEquals(6, sum);
		
	}
	
	@Test
	public void averageOfIntValues() {
		Supplier<IntStream> s = () -> IntStream.of(1, 2, 3);
		double avg = s.get().average().getAsDouble();
		assertEquals(2.0, avg, 0.0);
	}
}
