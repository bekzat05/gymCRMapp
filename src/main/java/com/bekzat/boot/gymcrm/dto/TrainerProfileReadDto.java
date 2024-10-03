package com.bekzat.boot.gymcrm.dto;

import com.bekzat.boot.gymcrm.model.TrainingType;

import java.util.List;

public record TrainerProfileReadDto(String username,
                                    String firstName,
                                    String lastName,
                                    List<TrainingType.Type> specialization,
                                    List<TraineeReadDto> traineesList) {
}