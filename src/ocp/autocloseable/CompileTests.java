package ocp.autocloseable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class CompileTests {

	public class MyResource /* extends AutoClosable */ {
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
		// try(MyResource res = new MyResource()) {}
	}

	@Test
	public void autocloseableResourceShouldBeFinal() {
		try (/* final */BufferedReader br = new BufferedReader(new FileReader("file1.txt"))) {

			// br = new BufferedReader(new FileReader("file2.txt")); //-- DOES NOT COMPILE br in the try-with-resources
			// block is considered final
		} catch (IOException e) {
			// ignored
		}

	}
}
