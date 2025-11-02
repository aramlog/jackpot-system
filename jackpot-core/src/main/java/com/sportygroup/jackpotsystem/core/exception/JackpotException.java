package com.sportygroup.jackpotsystem.core.exception;

/**
 * Base exception for all jackpot system exceptions.
 */
public abstract class JackpotException extends RuntimeException {

    public JackpotException(String message) {
        super(message);
    }

    public JackpotException(String message, Throwable cause) {
        super(message, cause);
    }
}

