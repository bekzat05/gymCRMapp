package com.bekzat.boot.gymcrm.dto;

import com.bekzat.boot.gymcrm.model.TrainingType;

import java.util.List;

public record TrainerReadDto(Long id,
                             String username,
                             String firstName,
                             String lastName,
                             List<TrainingType.Type> specialization
) {

}
