package ocp.visibility;

import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class VisibilityTests {

	@Test
	public void test01() {
	Stream<Vendor> s = Stream.of(new Vendor("Dell", 7), new Vendor("HP", 9), new Vendor("Cisco",6));
	
	
	ToIntFunction<Vendor> f = p->p.rating;
	IntStream out = s.mapToInt(f);
	int sum = (out.skip(1).sum());
	System.out.println(sum);
	}
	
	static class Vendor {
		private int rating;
		private String name;
		Vendor(String n, int r) {
			rating =r;
			name= n;
		}
	}
}
