package ocp.lang;

@SuppressWarnings("unused")
public class InheritanceTests {

	interface IParent {}
	interface IChild extends IParent {}
	interface IOther {}
	
	class Parent {}
	class Child extends Parent {}
	class Other {}
	
	{
		IChild cI = null;
		IParent pI = null;
		IOther oI = null;
		
		Child c = null;
		Other o = null;
		Parent p = null;
		
		cI = (IChild) oI; //possible scenario:  class AnotherImpl implements IOther, IChild 
		c = (Child)p;	// possible scenario Parent p = new Child(); Child c = (Child)p;
	  //c = (Child) o;	// multiple inheritance is not possible so a class that extends both Other and Child cannot exist
		
		cI = (IChild)o; // possible scenario  class AnotherImpl extends Other implements IChild. If class Other is declared
						// final this won't compile any more
	}
			
}
