package ocp.lambda;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamMaxTests {

	@Test
	public void testMax01() {
		Optional<Integer> max = Stream.of(1, 2, 8, 4, 34, 23)
				.max(new Comparator<Integer>() {
					@Override
					public int compare(Integer o1, Integer o2) {
						return o1.compareTo(o2);
					}
				});
		assertEquals(Integer.valueOf(34), max.get());

		//-- with IntStream we get call max without a comparator 
		OptionalInt maxi = IntStream.of(1, 2, 8, 4, 34, 23)
				.max();
		assertEquals(34, maxi.getAsInt());
	}
}
