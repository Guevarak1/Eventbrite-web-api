package com.wrapper.eventbrite;

import com.wrapper.eventbrite.model_objects.spec.Email;
import com.wrapper.eventbrite.model_objects.spec.User;
import com.wrapper.eventbrite.requests.data.GetMyUserRequest;

public class Main {
    public static final String ACCESS_TOKEN = "your access token";

    public static void main(String[] args) {

        System.out.println("hello events!");

        EventbriteApi api = EventbriteApi
                .builder()
                .setAccessToken(ACCESS_TOKEN)
                .build();

        GetMyUserRequest userRequest = api.getMyUser().build();

        try {
            User userResponse = userRequest.execute();
            System.out.println("userId: " + userResponse.getId());
            for (Email email : userResponse.getEmails()) {
                System.out.println("user email: " + email.getEmail());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
