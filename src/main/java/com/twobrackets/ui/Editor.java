package com.twobrackets.ui;

import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.List;

public class Editor {
    private int currentRow = 1;
    private int currentColumn = 0;
    private List<String> lines;
    private String currentLine;
    private int screenWidth;
    private int screenHeight;
    private TextGraphics textGraphics;

    public Editor(List<String> lines, TextGraphics textGraphics, int screenWidth, int screenHeight) {
        this.lines = lines;
        this.textGraphics = textGraphics;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void displayEditor(){
        renderText();
        currentLine = lines.get(currentRow -1 );
    }

    private void renderText(){
        int screenRow = 1;

        for (String line : lines) {
            if (line.isEmpty()) {
                textGraphics.putString(0, screenRow++, " ");
            } else {
                int startIdx = 0;
                while (startIdx < line.length()) {
                    int endIdx = Math.min(startIdx + screenWidth, line.length());
                    String part = line.substring(startIdx, endIdx);
                    textGraphics.putString(0, screenRow++, part);
                    startIdx += screenWidth;
                }
            }
        }
    }

    public List<String> getLines(){
        return lines;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    private void updateLine() {
        String clearString = " ".repeat(screenWidth);
        textGraphics.putString(0, currentRow, clearString);
        textGraphics.putString(0, currentRow, currentLine);
        lines.set(currentRow - 1, currentLine);
    }

    public void addChar(char c) {
        if (currentColumn == currentLine.length()) {
            currentLine += c;
        } else {
            currentLine = currentLine.substring(0, currentColumn) + c + currentLine.substring(currentColumn);
        }
        currentColumn++;
        updateLine();
    }

    public void enter() {
        String currentLine = lines.get(currentRow - 1);
        String firstPart = currentLine.substring(0, currentColumn);
        String secondPart = currentLine.substring(currentColumn);

        lines.set(currentRow - 1, firstPart);
        lines.add(currentRow, secondPart);

        currentRow++;
        currentColumn = 0;
        this.currentLine = lines.get(currentRow - 1);
        renderText();
    }

    public void backSpace(){
        if (currentColumn > 0) {
            currentColumn--;
            currentLine = currentLine.substring(0, currentColumn) + currentLine.substring(currentColumn + 1);
            lines.set(currentRow - 1, currentLine);
            updateLine();
        } else if (currentRow > 1) {
            String aboveLine = lines.get(currentRow - 2);
            String currentLine = lines.get(currentRow - 1);
            currentColumn = aboveLine.length();
            aboveLine += currentLine;
            lines.set(currentRow - 2, aboveLine);
            lines.remove(currentRow - 1);
            currentRow--;
            renderText();
        }
    }

    public void upp(){
        if(currentRow > 1){
            currentRow--;
            currentLine = lines.get(currentRow - 1);
        }
    }

    public void down(){
        if(currentRow < lines.size() - 1){
            currentRow++;
            currentLine = lines.get(currentRow - 1);
        }
    }

    public void left(){
        if (currentColumn > 0) {
            currentColumn--;
        }
    }

    public void right(){
        if (currentColumn < currentLine.length()) {
            currentColumn++;
        }
    }

    public void delete(){
        if (currentColumn < currentLine.length()) {
            currentLine = currentLine.substring(0, currentColumn) + currentLine.substring(currentColumn + 1);
        }
        updateLine();
    }
}
