package ocp.optional;

import static org.junit.Assert.assertEquals;

import java.util.Optional;
import java.util.function.Function;

import org.junit.Test;

public class OptionalTests {

	@Test
	public void test01() {
		Optional<?> opt = Optional.ofNullable(null);
		assertEquals("Optional.empty", opt.toString());
		
		Optional<Long> ol = Optional.of(1L);
		assertEquals("Optional[1]", ol.toString());
		
		Optional<Double> od = Optional.of(new Double(2D));
		assertEquals("Optional[2.0]", od.toString());
		
		Optional<?> empty = Optional.empty();
		assertEquals("Optional.empty", empty.toString());
	}
	
	@Test
	public void test02() {
		Optional<Integer> opt = Optional.of(new Integer(2));
		assertEquals(Integer.valueOf(5), opt.orElseGet(() -> new Integer(5)));
		opt.orElse(5);
		opt.orElseThrow(RuntimeException::new);
	}
	
	@Test
	/**
	 * If a value is present, apply the provided mapping function to it, and if the result is non-null, return an
	 * Optional describing the result. Otherwise return an empty Optional
	 */
	public void mapShouldTestIfValuePresent() throws Exception {
		Optional<String> os = Optional.empty();
		assertEquals(Integer.valueOf(5), os.map(String::length)
				.orElse(5));

		os = Optional.of("foo");
		assertEquals(Integer.valueOf(3), os.map(String::length)
				.orElseThrow(Exception::new));

		os.flatMap(s -> Optional.ofNullable("bar"))
				.ifPresent(s -> assertEquals("bar", s));
	}
}
