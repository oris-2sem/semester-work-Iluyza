package ru.desinfection.site.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.desinfection.site.utils.Constants;

@Component
@RequiredArgsConstructor
public class ExceptionMessage {

    private final Environment environment;

    public String getExceptionMessage(String name) {
        return environment.getProperty(Constants.EXCEPTION_MESSAGE_PROPERTY_PREFIX.getValue() + name);
    }
}
