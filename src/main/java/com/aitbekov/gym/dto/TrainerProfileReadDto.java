package com.aitbekov.gym.dto;

import com.aitbekov.gym.model.TrainingType;

import java.util.List;

public record TrainerProfileReadDto(
        String username,
        String firstName,
        String lastName,
        List<TrainingType.Type> specializations,
        List<TraineeReadDto> traineesList
) {
}
