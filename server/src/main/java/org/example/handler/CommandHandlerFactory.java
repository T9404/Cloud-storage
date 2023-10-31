package org.example.handler;

import org.example.constant.Constants;
import org.example.service.AuthService;
import org.example.service.FileStreamService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommandHandlerFactory {
    private final Map<String, CommandHandler> handlers = new HashMap<>();

    public CommandHandlerFactory(AuthService authService, FileStreamService fileStreamService) {
        handlers.put(Constants.Command.REGISTER_COMMAND, new RegisterCommandHandler(authService));
        handlers.put(Constants.Command.LOGIN_COMMAND, new LoginCommandHandler(authService));
        handlers.put(Constants.Command.LOGOUT_COMMAND, new LogoutCommandHandler(authService));
        handlers.put(Constants.Command.HELP_COMMAND, new HelpCommandHandler());
        handlers.put(Constants.Command.DOWNLOAD_COMMAND, new DownloadFileCommandHandler(fileStreamService));
        handlers.put(Constants.Command.MOVE_COMMAND, new MoveFileCommandHandler(fileStreamService));
        handlers.put(Constants.Command.COPY_COMMAND, new CopyFileCommandHandler(fileStreamService));
    }

    public Optional<CommandHandler> getHandler(String text) {
        return Optional.ofNullable(handlers.get(text));
    }
}
