package ocp.optional;

import java.util.Optional;

import org.junit.Test;

public class OptionalTests {

	@Test
	public void test01() {
		Optional<?> opt = Optional.ofNullable(null);
		System.out.println(opt);
		
		Optional<Long> ol = Optional.of(1L);
		System.out.println(ol);
		
		Optional<?> empty = Optional.empty();
		System.out.println(empty);
	}
}
