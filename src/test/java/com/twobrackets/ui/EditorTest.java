package com.twobrackets.ui;

import com.googlecode.lanterna.graphics.TextGraphics;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class EditorTest {
    private Editor editor;

    @Before
    public void setUp() {
        TextGraphics mockTextGraphics = mock(TextGraphics.class);
        List<String> list = new ArrayList<>();
        list.add("");
        editor = new Editor(list, mockTextGraphics, 80, 24);
        editor.displayEditor();
    }

    @Test
    public void testAddCharToEmptyLine() {
        editor.addChar('a');
        List<String> lines = editor.getLines();
        assertEquals("Expected 'a' in line", "a", lines.get(0));
    }

    @Test
    public void testAddCharToNonEmptyLine() {
        editor.addChar('a');
        editor.addChar('b');
        List<String> lines = editor.getLines();
        assertEquals("Expected 'ab' in line", "ab", lines.get(0));
    }

    @Test
    public void testEnterAtEndOfLine() {
        editor.addChar('a');
        editor.enter();
        List<String> lines = editor.getLines();
        assertEquals("Expected empty line after 'a'", "", lines.get(1));
    }

    @Test
    public void testEnterAtBeginningOfLine() {
        editor.addChar('a');
        editor.left();
        editor.enter();
        List<String> lines = editor.getLines();
        assertEquals("Expected 'a' to move to new line", "a", lines.get(1));
        assertEquals("Expected empty line before 'a'", "", lines.get(0));
    }

    @Test
    public void testBackspaceAtStartOfLine() {
        editor.addChar('a');
        editor.enter();
        editor.backSpace();
        List<String> lines = editor.getLines();
        assertEquals("Expected one line", 1, lines.size());
    }

    @Test
    public void testBackspaceToRemoveCharacter() {
        editor.addChar('a');
        editor.addChar('b');
        editor.backSpace();
        List<String> lines = editor.getLines();
        assertEquals("Expected 'a' after removing 'b'", "a", lines.get(0));
    }

    @Test
    public void testLeft() {
        editor.addChar('a');
        editor.addChar('b');
        editor.addChar('c');
        editor.left();
        assertEquals("Expected pointer to be on column", 2, editor.getCurrentColumn());
    }

    @Test
    public void testRight() {
        editor.addChar('a');
        editor.addChar('b');
        editor.addChar('c');
        editor.left();
        editor.left();
        editor.left();
        editor.right();
        assertEquals("Expected pointer to be on column", 1, editor.getCurrentColumn());
    }

    @Test
    public void testUp(){
        editor.addChar('a');
        editor.enter();
        editor.addChar('b');
        editor.upp();
        assertEquals("Expected pointer to be on row", 1, editor.getCurrentRow());
    }

    @Test
    public void testDelete() {
        editor.addChar('a');
        editor.addChar('b');
        editor.addChar('c');
        editor.left();
        editor.left();
        editor.delete();
        List<String> lines = editor.getLines();
        assertEquals("Expected 'abc' to be 'ac'", "ac", lines.get(0));
    }
}