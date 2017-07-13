package ocp.autocloseable;

import java.util.Arrays;

public class TestAutocloseable02 {

	static class ResourceA implements AutoCloseable {
		
		public ResourceA() throws Exception {
			//throw new Exception("ResourceA ctor exception");
		}
		
		public void read() throws Exception {
			throw new Exception("ResourceA read exception");
		}

		@Override
		public void close() throws Exception {
			throw new Exception("ResourceA close exception");
		}
	};

	static class ResourceB implements AutoCloseable {

		public ResourceB() throws Exception {
			//throw new Exception("ResourceB ctor exception");
		}

		public void read() throws Exception {
			throw new Exception("ResourceB read exception");
		}

		@Override
		public void close() throws Exception {
			throw new Exception("ResourceB close exception");
		}
	};

	// a test method
	public static void test() throws Exception {
		try (ResourceA a = new ResourceA();
		 ResourceB b = new ResourceB()
		) {
			//a.read();
			// b.read();
		} catch (Exception e) {
			System.out.printf("%S\n\n", Arrays.asList(e.getSuppressed()));
			throw e;
		}
	}

	public static void test02() throws Exception {
		try (ResourceB b = new ResourceB()) {
			//b.read();
		}
	}
	
	public static void main(String[] args) throws Exception {
		test();
		//test02();
	}
	
	
}
