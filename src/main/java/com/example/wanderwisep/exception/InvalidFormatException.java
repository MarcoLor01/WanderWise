package com.example.wanderwisep.exception;

public class InvalidFormatException extends Exception{

    private static final long serialVersionUID = 1L;

    public InvalidFormatException(String message){
        super(message);
    }

    public InvalidFormatException(Throwable cause){
        super(cause);
    }

    public InvalidFormatException(String message, Throwable cause) {
        super(message,cause);
    }


}
