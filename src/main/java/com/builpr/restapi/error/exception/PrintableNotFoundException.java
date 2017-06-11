package com.builpr.restapi.error.exception;

/**
 * Exception if an printable can not be found
 */
public class PrintableNotFoundException extends Exception {

    public PrintableNotFoundException() {
        super();
    }

    public PrintableNotFoundException(String message) {
        super(message);
    }
}
