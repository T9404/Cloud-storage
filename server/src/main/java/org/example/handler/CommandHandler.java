package org.example.handler;

import io.netty.channel.ChannelHandlerContext;

public interface CommandHandler {
    String handle(ChannelHandlerContext ctx, String command);
}
