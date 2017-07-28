package ocp.file;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ocp.lambda.ExceptionLoggingRule;

public class PathTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Rule
	public ExceptionLoggingRule exLogger = new ExceptionLoggingRule();

	@Test
	public void test01() throws IOException {
		// get the current directory
		Path p = Paths.get(".")
				.normalize()
				.toAbsolutePath();
		Path rp = p.resolve("test/path/pathTest.tmp");

		assertTrue(rp.endsWith("pathTest.tmp"));
		assertEquals("proj", rp.getName(0)
				.toString());
		assertEquals("pathTest.tmp", rp.getFileName()
				.toString());
		assertTrue(rp.startsWith("c:\\"));

		Files.deleteIfExists(rp);
		Files.deleteIfExists(rp.getParent());
		Files.createDirectories(rp.getParent());
		Files.createFile(rp);

		BasicFileAttributeView bfav = Files.getFileAttributeView(rp, BasicFileAttributeView.class);
		assertEquals("basic", bfav.name());
		BasicFileAttributes bfas = bfav.readAttributes();
		assertTrue(bfas.isRegularFile());
		
		
		BasicFileAttributeView dosfav = Files.getFileAttributeView(rp, DosFileAttributeView.class);
		assertEquals("dos", dosfav.name());
		
		// set the lastModifiedTime and read the attribute again
		bfav.setTimes(FileTime.from(1, TimeUnit.DAYS), null, null);
		FileTime ft = (FileTime)Files.getAttribute(rp, "dos:lastModifiedTime");
		assertEquals(LocalDateTime.of(1970, 1,2, 0, 0, 0).toInstant(ZoneOffset.UTC), ft.toInstant());
		
		Map<String, Object> attrs = Files.readAttributes(rp, "dos:*");
		System.out.println(attrs);
	}

	@Test
	public void test02() {
		Path p = Paths.get("/home/users/bcp/");

		assertEquals(3, p.getNameCount());
		assertEquals("\\", p.getRoot().toString());
		assertEquals("\\home\\users", p.getParent().toString());
		assertTrue(p.startsWith("/"));
		assertTrue(p.endsWith("bcp/"));
		assertEquals("..", p.resolve("..")
				.getFileName()
				.toString());
	}

	@Test
	public void testGetComponentsRelativePath() {
		Path p = Paths.get("users/bcp/data");

		assertNull(p.getRoot());
		// forward slash file separator in the path is replaced automatically with the windows separator
		assertEquals("users\\bcp", p.getParent().toString());
		assertEquals("data", p.getFileName().toString());
	}

	@Test
	public void testGetComponentsAbsolutePath() {
		Path p = Paths.get("c:/users/bcp");

		assertEquals("c:\\", p.getRoot().toString());
		assertEquals("c:\\users", p.getParent().toString());
		assertEquals("bcp", p.getFileName().toString());
	}

	@Test
	public void testRelativizeOfAbsoluteAndRelativePath() throws Exception {
		// absolute paths
		Path p1 = Paths.get("/usr/local/");
		Path p2 = Paths.get("/etc");
		assertEquals("..\\..\\etc", p1.relativize(p2)
				.toString());

		// relative paths
		Path p3 = Paths.get("foo");
		Path p4 = Paths.get("bar");
		assertEquals("..\\bar", p3.relativize(p4)
				.toString());

		Path p5 = Paths.get("/a/b/c/d");
		Path p6 = Paths.get("/a/b/x");
		assertEquals("..\\..\\x", p5.relativize(p6).toString());
		
		Path p7 = Paths.get("../foo");
		Path p8 = Paths.get("bar");
		assertEquals("..\\..\\bar", p7.relativize(p8).toString());
		
		Path p9 = Paths.get("./../foo");
		Path p10 = Paths.get("bar");
		assertEquals("..\\..\\..\\bar", p9.relativize(p10).toString());
	}
	
	@Test
	public void relativizeShouldWorkOnSameTypeOfPath() throws Exception {
		Path p1 = Paths.get("c:/temp/data");
		Path p2 = Paths.get("users/data");
		
		// absolute to relative is an invalid operation
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("'other' is different type of Path");
		p1.relativize(p2);
	}
	
	@Test
	public void relativizeOfWindowsPath() {
		Path p = Paths.get("c://temp");
		assertEquals("..\\proj", p.relativize(Paths.get("c://proj")).toString());
		assertEquals("c:\\proj", p.resolve(Paths.get("c://proj")).toString());
	}
	
	@Test
	public void testEndsWith() {
		Path p = Paths.get("aa", "bb", "cee"); // aa/bb/cee
		assertTrue(p.endsWith(Paths.get("bb", "cee"))); // aa/bb/cee endsWith bb/cee 
		// endsWith uses directory boundaries and it is not just
		assertFalse(p.endsWith("b/cee"));
		assertFalse(p.endsWith(Paths.get("ee")));
		
		assertTrue(p.startsWith("aa"));
		assertTrue(p.startsWith("aa/bb"));
		assertFalse(p.startsWith("a"));
		
	}
}
