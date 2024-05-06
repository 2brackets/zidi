package com.twobrackets;

import com.googlecode.lanterna.input.KeyStroke;
import com.twobrackets.ui.ScreenHandler;

import java.io.IOException;
import java.util.List;

public class Zidi {
    public static void main(String[] args) {
        FileManager fileManager;
        ScreenHandler screenHandler;
        List<String> lines;
        String Os = OSUtils.getOsName();
        boolean isRunning = true;
        KeyStroke keyStroke;
        System.out.println("Starting Zidi On " + Os);

        if (args.length > 0) {
            fileManager = new FileManager(args[0]);
        } else {
            fileManager = new FileManager(null);
        }
        screenHandler = new ScreenHandler();
        screenHandler.displayScreen(fileManager.getLines(), fileManager.getFileName());
        while (isRunning) {
            try {
                keyStroke = screenHandler.getScreen().pollInput();
                if (keyStroke != null) {
                    switch (keyStroke.getKeyType()){
                        case Escape:
                            isRunning = false;
                            System.out.println("End Program");
                            break;
                        case Enter:
                            System.out.println("New Title");
                            break;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
