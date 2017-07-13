package ocp.primitives;

import org.junit.Test;

public class PrimitivesTest {

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
	
	byte b8 = 0X7_F; // 7 = 0111, F = 1111
	byte b9 = -0x8_0; //8 = 1000
	
	int i1 = 7 +'d'; //107
		
//	@Test
//	public void test() {
//		System.out.println();
//	}
}
