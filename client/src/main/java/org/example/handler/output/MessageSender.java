package org.example.handler.output;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

public interface MessageSender {
    void sendMessage(String message, ChannelHandlerContext ctx);
}
