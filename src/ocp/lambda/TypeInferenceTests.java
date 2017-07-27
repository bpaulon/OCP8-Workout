package ocp.lambda;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

import org.junit.Test;

/**
 * Compiler performs type inference for lambda expressions:
 * 
 * The compiler infers the type of the parameters if you do not specify the type parameters in a lambda function
 * definition. When you specify the type of parameters, you need to specify all or none; or else you will get a compiler
 * error.
 * 
 * You can omit the parenthesis if there is only one parameter. But in this case, you cannot provide the type
 * explicitly. You should leave it to the compiler to infer the type of that single parameter.
 * 
 * The return type of the lambda function is inferred from the body. If any of the code in the lambda returns a value,
 * then all the paths should return a value; or else you will get a compiler error.
 * 
 *
 */
@SuppressWarnings("unused")
public class TypeInferenceTests {

	@Test
	public void testGenericInference() {
		Function<?, ?> fun = Function.<String>identity();
	}

	public void testTypeInference() throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newCachedThreadPool();
		es.submit(() -> {
			throw new IOException();
		});

		// The passed in lambda is inferred to Callable
		Future<?> f = es.submit(() -> {
			Thread.sleep(1000);
			return null;
		});
		
		
		/*
		 * There is no return statement in this lambda expression body and hence the compiler infers the return type 
		 * of this expression as void type. Therefore the parameter passed to the submit method is a Runnable not a
		 * Callable. The problem is Runnable.run() method does not launch an Exception so the following does not compile
		 */
		f = es.submit(() -> {
			//Thread.sleep(1000); //-- DOES NOT COMPILE. 
		});
	}

}
