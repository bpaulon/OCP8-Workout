package ocp.autocloseable;

import java.io.IOException;

import org.junit.Test;

public class SuppressedExceptionTests {

	public static void method01() {
		throw new RuntimeException();
	}

	// finally block does not complete normally
	public static void method02() throws Exception {
		try {
			method01();
		} catch (Exception e) {
			e = new Exception(e);
			throw e;
		} finally {
			throw new IOException();
		}
	}

	@Test
	public void test01() {
		try {
			method02();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			Throwable[] throwables = e.getSuppressed();
			for (Throwable throwable : throwables) {
				System.out.println(throwable);
			}
		}
	}
}
