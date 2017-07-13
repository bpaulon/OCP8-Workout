package ocp.thread.atomic;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import org.junit.Test;

public class AtomicTests {

	@Test
	public void test01() {
		AtomicInteger ai = new AtomicInteger();
		assertEquals(1, ai.incrementAndGet());
		assertEquals(1, ai.get());
		assertEquals(true, ai.compareAndSet(1, 13));
		// ai is now 13
		assertEquals(false, ai.compareAndSet(99, 17));
		assertEquals(13, ai.get());
		// ai is now 14 but returned value is before the increment
		assertEquals(13, ai.getAndIncrement());
		assertEquals(14, ai.get());
	}
	
	@Test
	public void test02() {
		AtomicInteger ai = new AtomicInteger();
		assertEquals(1, ai.addAndGet(1));
	}
}
