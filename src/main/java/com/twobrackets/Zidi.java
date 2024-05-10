package com.twobrackets;

import com.googlecode.lanterna.input.KeyStroke;
import com.twobrackets.events.InputHandler;
import com.twobrackets.ui.ScreenHandler;

import java.io.IOException;
import java.util.List;

public class Zidi {
    public static void main(String[] args) {
        FileManager fileManager;
        ScreenHandler screenHandler;
        InputHandler inputHandler;
        String Os = OSUtils.getOsName();
        KeyStroke keyStroke;
        System.out.println("Starting Zidi On " + Os);

        if (args.length > 0) {
            fileManager = new FileManager(args[0]);
        } else {
            fileManager = new FileManager(null);
        }
        screenHandler = new ScreenHandler();
        screenHandler.displayScreen(fileManager.getLines(), fileManager.getFileName());
        inputHandler = new InputHandler(screenHandler);

        while (screenHandler.isRunning()) {
            try {
                keyStroke = screenHandler.getScreen().pollInput();
                if (keyStroke != null) {
                    inputHandler.input(keyStroke);
                    fileManager.saved = false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
