# Eventbrite-web-api

Usage: 

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
