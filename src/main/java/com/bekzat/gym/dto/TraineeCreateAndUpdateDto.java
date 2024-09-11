package com.bekzat.gym.dto;

import java.util.Date;

public record TraineeCreateAndUpdateDto(String firstName,
                                        String lastName,
                                        String address,
                                        Date dateOfBirth,
                                        Boolean isActive) {
}
