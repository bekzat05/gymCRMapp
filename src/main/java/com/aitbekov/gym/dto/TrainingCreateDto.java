package com.aitbekov.gym.dto;

import com.aitbekov.gym.model.TrainingType;

public record TrainingCreateDto(
        String traineeUsername,
        String trainerUsername,
        String name,
        TrainingType.Type type,
        int duration
) {
}
