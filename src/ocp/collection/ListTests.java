package ocp.collection;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class ListTests {

	@Test
	public void test01() {
		List<String> l1 = Arrays.asList("a", "b");
		
		// replaceAll accepts an UnaryOperator
		l1.replaceAll(s -> s+"*");
		assertEquals(Arrays.asList("a*", "b*"), l1);
	}
	
	@Test
	public void test02() {
		List<String> l1 = new ArrayList<>(Arrays.asList("a", "b"));
		//removeIf needs a Predicate
		l1.removeIf(s -> s.startsWith("a"));
		assertEquals(1, l1.size());
	}
	
}
