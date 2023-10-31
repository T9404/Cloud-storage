package org.example.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.example.container.DIContainer;
import org.example.container.DIContainerSingleton;
import org.example.repository.AbstractRepository;
import org.example.repository.impl.UserRepository;
import org.example.service.AuthService;
import org.example.service.FileStreamService;
import org.example.session.SessionManager;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    public ServerInitializer() {
        DIContainer container = DIContainerSingleton.getInstance();
        container.register(SessionManager.class, SessionManager.class);
        container.register(AbstractRepository.class, UserRepository.class);
        container.register(AuthService.class, AuthService.class);
        container.register(FileStreamService.class, FileStreamService.class);
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new ServerHandler());
    }
}
