package ocp.primitives;

import org.junit.Test;

/**
 * A boxing conversion (§5.1.7) is optionally FOLLOWED by a WIDENING reference conversion
 * 
 * @see http://docs.oracle.com/javase/specs/jls/se7/html/jls-5.html#jls-5.1.7
 */
@SuppressWarnings("unused")
public class BoxingTests {

	/**
	 * Widening primitive conversions
	 * 
	 * <ul>
	 * <li>byte to short, int, long, float, or double</li>
	 * <li>short to int, long, float, or double
	 * <li>char to int, long, float, or double
	 * <li>int to long, float, or double
	 * <li>long to float or double
	 * <li>float to double
	 * </ul>
	 */
	
	@Test
	public void unboxedThenWiden() {
		int i1 = 0;
		Integer io1 = Integer.valueOf(i1);
		int i2 = io1;
		// unbox and widen
		double d1 = io1;
		float f1 = io1;
		// widen
		double d2 = f1;
		
		
		// widen before boxing cannot work. It's actually the equivalent of Double d02 = Float.valueOf(f1)
		// Double d02 = f1;
		
		// widening from Float to number
		Number d01 = f1; 
		
		// cannot narrow primitive
		// float f2 = do1; //-- DOES NOT compile
	}
}
