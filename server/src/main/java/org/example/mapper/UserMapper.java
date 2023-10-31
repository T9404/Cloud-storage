package org.example.mapper;

import org.example.entity.User;

public class UserMapper {

    /**
     * Converts a command with login and password to a User object
     * <br>
     * First credential is a command, second is a login, third is a password that getting from user
     */
    public static User toUser(String commandWithLoginAndPassword) {
        String[] credentials = commandWithLoginAndPassword.split("\\s+", 3);
        return new User(credentials[1], credentials[2]);
    }
}
