package com.example.demo.exceptions;

public record ExceptionNotFoundResponse(
        int status,
        String error,
        String message,
        String path) {
}
