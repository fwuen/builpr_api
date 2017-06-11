package com.builpr.restapi.error.response.account.printable;

/**
 * Error-enum for the request /printable/edit
 */
public enum PrintableEditError {
    USER_INVALID(1, "Invalid user"),
    PRINTABLE_NOT_EXISTING(2, "The printable is not exisiting"),
    TITLE_INVALID(3, "The title is invalid"),
    DESCRIPTION_INVALID(4, "The description is too long"),
    CATEGORIES_INVALID(5, "The categories are invalid"),
    NO_AUTHORIZATION(6, "No authorization to edit the printable");


    private final int code;
    private final String description;

    private PrintableEditError(int code, String description) {
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
