package ocp.lambda.parallel;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

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
		IntStream.rangeClosed(1, 100)
				.parallel()
				.boxed()
				.forEachOrdered(System.out::println);
	}

	@Test
	public void reduceShouldPreserveOrder() {
		String s1 = Arrays.asList("a", "b", "c", "d", "e")
				.stream()
				.parallel()
				.reduce("", (a, b) -> a + b);
		System.out.println(s1);
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
				.peek( s -> System.out.format(">>>%s\n", s))
				.reduce("", (a, b) -> a + b);
		System.out.println(rs1 +"___________________\n");

		
		UnaryOperator<Integer> uo = new UnaryOperator<Integer>() {
			// workaround eclipse bug. If line too long it cannot display it so I flush from time to time
			int bufferSize = 0;
			public Integer apply(Integer i){
				System.out.printf("%2s", i );
				if(++bufferSize == 100) {
					System.out.println();
					bufferSize = 0;
				}
				return ++i %5;
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
		System.out.println(IntStream.of(1,2,3,4).isParallel());
	}
}
