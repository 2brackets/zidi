package com.twobrackets.ui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class StatusBar {
    private int currentRow;
    private int currentCol;
    private int screenHeight;
    private TextGraphics textGraphics;

    public StatusBar(TextGraphics textGraphics, int screenHeight){
        currentRow = 1;
        currentCol = 0;
        this.textGraphics = textGraphics;
        this.screenHeight = screenHeight;
    }

    public void displayStatusBar(Screen screen){
        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK_BRIGHT);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        textGraphics.fillRectangle(new TerminalPosition(0, screenHeight - 1), new TerminalSize(screen.getTerminalSize().getColumns(), 1), ' ');
        String rowInfo = getRowInfo();
        String colInfo = getColInfo();
        textGraphics.putString(new TerminalPosition(1, screenHeight - 1), rowInfo, SGR.BOLD);
        textGraphics.putString(new TerminalPosition(getColStartPosition(rowInfo), screenHeight - 1), colInfo, SGR.BOLD);
        textGraphics.setBackgroundColor(TextColor.ANSI.DEFAULT);
        textGraphics.setForegroundColor(TextColor.ANSI.DEFAULT);
    }

    public void setCurrentRow(int row){
        currentRow = row;
    }
    public void setCurrentCol(int col){
        currentCol = col;
    }

    public void updateStatusBar(int row, int col, Screen screen){
        currentRow = row;
        currentCol = col;
        displayStatusBar(screen);
    }

    private String getRowInfo(){
        return  "Row: " + currentRow;
    }
    private String getColInfo(){
        return  "Col: " + currentCol;
    }
    private int getColStartPosition(String rowInfo){
        return rowInfo.length() + 3;
    }

}
