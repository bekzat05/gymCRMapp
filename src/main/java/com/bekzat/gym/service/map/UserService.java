package com.bekzat.gym.service.map;

import com.bekzat.gym.dao.UserRepository;
import com.bekzat.gym.dto.CredentialsDto;
import com.bekzat.gym.dto.UserReadDto;
import com.bekzat.gym.exceptions.AuthenticationException;
import com.bekzat.gym.exceptions.UserNotFoundException;
import com.bekzat.gym.mapper.UserMapper;
import com.bekzat.gym.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserCredentialsService userCredentialsService;
    private final UserMapper userMapper;


    public UserReadDto findById(Long id, CredentialsDto credentialsDto) {
        if (userCredentialsService.checkCredentials(credentialsDto)) {
            throw new AuthenticationException("Invalid credentials");
        }
        log.info("Finding user by ID: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));


    }
    @Transactional
    public boolean delete(Long id, CredentialsDto credentialsDto) {
        if (userCredentialsService.checkCredentials(credentialsDto)) {
            throw new AuthenticationException("Invalid credentials");
        }
        log.info("Delete user by ID: {}", id);
        Optional<User> maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(user.getId()));
        return maybeUser.isPresent();
    }
}
