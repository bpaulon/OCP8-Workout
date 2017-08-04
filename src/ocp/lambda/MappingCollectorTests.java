package ocp.lambda;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

@SuppressWarnings("unused")
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
				.collect(Collectors.toMap(Function.identity() /* k -> k */, 
						k -> index.incrementAndGet(),
						(key1, key2) -> key1));
				
		assertEquals(Arrays.asList("aa", "bb", "cc"), map.keySet()
				.stream()
				.sorted()
				.collect(Collectors.toList()));
		System.out.println(map);
	}

	@Test
	public void duplicateKeysShouldBeGrouped() {
		AtomicInteger index = new AtomicInteger();

		// {cc=[4], bb=[2], aa=[1, 3]}
		Map<String, List<Integer>> map = supplier.get()
				.collect(Collectors.groupingBy(Function.identity(),
						Collectors.mapping(k -> (Integer) index.incrementAndGet(), Collectors.toList())));

		// [1, 2, 3, 4]
		List<Integer> positions = map.keySet()
				.stream()
				.flatMap(k -> map.get(k).stream())
				.sorted()
				.collect(Collectors.toList());
		assertEquals(Arrays.asList(1, 2, 3, 4), positions);
	}
	
	@Test
	public void testMinBy() {
		Map<String, Long> map = supplier.get()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		System.out.println(map);
	}
	
	@Test
	public void testGroupingWithPartioning() {
		Map<Integer, ?> m = Stream.of(56, 54, 1, 31, 98, 98, 16)
				.collect(Collectors.groupingBy(i -> i % 10));
		//{1=[1, 31], 4=[54], 6=[56, 16], 8=[98, 98]}
		System.out.println(m);

		m = Stream.of(56, 54, 1, 31, 98, 98, 16)
				.collect(Collectors.groupingBy(i -> i % 10, Collectors.partitioningBy(s -> s %2 == 0)));
		//{1={false=[1, 31], true=[]}, 4={false=[], true=[54]}, 6={false=[], true=[56, 16]}, 8={false=[], true=[98, 98]}}
		System.out.println(m);

		m = Stream.of(56, 54, 1, 31, 98, 98, 16)
				.collect(Collectors.groupingBy(i -> i % 10, TreeMap::new, Collectors.partitioningBy(i -> i > 5)));
		//{1={false=[1], true=[31]}, 4={false=[], true=[54]}, 6={false=[], true=[56, 16]}, 8={false=[], true=[98, 98]}}
		System.out.println(m);
	}
	
	
	@Test
	public void testGroupingByReturn() {
		Supplier<Stream<Integer>> s = () -> Stream.of(1,1, 2,3,4, 13, 13);
		//Supplier<Stream<?>> s = () -> Stream.empty();
		
		Map<?,?> m1 = s.get().collect(Collectors.groupingBy(Object::toString));
		System.out.println(m1);
		
		ConcurrentMap<?,?> m2 = s.get().collect(Collectors.groupingByConcurrent(Object::toString));
		System.out.println(m2);
		
		ConcurrentMap<?,?> m3 = s.get().collect(Collectors.toConcurrentMap(Object::toString, Object::toString, (k, v) -> v));
		System.out.println(m3);
		
		Map<?,?> m4 = s.get().collect(Collectors.mapping(Object::toString, Collectors.toMap(Function.identity(), Function.identity(), (k, v) -> v)));
		System.out.println(m4);
		
		List<?> m5 = s.get().collect(Collectors.mapping(Object::toString, Collectors.toList()));
		System.out.println(m5);
	}
}
