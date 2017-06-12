package com.builpr.restapi.error.printable;

/**
 *
 */
public enum PrintableDownloadError {
    PRINTABLE_ID_INVALID(1, "The ID is invalid");

    private final int code;
    private final String description;

    private PrintableDownloadError(int code, String description) {
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
