package org.example.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.constant.Constants;
import org.example.container.DIContainer;
import org.example.container.DIContainerSingleton;
import org.example.annotation.Inject;
import org.example.handler.CommandHandler;
import org.example.handler.CommandHandlerFactory;
import org.example.service.AuthService;
import org.example.service.FileStreamService;
import org.example.session.SessionManager;
import org.example.util.CommandUtil;
import org.example.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    private final SessionManager sessionManager;
    private final AuthService authService;
    private final FileStreamService fileStreamService;
    private static int newClientIndex = 1;
    private String clientName;

    @Inject
    public ServerHandler() {
        DIContainer container = DIContainerSingleton.getInstance();
        sessionManager = container.resolve(SessionManager.class);
        authService = container.resolve(AuthService.class);
        fileStreamService = container.resolve(FileStreamService.class);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        sessionManager.addChannel(ctx.channel());
        clientName = Constants.ServerHandler.DEFAULT_CLIENT_NAME + newClientIndex;
        newClientIndex++;
        logger.info("New client connected: {}", clientName);
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        String input = byteBuf.toString(StandardCharsets.UTF_8);
        boolean isHandled = handleUploadData(channelHandlerContext, input);
        if (isHandled) {
            MessageUtil.sendMessage(channelHandlerContext, Constants.ServerHandler.UPLOAD_CHUNK_SUCCESSFULLY);
            return;
        }
        handleCommand(channelHandlerContext, input);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        sessionManager.removeChannel(ctx.channel());
        sessionManager.removeSession(ctx.channel());
        logger.info("Client {} went offline", clientName);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        sessionManager.removeChannel(ctx.channel());
        logger.info("Client disconnected: {}",clientName);
        ctx.close();
    }

    private boolean handleUploadData(ChannelHandlerContext ctx, String input) {
        try {
            return fileStreamService.upload(input);
        } catch (Exception e) {
            return false;
        }
    }

    private void handleCommand(ChannelHandlerContext channelHandlerContext, String command) {
        String commandName = CommandUtil.getCommandName(command);
        CommandHandlerFactory factory = new CommandHandlerFactory(authService, fileStreamService);
        Optional<CommandHandler> handler = factory.getHandler(commandName);

        if (handler.isPresent()) {
            String response = handler.get().handle(channelHandlerContext, command);
            MessageUtil.sendMessage(channelHandlerContext, response);
        } else {
            MessageUtil.sendMessage(channelHandlerContext, Constants.ServerHandler.UNKNOWN_COMMAND);
        }
    }
}