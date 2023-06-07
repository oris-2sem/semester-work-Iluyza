package ru.desinfection.site.exception;

public class NoSuchPictureException extends RuntimeException {
    public NoSuchPictureException(String message) {
        super(message);
    }
}
