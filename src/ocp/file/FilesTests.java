package ocp.file;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class FilesTests {

	@Test
	public void filesAreTheSame() throws IOException {
		Path p1 = Paths.get("a/b/test1.txt");
		Path p2 = Paths.get("a" , "b").resolve("test1.txt");
		assertTrue(Files.isSameFile(p1, p2));
	}
	
	@Test
	public void test02() {
		// A Stream can be auto-closed (BaseStream implements AutoCloseable
		try (Stream<String> lines = Files.lines(Paths.get("file1.txt"));
				BufferedWriter bw = Files.newBufferedWriter(Paths.get("file2.txt"))) {
			//lines.forEach(bw::write); //--DOES NOT COMPILE - the writer method throws a checked exception (IOException) inside a lambda
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test03() throws IOException {
		// list one level
		Files.list(Paths.get("test")
				.toAbsolutePath())
				.forEach(p -> System.out.println(p.toString()));
	}

	@Test
	public void testWalk() throws IOException {
		// by default walk does not traverse symbolic links therefore no depth param is necessary
		List<Path> l1 = Files.walk(Paths.get("test/file.txt"))
				.collect(Collectors.toList());
		assertEquals(1, l1.size());
		assertEquals("test\\file.txt", l1.get(0).toString());
		
		List<Path> l2 = Files.walk(Paths.get("test")).
				collect(Collectors.toList());
		System.out.println(l2);
		
		List<Path> l3 = Files.walk(Paths.get("test"), 3, FileVisitOption.FOLLOW_LINKS)
		.collect(Collectors.toList());
		System.out.println(l3);
	}
	
	@Test
	public void testFind() throws Exception {
		List<Path> l1 = Files.find(Paths.get("test"), Integer.MAX_VALUE, (p, a) -> a.isRegularFile())
				.map(Path::toAbsolutePath)
				.collect(Collectors.toList());
		System.out.println(l1);
	}
	
}
