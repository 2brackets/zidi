package com.twobrackets.ui;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class ScreenHandler {
    private String title;
    private final Terminal terminal;
    private TerminalSize terminalSize;
    private final Screen screen;
    private int screenWidth;
    private int startColumn;
    private TextGraphics textGraphics;

    public ScreenHandler(String title){
        this.title = title;
        try {
            this.terminal = new DefaultTerminalFactory().createTerminal();
            this.screen = new TerminalScreen(terminal);
            screen.startScreen();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void displayScreen(){
        if (this.screen == null) {
            System.err.println("Screen not initialized.");
            return;
        }
        terminalSize = screen.getTerminalSize();
        screenWidth = terminalSize.getColumns();

        startColumn = (screenWidth - title.length()) / 2;
        textGraphics = screen.newTextGraphics();
        textGraphics.setBackgroundColor(TextColor.ANSI.BLUE);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(screen.getTerminalSize().getColumns(), 1), ' ');
        textGraphics.putString(new TerminalPosition(1, 0), "Zidi 1.0.1", SGR.BOLD);
        textGraphics.putString(new TerminalPosition(startColumn, 0), title);
        textGraphics.setBackgroundColor(TextColor.ANSI.DEFAULT);
        textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);

        try {
            screen.refresh();
        } catch (Exception e) {
            System.out.println("Error refreshing screen. " + e.getMessage());
        }
    }

    public void changeTitle(String str){
        this.title = str;
        try {
            textGraphics.putString(new TerminalPosition(startColumn, 0), title);
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Screen getScreen() {
        return screen;
    }
}

