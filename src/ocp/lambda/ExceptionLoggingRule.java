package ocp.lambda;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ExceptionLoggingRule implements TestRule {

	public Statement apply(Statement base, Description description) {
        return statement(base);
    }

    private Statement statement(final Statement base) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } catch (Exception e) {
                    System.out.println("JUnit caught exception:");
                    e.printStackTrace(System.out);
                    throw e;
                }
            }
        };
    }
	 
	
};
