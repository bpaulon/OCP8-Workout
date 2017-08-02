package ocp.lambda.parallel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * limit, skip, Collectors.toList(), findFirst, forEachOrdered will return the same result on ordered serial and
 * parallel streams
 * 
 *
 */
public class ParallelStreamTests {

	@Test
	public void collectorsShouldPreserveOrder() {
		// the Collectors.toList() preserves the encounter order
		List<Integer> l1 = IntStream.iterate(0, i -> ++i)
				.boxed()
				.limit(100)
				.parallel()
				.collect(Collectors.toList());

		assertEquals(IntStream.rangeClosed(0, 99)
				.boxed()
				.collect(Collectors.toList()), l1);

	}

	@Test
	public void forEachOrderedShouldPreserveOrder() {
		List<Integer> acc = new ArrayList<>();
		Supplier<Stream<Integer>> sup = () -> IntStream.rangeClosed(1, 10)
				.parallel()
				.boxed();

		// forEachOrdered maintains the encounter order
		sup.get()
				.forEachOrdered(s -> acc.add(s));
		assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), acc);

		// forEach does not maintain the encounter order
		List<Integer> acc2 = new ArrayList<>();
		sup.get()
				.forEach(s -> acc2.add(s));
		assertThat(acc2, not(equalTo(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))));
	}

	@Test
	public void reduceShouldPreserveOrder() {
		String s1 = Arrays.asList("a", "b", "c", "d", "e")
				.stream()
				.parallel()
				.reduce("", (a, b) -> a + b);
		assertEquals("abcde", s1);
	}

	@Test
	public void generatedStreamShouldNotBeOrdered() {
		final List<String> l1 = Arrays.asList("a", "b", "c", "d", "e");

		Supplier<String> su1 = new Supplier<String>() {
			int i = 0;

			public String get() {
				String s1 = l1.get(i++ % 5);
				System.out.println(s1);
				return s1;

			}
		};
		String rs1 = Stream.generate(su1)
				.limit(10)
				.parallel()
				.peek(s -> System.out.format(">>>%s\n", s))
				.reduce("", (a, b) -> a + b);
		System.out.println(rs1 + "___________________\n");

		UnaryOperator<Integer> uo = new UnaryOperator<Integer>() {
			// workaround eclipse bug. If line too long it cannot display it so I flush from time to time
			int bufferSize = 0;

			public Integer apply(Integer i) {
				System.out.printf("%2s", i);
				if (++bufferSize == 100) {
					System.out.println();
					bufferSize = 0;
				}
				return ++i % 5;
			}
		};
		String rs2 = Stream.iterate(0, uo)
				.map(i -> l1.get(i))
				.limit(10)
				.parallel()
				.peek(System.out::print)
				.reduce("", (a, b) -> a + b);

		System.out.format("\n>>%s\n", rs2);

	}

	@Test
	public void test03() {
		Supplier<Stream<?>> s = () -> Stream.empty();
		assertFalse(s.get()
				.isParallel());
		assertTrue(s.get()
				.parallel()
				.isParallel());
		assertFalse(s.get()
				.parallel()
				.sequential()
				.isParallel());
	}

	@Test
	public void test04() {
		IntStream.of(1, 1, 3, 3, 7, 6, 7)
				.distinct()
				.parallel()
				.map(i -> i * 2)
				.sequential()
				.forEach(System.out::print); // -> 261412
	}

	@Test
	public void findFirstShouldPreserveOrder() {
		Supplier<IntStream> sup = () -> IntStream.rangeClosed(1, 100)
				.parallel();
		int i1 = sup.get()
				.findFirst()
				.getAsInt();
		assertEquals(1, i1);

		// findAny is non-deterministic
		i1 = sup.get()
				.findAny()
				.getAsInt();
		System.out.println(i1);
		// assertNotEquals(1, i1);
	}

	@Test
	public void limitShouldPreserveOrder() {
		// we forcedly adding elements to a synchronized collection in a forEach call
		List<Integer> l1 = Collections.synchronizedList(new ArrayList<>());
		Supplier<IntStream> sup = () -> IntStream.rangeClosed(1, 100)
				.parallel();
		sup.get()
				.boxed()
				.limit(90)
				.forEach(i -> l1.add(i));

		Collections.sort(l1);
		System.out.println(l1.size());
		System.out.println(l1);
	}
	
	@Test
	public void reduceIdentityIsRepeated() {
		String res = Arrays.asList("a", "b", "c", "d", "e")
				.parallelStream()
				.reduce("*", String::concat);
		System.out.println(res); // --> *a*b*c*d*e
	}
	
	@Test
	public void sortedCreatesEncounterOrderForCollect() {
		Supplier<Stream<String>> sup = () -> Arrays.asList("x", "b", "c", "a", "e")
				.parallelStream();
		List<String> l1 = null;
		l1 = sup.get().sorted()
				.collect(Collectors.toCollection(ArrayList<String>::new));
		assertEquals(Arrays.asList("a","b","c","e","x"), l1);
		
		// the forEach breaks the order
		sup.get().sorted().forEach(System.out::print);
		// need to use forEachOrdered
		sup.get().sorted().forEachOrdered(System.out::print);
	}
}
