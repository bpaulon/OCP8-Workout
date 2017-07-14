package ocp.generics;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ocp.lambda.ExceptionLoggingRule;

public class CovariantTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	@Rule
	public ExceptionLoggingRule exLogger = new ExceptionLoggingRule();
	
	public static <T extends Number> List<T> process(List<T> args) {
		return args;
	}

	public static <T extends Number> T[] process(T[] args) {
		return args;
	}
	
	@SuppressWarnings("unused")
	@Test
	public void test01() {
		ArrayList<Integer> list = new ArrayList<>();
		
		// VALID
		List<Integer> l1 = process(list);
		
		// INVALID - Generic types are INVARIANT. Invariant simply means irrespective of X being subtype of Y or not ,
		// 
		// Process(List<Integer>) returns List<Integer> which 
		// cannot be converted to List<Number>
		// List<Number> numbers = process(list); // DOES NOT COMPILE
		//
		// It it would then we could have add a Long or Double to the List<Number> and get a ClassCast exception at runtime
		// 
		// Eg:
		//		List<Integer> li1 = new ArrayList<>();
		//		List<Number> li2 = li1; // - DOES NOT COMPILE
		//		li2.add(new Double(2));
		//		Integer i1 = li1.get(0); // wrong type - cannot convert from Double to Integer
		
		// VALID List<Integer> is a subtype of List<? super Integer>
		List<? super Integer> l2 = process(list);
		
		// VALID List<Integer> is a subtype of both List<? extends Integer>, List<? extends Number> and List<?> 
		List<? extends Integer> l3 = process(list);
		List<? extends Number> l4 = process(list);
		List<?> l5 = process(list);
	}
	
	@Test
	@SuppressWarnings("unused")
	public void test02() {
		Integer[] ia = new Integer[] {1,2,3,};
		
		Integer[] a1 = process(ia);
		
		// Arrays are COVARIANT. If X is subtype of Y then X[] will also be sub type of Y[]. 
		// The following is VALID
		Number[] a2 = process(ia);
		
		// we try to store a Long into an array of Integers. The type of the array is known at runtime
		thrown.expect(ArrayStoreException.class);
		a2[1] = new Long(1L);
	}
	
	@Test
	public void test03() {
		// Obviously you cannot instantiate a generic type because of type erasure. The following are INVALID 
		// new E();
		// new ArrayList<? extends Number>();
		
		// but this is VALID because the exact type is inferred (in this case is Object)
		new ArrayList<>();
	}
	
}
