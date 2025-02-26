package com.aitbekov.gym.dto;

import com.aitbekov.gym.model.TrainingType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TrainingReadDto(
        Long id,
        String name,
        LocalDateTime date,
        int duration,
        TraineeReadDto trainee,
        TrainerReadDto trainer,

        TrainingType.Type type
) {
}
