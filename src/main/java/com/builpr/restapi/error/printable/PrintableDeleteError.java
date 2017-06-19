package com.builpr.restapi.error.printable;

import com.builpr.restapi.error.response.MappableError;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * PrintableDeleteError
 */
public enum PrintableDeleteError implements MappableError {
    PRINTABLE_NOT_EXISTING(1, "The printable is not exisiting"),
    NO_AUTHORIZATION(2, "No authorization to delete the printable");

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

    public Map<Integer, String> toMap() {
        Map<Integer, String> map = Maps.newHashMap();
        map.put(code, description);

        return map;
    }
}

