package ocp.funcinterfaces;

import org.junit.Test;
import static org.junit.Assert.*;

public class FuncInterface04Tests {

	interface ApplyRateFunc<T> {
		T apply(T origin, T rage);
	}

	@Test
	public void test01() {
		ApplyRateFunc<Double> f1 = new ApplyRateFunc<Double>() {
			@Override
			public Double apply(Double origin, Double rate) {
				return origin * (1 + rate);
			}
		}::apply;

		// equivalent to
		ApplyRateFunc<Double> f2 = new ApplyRateFunc<Double>() {
			@Override
			public Double apply(Double origin, Double rate) {
				return origin * (1 + rate);
			}
		};
		f1 = f2::apply;

		// equivalent to
		class ClassA {
			public Double m(Double d1, Double d2) {
				return d1 * (1 + d2);
			}
		}
		f1 = new ClassA()::m;

		// equivalent to
		f1 = new Object() {
			public Double m(Double d1, Double d2) {
				return d1 * (1 + d2);
			}
		}::m;

		ApplyRateFunc<Double> f4 = (d1, d2) -> d1 * (1 + d2);

		assertEquals(f4.apply(1.0, 3.0), f1.apply(1.0, 3.0));
	}
}
