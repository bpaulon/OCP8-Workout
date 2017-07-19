package ocp.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import static org.junit.Assert.*;

public class FilesTests {

	@Test
	public void filesAreTheSame() throws IOException {
		Path p1 = Paths.get("a/b/test1.txt");
		Path p2 = Paths.get("a" , "b").resolve("test1.txt");
		assertTrue(Files.isSameFile(p1, p2));
	}
}
