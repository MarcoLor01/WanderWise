package com.example.wanderwisep.exception;

public class RequestNotFoundException extends Exception {

    public RequestNotFoundException(String message) {
        super(message);
    }

    public RequestNotFoundException(Throwable cause) {
        super(cause);
    }

    public RequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
