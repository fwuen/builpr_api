package com.builpr.restapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Dominic Fuchs
 */
@EqualsAndHashCode
@ToString
public class SimplePayload {

    @Getter private String payload;

    public SimplePayload(@NonNull String payload) {
        this.payload = payload;
    }

    /* Just for Jackson */
    private SimplePayload() { }

}
