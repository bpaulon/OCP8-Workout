package ocp.autocloseable;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

@SuppressWarnings("unused")
public class CompileTests {

	@Test
	public void test01() {

		class MyResource /* implements AutoCloseable */ {
			public MyResource() {
			}

			public void close() {
				System.out.println("MyResource closing");
			}
		}

		/*
		 * this doesn't compile. It must implement AutoClosable or any of it's sub-types
		 */
		// try(MyResource res = new MyResource()) {}
	}

	@Test
	public void testClosable() throws IOException {
		class MyResource implements Closeable { // interface Closeable extends AutoCloseable

			@Override
			public void close() throws FileNotFoundException {
			}
		}

		// we can have a try-with-resources without catch. The exception thrown by the close method should be declared
		try (MyResource res = new MyResource()) {
		}

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
