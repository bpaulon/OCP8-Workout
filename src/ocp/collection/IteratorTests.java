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
import java.util.function.UnaryOperator;
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

	final List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
	
	/**
	 * Cannot remove elements in a for loop. It throws ConcurrentModificationException. 
	 * Use an iterator for that.
	 */
	@Test
	public void removeOnIteratedListShouldThrowException() {
		thrown.expect(ConcurrentModificationException.class);
		for (String el : list) {
			list.remove(el);
		}
	}

	@Test
	public void removeOnIteratorShouldChangeCollection() {
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			it.next();
			it.remove();
		}

		assertTrue(list.isEmpty());
	}
	
	@Test
	public void iteratingAModifiedCollectionShouldThrowException() {
		Iterator<String> it = list.iterator();
		while(it.hasNext()) {
			list.add("t");
			
			thrown.expect(ConcurrentModificationException.class);
			it.next();
		}
	}

	@Test
	public void concurrentHashMapShouldAllowModifications() {
		ConcurrentHashMap<Integer, String> map = IntStream.of(1, 2, 3, 4, 5)
				.boxed()
				.collect(Collectors.toMap(UnaryOperator.identity(), k -> k + "", (v1, v2) -> v1, ConcurrentHashMap::new));

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
	
	@Test
	public void fromIteratorToList() {
		Iterator<Integer> it = Arrays.asList(1, 2,3).iterator();
		it.forEachRemaining(new ArrayList<>()::add);
	}

}
