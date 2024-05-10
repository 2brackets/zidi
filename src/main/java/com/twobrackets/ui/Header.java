package com.twobrackets.ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Header {
    private String title;
    private TextGraphics textGraphics;
    private int screenWidth;

    public Header(String title, TextGraphics textGraphics, int screenWidth) {
        this.title = title;
        this.textGraphics = textGraphics;
        this.screenWidth = screenWidth;
    }

    public void displayHeader(int screenColumns){
        int titleStartColumn = (screenWidth - title.length()) / 2;
        textGraphics.setBackgroundColor(TextColor.ANSI.RED);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(screenColumns, 1), ' ');
        textGraphics.putString(new TerminalPosition(1, 0), "Zidi 1.0.1", SGR.BOLD);
        textGraphics.putString(new TerminalPosition(titleStartColumn, 0), title, SGR.BOLD);
        textGraphics.setBackgroundColor(TextColor.ANSI.DEFAULT);
        textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
    }

    public void setTitle(String newTitle){
        this.title = newTitle;
    }
}
