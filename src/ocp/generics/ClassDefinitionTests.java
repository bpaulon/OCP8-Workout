package ocp.generics;

import java.util.concurrent.Callable;

public class ClassDefinitionTests {

	// Integer in the class definition is just a generic type. Might be named T as well
	// It hides the java.lang.Integer type
	// @SuppressWarnings("hiding")
	class MyCallable<Integer extends Number> implements Callable<Number> {

		@Override
		public Integer call() throws Exception {
			return null;
		}
	}

	class MyCallable01 implements Callable<Number> {

		// call can return a covariant type (Integer extends Number) so this is
		// a valid override
		@Override
		public Integer call() throws Exception {
			return null;
		}
	}

}
