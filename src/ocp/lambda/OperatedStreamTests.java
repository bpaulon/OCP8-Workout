package ocp.lambda;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperatedStreamTests {
	
	@Rule
	public ExceptionLoggingRule exLogger = new ExceptionLoggingRule();
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void operatedUponStreamShouldThrowException() {
		Stream<Integer> stream = IntStream.rangeClosed(1,5).boxed();
		stream.mapToInt(i -> i);
		
		// even after an intermediate operation the stream cannot be consumed anymore
		expectOperatedUponOrClosedException();
		stream.forEach(System.out::println);
	}
	
	@Test
	public void closedStreamShouldThrowException() {
		Stream<String> stream = Arrays.asList("1", "2", "3").stream();
		stream.close();
		
		expectOperatedUponOrClosedException();
		stream.forEach(System.out::println);
	}
	
	private void expectOperatedUponOrClosedException() {
		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("stream has already been operated upon or closed");
	}

}


