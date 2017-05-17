package com.builpr.search;

import lombok.Getter;
import lombok.NonNull;

/**
 * Provides ability to create custom Exceptions for searching matters
 */
public class SearchManagerException extends Exception {

    @Getter
    private Exception exception;

    /**
     * Creates a SearchManagerException-object
     * @param exception Exception-object that should be stored in the SearchManagerException-object
     */
    public SearchManagerException(@NonNull Exception exception) {
        this.exception = exception;
    }

}
