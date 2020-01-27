package com.wrapper.eventbrite.requests.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wrapper.eventbrite.exceptions.EventbriteWebApiException;
import com.wrapper.eventbrite.model_objects.spec.User;

import java.io.IOException;

@JsonDeserialize(builder = RetrieveCurrentUserRequest.Builder.class)
public class RetrieveCurrentUserRequest extends AbstractDataRequest<User> {

    private RetrieveCurrentUserRequest(final Builder builder) { super (builder); }

    public User execute() throws IOException, EventbriteWebApiException {
        String jsonString = getJson();
        return new User.JsonUtil().createModelObject(jsonString);
    }

    public static final class Builder extends AbstractDataRequest.Builder<User, Builder> {

        public Builder(final String accessToken) { super(accessToken); }

        @Override
        public RetrieveCurrentUserRequest build(){
            setPath("/v3/users/me");
            return new RetrieveCurrentUserRequest(this);
        }
    }
}
