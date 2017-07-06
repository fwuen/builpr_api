package com.builpr.restapi.error.response;

import java.util.Map;

/**
 *@author Marco Geißler
 */
public interface MappableError {
    Map<Integer, String> toMap();
}
