package ocp.lambda;

import java.util.function.Function;

public class LambdaInner02 {

	Function<String, Integer> f = new Function<String, Integer>() {
		public Integer apply(String s) {
			return Integer.parseInt(s);
		}
	};
}
