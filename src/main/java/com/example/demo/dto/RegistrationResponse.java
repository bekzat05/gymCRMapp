package com.example.demo.dto;

import lombok.Builder;

@Builder
public record RegistrationResponse(
        String token,
        String password
) {
}
