package com.example.wanderwisep.exception;

public class TouristGuideNotFoundException extends Exception {
    public TouristGuideNotFoundException(String message) {
        super(message);
    }

    public TouristGuideNotFoundException(Throwable cause) {
        super(cause);
    }

    public TouristGuideNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
