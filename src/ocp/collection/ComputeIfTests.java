package ocp.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public class ComputeIfTests {

	Map<String, Integer> countMap;

	@Before
	public void setup() {
		countMap = new HashMap<>();
		countMap.put("Jenny", 15);
		countMap.put("Tom", null);
	}

	@Test
	public void testComputeIfPresent01() {
		BiFunction<String, Integer, Integer> remappingFunction = (k, v) -> v + 1;

		Integer jennyV = countMap.computeIfPresent("Jenny", remappingFunction);
		Integer samV = countMap.computeIfPresent("Sam", remappingFunction);

		assertEquals(Integer.valueOf(16), jennyV);
		assertNull(samV);
		assertEquals(Arrays.asList(null, "16"), countMap.values()
				.stream()
				.sorted(Comparator.nullsFirst(Comparator.naturalOrder()))
				.collect(Collectors.toList()));
	}
	
	@Test
	public void testComputeIfPresent02() {
		BiFunction<String, Integer, String> remappingFunction = (k, v) -> k + "-";
		
		// will not compile. The returned type of the BiFunction must be the same type as 
		// the value in the map
		//String newMappedJenny = counts. computeIfPresent("Jenny", remappingFunction);
		
	}
	
	@Test
	public void testComputeIfAbsent() {
		Function<String, Integer> mappingFunction = (k) -> 1;
		
		Integer jennyVal = countMap.computeIfAbsent("Jenny", mappingFunction);
		Integer samVal = countMap.computeIfAbsent("Tom", mappingFunction);
		Integer tomVal  = countMap.computeIfAbsent("Tom", mappingFunction);
		
		System.out.println("jennyV:" + jennyVal 
				+ "\n samV:" + samVal  
				+ "\n tomV:" + tomVal);
		System.out.println(countMap);
	}
	
}
