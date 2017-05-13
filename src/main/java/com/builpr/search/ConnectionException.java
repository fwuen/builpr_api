package com.builpr.search;

import lombok.Getter;
import lombok.NonNull;

public class ConnectionException extends Exception {

    @Getter
    private Exception exception;

    public ConnectionException(@NonNull Exception exception) {
        this.exception = exception;
    }
}
