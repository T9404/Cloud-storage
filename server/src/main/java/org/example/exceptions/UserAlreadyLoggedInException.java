package org.example.exceptions;

public class UserAlreadyLoggedInException extends RuntimeException {
    public UserAlreadyLoggedInException() {
        super("User already logged in");
    }
}
