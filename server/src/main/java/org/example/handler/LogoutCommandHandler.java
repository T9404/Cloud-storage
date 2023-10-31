package org.example.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.constant.Constants;
import org.example.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogoutCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(DownloadFileCommandHandler.class);
    private final AuthService authService;

    public LogoutCommandHandler(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public String handle(ChannelHandlerContext ctx, String command) {
        try {
            authService.logout(ctx.channel());
            return Constants.ResponseCommandHandler.LOGOUT_SUCCESSFUL;
        } catch (Exception e) {
            logger.error("Error while logging out: {}", e.getMessage());
            return e.getMessage();
        }
    }
}
