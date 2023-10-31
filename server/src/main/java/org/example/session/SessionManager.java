package org.example.session;

import io.netty.channel.Channel;
import org.example.entity.Session;
import org.example.exceptions.ClientNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SessionManager {
    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);
    private final List<Session> clients = new ArrayList<>();

    public void addChannel(Channel channel) {
        clients.add(new Session(channel));
    }

    public void removeChannel(Channel channel) {
        clients.removeIf(client -> client.getChannel() == channel);
    }

    public void setSession(Channel channel, String session) {
        Session client = getClient(channel);
        client.setSession(session);
    }

    public void removeSession(Channel channel) {
        Session client = getClient(channel);
        client.setSession(null);
    }

    public Optional<String> getSession(Channel channel) {
        Session client = getClient(channel);
        if (client.getSession() == null) {
            return Optional.empty();
        }
        return client.getSession().describeConstable();
    }

    public void addLoginAttempt(Channel channel) {
        Session client = getClient(channel);
        client.incrementLoginAttempts();
    }

    public void resetLoginAttempts(Channel channel) {
        Session client = getClient(channel);
        client.resetLoginAttempts();
    }

    public int getLoginAttempts(Channel channel) {
        Session client = getClient(channel);
        return client.getLoginAttempts();
    }

    private Session getClient(Channel channel) {
        for (Session client : clients) {
            if (client.getChannel() == channel) {
                return client;
            }
        }
        logger.error("Client not found: {}", channel.remoteAddress().toString());
        throw new ClientNotFoundException();
    }
}
