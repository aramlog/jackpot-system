package com.sportygroup.jackpotsystem.core.exception;

import java.time.Instant;

public record ErrorResponse(
        String message,
        String error,
        int status,
        Instant timestamp
) {
    public static ErrorResponse of(JackpotException exception, int status) {
        return new ErrorResponse(
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                status,
                Instant.now()
        );
    }

    public static ErrorResponse of(String message, String error, int status) {
        return new ErrorResponse(
                message,
                error,
                status,
                Instant.now()
        );
    }
}

