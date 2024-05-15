package com.twobrackets.ui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.lang.reflect.Field;

class StatusBarTest {
    private StatusBar statusBar;
    private TextGraphics mockTextGraphics;
    private int screenHeight;

    @BeforeEach
    void setUp() {
        mockTextGraphics = mock(TextGraphics.class);
        screenHeight = 25;
        statusBar = new StatusBar(mockTextGraphics, screenHeight);
        statusBar.displayStatusBar(new TerminalSize(80,screenHeight).getRows());
    }

    @Test
    void displayStatusBar_ShouldInvokeCorrectGraphicsOperations() {
        int screenColumns = 80;
        statusBar.updateStatusBar(5, 10, screenColumns);
        verify(mockTextGraphics, times(2)).setBackgroundColor(TextColor.ANSI.BLACK_BRIGHT);
        verify(mockTextGraphics, times(2)).setForegroundColor(TextColor.ANSI.WHITE);
        verify(mockTextGraphics).fillRectangle(new TerminalPosition(0, screenHeight - 1), new TerminalSize(screenColumns, 1), ' ');
        verify(mockTextGraphics).putString(new TerminalPosition(1, screenHeight - 1), "Row: 5", SGR.BOLD);
        verify(mockTextGraphics).putString(new TerminalPosition(9, screenHeight - 1), "Col: 10", SGR.BOLD);
    }

    @Test
    void updateStatusBar_ShouldUpdateCurrentRowAndColumnCorrectly() {
        statusBar.updateStatusBar(22, 33, screenHeight);
        try {
            Field currentRowField = StatusBar.class.getDeclaredField("currentRow");
            currentRowField.setAccessible(true);
            int currentRow = (int) currentRowField.get(statusBar);
            Assertions.assertEquals(22, currentRow);

            Field currentColField = StatusBar.class.getDeclaredField("currentCol");
            currentColField.setAccessible(true);
            int currentCol = (int) currentColField.get(statusBar);
            Assertions.assertEquals(33, currentCol);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to access private fields", e);
        }
    }
}
