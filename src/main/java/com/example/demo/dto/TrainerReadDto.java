package com.example.demo.dto;

import com.example.demo.model.TrainingType;

import java.util.List;

public record TrainerReadDto(Long id,
                             String username,
                             String firstName,
                             String lastName,
                             List<TrainingType.Type> specialization
) {

}
