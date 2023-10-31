package org.example.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.handler.input.InputServerResponseHandler;
import org.example.handler.input.InputDataDownloadHandler;
import org.example.handler.output.MessageSender;
import org.example.handler.output.MessageSenderFactory;
import org.example.service.FileStreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

@ChannelHandler.Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private final InputDataDownloadHandler inputDataDownloadHandler;
    private final FileStreamService fileStreamService;
    private final InputServerResponseHandler inputHandler;
    private ChannelHandlerContext ctx;

    public ClientHandler() {
        fileStreamService = new FileStreamService();
        inputDataDownloadHandler = new InputDataDownloadHandler(fileStreamService);
        inputHandler = new InputServerResponseHandler();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.ctx = ctx;
        logger.info("Connected to the server.\n" + "Use /help to see available commands, /exit to exit");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        try {
            String receivedMessage = byteBuf.toString(StandardCharsets.UTF_8);
            boolean isHandled = inputDataDownloadHandler.handleInputData(receivedMessage);
            if (!isHandled) {
                handleServerResponse(receivedMessage);
            }
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Exception caught in client handler", cause);
        ctx.close();
    }

    public void sendMessage(String message) {
        if (ctx.channel().isWritable()) {
            MessageSenderFactory messageSenderFactory = new MessageSenderFactory(fileStreamService);
            MessageSender messageSender = messageSenderFactory.getHandler(message);
            messageSender.sendMessage(message, ctx);
        } else {
            logger.warn("No connection established to send message: {}", message);
        }
    }

    private void handleServerResponse(String input) {
        inputHandler.handle(input);
    }
}
