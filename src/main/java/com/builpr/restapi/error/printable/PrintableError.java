package com.builpr.restapi.error.printable;

import com.builpr.restapi.error.response.MappableError;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Markus Goller
 *
 * Error-enum for the request /printable
 */
public enum PrintableError implements MappableError {

    PRINTABLE_NOT_FOUND(1, "Printable coould not be found");
    private final int code;
    private final String description;

    private PrintableError(int code, String description) {
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

