package com.twobrackets.ui;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;

public class ScreenHandler {
    private final Terminal terminal;
    private TerminalSize terminalSize;
    private final Screen screen;
    private TextGraphics textGraphics;
    private UITextHeader uiTextHeader;
    private StatusBar statusBar;
    private int screenWidth;
    private int screenHeight;
    int currentColumn = 0;
    int currentRow = 1;
    private List<String> lines;
    private String currentLine;

    public ScreenHandler(){
        try {
            this.terminal = new DefaultTerminalFactory().createTerminal();
            this.screen = new TerminalScreen(terminal);
            screen.startScreen();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void displayScreen(List<String> lines, String fileName) {
        if (this.screen == null) {
            System.err.println("Screen not initialized.");
            return;
        }
        terminalSize = screen.getTerminalSize();
        screenWidth = terminalSize.getColumns();
        screenHeight = terminalSize.getRows();
        textGraphics = screen.newTextGraphics();

        uiTextHeader = new UITextHeader(fileName, textGraphics, screenWidth);
        uiTextHeader.displayHeader(screen);

        currentRow = 1;

        for (String line : lines) {
            int startIdx = 0;
            while (startIdx < line.length()) {
                int endIdx = Math.min(startIdx + screenWidth, line.length());
                String part = line.substring(startIdx, endIdx);

                textGraphics.putString(0, currentRow, part);
                currentRow++;
                startIdx += screenWidth;
            }
        }
        currentRow = 1;
        currentColumn = 0;
        currentLine = lines.get(0);
        screen.setCursorPosition(new TerminalPosition(currentColumn, currentRow));

        statusBar = new StatusBar(textGraphics, screenHeight);
        statusBar.displayStatusBar(screen);

        textGraphics.setBackgroundColor(TextColor.ANSI.DEFAULT);
        textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
        try {
            screen.refresh();
        } catch (Exception e) {
            System.out.println("Error refreshing screen. " + e.getMessage());
        }
    }

    private void refresh(){
        try {
            statusBar.updateStatusBar(currentRow, currentColumn, screen);
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateLineDisplay() {
        String clearString = " ".repeat(screenWidth);
        textGraphics.putString(0, currentRow, clearString);
        textGraphics.putString(0, currentRow, currentLine);
        screen.setCursorPosition(new TerminalPosition(currentColumn, currentRow));
        refresh();
    }

     public void addChar(char c) {
         if (currentColumn == currentLine.length()) {
             currentLine += c;
         } else {
             currentLine = currentLine.substring(0, currentColumn) + c + currentLine.substring(currentColumn);
         }
         currentColumn++;
         updateLineDisplay();
     }

     public void backSpace(){
         if (currentColumn > 0) {
             currentColumn--;
             currentLine = currentLine.substring(0, currentColumn) + currentLine.substring(currentColumn + 1);
             updateLineDisplay();
         }
     }

    public Screen getScreen() {
        return screen;
    }
}

