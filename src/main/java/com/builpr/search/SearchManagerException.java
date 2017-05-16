package com.builpr.search;

import lombok.Getter;
import lombok.NonNull;

public class SearchManagerException extends Exception {

    @Getter
    private Exception exception;

    /**
     *
     * @param exception
     */
    public SearchManagerException(@NonNull Exception exception) {
        this.exception = exception;
    }

}
