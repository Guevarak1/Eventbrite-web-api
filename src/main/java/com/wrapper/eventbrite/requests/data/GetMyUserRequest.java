package com.wrapper.eventbrite.requests.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wrapper.eventbrite.exceptions.EventbriteWebApiException;
import com.wrapper.eventbrite.model_objects.spec.User;

import java.io.IOException;

@JsonDeserialize(builder = GetMyUserRequest.Builder.class)
public class GetMyUserRequest extends AbstractDataRequest<User> {

    private GetMyUserRequest(final Builder builder) { super (builder); }

    public User execute() throws IOException, EventbriteWebApiException {
        String jsonString = getJson();
        return new User.JsonUtil().createModelObject(jsonString);
    }

    public static final class Builder extends AbstractDataRequest.Builder<User, Builder> {

        public Builder(final String accessToken) { super(accessToken); }

        @Override
        public GetMyUserRequest build(){
            setPath("/v3/users/me");
            return new GetMyUserRequest(this);
        }
    }
}
