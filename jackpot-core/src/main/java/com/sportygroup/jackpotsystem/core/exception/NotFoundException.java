package com.sportygroup.jackpotsystem.core.exception;

/**
 * Exception thrown when a requested resource is not found.
 */
public class NotFoundException extends JackpotException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

