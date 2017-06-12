package com.builpr.restapi.error.printable;

/**
 * Error-enum for the request /printable
 */
public enum PrintableError {

    TOKEN_INVALID(1, "The given token is invalid"),
    PRINTABLE_NOT_EXISTING(2, "The printable is not exisiting");

    private final int code;
    private final String description;

    private PrintableError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}

