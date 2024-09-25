package com.bekzat.gym.dto;

import com.bekzat.gym.model.TrainingType;

public record TrainingCreateDto(
        String traineeUsername,
        String trainerUsername,
        String name,
        TrainingType.Type type,
        int duration
) {
}
