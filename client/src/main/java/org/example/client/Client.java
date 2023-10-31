package org.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.example.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public void run() {
        logger.info(Constants.Commands.ENTER_COMMAND_MESSAGE);

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                if (command.contains(Constants.Commands.CONNECT_COMMAND)) {
                    establishConnectionWithServer();
                } else if (command.contains(Constants.Commands.EXIT_COMMAND)) {
                    break;
                } else {
                    logger.info("Unknown command for client!\n" + Constants.Commands.ENTER_COMMAND_MESSAGE);
                }
            }
        }
    }

    private void establishConnectionWithServer() {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try (Scanner scanner = new Scanner(System.in)) {
            Bootstrap bootstrap = configureBootstrap(eventLoopGroup);
            ChannelFuture future = bootstrap.connect(Constants.Server.HOST, Constants.Server.PORT).sync();
            ClientHandler clientHandler = (ClientHandler) future.channel().pipeline().last();

            while (true) {
                String userInput = scanner.nextLine();
                if (userInput.equals(Constants.Commands.EXIT_COMMAND)) {
                    break;
                }
                clientHandler.sendMessage(userInput);
            }
        } catch (InterruptedException e) {
            logger.error("Connection was interrupted", e);
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    private Bootstrap configureBootstrap(NioEventLoopGroup eventLoopGroup) {
        return new Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer());
    }
}
