package ocp.file;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class Path01Tests {

	@Test
	public void testAbsolutePath(){
		Path p = Paths.get("proj");
		System.out.println(p.toAbsolutePath().toString());

		p = Paths.get("/proj");
		System.out.println(p.toAbsolutePath().toString());
		System.out.println(p.relativize(Paths.get("/temp")));
	}
	
	@Test
	public void testNormalize() {
		Path p = Paths.get(".").normalize();
		assertEquals("", p.toString());
		assertEquals(1, p.getNameCount());
		assertEquals("", p.getFileName().toString());
		assertNull(p.getParent());
		assertNull(p.getRoot());
	}
}
