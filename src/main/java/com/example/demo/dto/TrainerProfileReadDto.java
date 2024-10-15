package com.example.demo.dto;

import com.example.demo.model.TrainingType;

import java.util.List;

public record TrainerProfileReadDto(String username,
                                    String firstName,
                                    String lastName,
                                    List<TrainingType.Type> specialization,
                                    List<TraineeReadDto> traineesList) {
}
