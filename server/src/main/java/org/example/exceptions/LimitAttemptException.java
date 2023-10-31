package org.example.exceptions;

public class LimitAttemptException extends RuntimeException {

    public LimitAttemptException() {
        super("Limit login attempts");
    }
}
