package ocp.lambda;

import java.util.ArrayList;
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
}
