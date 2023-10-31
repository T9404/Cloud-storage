package org.example.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.constant.Constants;
import org.example.service.FileStreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoveFileCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(RegisterCommandHandler.class);
    private final FileStreamService fileStreamService;

    public MoveFileCommandHandler(FileStreamService fileStreamService) {
        this.fileStreamService = fileStreamService;
    }

    @Override
    public String handle(ChannelHandlerContext ctx, String receivedMessage) {
        try {
            fileStreamService.moveFile(receivedMessage);
            return Constants.MoveFileCommandHandler.FILE_MOVED_SUCCESSFULLY;
        } catch (Exception e) {
            logger.error("Error while moving file: {}", e.getMessage());
            return Constants.MoveFileCommandHandler.ERROR_WHILE_MOVING_FILE + e.getMessage();
        }
    }
}
