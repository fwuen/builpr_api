package com.builpr.restapi.exception;


public class InvalidModelIdException extends Exception {

    public InvalidModelIdException(String message) {
        super(message);
    }

    public InvalidModelIdException() {
        super();
    }
}
