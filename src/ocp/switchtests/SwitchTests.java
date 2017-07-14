package ocp.switchtests;

import org.junit.Test;

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
	
}
