package org.example.service;

import io.netty.channel.Channel;
import org.example.constant.Constants;
import org.example.annotation.Inject;
import org.example.entity.User;
import org.example.exceptions.*;
import org.example.repository.AbstractRepository;
import org.example.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final AbstractRepository<User> userRepository;
    private final SessionManager sessionManager;

    @Inject
    public AuthService(AbstractRepository<User> userRepository, SessionManager sessionManager) {
        this.userRepository = userRepository;
        this.sessionManager = sessionManager;
    }

    public void register(User user) {
        Optional<User> userDb = userRepository
                .findAll(u -> u.getLogin().equals(user.getLogin()))
                .stream()
                .findFirst();
        if (userDb.isPresent()) {
            logger.error("User '{}' already exists", user.getLogin());
            throw new UserAlreadyExists();
        }
        userRepository.save(user);
        logger.info("User '{}' registered successfully", user.getLogin());
    }

    public void login(User potentialUser, Channel channel) {
        validateLoginAttempt(channel);

        User user = getUserByLogin(potentialUser)
                .orElseThrow(WrongCredentialsException::new);
        sessionManager.setSession(channel, user.getLogin());
        sessionManager.resetLoginAttempts(channel);
        logger.info("User '{}' logged in. SocketAddress: {}", user.getLogin(), channel.remoteAddress());
    }

    public void logout(Channel channel) {
        if (sessionManager.getSession(channel).isEmpty()) {
            logger.error("User not logged in. SocketAddress: {}", channel.remoteAddress());
            throw new UserNotLoggedInException();
        }
        sessionManager.removeSession(channel);
        sessionManager.resetLoginAttempts(channel);
        logger.info("User logged out. SocketAddress: {}", channel.remoteAddress());
    }

    private void validateLoginAttempt(Channel channel) {
        sessionManager.addLoginAttempt(channel);
        if (sessionManager.getSession(channel).isPresent()) {
            logger.error("User already logged in. SocketAddress: {}", channel.remoteAddress());
            throw new UserAlreadyLoggedInException();
        }

        int count = sessionManager.getLoginAttempts(channel);
        if (count > Constants.ServerHandler.MAX_LOGIN_ATTEMPTS) {
            logger.error("Too many login attempts. SocketAddress: {}", channel.remoteAddress());
            throw new LimitAttemptException();
        }
    }

    private Optional<User> getUserByLogin(User user) {
        return userRepository
                .findAll(u -> u.getLogin().equals(user.getLogin()) && u.getPassword().equals(user.getPassword()))
                .stream()
                .findFirst();
    }
}
