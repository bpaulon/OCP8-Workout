package ocp.lambda;

import java.util.function.Function;

public class LambdaInner01 {
	Function<String, Integer> f = s -> Integer.parseInt(s);
	{
		f.apply("2");
	}
}
