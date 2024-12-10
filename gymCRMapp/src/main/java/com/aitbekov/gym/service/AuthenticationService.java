package com.aitbekov.gym.service;

import com.aitbekov.gym.dto.*;

public interface AuthenticationService {
    RegistrationResponse registerTrainee(TraineeRegistrationDto traineeRegistrationDto);

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    RegistrationResponse registerTrainer(TrainerRegistrationDto trainerRegistrationDto);

    void logout(String token);
}
