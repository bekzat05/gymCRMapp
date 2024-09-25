package com.bekzat.gym.service.map;

import com.bekzat.gym.dao.UserRepository;
import com.bekzat.gym.dto.CredentialsDto;
import com.bekzat.gym.exceptions.AuthenticationException;
import com.bekzat.gym.exceptions.UserNotFoundException;
import com.bekzat.gym.model.User;
import com.bekzat.gym.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class UserCredentialsService implements UserService {

    private final UserRepository userRepository;

    @Transactional
    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int suffix = 1;

        while (userRepository.existsByUsername(username)) {
            username = baseUsername + suffix;
            suffix++;
        }

        return username;
    }

    public String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    @Transactional

    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId.toString()));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public boolean checkCredentials(CredentialsDto credentialsDto) {
        return userRepository.login(credentialsDto);
    }

    @Override
    @Transactional
    public boolean login(CredentialsDto credentialsDto) {
        return userRepository.login(credentialsDto);
    }

    @Override
    @Transactional
    public boolean changePassword(CredentialsDto credentialsDto, String newPassword) {
        if (checkCredentials(credentialsDto)) {
            User user = userRepository.findByUsername(credentialsDto.username())
                    .orElseThrow(() -> new AuthenticationException("Incorrect credentials"));
            user.setPassword(newPassword);
            return true;
        } else return false;
    }
}
