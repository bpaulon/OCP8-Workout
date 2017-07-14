package ocp.lambda;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import org.junit.Test;
import static org.junit.Assert.*;

public class EffectiveFinalCompileTests {

	
	@Test
	public void test01() {
		AtomicLong a = new AtomicLong(1L);
		IntStream.range(1,3).forEach(s -> a.set(s));
		assertEquals(2L, a.get());
	}
	
	@Test
	public void test02() {
		AtomicLong a = new AtomicLong(1L);
		IntStream.range(1,3).forEach(s -> a.set(s));
		// changing the object reference after this line will break the lambda expression - DOES NOT compile
		// -> "Local variable a defined in an enclosing scope must be final or effectively final"
		// a = new AtomicLong(3L);
		
		// Changing the object reference in the lambda has the same effect
		//IntStream.range(1, 3).peek(s -> a = new AtomicLong(s)); // -> DOES NOT compile
	}
	
	@SuppressWarnings("unused")
	@Test
	public void test03() {
		AtomicLong al = new AtomicLong(1L);
		al = new AtomicLong(2L);
		// the al AtomicLong reference changed ones so it is not effectively final - DOES NOT compile if used in a lambda 
		// after this line
		// -> "Local variable a defined in an enclosing scope must be final or effectively final"
		// IntStream.range(1,3).forEach(s -> a.set(s));
		
		
		int i = 0; 
		// Same for variable i. 
		// IntStream.of(1,2,3).forEach(s -> i++); // -> DOES NOT compile
	}
}
