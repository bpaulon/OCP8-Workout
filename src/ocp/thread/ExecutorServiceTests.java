package ocp.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import org.junit.Test;

public class ExecutorServiceTests {

	static int counter =0;
	@Test
	public void test01() {
		ExecutorService es = Executors.newScheduledThreadPool(10);
		DoubleStream.of(2.1, 3.4).forEach(c -> es.submit(() -> System.out.print(10*c)));
	}
	
	@Test 
	public void test02() {
		//int counter = 0;
		ExecutorService service = Executors.newSingleThreadExecutor();
		List<Future<?>> results = new ArrayList<>();
		IntStream.iterate(0 , i->i+1).limit(5).forEach(i -> results.add(service.submit(() -> ++counter)));
	}
}
