package ocp.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class GenericsCompileTests {

	@Test
	public void test01() {
		List<? super Number> list1 = new ArrayList<>();
		List<? extends Number> list2 = new ArrayList<>();

		list1.add(0);
		list1.add(2.4f);
		
		/**
		 * The PECS rule - "Producer extends, Consumer super" - applies here.
		 * 
		 * "Producer Extends" - If you need a List to produce T values (you want to read Ts from the list), you need to
		 * declare it with ? extends T, e.g. List<? extends Integer>. But you cannot add to this list.
		 * 
		 * "Consumer Super" - If you need a List to consume T values (you want to write Ts into the list), you need to
		 * declare it with ? super T, e.g. List<? super Integer>. But there are no guarantees what type of object you
		 * may read from this list.
		 * 
		 * If you need to both read from and write to a list, you need to declare it exactly with no wildcards, e.g.
		 * List<Integer>.
		 * 
		 * 
		 * Simple use case to define a method that copies from one collection to another making sure they are compatible
		 * types
		 * 
		 * Note this example from the Java Generics FAQ. Note how the source list src (the producing list) uses extends,
		 * and the destination list dest (the consuming list) uses super:
		 * 
		 * @formatter:off
		 *	
		 *	public class Collections { 
	  	 *		public static <T> void copy(List<? super T> dest, List<? extends T> src) 
	  	 *		{
	     * 			for (int i=0; i<src.size(); i++) 
	     *   		dest.set(i,src.get(i)); 
	  	 *		} 
		 *	}
		 *	
		 * @formatter:on			
		 */
		
		
		 /* The add method is actually list1.add(? super Number e) therefore list1 is a consumer of Number so its
		 * actual type must be an ancestor of Number or Number type itself.
		 * 
		 * Both definitions are valid:
		 * List<? super Number> list1 = new ArrayList<Object>();
		 * List<? super Number> list1 = new ArrayList<Number>();
		 * 
		 * The following is not valid:
		 * List<? super Number> list1 = new ArrayList<Integer>();
		 * 
		 * 
		 * This is a GENERIC way to say that you CANNOT ADD anything that's not a Number but you WILL GET an Object.
		 * The following are 
		 * - VALID add operations
		 * 		list1.add(2.4f);
		 * 		list1.add(4L);
		 * 
		 * - INVALID add operations
		 * 		list1.add("");
		 * 		list1.add(new Object());
		 * 
		 * The list1.get(index) always returns an Object
		 *  
		 */
		
		
		/*
		 * The PECS rule applies to list2 as well.
		 * 
		 * This is a GENERIC way to say that you CANNOT ADD ANYTHING to the collection but you are GUARANTEED TO GET a Number
		 * 
		 * The following are 
		 * 		 
		 * - INVALID add operations
		 * 		list2.add(new Object());
		 * 		list2.add(2.0f);
		 * 		list2.add(0);
		 * 
		 * The following are valid GET operations
		 * 		Number number = list2.get(0);
		 */
		
		//list2.add(0); // does not compile
		Number number = list2.get(0);
		
	}
	
	@Test
	public void test02() {
		List<String> list = new ArrayList<>();
		List<? super String> subList = new ArrayList<>();
		
		// The List.addAll() method requires an argument of type Collection<? extends String>, thus the element type of the new ArrayList 
		// instance 
		list.addAll(new ArrayList<>());
		
		/*
		 * It does not compile. The definition of the subList declares that you can add objects that are of String type but there is
		 * no guarantee what type of object you can get. Therefore adding its elements to a list of String objects results in compile
		 * error
		 */
		//list.addAll(subList);
	}
}
