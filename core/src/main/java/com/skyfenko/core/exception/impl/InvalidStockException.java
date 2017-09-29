package com.skyfenko.core.exception.impl;

import com.skyfenko.core.exception.ApplicationException;

/**
 * Thrown when stock is invalid (mostly id is null)
 *
 * @author Stanislav Kyfenko
 */
public class InvalidStockException extends ApplicationException {
    public InvalidStockException(String message) {
        super(message);
    }
}
