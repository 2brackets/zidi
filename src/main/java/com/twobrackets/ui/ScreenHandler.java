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
    private Header header;
    private Editor editor;
    private StatusBar statusBar;
    private int screenWidth;
    private int screenHeight;
    private boolean running = true;

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
        int screenColumns = screen.getTerminalSize().getColumns();
        header = new Header(fileName, textGraphics, screenWidth);
        header.displayHeader(screenColumns);

        editor = new Editor(lines,textGraphics,screenWidth,screenHeight);
        editor.displayEditor();

        statusBar = new StatusBar(textGraphics, screenHeight);
        statusBar.displayStatusBar(screenColumns);

        screen.setCursorPosition(new TerminalPosition(0, 1));
        try {
            screen.refresh();
        } catch (Exception e) {
            System.out.println("Error refreshing screen. " + e.getMessage());
        }
    }

    public void refresh(boolean clearScreen){
        try {
            int screenColumns = screen.getTerminalSize().getColumns();
            int row = editor.getCurrentRow();
            int column = editor.getCurrentColumn();
            screen.setCursorPosition(new TerminalPosition(column, row));
            if(clearScreen){
                screen.clear();
                header.displayHeader(screenColumns);
                editor.displayEditor();
            }
            statusBar.updateStatusBar(row, column, screenColumns);
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Screen getScreen() {
        return screen;
    }
    public Editor getEditor(){
        return editor;
    }

    public boolean isRunning() {
        return running;
    }

    public void exit(){
        try {
            screen.close();
            running = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

