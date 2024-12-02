package com.aitbekov.gym.exceptions;

public class TrainerNotFoundException extends NotFoundException {
    public TrainerNotFoundException(Long id) {
        super("Trainer with id " + id + " not found");
    }

    public TrainerNotFoundException(String username) {
        super("Trainer with username " + username + " not found");
    }
}
