package ocp.thread;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CallableTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
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

	@Test
	public void futureFromRunnableShouldReturnNull() throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newCachedThreadPool();
		Future<?> f = es.submit(() -> {
			System.out.println("running");
		});
		assertNull(f.get());
	}

	@Test
	public void shouldLaunchExceptionWhenTimedout() throws InterruptedException, ExecutionException, TimeoutException {
		ExecutorService service = Executors.newSingleThreadExecutor();
		Future<String> f = service.submit(() -> {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// ignored
			}
			return "";
		});

		thrown.expect(TimeoutException.class);
		f.get(500, TimeUnit.MILLISECONDS);
	}
	

}
