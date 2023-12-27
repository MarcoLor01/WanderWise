package com.example.wanderwisep.exception;

public class TourException extends Exception {
    public TourException(String message) {
        super(message);
    }

    public TourException(Throwable cause) {
        super(cause);
    }

    public TourException(String message, Throwable cause) {
        super(message, cause);
    }
}
