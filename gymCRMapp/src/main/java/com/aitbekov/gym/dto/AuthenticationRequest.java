package com.aitbekov.gym.dto;

public record AuthenticationRequest(
        String username,
        String password
){
}
