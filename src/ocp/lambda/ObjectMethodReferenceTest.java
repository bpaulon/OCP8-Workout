package ocp.lambda;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class ObjectMethodReferenceTest {

	public static class ClassA {
		public static void m1() {
		}

		public static void m2(String arg) {
		}

		public void m3(String arg) {
		}

		public String m4() {
			return "ClassA";
		}
	}

	private void method01(String s) {
	}

	@Test
	public void test01() {
		Supplier<Stream<String>> supplier = () -> Arrays.asList("1", "2", "3")
				.stream();

		supplier.get()
				.forEach(this::method01);
		supplier.get()
				.forEach(ClassA::m2);
		supplier.get()
				.forEach(new ClassA()::m3);

	}

	@Test
	public void test02() {
		Supplier<ClassA> supplier = ClassA::new;
		Consumer<ClassA> consumer = s -> ClassA.m1();
		Stream.generate(supplier)
				.limit(5)
				.forEach(consumer);
	}

	@Test
	public void test03() {
		
		class ClassB extends ClassA {
			public String m4() {
				return "ClassB";
			}
		}
		
		
		Supplier<ClassA> supplier = ClassB::new; 
		/* equivalent to 
		 * 
		 * () -> return new ClassB(); 
		 * 
		 * OR 
		 * 
		 * new Supplier<ClassA>() {
		 *   public ClassA get() {
		 *	   return new ClassB();
		 *	 }
		 * };
		 * 
		 */
		
		List<ClassA> list = Stream.generate(supplier)
				.limit(2)
				.collect(Collectors.toList());

		assertTrue(list.stream()
				.allMatch(s -> s instanceof ClassB));
	}
}
