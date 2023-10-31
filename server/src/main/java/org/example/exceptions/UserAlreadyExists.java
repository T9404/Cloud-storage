package org.example.exceptions;

public class UserAlreadyExists extends RuntimeException {
    public UserAlreadyExists() {
        super("User with this username already exists");
    }
}
