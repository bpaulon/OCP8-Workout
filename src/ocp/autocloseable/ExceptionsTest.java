package ocp.autocloseable;

import org.junit.Test;

public class ExceptionsTest {

	public class MyResource /*extends AutoClosable */{
		public MyResource() {
			
		}
		public void close() {
			System.out.println("MyResource closing");
		}
	}
	@Test
	public void test01() {
		/*
		 * this doesn't compile. It must implement AutoClosable or any of it's sub-types
		 */
		//try(MyResource res = new MyResource()) {}
	}
}
