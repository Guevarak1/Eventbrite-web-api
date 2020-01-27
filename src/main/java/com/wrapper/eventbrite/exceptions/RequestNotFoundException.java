package com.wrapper.eventbrite.exceptions;

public class RequestNotFoundException extends EventbriteWebApiException {
    public RequestNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
