package ru.desinfection.site.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.desinfection.site.exception.AccountAlreadyExistsException;
import ru.desinfection.site.exception.MaxAmountOfItemsInOrderException;
import ru.desinfection.site.exception.NoSuchAccountException;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AccountAlreadyExistsException.class)
    public String handleAccountAlreadyExistsException(AccountAlreadyExistsException exception) {
        log.trace("Caught exception: {}", exception);
        return exception.getLocalizedMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchAccountException.class)
    public String handleNoSuchAccountException(NoSuchAccountException exception) {
        log.trace("Caught exception: {}", exception);
        return exception.getLocalizedMessage();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(MaxAmountOfItemsInOrderException.class)
    public String handleMaxAmountOfItemsInOrderException(MaxAmountOfItemsInOrderException exception) {
        log.trace("Caught exception: {}", exception);
        return exception.getLocalizedMessage();
    }
}
