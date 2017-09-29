package com.skyfenko.core.exception.impl;

import com.skyfenko.core.exception.ApplicationException;

/**
 * Thrown when we cannot find authenticated user in security context
 *
 * @author Stanislav Kyfenko
 */
public class UnableToFetchUserException extends ApplicationException {
    public UnableToFetchUserException(String message) {
        super(message);
    }
}
