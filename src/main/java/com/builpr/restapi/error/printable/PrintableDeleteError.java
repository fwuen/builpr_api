package com.builpr.restapi.error.printable;

/**
 *
 */
public enum PrintableDeleteError {
    USER_INVALID(1, "Invalid user"),
    PRINTABLE_NOT_EXISTING(2, "The printable is not exisiting"),
    NO_AUTHORIZATION(3, "No authorization to delete the printable");

    private final int code;
    private final String description;

    private PrintableDeleteError(int code, String description) {
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

