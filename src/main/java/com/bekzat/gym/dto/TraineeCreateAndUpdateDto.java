package com.bekzat.gym.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

public record TraineeCreateAndUpdateDto(
        @NotNull
        String username,
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        String address,
        Date dateOfBirth,
        Boolean isActive) {
}
