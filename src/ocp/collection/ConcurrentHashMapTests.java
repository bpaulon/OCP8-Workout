package ocp.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConcurrentHashMapTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	ConcurrentHashMap<String, LongAdder> map = Stream.of("a", "b", "c", "d")
			.collect(Collectors.toMap(Function.identity(), i -> new LongAdder(), (v1, v2) -> v1,
					ConcurrentHashMap::new));

	@Test
	public void test01() {

		map.computeIfAbsent("Z", k -> new LongAdder())
				.increment();
		assertNotNull(map.get("Z"));
		assertEquals(1, map.get("Z")
				.longValue());

		map.computeIfPresent("X", (k, v) -> {
			v.increment();
			return v;
		});
		assertNull(map.get("X"));

		thrown.expect(NullPointerException.class);
		// assumes key X exists and it fails with NPE when trying to increment
		map.compute("X", (k, v) -> {
			v.increment();
			return v;
		});
	}

	@Test
	public void test02() {
		LongAdder la = new LongAdder();
		la.add(2);
		
		BiFunction<LongAdder, LongAdder, LongAdder> bf = (oldValue, newValue) -> {
			oldValue.add(newValue.longValue());
			return oldValue;
		};

		// merge (key, newValue, BiFunction(oldValue, newValue))
		map.merge("a", la, bf);
		assertEquals(2, map.get("a")
				.longValue());

		// the key "X" does not exist and the mapping function doesn't get called
		map.merge("X", la, bf);
		assertEquals(la, map.get("X"));
	}

}
