package com.builpr.restapi.error.printable;

/**
 *
 */
public enum PrintableNewError {
    TITLE_INVALID(1, "Title is invalid"),
    DESCRIPTION_INVALID(2, "The description is invalid"),
    CATEGORIES_INVALID(3, "The categories are invalid"),
    FILE_INVALID(4, "The file is invalid"),
    USER_INVALID(5, "User is invalid"),
    NO_AUTHORIZATION(6, "No authorization"),
    FILE_NOT_EXISTING(7, "File not existing");

    private final int code;
    private final String description;

    private PrintableNewError(int code, String description) {
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

