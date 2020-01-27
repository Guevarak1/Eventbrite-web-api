package com.wrapper.eventbrite.exceptions;

public class BadRequestException extends EventbriteWebApiException {
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
