package com.aitbekov.gym.exceptions;

public class TraineeNotFoundException extends NotFoundException {
    public TraineeNotFoundException(Long id) {
        super("Trainee with id " + id + " not found");
    }

    public TraineeNotFoundException(String username) {
        super("Trainee with username " + username + " not found");
    }

}
