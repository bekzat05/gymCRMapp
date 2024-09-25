package com.bekzat.gym.exceptions;

public class TraineeNotFoundException extends NotFoundException {
    public TraineeNotFoundException(Long id) {
        super("Trainee with id " + id + " not found");
    }

}
