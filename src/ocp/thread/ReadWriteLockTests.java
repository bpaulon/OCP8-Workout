package ocp.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

import org.junit.Test;

@SuppressWarnings("unused")
public class ReadWriteLockTests {

	/**
	 * A thread that tries to acquire a read lock (non-reentrantly) will block if either the write lock is held, or
	 * there is a waiting writer thread.
	 * 
	 * Main thread here trying to acquire a writLock (a.w()) will block the other threads trying to acquire the readLock
	 * in method r()
	 */
	@Test
	public void testReleaseLock() throws InterruptedException {
		class A {

			ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

			void r() {
				Lock lock = readWriteLock.readLock();
				System.out.println(tn() + " acquiring lock...");
				lock.lock();
				System.out.println(tn() + " acquired lock -> count:" + readWriteLock.getReadLockCount());

				sleep();
				System.out.println(tn() + " done");
			}

			void w() {
				Lock lock = readWriteLock.writeLock();
				System.out.println(tn() + " acquiring lock " + readWriteLock.isWriteLocked());
				lock.lock();
				System.out.println(tn() + " acquired lock");
			}
		}

		List<Thread> threads = new ArrayList<Thread>();
		A a = new A();
		IntStream.rangeClosed(1, 5)
				.forEach(i ->
		{
					Thread th1 = new Thread(() -> a.r());
					threads.add(th1);
					th1.start();
				});

		a.w();

	}

	// current thread name
	private String tn() {
		return Thread.currentThread()
				.getName();
	}

	private void sleep() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// ignored
		}
	}
}
