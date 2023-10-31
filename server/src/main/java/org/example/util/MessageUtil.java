package org.example.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.nio.charset.StandardCharsets;

public class MessageUtil {

    public static void sendMessage(ChannelHandlerContext channelHandlerContext, String message) {
        ByteBuf response = Unpooled.buffer();
        response.writeBytes(message.getBytes(StandardCharsets.UTF_8));
        channelHandlerContext.writeAndFlush(response);
    }

    public static void sendMessage(ChannelHandlerContext channelHandlerContext, String firstPart, String secondPart) {
        ByteBuf response = Unpooled.buffer();
        response.writeBytes(firstPart.getBytes(StandardCharsets.UTF_8));
        response.writeBytes(secondPart.getBytes(StandardCharsets.UTF_8));
        channelHandlerContext.writeAndFlush(response);
    }
}
