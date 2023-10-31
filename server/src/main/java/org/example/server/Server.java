package org.example.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.example.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public Server() {
        this.bossGroup = new NioEventLoopGroup(Constants.Server.NUMBER_OF_THREADS);
        this.workerGroup = new NioEventLoopGroup();
    }

    public void start() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInitializer());
            ChannelFuture future = bootstrap.bind(Constants.Server.PORT).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("An error occurred while starting the server: {}", e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
