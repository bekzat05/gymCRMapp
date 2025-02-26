package com.aitbekov.gym.dto;

import lombok.Builder;

@Builder
public record RegistrationResponse(
        String token,
        String password
) {
}
