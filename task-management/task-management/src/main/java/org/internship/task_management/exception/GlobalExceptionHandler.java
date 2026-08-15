package org.internship.task_management.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidProjectName(InvalidProjectArgumentException ex, HttpServletRequest request) {
        return buildError(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidTaskTitle(InvalidTaskArgumentException ex, HttpServletRequest request) {
        return buildError(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleProjectNotFound(ProjectNotFoundException ex, HttpServletRequest request) {
        return buildError(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleTaskNotFound(TaskNotFoundException ex, HttpServletRequest request) {
        return buildError(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleTaskAssignment(TaskAssignmentException ex, HttpServletRequest request) {
        return buildError(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        return buildError(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildError(Exception ex, HttpServletRequest request, HttpStatus status) {
        ErrorResponse error = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(error);
    }
}
