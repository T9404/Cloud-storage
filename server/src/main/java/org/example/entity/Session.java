package org.example.entity;

import io.netty.channel.Channel;

public class Session {
    private final Channel channel;
    private String session;
    private int loginAttempts;

    public Session(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void incrementLoginAttempts() {
        loginAttempts++;
    }

    public void resetLoginAttempts() {
        loginAttempts = 0;
    }
}
