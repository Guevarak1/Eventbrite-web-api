package com.wrapper.eventbrite.exceptions;

import org.apache.http.HttpException;

public class EventbriteWebApiException extends HttpException {
    public EventbriteWebApiException() {
        super();
    }

    public EventbriteWebApiException(String message) {
        super(message);
    }

    public EventbriteWebApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
