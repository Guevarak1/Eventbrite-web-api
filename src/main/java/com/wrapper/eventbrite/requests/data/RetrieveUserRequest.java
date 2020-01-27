package com.wrapper.eventbrite.requests.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wrapper.eventbrite.exceptions.EventbriteWebApiException;
import com.wrapper.eventbrite.model_objects.spec.User;

import java.io.IOException;

@JsonDeserialize(builder = RetrieveUserRequest.Builder.class)
public class RetrieveUserRequest extends AbstractDataRequest<User>{

    private RetrieveUserRequest(final Builder builder) { super(builder); }

    @Override
    public User execute() throws IOException, EventbriteWebApiException {
        return new User.JsonUtil().createModelObject(getJson());
    }

    public static final class Builder extends AbstractDataRequest.Builder<User,Builder> {

        public Builder(final String accessToken) { super(accessToken); }

        public Builder userId(final String userId) {
            return setPathParameter("user_id", userId);
        }

        public RetrieveUserRequest build() {
            setPath("/v3/users/{user_id}");
            return new RetrieveUserRequest(this);
        }
    }
}
