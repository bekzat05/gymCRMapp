package com.bekzat.gym.dto;

import com.bekzat.gym.model.TrainingType;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record TrainerCreateDto (
        @NotBlank(message = "First name must not be blank")
        String firstName,

        @NotBlank(message = "Last name must not be blank")
        String lastName,
        List<TrainingType.Type> specializations,
        Boolean isActive){
}
