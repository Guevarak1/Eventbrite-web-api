package com.wrapper.eventbrite.model_objects.spec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.JsonObject;
import com.wrapper.eventbrite.model_objects.AbstractModelObject;

@JsonDeserialize(builder = Email.Builder.class)
public class Email extends AbstractModelObject {
    private final String email;
    private final Boolean verified;
    private final Boolean primary;

    private Email(final Builder builder) {
        super(builder);

        this.email = builder.email;
        this.verified = builder.verified;
        this.primary = builder.primary;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getVerified() {
        return verified;
    }

    public Boolean getPrimary() {
        return primary;
    }

    @Override
    public Builder builder() {
        return new Builder();
    }

    public static final class Builder extends AbstractModelObject.Builder {
        private String email;
        private Boolean verified;
        private Boolean primary;

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setVerified(Boolean verified) {
            this.verified = verified;
            return this;
        }

        public Builder setPrimary(Boolean primary) {
            this.primary = primary;
            return this;
        }

        @Override
        public Email build() {
            return new Email(this);
        }
    }

    public static final class JsonUtil extends AbstractModelObject.JsonUtil<Email> {

        @Override
        public Email createModelObject(JsonObject jsonObject) {
            if (jsonObject == null || jsonObject.isJsonNull()) return null;

            return new Email.Builder()
                    .setEmail(hasAndNotNull(jsonObject, "email")
                            ? jsonObject.get("email").getAsString() : null)
                    .setVerified(hasAndNotNull(jsonObject, "verified")
                            ? jsonObject.get("verified").getAsBoolean() : null)
                    .setPrimary(hasAndNotNull(jsonObject, "primary")
                            ? jsonObject.get("primary").getAsBoolean() : null)
                    .build();
        }
    }
}
