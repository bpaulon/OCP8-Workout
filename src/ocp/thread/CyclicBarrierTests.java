package ocp.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.junit.Test;

/**
 * I submit a total of 9 threads for a CyclicBarrier of 3 parties. The barrier should be tripped 3 times
 *
 */
public class CyclicBarrierTests {

	CyclicBarrier cb1 = new CyclicBarrier(3, () -> System.out.println("barrier tripped."));

	class A {
		private void doStuff() {
			System.out.println(tn() + " awaiting at barrier.");
			try {
				cb1.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			System.out.println(tn() + " DONE.");
		}
	}

	@Test
	public void testMultipleBreakBarrier() {
		ExecutorService s = Executors.newFixedThreadPool(9);

		A a = new A();
		IntStream.rangeClosed(1, 3)
				.forEach(i -> s.submit(() -> a.doStuff()));
	}

	// current thread name
	private String tn() {
		return Thread.currentThread()
				.getName();
	}
}
