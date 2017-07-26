package ocp.switchtests;

import org.junit.Test;

/**
 * Only convertible int values (int, short, byte, char), their wrappers(Integer, Short, Byte, Character)
 * Enum and strings can be used with switch
 *
 */
public class SwitchTests {

	@Test
	public void test01() {
		Integer int1 = 0;
		Integer int2 = 1;

		// works with autoboxing
		switch (int1) {
		case 1:
			System.out.println(int1);
			break;
		case 2:
			System.out.println(int2);
			break;
		
		}
	}

	enum TestEnum {
		A, B
	}

	@Test
	public void test02() {

		TestEnum test = TestEnum.A;
		switch (test) {
		case A:
			System.out.println(TestEnum.A);
			break;
		case B:
			break;
		default:
			break;
		}
	}
	
	@Test
	public void test03() {
		char ch = 'c';
		switch(ch) {
			case 'c' : break;
			case 'b' : 
			default:
		}
	}
	
	@Test
	public void test04() {
		Character ch = 'c';
		switch(ch) {
		case 'c' :break;
		//case 'c': break; //-- DOES NOT COMPILE - duplicate case
		case 2: break;
		}
		
		Short sh = 7;
		switch(sh) {
			case '4': break;
			default: break; // default can be anywhere
			case 3 : break;
		}
		
		// the default does not get called if a matching case exists.
		Byte b = -128;
		switch (b) {
		default: System.out.println("default");
		case -128 : System.out.println(b);
		}
		
		switch (b) {}
	}
	
}
