package org.example.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.constant.Constants;
import org.example.service.FileStreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadFileCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(DownloadFileCommandHandler.class);
    private final FileStreamService fileStreamService;

    public DownloadFileCommandHandler(FileStreamService fileStreamService) {
        this.fileStreamService = fileStreamService;
    }

    @Override
    public String handle(ChannelHandlerContext ctx, String command) {
        try {
            fileStreamService.sendFile(ctx, command);
            return Constants.DownloadCommandHandler.FILE_SENT_SUCCESSFULLY;
        } catch (Exception e) {
            logger.error("Error while sending file: {}", e.getMessage());
            return e.getMessage();
        }
    }
}
