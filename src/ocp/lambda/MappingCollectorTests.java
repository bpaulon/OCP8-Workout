package ocp.lambda;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MappingCollectorTests {

	@Rule
	public ExceptionLoggingRule exLogger = new ExceptionLoggingRule();
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	Supplier<Stream<String>> supplier = () -> Arrays.asList("aa", "bb", "aa", "cc")
			.stream();

	@Test
	public void duplicateKeysShouldThrowException() {
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("Duplicate key");
		supplier.get()
				.collect(Collectors.toMap(k -> k, k -> k.length()));
	}

	@Test
	public void duplicateKeysShouldBeMerged() {
		AtomicInteger index = new AtomicInteger();
		Map<String, Integer> map = supplier.get()
				.collect(Collectors.toMap(UnaryOperator.identity() /* k->k */, 
						k -> index.incrementAndGet(),
						(key1, key2) -> key1));
		assertEquals(Arrays.asList("aa", "bb", "cc"), map.keySet()
				.stream()
				.sorted()
				.collect(Collectors.toList()));

	}

	@Test
	public void duplicateKeysShouldBeGrouped() {
		AtomicInteger index = new AtomicInteger();

		// {cc=[4], bb=[2], aa=[1, 3]}
		Map<String, List<Integer>> map = supplier.get()
				.collect(Collectors.groupingBy(k -> (String) k,
						Collectors.mapping(k -> (Integer) index.incrementAndGet(), Collectors.toList())));

		// [1, 2, 3, 4]
		List<Integer> list = map.keySet()
				.stream()
				.flatMap(k -> map.get(k)
						.stream())
				.sorted()
				.collect(Collectors.toList());
		assertEquals(Arrays.asList(1, 2, 3, 4), list);
	}
}
