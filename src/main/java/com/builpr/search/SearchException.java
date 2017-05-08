package com.builpr.search;

import lombok.Getter;
import lombok.NonNull;

public class SearchException extends Exception {

    @Getter
    private Exception exception;

    public SearchException(@NonNull Exception exception) {
        this.exception = exception;
    }

}
