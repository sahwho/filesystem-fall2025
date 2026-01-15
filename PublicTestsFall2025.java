import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

/*
 * ADSB filesystem Project
 * Public Tests
 * Fall 2025
 * Author @sahwho
 * Framework junit4
 */

public class PublicTestsFall2025 {

	// Tests for File
	@Test
	public void testFileConstructor() throws InterruptedException {
		File f = new File("fileName", null);
		assertEquals("fileName", f.getFilename());
		assertEquals(null, f.getParent());
		Thread.sleep(5); // try to force a tangible delay
		LocalDateTime t = LocalDateTime.now();
		assertTrue(f.getLastAccessed().isBefore(t));
		assertTrue(f.getLastAccessed().isBefore(t));
	}
	
	@Test
	public void testFileToString() {
		File f = new File("fileName", null);
		assertEquals("fileName", f.toString());
	}

	// Directory Tests
	@Test
	public void testDirectoryConstructor() throws InterruptedException {
		Directory d = new Directory("dirName", null, "path");
		assertEquals("dirName", d.getFilename());
		assertEquals(null, d.getParent());
		Thread.sleep(5);
		LocalDateTime t = LocalDateTime.now();
		assertTrue(d.getLastAccessed().isBefore(t));
		assertTrue(d.getLastAccessed().isBefore(t));
		assertEquals(new ArrayList<File>(), d.getFiles());
		assertEquals("path", d.getPathToDir());
	}
	
	@Test
	public void testDirectoryToString() {
		Directory d = new Directory("dirName", null, "path");
		assertEquals("dirName/", d.toString());
	}
	
	// FileSystem Tests
	@Test
	public void testFileSystemConstructor() {
		FileSystem fs = new FileSystem();
		assertTrue(fs.getRoot().equals(fs.getWorkingDirectory()));
		assertEquals(fs.getRoot(), fs.getRoot().getParent());
		assertEquals("", fs.getRoot().getPathToDir());
		assertEquals("", fs.getRoot().getFilename());
	}
	
	@Test
	public void testPwdRoot() {
		FileSystem fs = new FileSystem();
		assertEquals("/", fs.pwd());
	}
	
	@Test
	public void testTouchRootNewFileA() {
		FileSystem fs = new FileSystem();
		assertEquals(FileSystem.TOUCH_NEW_SUCCESSFUL, fs.touch("a"));
		ArrayList<File> files = fs.getRoot().getFiles();
		assertEquals(1, files.size());
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
		assertEquals("a", files.get(0).getFilename());
	}
	
	@Test
	public void testTouchRootNewFileABC() {
		FileSystem fs = new FileSystem();
		assertEquals(FileSystem.TOUCH_NEW_SUCCESSFUL, fs.touch("b"));
		assertEquals(FileSystem.TOUCH_NEW_SUCCESSFUL, fs.touch("C"));
		assertEquals(FileSystem.TOUCH_NEW_SUCCESSFUL, fs.touch("a"));
		ArrayList<File> files = fs.getRoot().getFiles();
		assertEquals(3, files.size());
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
		assertEquals("a", files.get(0).getFilename());
		assertEquals("b", files.get(1).getFilename());
		assertEquals("C", files.get(2).getFilename());
	}
	
	@Test
	public void testTouchExistingFile() throws InterruptedException {
		FileSystem fs = new FileSystem();
		assertEquals(FileSystem.TOUCH_NEW_SUCCESSFUL, fs.touch("alpha"));
		LocalDateTime initialModifiedTime = fs.getRoot().getFiles().get(0).getLastModified();
		LocalDateTime initialAccessedTime = fs.getRoot().getFiles().get(0).getLastAccessed();
		Thread.sleep(5);
		assertEquals(FileSystem.TOUCH_UPDATE_SUCCESSFUL, fs.touch("alpha"));
		LocalDateTime newModifiedTime = fs.getRoot().getFiles().get(0).getLastModified();
		LocalDateTime newAccessedTime = fs.getRoot().getFiles().get(0).getLastAccessed();
		assertTrue(newModifiedTime.isAfter(initialModifiedTime));
		assertTrue(newAccessedTime.isAfter(initialAccessedTime));
		ArrayList<File> files = fs.getRoot().getFiles();
		assertEquals(1, files.size());
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
		assertEquals("alpha", files.get(0).getFilename());
	}
	
	//test mkdir in root (simple case)
	@Test
	public void testMkdirInRootSimple() {
		FileSystem fs = new FileSystem();
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("alpha"));
		
