package com.aitbekov.gym.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RuntimeExceptionResponse {
    private int statusCode;
    private String message;
    private String detailedMessage;
    private LocalDateTime timestamp;
}
