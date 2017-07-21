package ocp.lambda;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class CollectorTests {

	@Test
	public void test01() {
		Long count = Stream.of(1,2,3,4).collect(Collectors.counting());
	}
}
