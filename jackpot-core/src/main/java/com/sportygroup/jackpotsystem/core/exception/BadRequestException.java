package com.sportygroup.jackpotsystem.core.exception;

/**
 * Exception thrown when a bad request is made.
 */
public class BadRequestException extends JackpotException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}

