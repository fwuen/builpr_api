package com.builpr.restapi.error.printable;

import com.builpr.restapi.error.response.MappableError;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author Markus Goller
 *
 * PrintableDownloadError
 */
public enum PrintableDownloadError implements MappableError {
    PRINTABLE_ID_INVALID(1, "The ID is invalid"),
    DOWNLOAD_FAILED(2, "The download failed");

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

    public Map<Integer, String> toMap() {
        Map<Integer, String> map = Maps.newHashMap();
        map.put(code, description);

        return map;
    }
}
