package ocp.collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

@SuppressWarnings("unused")
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
		
		// The re-mapping function takes as parameters the 2 values of the maps to be merged and the result is
		// the new value to be put in the map being merged
		BiFunction<String, String, String> remappingFunction = (s, t) -> {
			System.out.println("s: " + s + " t:" + t);
			return s + t;
		};
		m.forEach((k, v) -> m2.merge(k, v, remappingFunction));

		System.out.println(m2);
	}
	
	/**
	 * HashMap has three constructors to create an empty map with initial capacity and load factor
	 * 
	 * <ul>
	 * <li>HashMap() - capacity 16, load factor 0.75</li>
	 * <li>HashMap(int initialCapacity)</li>
	 * <li>HashMap(int initialCapactiy, float loadFactor)</li>
	 * </ul>
	 * The following test the correct selection of the constructor using lambda Supplier, Function and BiFunction
	 */
	@Test
	public void shouldResolveConstructor() {
		Supplier<Map<?, ?>> s = HashMap::new;
		Map<?, ?> m = s.get();

		Function<Integer, Map<?, ?>> f = HashMap::new;
		m = f.apply(1);

		BiFunction<Integer, Integer, Map<String, String>> bf = HashMap::new;
		m = bf.apply(1, 1);
	}
	
	@Test
	public void testNull() {
		Map<Integer, String> m = new HashMap<>(1, 2.0f);
		m.put(null, "abc");
		m.put(null, "def");
		m.put(1, null);
		m.put(2, "xyz");

		assertEquals("def", m.get(null));
		assertTrue(m.containsKey(null));
		assertTrue(m.containsKey(1));

		// EXISTING KEY & (IRRELEVANT) COMPUTED VALUE -> does nothing and returns old value
		String s = m.computeIfAbsent(null, key -> ">" + key);
		assertEquals("def", s);

		// NON-EXISTING KEY & NULL COMPUTED VALUE -> does nothing and returns null
		s = m.computeIfAbsent(999, key -> null);
		assertThat(s, is(nullValue()));

		// NON-EXISTING KEY & NOT NULL COMPUTED VALUE -> replaces old value with computed value and returns new value
		s = m.computeIfAbsent(999, key -> ">" + key);
		assertThat(s, is(">999"));


		// NON-EXISTING KEY & (IRRELEVANT) COMPUTED VALUE -> does nothing and returns null
		s = m.computeIfPresent(333, (key, value) -> null);
		assertThat(s, is(nullValue()));
		assertThat(m.containsKey(333), is(false));

		// EXISTING KEY & NULL OLD VALUE -> does nothing and returns null
		s = m.computeIfPresent(1, (key, value) -> null);
		assertThat(s, is(nullValue()));
		assertThat(m.containsKey(1), is(true));

		// EXISTING KEY & NULL COMPUTED VALUE -> REMOVES existing key and returns null
		s = m.computeIfPresent(null, (key, oldValue) -> null);
		assertThat(s, is(nullValue()));
		assertThat(m.containsKey(null), is(false));

		// EXISTING KEY & NOT NULL COMPUTED VALUE -> replaces old value with computed value and returns new value
		s = m.computeIfPresent(2, (key, oldValue) -> ">" + oldValue);
		assertThat(s, is(">xyz"));
	}

	@Test
	public void testMerge() {
		//System.out.println(m);
		// Existing key - null value in map -> sets key
		m.put(5, null);
		m.merge(5, "aaa", (oldVal, newVal) -> null);
		System.out.println(m);
		
		// NON EXISTING KEY ->sets Key
		m.merge(6, "aaa", (oldVal, newVal) -> null);
		System.out.println(m);
		
		
		// key not in map
		m.merge(7, "###", (val1, val2) -> val1 + val2);
		System.out.println(m);
		
		m.merge(4, "+++", (oldValue, newValue /* newValue = "+++"*/) -> oldValue + "-" +newValue);
		System.out.println(m);
	}
}
