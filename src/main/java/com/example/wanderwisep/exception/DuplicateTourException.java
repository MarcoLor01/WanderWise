package com.example.wanderwisep.exception;

public class DuplicateTourException extends Exception {

    public DuplicateTourException(String message) {
        super(message);
    }

    public DuplicateTourException(Throwable cause) {
        super(cause);
    }

    public DuplicateTourException(String message, Throwable cause) {
        super(message, cause);
    }

}
