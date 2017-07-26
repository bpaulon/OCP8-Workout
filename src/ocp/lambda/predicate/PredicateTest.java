package ocp.lambda.predicate;

import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class PredicateTest {

	@Test
	public void testPredicate01() {
		BiPredicate<String, String> b1 = String::startsWith;
		Assert.assertTrue(b1.test("abc", "a"));

		Predicate<String> b2 = String::isEmpty;
		Stream.of("a", "b", "c")
				.filter(b2.negate())
				.forEach(System.out::println);
	}
	
	class ClassA {
		public boolean testValue() {
			return false;
		}
		
		public String m() {
			return "abc";
		}
		
		public String m(String arg) {
			return null;
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testConsumer() {
		Predicate<ClassA> p = ClassA::testValue;
		Function<ClassA, String> f = ClassA::m;
		Supplier<ClassA> s = ClassA::new;
		UnaryOperator<String> uo = String::trim;
		BinaryOperator<String> bo = String::concat;
		// Function<String, String> func = String::concat; // DOES NOT Compile - it should be a BiFunction
		Function<String, String> func2 = new ClassA()::m;
		
		ClassA ca = new ClassA();
		Function<String, String> f3 = ca::m;
	
	}
	
	/**
	 * The compiler is smart enough to ignore ambiguities in which all the applicable methods are instance methods: 
	 * @see http://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.13.1
	 */
	interface Fun<T, R> {
		R apply(T arg);
	}

	class C {
		int size() {
			return 0;
		}

		int size(Object arg) {
			return 0;
		}

		int size(C arg) {
			return 0;
		}

		void test() {
			Fun<C, Integer> f1 = C::size;
			// OK: reference is to instance method size()
		}
	}
	
	interface Task {
		public void invoke();
	}
	
	@SuppressWarnings("unused")
	public void converting(){
		
		Task t = () -> System.out.println("hi");
		Runnable r = t::invoke;
		
		Runnable r2 = () -> System.out.println("foo");
		Runnable r3 = r2::run; //equivalent to () -> r2.run();
		r3 = () -> r2.run();
		
		//this doesn't compile. r2 must be effective final so we cannot re-assign it
		//r2 = () -> r2.run();
	}
	
}
