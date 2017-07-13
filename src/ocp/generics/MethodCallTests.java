package ocp.generics;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MethodCallTests {

	public static <T extends Number> List<T> process(List<T> args) {
		return null;
	}

	@SuppressWarnings("unused")
	@Test
	public void test02() {
		ArrayList<Integer> list = new ArrayList<>();
		
		// VALID
		List<Integer> l1 = process(list);
		
		// INVALID - Generic types are invariant. Process(List<Integer>) returns List<Integer> which 
		// cannot be converted to List<Number>
		// List<Number> numbers = process(list); // DOES NOT COMPILE
		
		// VALID List<Integer> is a subtype of List<? super Integer>
		List<? super Integer> l2 = process(list);
		
		// VALID List<Integer> is a subtype of both List<? extends Integer>, List<? extends Number> and List<?> 
		List<? extends Integer> l3 = process(list);
		List<? extends Number> l4 = process(list);
		List<?> l5 = process(list);
	}
	
}
