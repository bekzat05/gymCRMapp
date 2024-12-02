package com.aitbekov.gym.service.map;

import com.aitbekov.gym.dto.*;
import com.aitbekov.gym.exceptions.UserNotFoundException;
import com.aitbekov.gym.model.Role;
import com.aitbekov.gym.model.Trainee;
import com.aitbekov.gym.model.Trainer;
import com.aitbekov.gym.model.User;
import com.aitbekov.gym.repository.UserRepository;
import com.aitbekov.gym.security.config.JwtService;
import com.aitbekov.gym.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Random;

@Service
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceMap implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public String generateUsername(String firstName, String lastName) {
        log.info("Generating username for {} {}", firstName, lastName);
        String baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String finalUsername = baseUsername;
        int suffix = 1;

        while (userRepository.existsByUsername(finalUsername)) {
            finalUsername = baseUsername + suffix;
            suffix++;
        }

        log.info("Generated username: {}", finalUsername);
        return finalUsername;
    }

    public String generateRandomPassword() {
        log.info("Generating random password");
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        String generatedPassword = password.toString();
        log.info("Generated random password: {}", generatedPassword);
        return generatedPassword;
    }


    @Override
    @Transactional
    public RegistrationResponse registerTrainee(TraineeRegistrationDto traineeRegistrationDto) {
        log.info("Registering trainee: {}", traineeRegistrationDto);
        Trainee user = new Trainee();
        String password = generateRandomPassword();
        user.setFirstName(traineeRegistrationDto.firstName());
        user.setLastName(traineeRegistrationDto.lastName());
        user.setUsername(generateUsername(traineeRegistrationDto.firstName(), traineeRegistrationDto.lastName()));
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        user.setAddress(traineeRegistrationDto.address());
        user.setDateOfBirth(traineeRegistrationDto.dateOfBirth());
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        log.info("Trainee registered successfully: {}", user.getUsername());
        return RegistrationResponse.builder()
                .token(token)
                .password(password)
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try {
            User user = userRepository.findByUsername(authenticationRequest.username())
                    .orElseThrow(() -> {
                        log.error("User not found: {}", authenticationRequest.username());
                        return new UserNotFoundException(authenticationRequest.username());
                    });
            log.info("Authenticating user: {}", authenticationRequest.username());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.username(),
                            authenticationRequest.password()
                    )
            );

            String token = jwtService.generateToken(user);
            log.info("User authenticated successfully: {}", authenticationRequest.username());
            return AuthenticationResponse.builder()
                    .token(token)
                    .build();
        } catch (AuthenticationException e) {
            throw new com.aitbekov.gym.exceptions.AuthenticationException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public RegistrationResponse registerTrainer(TrainerRegistrationDto trainerRegistrationDto) {
        log.info("Registering trainer: {}", trainerRegistrationDto);
        Trainer user = new Trainer();
        String password = generateRandomPassword();
        user.setFirstName(trainerRegistrationDto.firstName());
        user.setLastName(trainerRegistrationDto.lastName());
        user.setUsername(generateUsername(trainerRegistrationDto.firstName(), trainerRegistrationDto.lastName()));
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        log.info("Trainer registered successfully: {}", user.getUsername());
        return RegistrationResponse.builder()
                .token(token)
                .password(password)
                .build();
    }

    @Override
    public void logout(String token) {
        jwtService.invalidateToken(token);
    }
}
