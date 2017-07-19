package ocp.lambda.function;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;
@SuppressWarnings("unused")
public class FunctionTests {
	
	@Test
	public void test01() {
		String val = Integer.toString(1);
		val = new Integer(1).toString();

		/**
		 * There are two methods:
		 * 
		 * instance Integer.toString()
		 * static Integer.toString(int i)
		 * 
		 * so the compiler cannot select the implementation. The following does not compile
		 */
		 //Function<Integer,String> f = Integer::toString; //-- DOES NOT compile
	}
	
	@Test
	public void test02() {
		List<Integer> l1 = Arrays.asList(3, 4, 2, 6, 8);
		for (double i : l1) {
			System.out.println(i);
		}

		Function<Integer, Integer> f = (Integer i) -> i * 2;
		BiFunction<Integer, Integer, String> bf = (Integer i, Integer j) -> i + j + "";
	}
	
	@Test
	public void shouldResolveCorrectConstructor() {
		class C1 {
			private C1() {
				System.out.println("default ctor");
			}

			private C1(int i) {
				System.out.println("int i:" + i);
			}

			private C1(double d) {
				System.out.println("double d:" + d);
			}

			private C1(int i, float f) {
				System.out.printf("int i:%s, float f: %s\n", i, f);
			}
			
			private C1 (Number i) {}
		}

		Supplier<C1> s1 = C1::new;
		C1 c1 = s1.get();

		// widen -> C1(Number i)
		Function<Integer, C1> f1 = C1::new;
		c1 = f1.apply(1);

		// unbox and widen from float to double -> C1(double d)
		Function<Float, C1> f2 = C1::new;
		c1 = f2.apply(1f);

		// unbox first param and unbox second param and widen from int to float -> C1(int i, float f)
		BiFunction<Integer, Integer, C1> bf = C1::new;
		c1 = bf.apply(1, 2);
	}
	
}
