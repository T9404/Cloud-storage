package org.example.exceptions;

public class CreationInstanceException extends RuntimeException {
    public CreationInstanceException() {
        super("Failed to create instance");
    }
}
