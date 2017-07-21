package ocp.autocloseable;

import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;


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
			assertTrue(e instanceof IOException);
			
			StackTraceElement ste = e.getStackTrace()[0];
			assertEquals(23, ste.getLineNumber());

			// no suppressed exceptions
			Throwable[] throwables = e.getSuppressed();
			assertTrue(throwables.length == 0);
		}
	}
}
