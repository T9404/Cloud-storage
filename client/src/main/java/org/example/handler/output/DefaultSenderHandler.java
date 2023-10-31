package org.example.handler.output;

import io.netty.channel.ChannelHandlerContext;
import org.example.service.DefaultMessageService;

public class DefaultSenderHandler implements MessageSender {
    private final DefaultMessageService defaultMessageService;

    public DefaultSenderHandler() {
        defaultMessageService = new DefaultMessageService();
    }

    @Override
    public void sendMessage(String message, ChannelHandlerContext ctx) {
        defaultMessageService.sendMessage(message, ctx);
    }
}
