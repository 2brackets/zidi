package com.twobrackets.ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class UITextHeader {
    private String title;
    private TextGraphics textGraphics;
    private int screenWidth;

    public UITextHeader(String title, TextGraphics textGraphics, int screenWidth) {
        this.title = title;
        this.textGraphics = textGraphics;
        this.screenWidth = screenWidth;
    }

    public void displayHeader(Screen screen){
        int titleStartColumn = (screenWidth - title.length()) / 2;
        textGraphics.setBackgroundColor(TextColor.ANSI.RED);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(screen.getTerminalSize().getColumns(), 1), ' ');
        textGraphics.putString(new TerminalPosition(1, 0), "Zidi 1.0.1", SGR.BOLD);
        textGraphics.putString(new TerminalPosition(titleStartColumn, 0), title, SGR.BOLD);
    }

    public void setTitle(String newTitle){
        this.title = newTitle;
    }
}
