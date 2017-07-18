package ocp.funcinterfaces;

import org.junit.Test;

public class FuncInterface02Tests {

	interface A {
		default void m() {
		}
	}

	interface B extends A {
		// default void m() {}
	}

	interface C extends A {
		// default void m() {}
	}

	class D implements B, C {
		@Override
		public void m() {
			C.super.m();
		}
	}

	/**
	 * In the initial case (the code above), the implementation of m inherited by D is unambiguously that defined by A —
	 * there is no other possibility.
	 * 
	 * If the situation is changed so that B now also declares a default implementation of m, that becomes the
	 * implementation that D inherits by the “most specific implementation” rule. But if both B and C provide default
	 * implementations, then they conflict, and D must either use the syntax X.super.m(...) to explicitly choose one of
	 * them, or else redeclare the method itself, overriding all supertype declarations.
	 *
	 */

	@Test
	public void test01() {
		D d = new D();
		d.m();
	}
}
