package com.builpr.restapi.error.Rating;

import com.builpr.restapi.error.response.MappableError;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 *
 */
public enum RatingNewError implements MappableError {
    RATING_INVALID(1, "The given rating is invalid"),
    PRINTABLE_NOT_EXISTING(2, "The printable is not exisiting"),
    TEXT_INVALID(3, "Text invalid"),
    NO_AUTHORIZATION(4, "No authorization"),
    USER_INVALID(5, "User invalid"),
    RATING_ALREADY_EXISTS(6, "There is already a rating for this printable");

    private final int code;
    private final String description;

    RatingNewError(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    public Map<Integer, String> toMap() {
        Map<Integer, String> map = Maps.newHashMap();
        map.put(code, description);

        return map;
    }
}