package ocp.thread;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomTests {

	
	// nextInt is normally exclusive of the top value,
	// so add 1 to make it inclusive
	int randomNum = ThreadLocalRandom.current().nextInt(0, 10 + 1);
}
