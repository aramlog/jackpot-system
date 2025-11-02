package com.sportygroup.jackpotsystem.core.exception;

/**
 * Exception thrown when an internal server error occurs.
 */
public class InternalServerException extends JackpotException {

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

