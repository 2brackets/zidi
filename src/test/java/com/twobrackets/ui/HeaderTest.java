package com.twobrackets.ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.twobrackets.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;

class HeaderTest {
    private Header header;
    private TextGraphics mockTextGraphics;
    private FileManager fileManager;

    @BeforeEach
    void setUp() throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("testFile.txt");
        if (resource == null) {
            throw new AssertionError("Test file not found");
        }
        Path path = Paths.get(resource.toURI());
        fileManager = new FileManager(path.toString());
        TerminalSize terminalSize = new TerminalSize(80, 60);
        mockTextGraphics = mock(TextGraphics.class);
        header = new Header(fileManager.getFileName(), mockTextGraphics, terminalSize.getColumns());
    }

    @Test
    void displayHeader_correctlySetsGraphics() {
        header.displayHeader(80);
        verify(mockTextGraphics).setBackgroundColor(TextColor.ANSI.RED);
        verify(mockTextGraphics).setForegroundColor(TextColor.ANSI.WHITE);
        verify(mockTextGraphics).fillRectangle(new TerminalPosition(0, 0), new TerminalSize(80, 1), ' ');
        verify(mockTextGraphics).putString(new TerminalPosition(1, 0), "Zidi 1.0.1", SGR.BOLD);
        verify(mockTextGraphics).putString(new TerminalPosition((80 - fileManager.getFileName().length()) / 2, 0), fileManager.getFileName(), SGR.BOLD);
    }
}

