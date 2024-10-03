package com.bekzat.boot.gymcrm.exceptions;

public record ExceptionNotFoundResponse(
        int status,
        String error,
        String message,
        String path) {
}
