package org.internship.task_management.exception;

public class InvalidProjectNameException extends RuntimeException {
    public InvalidProjectNameException(String message) {
        super(message);
    }
}
