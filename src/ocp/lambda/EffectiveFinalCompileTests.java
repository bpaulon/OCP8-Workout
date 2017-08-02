package ocp.lambda;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Any local variable, formal parameter, or exception parameter used but not declared 
 * in a lambda expression must either be declared final or be effectively final (§4.12.4), 
 * or a compile-time error occurs where the use is attempted.
 *
 */
@SuppressWarnings("unused")
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
	
	int i1 =0;
	/**
	 * ONLY the LOCAL variables must be effective final. Instance variables are accessed through a field access operation
	 * on a reference to some object object.instance_variable. Even when you don't explicitly access it is treated as
	 * this.instance_variable. The real "variable" here is this which is effectively final.
	 */
	@Test
	public void test04() {
		Runnable r = () -> {
			i1 +=1; //equivalent to this.i1 +=1;
		};
		i1++;
	}
	
	
	@Test
	public void test05() {
		int i = 1; 
		Runnable r = new Runnable () {
			public void run () {
				// starting with JAVA 8 can access a non-final (effective final) local variable
				int i1 = i; 
				//++i; //DOES NOT COMPILE - var i is not effectively final 
			}
		};
	}
	
}
