package org.example.service;

import io.netty.channel.ChannelHandlerContext;
import org.example.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMessageService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageService.class);

    public void sendMessage(String message, ChannelHandlerContext ctx) {
        try {
            MessageUtil.sendMessage(ctx, message);
        } catch (Exception e) {
            logger.error("Failed to send message to server: {}", e.getMessage());
        }
    }
}
