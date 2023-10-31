package org.example.handler.output;

import org.example.constant.Constants;
import org.example.service.FileStreamService;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderFactory {
    private final Map<String, MessageSender> handlers = new HashMap<>();

    public MessageSenderFactory(FileStreamService fileStreamService) {
        handlers.put(Constants.Commands.UPLOAD_COMMAND, new FileUploadToServerHandler(fileStreamService));
        handlers.put(Constants.Commands.DEFAULT_COMMAND, new DefaultSenderHandler());
    }

    public MessageSender getHandler(String text) {
        return text.contains(Constants.Commands.UPLOAD_COMMAND) ?
                handlers.get(Constants.Commands.UPLOAD_COMMAND) : handlers.get(Constants.Commands.DEFAULT_COMMAND);
    }
}