		assertTrue(fs.getRoot().getFiles().get(0) instanceof Directory);
		if (fs.getRoot().getFiles().get(0) instanceof Directory) {
			assertEquals("/alpha", ((Directory)fs.getRoot().getFiles().get(0)).getPathToDir());
		} else {
			fail("File was not a directory, but should have been");
		}
		ArrayList<File> files = fs.getRoot().getFiles();
		assertEquals(1, files.size());
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
		assertEquals("alpha", files.get(0).getFilename());
	}
	
	@Test
	public void testTouchUnsuccessfulDirWithSameNameExists() throws InterruptedException {
		FileSystem fs = new FileSystem();
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("alpha"));
		LocalDateTime initialModifiedTime = fs.getRoot().getFiles().get(0).getLastModified();
		LocalDateTime initialAccessedTime = fs.getRoot().getFiles().get(0).getLastAccessed();
		Thread.sleep(5);
		assertEquals(FileSystem.TOUCH_UNSUCCESSFUL_DIR_WITH_SAME_NAME_EXISTS, fs.touch("alpha"));
		assertEquals(initialModifiedTime, fs.getRoot().getFiles().get(0).getLastModified());
		assertEquals(initialAccessedTime, fs.getRoot().getFiles().get(0).getLastAccessed());
		assertTrue(fs.getRoot().getFiles().get(0) instanceof Directory);
		if (fs.getRoot().getFiles().get(0) instanceof Directory) {
			assertEquals("/alpha", ((Directory)fs.getRoot().getFiles().get(0)).getPathToDir());
		} else {
			fail("File was not a directory, but should have been");
		}
		ArrayList<File> files = fs.getRoot().getFiles();
		assertEquals(1, files.size());
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
		assertEquals("alpha", files.get(0).getFilename());
	}
	
	@Test
	public void testCdDotDot() {
		FileSystem fs = new FileSystem();
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
		fs.cd("..");
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
	}
	
	@Test
	public void testMkdirCdCdDotDot() {
		FileSystem fs = new FileSystem();
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("alpha"));
		fs.cd("alpha");
		assertFalse(fs.getWorkingDirectory().equals(fs.getRoot()));
		assertEquals("/alpha", fs.getWorkingDirectory().getPathToDir());
		fs.cd("..");
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
	}
	
	@Test
	public void testMkdirCdTouch() {
		FileSystem fs = new FileSystem();
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("alpha"));
		fs.cd("beta"); //doesn't exist, cd should do nothing
		assertTrue(fs.getWorkingDirectory().equals(fs.getRoot()));
		assertEquals("", fs.getWorkingDirectory().getPathToDir());
		fs.cd("alpha");
		assertTrue(fs.getRoot().getFiles().get(0) instanceof Directory);
		assertEquals("/alpha", fs.getWorkingDirectory().getPathToDir());
		assertFalse(fs.getWorkingDirectory().equals(fs.getRoot()));
		fs.touch("beta");
		
		assertFalse(fs.getWorkingDirectory().getFiles().get(0) instanceof Directory);
		ArrayList<File> files = fs.getWorkingDirectory().getFiles();
		assertEquals(1, files.size());
		assertEquals("beta", files.get(0).getFilename());
	}
	
	@Test
	public void testRecursiveCd() {
		FileSystem fs = new FileSystem();
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("beta"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("gamma"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("delta"));
		
		fs.cd("delta");
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("gamma"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("beta"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("delta"));
		
		fs.cd(".."); // back to root
		
		fs.cd("delta/gamma");
		assertEquals("/delta/gamma", fs.getWorkingDirectory().getPathToDir());
		
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("omega"));
		assertEquals(FileSystem.TOUCH_NEW_SUCCESSFUL, fs.touch("epsilon"));
		assertEquals(FileSystem.TOUCH_UNSUCCESSFUL_DIR_WITH_SAME_NAME_EXISTS, fs.touch("omega"));
		
		assertEquals(2, fs.getWorkingDirectory().getFiles().size());
	}
	
	//test partial nested cd case
	@Test
	public void testRecursivePartialTraversal() {
		FileSystem fs = new FileSystem();
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("beta"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("gamma"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("delta"));
		
		fs.cd("delta");
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("gamma"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("beta"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("delta"));
		
		fs.cd(".."); // back to root
		
		fs.cd("delta/alpha"); // alpha doesn't exist in delta, so should remain in alpha
		assertEquals("/delta", fs.getWorkingDirectory().getPathToDir());
		
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("omega"));
		assertEquals(FileSystem.TOUCH_NEW_SUCCESSFUL, fs.touch("epsilon"));
		assertEquals(FileSystem.TOUCH_UNSUCCESSFUL_DIR_WITH_SAME_NAME_EXISTS, fs.touch("omega"));
		
		assertEquals(5, fs.getWorkingDirectory().getFiles().size());
	}
	
	@Test
	public void testFind() {
		FileSystem fs = new FileSystem();
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("beta"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("gamma"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("delta"));
		
		fs.cd("delta");
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("gamma"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("beta"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("delta"));
		
		
		assertEquals(FileSystem.TOUCH_UNSUCCESSFUL_DIR_WITH_SAME_NAME_EXISTS, fs.touch("delta"));
		
		fs.cd("gamma");
		assertEquals(FileSystem.TOUCH_NEW_SUCCESSFUL, fs.touch("delta"));
		
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("/delta");
		expected.add("/delta/delta");
		expected.add("/delta/gamma/delta");
		
		assertEquals(expected, fs.find("delta"));
	}
	
	@Test
	public void testTree() {
		FileSystem fs = new FileSystem();
		assertEquals(fs.getRoot(), fs.getWorkingDirectory());
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("beta"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("gamma"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("delta"));
		
		fs.cd("delta");
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("gamma"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("beta"));
		assertEquals(FileSystem.MKDIR_SUCCESSFUL, fs.mkdir("delta"));
		
		
		assertEquals(FileSystem.TOUCH_UNSUCCESSFUL_DIR_WITH_SAME_NAME_EXISTS, fs.touch("delta"));
		
		fs.cd("gamma");
		assertEquals(FileSystem.TOUCH_NEW_SUCCESSFUL, fs.touch("delta"));
		/*
			/
			|----beta/
			|----delta/
			     |----beta/
			     |----delta/
			     |----gamma/
			          |----delta
			|----gamma/
		 */
		String expected = "/\n|----beta/\n|----delta/\n     |----beta/\n     |----delta/\n     |----gamma/\n          |----delta\n|----gamma/";
		
		assertEquals(expected, fs.tree(fs.getRoot()));
	}
}
