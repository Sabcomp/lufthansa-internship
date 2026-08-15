package de.lhind.internship.mini.project.exception;

public class RoomCapacityExceededException extends RuntimeException {
    public RoomCapacityExceededException(String message) {
        super(message);
    }
}
