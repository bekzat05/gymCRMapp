package com.bekzat.boot.gymcrm.dto;

import com.bekzat.boot.gymcrm.model.TrainingType;

public record TrainingCreateDto(
        String traineeUsername,
        String trainerUsername,
        String name,
        TrainingType.Type type,
        int duration
) {
}
