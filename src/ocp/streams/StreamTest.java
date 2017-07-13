package ocp.streams;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;
import static org.junit.Assert.*;

public class StreamTest {

	@Test
	public void testReducingOps01() {
		Supplier<Stream<Integer>> supplier = () -> Stream.of(1,2,3,4,5);
		
		
		assertEquals(5,
				supplier.get().mapToInt(i -> i.intValue()).count());
		
		assertEquals(3, 
				supplier.get().mapToDouble(i-> i.intValue()).average().getAsDouble(), 0.0);
		
		assertEquals(.8, .1 + .7 + "");
		
	}
}
