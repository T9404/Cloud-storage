package org.example.exceptions;

public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException() {
        super("Invalid username or password");
    }
}
