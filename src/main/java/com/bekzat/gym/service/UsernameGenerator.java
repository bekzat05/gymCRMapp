package com.bekzat.gym.service;

import com.bekzat.gym.model.User;

public class UsernameGenerator {

    private User user;

    public UsernameGenerator(User user) {
        this.user = user;
    }

    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        String finalUsername = baseUsername;
        int suffix = 1;



        while (User.getExistingUsernames().contains(finalUsername)) {
            finalUsername = baseUsername + suffix;
            suffix++;
        }

        User.getExistingUsernames().add(finalUsername);

        return finalUsername;
    }
}
