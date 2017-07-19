package ocp.funcinterfaces;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * A default method can be overridden by an abstract one
 *
 */
@SuppressWarnings("unused")
public class FuncInterface03Tests {

	interface I1 {
		/* public */ default String m() {
			return "foo";
		}
	}
	
	interface I2  extends I1 {
		/* public abstract */ String m();
	}
	
	class C1 {
		String m() {
			return "c1";
		}
	}
	
	abstract class C2 extends C1 {
		abstract String m();
	}
	
	
	@Test
	public void test01() {
		I1 i1 = new I2() {
			@Override
			public String m() {
				return "bar";
			}
			
		};
		
		assertEquals("bar", i1.m());
	}
}
