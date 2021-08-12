package com.revature.projectZero.util.exceptions;

/**
 * Occurs whenever a user makes a request that cannot properly be completed by the app.
 */
public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
