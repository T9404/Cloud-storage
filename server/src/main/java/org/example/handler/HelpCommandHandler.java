package org.example.handler;

import io.netty.channel.ChannelHandlerContext;
import org.example.constant.Constants;

public class HelpCommandHandler implements CommandHandler {

    @Override
    public String handle(ChannelHandlerContext ctx, String command) {
        return getHelp();
    }

    private String getHelp() {
        return Constants.ResponseCommandHandler.AVAILABLE_COMMANDS;
    }
}
