package com.bekzat.gym.exceptions;

public class TrainerNotFoundException extends NotFoundException {
    public TrainerNotFoundException(Long id) {
        super("Trainer with id " + id + " not found");
    }
}
