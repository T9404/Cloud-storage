package org.example.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.constant.Constants;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisterCommandHandler implements CommandHandler {
    private static final Logger logger = LoggerFactory.getLogger(RegisterCommandHandler.class);
    private final AuthService authService;

    public RegisterCommandHandler(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public String handle(ChannelHandlerContext ctx, String command) {
        try {
            User user = UserMapper.toUser(command);
            authService.register(user);
            return Constants.ResponseCommandHandler.REGISTRATION_SUCCESSFUL;
        } catch (Exception e) {
            logger.error("Error while registering user: {}", e.getMessage());
            return e.getMessage();
        }
    }
}
