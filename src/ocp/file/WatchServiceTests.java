package ocp.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.FileTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class WatchServiceTests {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	WatchService watchService;

	@Before
	public void setup() throws IOException {
		watchService = FileSystems.getDefault()
				.newWatchService();
	}

	/**
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test
	public void cancellingAWatchKeyShouldMakeItInvalid() throws InterruptedException, IOException {
		File createdFile = folder.newFile("file.txt");
		Path path = Paths.get(folder.getRoot()
				.getAbsolutePath());
		System.out.println(folder.getRoot()
				.getAbsolutePath());

		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_DELETE);

		touchFile(createdFile);
		WatchKey key = takeKeyAndAssertEquals(StandardWatchEventKinds.ENTRY_MODIFY);
		
		key.reset();
		assertTrue(key.isValid());

		touchFile(createdFile);
		key = takeKeyAndAssertEquals(StandardWatchEventKinds.ENTRY_MODIFY);

		key.cancel();
		assertFalse(key.isValid());
		touchFile(createdFile);
		assertNull(watchService.poll(500, TimeUnit.MILLISECONDS));

		key.cancel();
		assertFalse(key.isValid());
		assertNull(watchService.poll(500, TimeUnit.MILLISECONDS));

	}

	private void touchFile(File createdFile) throws IOException {
		Files.setLastModifiedTime(Paths.get(createdFile.getAbsolutePath()),
				FileTime.fromMillis(System.currentTimeMillis()));
	}

	private WatchKey takeKeyAndAssertEquals(Kind<Path> keyKind) throws IOException, InterruptedException {

		WatchKey key = watchService.take();//poll(500, TimeUnit.MILLISECONDS);
		assertNotNull(key);
		
		// The event count is always 1 for the registered event kinds: ENTRY_MODIFY, ENTRY_DELETE, ENTRY_CREATE
		// Only the 
		List<WatchEvent<?>> events = key.pollEvents();
		assertNotNull(events);
		assertEquals(1, events.size());
		
		WatchEvent<?> we = events.get(0);
		assertEquals(keyKind, we.kind());
		assertEquals(Paths.get("file.txt"), we.context());
		
		return key;
	}
}
