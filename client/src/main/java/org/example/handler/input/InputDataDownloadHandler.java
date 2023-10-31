package org.example.handler.input;

import org.example.service.FileStreamService;

public class InputDataDownloadHandler {
    private final FileStreamService fileStreamService;

    public InputDataDownloadHandler(FileStreamService fileStreamService) {
        this.fileStreamService = fileStreamService;
    }

    public boolean handleInputData(String message) {
        return fileStreamService.downloadFile(message);
    }
}
