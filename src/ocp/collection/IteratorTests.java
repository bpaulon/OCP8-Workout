package ocp.collection;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ocp.lambda.ExceptionLoggingRule;

public class IteratorTests {

	@Rule
	public ExceptionLoggingRule exLogger = new ExceptionLoggingRule();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * Cannot remove elements in a for loop. It throws ConcurrentModificationException. One must use an iterator for
	 * that.
	 */
	@Test
	public void removeOnIteratedListShouldThrowException() {
		List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));

		thrown.expect(ConcurrentModificationException.class);
		for (String el : list) {
			list.remove(el);
		}
	}

	@Test
	public void removeOnIteratorShouldChangeCollection() {
		List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
		Iterator<String> itList = list.iterator();
		while (itList.hasNext()) {
			itList.next();
			itList.remove();
		}

		assertTrue(list.isEmpty());
	}

	@Test
	public void concurrentHashMapShouldAllowModifications() {
		ConcurrentHashMap<Integer, String> map = IntStream.of(1, 2, 3, 4, 5)
				.boxed()
				.collect(Collectors.toMap(k -> k, k -> k + "", (v1, v2) -> v1, ConcurrentHashMap::new));

		// the keySet() method returns a KeySetView backed by the map. Modifications to the map reflect in the
		// KeySetView
		Set<Integer> keys = map.keySet()
				.stream()
				.collect(Collectors.toSet());
		// or
		// Integer[] keys = map.keySet().toArray(new Integer[]{});
		for (Integer key : keys) {
			String value = map.remove(key);
			map.put(key * 10, value);
		}

		List<Integer> al = new ArrayList<>(map.keySet());
		al.sort(Comparator.naturalOrder());
		assertEquals(Arrays.asList(10, 20, 30, 40, 50), al);
	}

}
