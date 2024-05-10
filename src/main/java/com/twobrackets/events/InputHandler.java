package com.twobrackets.events;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.twobrackets.ui.Editor;
import com.twobrackets.ui.ScreenHandler;

public class InputHandler {
    private Editor editor;
    private ScreenHandler screenHandler;

    public InputHandler(ScreenHandler screenHandler) {
        this.screenHandler = screenHandler;
        this.editor = screenHandler.getEditor();
    }

    public void input(KeyStroke keyStroke) {
        if(keyStroke.isCtrlDown()){
            System.out.println("Ctrl Down");
        }
        else {
            action(keyStroke);
        }
    }

    private void action(KeyStroke keyStroke){
        KeyType keyType = keyStroke.getKeyType();
        switch (keyType){
            case Character:
                char c = keyStroke.getCharacter();
                editor.addChar(c);
                screenHandler.refresh(false);
                break;
            case Enter:
                editor.enter();
                screenHandler.refresh(true);
                break;
            case Backspace:
                editor.backSpace();
                screenHandler.refresh(true);
                break;
            case Delete:
                editor.delete();
                screenHandler.refresh(false);
                break;
            case ArrowUp:
                editor.upp();
                screenHandler.refresh(false);
                break;
            case ArrowDown:
                editor.down();
                screenHandler.refresh(false);
                break;
            case ArrowLeft:
                editor.left();
                screenHandler.refresh(false);
                break;
            case ArrowRight:
                editor.right();
                screenHandler.refresh(false);
                break;
            case Escape:
                screenHandler.exit();
                break;
        }
    }
}
