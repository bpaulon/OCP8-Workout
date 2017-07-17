package ocp.thread;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class CallableTests {

	/**
	 * An exception thrown from a Call
	 * @throws InterruptedException
	 */
	@Test
	public void getShouldLaunchAnExecutionException() throws InterruptedException {
		Callable<String> c = () -> {
			throw new RuntimeException("from callable");
		};
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		Future<String> f = service.submit(c);

		try {
			f.get();
		} catch (ExecutionException e) {
			assertTrue(e instanceof ExecutionException);
			assertTrue(e.getCause() instanceof RuntimeException);
			assertTrue(e.getCause()
					.getMessage()
					.contains("from callable"));
		}

	}

}
