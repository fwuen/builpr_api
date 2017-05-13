package com.builpr.search;

import lombok.Getter;
import lombok.NonNull;

public class IndexException extends Exception {

    @Getter
    private Exception exception;

    public IndexException(@NonNull Exception exception) {
        this.exception = exception;
    }
}
