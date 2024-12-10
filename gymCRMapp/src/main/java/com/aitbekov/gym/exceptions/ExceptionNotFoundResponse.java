package com.aitbekov.gym.exceptions;

public record ExceptionNotFoundResponse(
        int status,
        String error,
        String message,
        String path) {
}
