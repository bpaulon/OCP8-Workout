package ocp.collection;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class ListTests {

	@Test
	public void test01() {
		List<String> l1 = Arrays.asList("a", "b");
		
		// replaceAll accepts an UnaryOperator
		l1.replaceAll(s -> s+"*");
		System.out.println(l1);
	}
}
