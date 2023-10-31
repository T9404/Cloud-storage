package org.example.handler.output;

import io.netty.channel.ChannelHandlerContext;
import org.example.service.FileStreamService;

public class FileUploadToServerHandler implements MessageSender {
    private final FileStreamService fileStreamService;

    public FileUploadToServerHandler(FileStreamService fileStreamService) {
        this.fileStreamService = fileStreamService;
    }

    @Override
    public void sendMessage(String message, ChannelHandlerContext ctx) {
        fileStreamService.uploadFile(message, ctx);
    }
}
