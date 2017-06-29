package com.builpr.restapi.error.search;

import com.builpr.restapi.error.response.MappableError;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Error-enum for search
 */
public enum SearchError implements MappableError {
    INVALID_QUERY(1, "Query is invalid"),
    INVALID_RATING_FILTER(2, "Value of ratingfilter invalid"),
    INVALID_ORDER(3, "Value of order invalid"),
    INVALID_SORT(4, "Value of sort invalid");

    private final int code;
    private final String description;

    SearchError(int code, String description) {
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
