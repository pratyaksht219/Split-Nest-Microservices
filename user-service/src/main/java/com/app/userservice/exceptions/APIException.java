package com.app.userservice.exceptions;

public class APIException extends RuntimeException{
    public static final long serialVersionUID = 1L;

    public APIException(String message) {
        super(message);
    }

    public APIException() {

    }
}
