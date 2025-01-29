package com.aitbekov.gym.dto;

import com.aitbekov.gym.model.TrainingType;

import java.util.List;

public record TrainerReadDto(
        Long id,
        String username,
        String firstName,
        String lastName,
        List<TrainingType.Type> specializations
) {
}
