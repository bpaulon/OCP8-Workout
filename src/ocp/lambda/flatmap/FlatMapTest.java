package ocp.lambda.flatmap;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
		System.out.println("strings + integers: " + reunion);

		// Stream.of(e) returns a stream of one element which is a list.
		Stream.of(strings, integers)
				.flatMap(e -> Stream.of(e))
				.forEach(System.out::print);

		// Slightly more complicated version where the list is transformed into a stream of elements
		Object[] a = Stream.of(strings, integers)
				.flatMap(e -> Stream.of(e)
						.flatMap(t -> t.stream()))
				.peek(System.out::println)
				.toArray(Object[]::new);

		System.out.println(Arrays.toString(a));
	}

	
	@Test
	public void test02() {
		List<Integer> l1 = Arrays.asList(1, 2, 3);
		Set<Integer> s1 = l1.stream()
				.flatMapToInt(n -> IntStream.of(n.intValue()))
				.boxed()
				.collect(Collectors.toSet());
		assertEquals(new HashSet<>(l1), s1);

		// transform multiple lists of Integer into int and box back to Integer 
		List<Integer> l2 = Arrays.asList(4, 5, 6);
		Set<Integer> s2 = Stream.of(l1, l2)
				.flatMapToInt(n -> n.stream()
						.flatMapToInt(i -> IntStream.of(i.intValue())))
				.boxed()
				.collect(Collectors.toSet());

		Set<Integer> s3 = new HashSet<>();
		s3.addAll(l1);
		s3.addAll(l2);
		assertEquals(s3, s2);
	}
	
	@Test
	public void test03() {
		// Stream.of(T...values) - is a var-arg method so we can pass in an array.
		String s = "ab,cd,ef";
		List<String> ls1 = Stream.of(s.split(","))
				.collect(Collectors.toList());
		List<String> ls2 = Stream.of(new String[] { "ab", "cd", "ef" })
				.collect(Collectors.toList());
		assertEquals(ls1, ls2);

		// Stream.of(T val) creates a stream from the passed in object (one element)
		List<Integer> l = Arrays.asList(1, 2, 3, 4);
		Stream.of(l)
				.forEach(e -> assertTrue(e instanceof List));
		Stream.of(l)
				.flatMap(l1 -> l1.stream())
				.forEach(e -> assertTrue(e instanceof Integer));
	}
}
