package com.example.springbackend.exception;

public class UserException extends RuntimeException {
    private final String errorCode;

    public UserException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}