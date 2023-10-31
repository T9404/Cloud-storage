package org.example.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.constant.Constants;
import org.example.service.FileStreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyFileCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(RegisterCommandHandler.class);
    private final FileStreamService fileStreamService;

    public CopyFileCommandHandler(FileStreamService fileStreamService) {
        this.fileStreamService = fileStreamService;
    }

    @Override
    public String handle(ChannelHandlerContext ctx, String command) {
        try {
            fileStreamService.copyFile(command);
            return Constants.CopyFileCommandHandler.FILE_COPIED_SUCCESSFULLY;
        } catch (Exception e) {
            logger.error("Error while copying file: {}", e.getMessage());
            return e.getMessage();
        }
    }
}
