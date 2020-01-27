package com.wrapper.eventbrite;

import com.wrapper.eventbrite.exceptions.EventbriteWebApiException;
import org.apache.http.Header;

import java.io.IOException;
import java.net.URI;

public interface IHttpManager {

    String get(URI uri, Header[] headers) throws IOException, EventbriteWebApiException;
}
