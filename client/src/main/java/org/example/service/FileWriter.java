package org.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileWriter {
    private static final Logger logger = LoggerFactory.getLogger(FileWriter.class);
    private final String fileNameAndPath;
    private final String fileContent;

    public FileWriter(String fileNameAndPath, String fileContent) {
        this.fileNameAndPath = fileNameAndPath;
        this.fileContent = fileContent;
    }

    public void writeToFile() {
        try (FileOutputStream output = new FileOutputStream(fileNameAndPath, true)) {
            output.write(fileContent.getBytes());
        } catch (IOException e) {
            logger.error("Failed to write to file: {}", e.getMessage());
        }
    }

    public void clearFile() {
        try (FileOutputStream output = new FileOutputStream(fileNameAndPath)) {
            logger.info("File cleared successfully: {}", fileNameAndPath);
        } catch (IOException e) {
            logger.error("Failed to clear file: {}", e.getMessage());
        }
    }
}
