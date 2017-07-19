package ocp.funcinterfaces;

import static org.junit.Assert.assertEquals;

import java.util.function.Supplier;

import org.junit.Test;

public class FuncInterfaceTests {

	interface I1 {
		// by default all the methods including default methods are public. Cannot make 
		static String foo() {
			return ("I1");
		}
		
		String baz();
	}
	
	interface I2 extends I1{
		// cannot override static methods so the following is VALID
		static String foo() {
			return("I2");
		}
		
		default String bar() {
			return "bar";
		}
		
		default String baz() {
			return "baz in I2";
		}
	}
	
	@Test
	public void shouldCallStaticMethod() {
		assertEquals("I1", I1.foo());
		assertEquals("I2", I2.foo());
	}
	
	@Test
	public void shouldReferenceDefaultMethod() {
		I2 i2 = new I2(){};
		Supplier<String> s = i2::bar;
		assertEquals("bar", s.get());
	}
	
	@Test
	public void shouldProvideImplementation() {
		// let's provide an implementation for I1
		I1 s = () -> "abc";
		assertEquals("abc", s.baz());
		
		// I2 si not a functional interface because it provides an implementation for the baz method
		// I2 s2 = () -> "abc"; //-- DOES NOT COMPILE
	}
}
