package com.builpr.restapi.error.response;

import java.util.Map;

/**
 *
 */
public interface MappableError {
    Map<Integer, String> toMap();
}
