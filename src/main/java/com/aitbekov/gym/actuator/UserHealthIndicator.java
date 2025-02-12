package com.aitbekov.gym.actuator;

import com.aitbekov.gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class UserHealthIndicator implements HealthIndicator {

    private final UserRepository userRepository;

    @Autowired
    public UserHealthIndicator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Health health() {
        if(userRepository.findAll().size() > 4) {
            return new Health.Builder().up().withDetail("users", "we have more than 4 users in our database").build();
        } else {
            return new Health.Builder().down().withDetail("users", "we have less than 4 users in our database").build();
        }
    }
}
