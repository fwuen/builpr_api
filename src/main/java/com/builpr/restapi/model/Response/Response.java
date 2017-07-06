package com.builpr.restapi.model.Response;

import com.builpr.restapi.error.response.MappableError;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Geißler, Markus Goller
 *
 * basic response
 */
public class Response<payloadType> {

    @Setter
    @Getter
    private boolean success = true;

    @Setter
    @Getter
    private payloadType payload;

    @Setter
    @Getter
    private Map<Integer, String> errorMap = new HashMap<>();

    public Response() { }

    public Response(@NonNull payloadType payload) {
        this.setPayload(payload);
    }

    public void addError(MappableError error) {
        errorMap.putAll(error.toMap());
    }

}
