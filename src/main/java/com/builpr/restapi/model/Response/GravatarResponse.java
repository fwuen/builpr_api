package com.builpr.restapi.model.Response;

import lombok.NonNull;

public class GravatarResponse extends Response<String> {

    public GravatarResponse() {
        super();
    }

    public GravatarResponse(@NonNull String payload) {
        super(payload);
    }

}
