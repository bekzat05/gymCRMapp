package com.bekzat.gym.model;

import com.bekzat.gym.service.PasswordGenerator;
import com.bekzat.gym.service.UsernameGenerator;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Data
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String username;

    public static Set<String> getExistingUsernames() {
        return existingUsernames;
    }

    public static void setExistingUsernames(Set<String> existingUsernames) {
        User.existingUsernames = existingUsernames;
    }

    private String password;
    private static Set<String> existingUsernames = new HashSet<>();

    public void createProfile() {
        UsernameGenerator usernameGenerator = new UsernameGenerator(this);
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        this.username = usernameGenerator.generateUsername(firstName, lastName);
        this.password = passwordGenerator.generateRandomPassword(10);
    }
}
