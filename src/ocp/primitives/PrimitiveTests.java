package ocp.primitives;

import org.junit.Test;

public class PrimitiveTests {

	float f1 = 102;
	float f2 = (int)102.0;
	//float f3 = 1f * 0.0; //does not compile (cannot convert from double to float)
	float f4 = 1f * (short)0.0;
	//float f5 = 1f * (boolean)0; //does not compile (cannot convert from int to boolean)
	
	byte b1 = 'c';
	byte b2 = 127;
	byte b3 = -128;
	// byte is from -128 to 127. Values outside this range cannot be converted. This DOES NOT compile without 
	// explicit cast
	//byte b4 = 128;
	byte b5 = (byte) 128;
	
	// the equivalent in binary
	byte b6 =  0B0111_1111;
	byte b7 = -0B1000_0000;
	//and in HEX
	byte b8 = 0X7_F; // 7 = 0111, F = 1111
	byte b9 = -0x8_0; //8 = 1000
	
	int i1 = 7 +'d'; //107
	
	long l1 = 200;
	double d2 = 200;
	float f = 70000;
	
//	@Test
//	public void test() {
//		System.out.println();
//	}
	
	@Test
	public void test01() {
		final char ch = 'c';
		Short s1  = ch;
		/*
		 * In addition, if the expression is a constant expression (§15.28) of type byte, short, char, or int:
           A narrowing primitive conversion may be used if the type of the variable is byte, short, or char, and 
           the value of the constant expression is representable in the type of the variable.

           A narrowing primitive conversion followed by a boxing conversion may be used if the type of the variable is:
           	Byte and the value of the constant expression is representable in the type byte.
            Short and the value of the constant expression is representable in the type short.
            Character and the value of the constant expression is representable in the type char.
		 */
		// the following does not compile
		// Integer i1 = ch; // DOES NOT COMPILE 
	}
}
