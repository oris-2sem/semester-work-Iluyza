package ru.desinfection.site.exception;

public class MaxAmountOfItemsInOrderException extends RuntimeException{

    public MaxAmountOfItemsInOrderException(String message) {
        super(message);
    }
}
