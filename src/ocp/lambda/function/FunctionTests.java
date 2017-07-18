package ocp.lambda.function;

import org.junit.Test;

public class FunctionTests {

	@Test
	public void test01() {
		String val = Integer.toString(1);
		val = new Integer(1).toString();

		// there are two functions so the compiler cannot select the implementation. The following does not compile
		// Function<Integer,String> f = Integer::toString; //-- DOES NOT compile
	}
}
