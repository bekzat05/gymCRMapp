package com.bekzat.gym.exceptions;

public class TrainingNotFoundException extends NotFoundException {
    public TrainingNotFoundException(Long id) {
        super("Training with id " + id + " not found");
    }
}
