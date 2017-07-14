package ocp.lambda.parallel;

import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class ParallelStreamTests {

	@Test
	public void collectorsShouldPreserveEncounterOrder() {
		// the Collectors.toList() preserves the encounter order
		System.out.println(IntStream.iterate(0, i -> ++i).boxed().limit(100).parallel().collect(Collectors.toList()));
		
		IntStream.iterate(0, i -> ++i).limit(100).boxed().parallel().forEach(System.out::print);
		
	}
}
