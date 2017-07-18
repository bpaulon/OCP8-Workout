package ocp.collection;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MapTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	Map<Integer, String> m = Stream.of(1, 2, 3, 4)
			.collect(Collectors.toMap(UnaryOperator.identity(), i -> i + "_"));

	@Test
	public void test01() {
		// map replaceAll takes a BiFunction where the key and value are the params and the result is the new value
		m.replaceAll((k, v) -> v.replace("_", "")
				.concat("*"));
		System.out.println(m);
	}

	@Test
	public void forEach_ModifyMap_ExceptionThrown() {
		thrown.expect(ConcurrentModificationException.class);
		m.forEach((k, v) -> m.remove(k));
	}

	@Test
	public void test02() {
		Map<Integer, String> m2 = new HashMap<>();
		m2.putAll(m);
		m2.put(5, "5*");
		// The remapping function takes 2 parameters wheich are the 2 values of the maps to be merged and the result is
		// the new value to be put in the map being merged
		BiFunction<String, String, String> remappingFunction = (s, t) -> {
			System.out.println("s: " + s + " t:" + t);
			return s + t;
		};
		m.forEach((k, v) -> m2.merge(k, v, remappingFunction));

		System.out.println(m2);
	}
	
}
