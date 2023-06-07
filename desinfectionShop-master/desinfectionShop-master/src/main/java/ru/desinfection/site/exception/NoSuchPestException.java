package ru.desinfection.site.exception;

public class NoSuchPestException extends RuntimeException {
    public NoSuchPestException(String message) {
        super(message);
    }
}
