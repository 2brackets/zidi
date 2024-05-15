package com.twobrackets;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class FileManagerTest {
    private FileManager fileManager;
    private Path path;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        URL resource = getClass().getClassLoader().getResource("testFile.txt");
        if (resource == null) {
            throw new AssertionError("Test file not found");
        }
        path = Paths.get(resource.toURI());
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testFileManagerCreationWithNullPath() {
        fileManager = new FileManager(null);
        assertEquals("untitled", fileManager.getFileName());
        assertTrue("Lines should be initialized with an empty string", fileManager.getLines().contains(""));
    }

    @Test
    public void testFileManagerCreationWithEmptyString() {
        fileManager = new FileManager("");
        assertEquals("untitled", fileManager.getFileName());
        assertTrue("Lines should be initialized with an empty string", fileManager.getLines().contains(""));
    }

    @Test
    public void testFileManagerCreationWithValidPath() {
        fileManager = new FileManager(path.toString());
        assertEquals("testFile.txt", fileManager.getFileName());
        assertFalse("Lines should not be empty after reading from file", fileManager.getLines().isEmpty());
        assertEquals("The quick brown fox jumps over the lazy dog.", fileManager.getLines().get(0));
    }

    @Test
    public void testReadFileContentsFailure() {
        Path invalidPath = Paths.get("/invalid/path.txt");
        fileManager = new FileManager(invalidPath.toString());
        assertTrue("Lines should be empty on read failure", fileManager.getLines().isEmpty());
        assertFalse("System.err should contain an error message", errContent.toString().isEmpty());
    }
}
