package ocp.lambda.flatmap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class FlatMapTest {

	@Test
	public void test01() {

		List<String> strings = Arrays.asList("a", "b", "c", "d");
		List<Integer> integers = Arrays.asList(1, 2, 3, 4);
		List<? extends Object> reunion = Stream.of(strings, integers)
				.flatMap(l -> l.stream())
				.collect(Collectors.toList());
		System.out.println(reunion);

		// Stream.of(e) returns a stream of one element which is a list.
		Stream.of(strings, integers)
				.flatMap(e -> Stream.of(e))
				.forEach(System.out::print);

		// Slightly more complicated version where the list is transformed into a stream of elements
		Stream.of(strings, integers)
				.flatMap(e -> Stream.of(e).flatMap(t -> t.stream()))
				.forEach(System.out::print);
	}

}
