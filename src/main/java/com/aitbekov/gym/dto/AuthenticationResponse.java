package com.aitbekov.gym.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token
) {
}
