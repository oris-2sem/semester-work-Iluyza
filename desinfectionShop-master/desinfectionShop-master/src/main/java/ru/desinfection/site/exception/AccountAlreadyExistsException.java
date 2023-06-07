package ru.desinfection.site.exception;

public class AccountAlreadyExistsException extends RuntimeException{

    public AccountAlreadyExistsException(String email) {
        super(String.format("Аккаунт с email %s уже существует", email));
    }
}
