package com.builpr.restapi.model.Response;

import lombok.NonNull;

/**
 * @author Dominic Fuchs
 */
public class GravatarResponse extends Response<String> {

    public GravatarResponse() {
        super();
    }

    public GravatarResponse(@NonNull String payload) {
        super(payload);
    }

}
