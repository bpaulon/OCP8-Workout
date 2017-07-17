package ocp.collection;

import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class MapTests {

	@Test
	public void test01() {
		Map<Integer,String> m = Stream.of(1,2,3,4).collect(Collectors.toMap(UnaryOperator.identity(), i -> i+"_"));
		// map replaceAll takes a BiFunction where the key and value are the params and the result is the new value
		m.replaceAll((k,v) -> v.replace("_", "").concat("*"));
		System.out.println(m);
	}
}
