package com.wrapper.eventbrite.model_objects.spec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.gson.JsonObject;
import com.wrapper.eventbrite.model_objects.AbstractModelObject;

@JsonDeserialize(builder = User.Builder.class)
public class User extends AbstractModelObject {

    private final String id;
    private final String name;
    private final String firstName;
    private final String lastName;
    private final Boolean isPublic;
    private final Email[] emails;

    private User(final Builder builder) {
        super(builder);

        this.id = builder.id;
        this.name = builder.name;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.isPublic = builder.isPublic;
        this.emails = builder.emails;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public Email[] getEmails() {
        return emails;
    }

    @Override
    public Builder builder() {
        return new Builder();
    }

    public static final class Builder extends AbstractModelObject.Builder {

        private String id;
        private String name;
        private String firstName;
        private String lastName;
        private Boolean isPublic;
        private Email[] emails;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setIsPublic(Boolean isPublic) {
            this.isPublic = isPublic;
            return this;
        }

        public Builder setEmails(Email[] emails) {
            this.emails = emails;
            return this;
        }

        @Override
        public User build() {
            return new User(this);
        }
    }

    public static final class JsonUtil extends AbstractModelObject.JsonUtil<User> {

        @Override
        public User createModelObject(JsonObject jsonObject) {
            if (jsonObject == null || jsonObject.isJsonNull()) return null;

            return new User.Builder()
                    .setId(hasAndNotNull(jsonObject, "id")
                            ? jsonObject.get("id").getAsString()
                            : null)
                    .setName(hasAndNotNull(jsonObject, "name")
                            ? jsonObject.get("name").getAsString()
                            : null)
                    .setFirstName(hasAndNotNull(jsonObject, "first_name")
                            ? jsonObject.get("first_name").getAsString() : null)
                    .setLastName(hasAndNotNull(jsonObject, "last_name")
                            ? jsonObject.get("last_name").getAsString()
                            : null)
                    .setIsPublic(hasAndNotNull(jsonObject, "is_public")
                            ? jsonObject.get("is_public").getAsBoolean()
                            : null)
                    .setEmails(hasAndNotNull(jsonObject, "emails")
                            ? new Email.JsonUtil().createModelObjectArray(
                            jsonObject.getAsJsonArray("emails"))
                            : null)
                    .build();
        }
    }
}
