package com.skyfenko.core.exception;

/**
 * Umbrella exception for the whole hierarchy of exceptions
 *
 * @author Stanislav Kyfenko
 */
public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
}
