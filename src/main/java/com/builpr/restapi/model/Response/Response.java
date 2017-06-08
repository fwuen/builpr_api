package com.builpr.restapi.model.Response;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

/**
 * basic response
 */
public class Response<payloadType, errorType> {

    @Setter
    @Getter
    private boolean success = true;

    @Setter
    @Getter
    private payloadType payload;

    @Setter
    @Getter
    private List<errorType> errorList = Lists.newArrayList();

    public Response() { }

    public Response(@NonNull payloadType payload) {
        this.setPayload(payload);
    }

    public void addError(@NonNull errorType error) {
        errorList.add(error);
    }

}
