package com.wrapper.eventbrite.exceptions;

public class UnauthorizedRequestException extends EventbriteWebApiException {
    public UnauthorizedRequestException(String errorMessage) {
        super(errorMessage);
    }
}
