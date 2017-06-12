package com.builpr.restapi.error.Rating;

/**
 *
 */
public enum RatingDeleteError {
    RATING_NOT_FOUND(1, "Rating cannot be found"),
    NO_AUTHORIZATION(2, "No authorization"),
    USER_INVALID(3, "User invalid");
    private final int code;
    private final String description;

    RatingDeleteError(int code, String description) {
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