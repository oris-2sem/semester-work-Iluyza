package ru.desinfection.site.utils;

import lombok.Getter;


public enum Constants {
    EXCEPTION_MESSAGE_PROPERTY_PREFIX("exception.messages.");


    @Getter
    private final String value;

    Constants(String value) {
        this.value = value;
    }
}
