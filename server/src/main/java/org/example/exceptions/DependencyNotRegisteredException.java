package org.example.exceptions;

public class DependencyNotRegisteredException extends RuntimeException {
    public DependencyNotRegisteredException() {
        super("Dependency not registered");
    }
}
