package org.internship.task_management.exception;

public class InvalidTaskArgumentException extends RuntimeException {
    public InvalidTaskArgumentException(String message) {
        super(message);
    }
}
