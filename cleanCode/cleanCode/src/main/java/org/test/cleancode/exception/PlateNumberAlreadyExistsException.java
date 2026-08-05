package org.test.cleancode.exception;

public class PlateNumberAlreadyExistsException extends RuntimeException {
    public PlateNumberAlreadyExistsException(String message) {
        super(message);
    }
}
