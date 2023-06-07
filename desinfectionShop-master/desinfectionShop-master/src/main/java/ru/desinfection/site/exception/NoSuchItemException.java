package ru.desinfection.site.exception;

public class NoSuchItemException extends RuntimeException {
    public NoSuchItemException(String message) {
        super(message);
    }
}
