package ocp.autocloseable;

import java.io.IOException;

import org.junit.Test;

public class AutoclosableWithFinallyTests {

	static class ResourceA implements AutoCloseable {
		public ResourceA() throws Exception {
			throw new Exception("ResourceA ctor exception");
		}

		public void read() throws Exception {
			throw new Exception("ResourceA read exception");
		}

		@Override
		public void close() throws Exception {
			throw new Exception("ResourceA close exception");
		}
	}

	@Test
	public void test01() throws Exception {
		try (ResourceA r = new ResourceA()) {
			throw new IOException("from try block");
		} catch (IOException e) {
			System.out.println(">> In catch block");
			e.printStackTrace();
			throw e;
		} finally {
			throw new RuntimeException("from finally");
		}
	}
}
