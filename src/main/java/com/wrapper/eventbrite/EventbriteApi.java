package com.wrapper.eventbrite;

import com.wrapper.eventbrite.requests.data.RetrieveCurrentUserRequest;
import com.wrapper.eventbrite.requests.data.RetrieveUserRequest;

import java.util.logging.Logger;

public class EventbriteApi {

    public static final Logger LOGGER = Logger.getLogger(EventbriteApi.class.getName());

    public static final EventbriteHttpManager DEFAULT_HTTP_MANAGER = new EventbriteHttpManager();

    public static final int DEFAULT_PORT = 443;

    public static final String DEFAULT_SCHEME = "https";

    public static final String DEFAULT_HOST = "www.eventbriteapi.com";

    private final IHttpManager httpManager;
    private final String scheme;
    private final String host;
    private final Integer port;
    private String accessToken;

    public EventbriteApi(Builder builder) {

        this.httpManager = builder.httpManager;
        this.scheme = builder.scheme;
        this.host = builder.host;
        this.port = builder.port;
        this.accessToken = builder.accessToken;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    //Requests
    public RetrieveCurrentUserRequest.Builder retrieveCurrentUser(){
        return new RetrieveCurrentUserRequest.Builder(accessToken)
                .setDefaults(httpManager, scheme, host, port);
    }

    public RetrieveUserRequest.Builder retrieveUser(String userId){
        return new RetrieveUserRequest.Builder(accessToken)
                .userId(userId)
                .setDefaults(httpManager, scheme, host, port);
    }

    public static class Builder {

        private IHttpManager httpManager = DEFAULT_HTTP_MANAGER;
        private String scheme = DEFAULT_SCHEME;
        private String host = DEFAULT_HOST;
        private int port = DEFAULT_PORT;
        private String accessToken;

        public Builder setAccessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public EventbriteApi build() {
            return new EventbriteApi(this);
        }
    }

}
