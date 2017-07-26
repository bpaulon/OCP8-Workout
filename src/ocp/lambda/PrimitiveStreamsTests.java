package ocp.lambda;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PrimitiveStreamsTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
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
	
	@Test
	public void toArray() {
		Supplier<Stream<Double>> su = () -> Stream.of(1.0, 2.0, 3.0, 4.0);
		IntFunction<Double[]> f = i -> new Double[i];
		Double[] a = su.get().toArray(f);

		// OR
		f = Double[]::new;
		a = su.get().toArray(f);

		assertArrayEquals(new Double[] { 1.0, 2.0, 3.0, 4.0 }, a);
	}
	
	@Test
	public void test03() {
		Supplier<IntStream> s = () -> IntStream.of(10, 5, 7, 13, 11);
		OptionalInt oi = s.get()
				.reduce(Integer::max);
		assertEquals(13, oi.getAsInt());

		Optional<Integer> oo = s.get()
				.mapToObj(i -> i)
				.max(Comparator.naturalOrder());
		assertEquals(Integer.valueOf(13), oo.get());
	}
	
	@Test
	public void modifyingABuiltStreamShouldThrowException() {
		IntStream.Builder b = IntStream.builder();
		b.add(1).add(2).add(3).accept(4);
		b.build();
		
		thrown.expect(IllegalStateException.class);
		b.add(5);
	}
	
	@Test
	public void convertAndNarrowTest() {
		Supplier<IntStream> s = () -> IntStream.of(1, 2, 3, 4, 5);
		int[] a = s.get()
				.map(i -> ++i)
				.mapToObj(i -> i)	// Stream<Integer>
				.mapToInt(i -> i)	//-> IntStream
				.mapToDouble(i -> i) //-> DoubleStream
				.mapToInt(i -> (int) i) //-> explicit narrow required to convert to ->IntStream from double
				.toArray();
		
		assertArrayEquals(new int[]{2,3,4,5,6}, a);		
	}
}
