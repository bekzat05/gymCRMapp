package com.example.demo.service;

import com.example.demo.dto.*;

public interface AuthenticationService {
    RegistrationResponse registerTrainee(TraineeRegistrationDto request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    RegistrationResponse registerTrainer(TrainerRegistrationDto request);

    void logout(String token);
}
