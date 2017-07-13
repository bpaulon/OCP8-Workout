package ocp.lambda;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class PeekTest {

	private Stream<String> stream = Stream.of("black bear", "brown bear", "grizzly");
	
	
	@Test
	public void testPeek01() {
		long count = stream.filter(s -> s.startsWith("g"))
				.peek(System.out::println)
				.count(); // grizzly
		
		System.out.println(count); // 1
	}
	
	@Test
	public void testPeek02() {
		long count = stream.peek(System.out::println)
				.count();
		
		System.out.println(count); //3
	}
	
	@Test
	public void testPeek03() {
		StringBuilder builder = new StringBuilder();
		stream.peek(l->builder.append(l + "/"))
			.forEach(System.out::println);
		
		System.out.println(builder.toString());
	}
	
	@Test 
	public void testCount() {
		IntStream intStream = IntStream.range(1,6);
		long count = intStream.count();
		System.out.println(intStream.average().orElse(0)*count);
	}
}
