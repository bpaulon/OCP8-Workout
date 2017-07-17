package ocp.collection;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ocp.lambda.ExceptionLoggingRule;

public class CopyOnWriteArrayListTests {

	@Rule
	public ExceptionLoggingRule exLogger = new ExceptionLoggingRule();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * From JavaDoc: Element-changing operations on iterators themselves (of CopyOnWriteArrayList) (remove, set, and add) 
	 * are not supported. These methods throw UnsupportedOperationException.
	 */
	@Test
	public void modifyOperationOnIteratorShouldThrowException() {
		CopyOnWriteArrayList<Integer> cwal = new CopyOnWriteArrayList<>(Arrays.asList(1, 2, 3));

		thrown.expect(UnsupportedOperationException.class);
		for (Iterator<Integer> iter = cwal.iterator(); iter.hasNext();) {
			iter.next();
			iter.remove();
		}
	}

	/**
	 * From JavaDoc: This array never changes during the lifetime of the iterator, so interference is impossible and the
	 * iterator is guaranteed not to throw ConcurrentModificationException. The iterator will not reflect additions,
	 * removals, or changes to the list since the iterator was created.
	 */
	// adding elements to the collection after the iterator was created will not show up in the iterator.
	@Test
	public void iteratorShouldNotReflectModifications() {
		CopyOnWriteArrayList<Integer> cwal = new CopyOnWriteArrayList<>(IntStream.iterate(1, i -> i + 1)
				.limit(9)
				.boxed()
				.collect(toList()));

		Iterator<Integer> iter = cwal.iterator();
		// add some elements to the array
		IntStream.rangeClosed(10, 20)
				.forEach(i -> {
					cwal.add(i);
				});

		List<Integer> list = new ArrayList<>();
		iter.forEachRemaining(list::add);
		// the iterator reflects the array before adding the new values
		assertEquals(IntStream.range(1, 10)
				.boxed()
				.collect(toList()), list);

		// test the values were added to the array
		assertEquals(IntStream.rangeClosed(1, 20)
				.boxed()
				.collect(toList()), cwal);
	}

	@Test
	public void modificationsShouldNotThrowConccurentModificationException() throws InterruptedException {
		CopyOnWriteArrayList<Integer> cwal = new CopyOnWriteArrayList<>(IntStream.iterate(1, i -> i + 1)
				.limit(9)
				.boxed()
				.collect(toList()));

		Thread th1 = new Thread(() -> {
			List<Integer> values = (IntStream.rangeClosed(10, 20)
					.boxed()
					.collect(toList()));
			// add and sleep to give up control to the other thread
			values.forEach(i -> {
				sleepRandom();
				cwal.add(i);
			});
		});
		th1.start();

		Thread th2 = new Thread(() -> {
			List<Integer> values = (IntStream.rangeClosed(21, 30)
					.boxed()
					.collect(toList()));
			// add and sleep to give up control to the other thread
			values.forEach(i -> {
				sleepRandom();
				cwal.add(i);
			});

		});
		th2.start();

		// make sure additions are done
		th1.join();
		th2.join();

		Collections.sort(cwal);
		// the sorted array should contain the integers from 1 to 30 inclusive
		assertEquals(IntStream.rangeClosed(1,30).boxed().collect(toList()), cwal);

	}

	private void sleepRandom() {
		try {
			Thread.sleep((int)new Random().nextDouble() * 10);
		} catch (InterruptedException e) {
			/* ignored */
		}
	}
}
