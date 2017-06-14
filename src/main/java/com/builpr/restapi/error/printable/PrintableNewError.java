package com.builpr.restapi.error.printable;

import com.builpr.restapi.error.response.MappableError;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 *
 */
public enum PrintableNewError implements MappableError {
    TITLE_INVALID(1, "Title is invalid"),
    DESCRIPTION_INVALID(2, "The description is invalid"),
    CATEGORIES_INVALID(3, "The categories are invalid"),
    FILE_INVALID(4, "The file is invalid"),
    USER_INVALID(5, "User is invalid");

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

    public Map<Integer, String> toMap() {
        Map<Integer, String> map = Maps.newHashMap();
        map.put(code, description);

        return map;
    }
}

