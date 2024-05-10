package com.twobrackets;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private Path filePath;
    private String fileName;
    private List<String> lines;
    public boolean saved = true;

    public FileManager(String filePath) {
        this.lines = new ArrayList<>();
        if (filePath == null || filePath.isEmpty()) {
            this.fileName = "untitled";
            this.filePath = null;
            this.lines.add("");
        } else {
            this.filePath = Paths.get(filePath);
            this.fileName = this.filePath.getFileName().toString();
            readFileContents();
        }
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public List<String> getLines() {
        return lines;
    }

    private void readFileContents() {
        try {
            lines.addAll(Files.readAllLines(filePath));
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}

