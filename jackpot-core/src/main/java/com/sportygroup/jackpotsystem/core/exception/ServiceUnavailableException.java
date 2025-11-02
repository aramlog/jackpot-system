package com.sportygroup.jackpotsystem.core.exception;

/**
 * Exception thrown when a service is unavailable (e.g., external API call failed).
 */
public class ServiceUnavailableException extends JackpotException {

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}

