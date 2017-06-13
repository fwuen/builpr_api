package com.builpr.restapi.error.printable;

import com.builpr.restapi.error.response.MappableError;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Error-enum for the request /printable/edit
 */
public enum PrintableEditError implements MappableError {
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

    public Map<Integer, String> toMap() {
        Map<Integer, String> map = Maps.newHashMap();
        map.put(code, description);

        return map;
    }
}
