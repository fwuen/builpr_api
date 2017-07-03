package com.builpr.restapi.error.rating;

import com.builpr.restapi.error.response.MappableError;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * RatingDeleteError
 */
public enum RatingDeleteError implements MappableError {
    RATING_NOT_FOUND(1, "rating cannot be found"),
    NO_AUTHORIZATION(2, "No authorization");

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

    public Map<Integer, String> toMap() {
        Map<Integer, String> map = Maps.newHashMap();
        map.put(code, description);

        return map;
    }
}