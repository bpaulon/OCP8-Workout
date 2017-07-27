package ocp.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class CollectorTests {

	@Test
	public void test01() {
		Long count = Stream.of(1, 2, 3, 4)
				.collect(Collectors.counting());
		// equivalent to
		Long l = Stream.of(1, 2, 3, 4)
				.count();
	}

	@Test
	public void test02() {
		System.out.println(Stream.of(1, 2, 3, 4, 5, 6)
				.collect(Collectors.groupingBy(i -> i % 3)));
		
		// equivalent to
		System.out.println(Stream.of(1, 2, 3, 4, 5, 6)
				.collect(Collectors.groupingBy(i -> i % 3, Collectors.toCollection(ArrayList::new))));

	}
	
	@Test
	public void test03() {
		Supplier<Integer> s = new Supplier<Integer>() {
			int i = 0;
			@Override
			public Integer get() {
				return ++i;
			}
		};
		
		Map<Integer, Integer> m = Stream.generate(s).limit(5).collect(Collectors.toMap(Function.identity(), Function.identity()));
		System.out.println(m);
		
		System.out.println(Arrays.toString(Stream.iterate("a", str -> str + "?").limit(5).toArray()));
	}
}
